package ru.alexander.rcvm.data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.alexander.compilers.rcl.data.Token.TokenType.*;

public class SyntaxTree {
    private final List<SyntaxTree> branches = new ArrayList<>();

    private final Token operation;


    private static LinkedList<Token> transform(List<Token> code, AtomicInteger index, boolean wCond) {
        LinkedList<Token> notation = new LinkedList<>();
        Stack<Token> stack = new Stack<>();

        Token token = code.get(index.get());
        cycle:
        while (token.type() != Token.TokenType.CODE_DIVIDER) {
            switch (token.type()) {
                case NUMBER, INDEX_DIVIDER, VARIABLE -> notation.addLast(token);
                case INSTRUCTION, FUNCTION -> stack.push(token);
                case COMMA_DIVIDER -> {
                    try {
                        while (!stack.peek().name.equals("("))
                            notation.addLast(stack.pop());
                    } catch (Exception ex) {
                        throw new IllegalStateException("Function wrong format!");
                    }
                }
                case MATH -> {
                    Operation op = null;
                    for (Operation value : Operation.values())
                        if (value.transcription.equals(token.name)) {
                            op = value;
                            break;
                        }
                    if (op == null)
                        throw new IllegalStateException("WTF!? " + code.subList(index.get() - 10, index.get() + 10));

                    while (!stack.isEmpty() && stack.peek().type() == Token.TokenType.MATH
                            && Operation.valueOf(stack.peek().name).priority <= op.priority)
                        notation.addLast(stack.pop());

                    stack.push(new Token(op.name(), Token.TokenType.MATH));
                }
                case GROUP_DIVIDER -> {
                    if (token.name.equals("(")) {
                        stack.push(token);
                    }
                    else {
                        try {
                            while (!stack.peek().name.equals("("))
                                notation.addLast(stack.pop());
                            stack.pop();

                            if (!stack.isEmpty()
                                    && (stack.peek().type() == Token.TokenType.FUNCTION
                                    || stack.peek().type() == Token.TokenType.INSTRUCTION))
                                notation.addLast(stack.pop());
                        } catch (Exception ex) {
                            if (wCond) break cycle;
                            throw new IllegalStateException("Function wrong format!");
                        }
                    }
                }

            }

            token = code.get(index.incrementAndGet());
        }

        while (!stack.isEmpty()) notation.addLast(stack.pop());

        return notation;
    }

    public static SyntaxTree buildTree(List<Token> code, AtomicInteger index, boolean wCond) {
        return new SyntaxTree(transform(code, index, wCond));
    }

    public SyntaxTree(LinkedList<Token> code) {
        operation = code.pollLast();

        Token.TokenType type = operation.type();

        if (type != VARIABLE
                && type != NUMBER) {

            if (type == FUNCTION) {

                while (!code.isEmpty()
                        && (code.peekLast().type() == VARIABLE
                        || code.peekLast().type() == NUMBER
                        || code.peekLast().type() == INDEX_DIVIDER)) {
                    branches.add(new SyntaxTree(code));
                }
            }
            else {
                branches.add(new SyntaxTree(code));
                if (!operation.name.equals("abs")) {
                    if (type != INDEX_DIVIDER)
                        branches.add(new SyntaxTree(code));
                    else code.pollLast();
                }
            }
        }
    }

    public static String bufferedVar;
    public String build(List<Function> functions, boolean systemComments) {
        switch (operation.type()) {
            case MATH -> {
                Operation value = Operation.valueOf(operation.name);
                switch (value) {
                    case NOT -> {
                        String op = value.name().toLowerCase();

                        String code = branches.get(0).build(functions, systemComments);

                        String var = "v_" + Integer.toHexString(this.hashCode());
                        code += op + " " + var + " " + bufferedVar + "\n";
                        bufferedVar = var;

                        return code;
                    }
                    case MUL, DIV, REM, ADD, SUB, LS, RS, LT,
                            MT, LET, MET, EQ, NEQ, AND, XOR, OR -> {
                        String op = value.name().toLowerCase();

                        String code = branches.get(1).build(functions, systemComments);
                        String rVar = bufferedVar;
                        code += branches.get(0).build(functions, systemComments);

                        String var = "v_" + Integer.toHexString(this.hashCode());
                        code += op + " " + var + " " + rVar + " " + bufferedVar + "\n";
                        bufferedVar = var;

                        return code;
                    }
                }
            }
            case INSTRUCTION -> {
                switch (operation.name) {
                    case "abs" -> {
                        String code = branches.get(0).build(functions, systemComments);

                        String var = "v_" + Integer.toHexString(this.hashCode());
                        code += operation.name + " " + var + " " + bufferedVar + "\n";
                        bufferedVar = var;

                        return code;
                    }
                }
            }
            case INDEX_DIVIDER -> {
                String code = branches.get(0).build(functions, systemComments);

                String var = "v_" + Integer.toHexString(this.hashCode());
                code += "dr " + var + " " + bufferedVar + "\n";
                bufferedVar = var;

                return code;
            }
            case NUMBER -> {
                bufferedVar = "n_" + operation.name;
                return "set " + bufferedVar + " " + operation.name + "\n";
            }
            case VARIABLE -> bufferedVar = operation.name;
            case FUNCTION -> {
                Function f = functions.stream().filter((func) ->
                        operation.name.equals(func.name().name)).findAny().orElse(null);

                if (f == null)
                    throw new IllegalStateException("Function with name "
                            + operation.name + " not exists!");

                StringBuilder code = new StringBuilder();

                code.append("push rp\n");
                for (int i = 0; i < f.vars().size(); i++)
                    code.append("push ").append(f.vars().get(i).name).append("\n");

                for (SyntaxTree branch : branches) {
                    code.append(branch.build(functions, systemComments));
                    code.append("push ").append(bufferedVar).append("\n");
                }
                code.append("goto func_").append(f.name().name).append("\n");

                int shift = code.toString().replace("\n", " ").split(" ").length + 9;

                code.insert(0, "add rp rp n_" + shift + "\n");
                code.insert(0, "set n_" + shift + " " + shift + "\n");
                code.insert(0, "ptr rp\n");

                String var = "v_" + Integer.toHexString(this.hashCode());
                code.append("poll ").append(var).append("\n");

                if (systemComments) {
                    code.insert(0, "\n# Call of function " + f.name().name + "\n");
                    code.append("\n");
                }

                bufferedVar = var;
                return code.toString();
            }

        }
        return "";
    }


    @Override
    public String toString() {
        return str(0);
    }

    public String str(int layer) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(layer)).append(operation);
        for (SyntaxTree branch : branches)
            builder.append("\n").append(branch.str(layer + 1));


        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(branches, operation);
    }

    private enum Operation {
        // Unary
        NOT(0, "~"),

        // Multiplicative
        MUL(1, "*"),
        DIV(1, "/"),
        REM(1, "%"),

        // Additive
        ADD(2, "+"),
        SUB(2, "-"),

        // Shift
        LS(3, "<<"),
        RS(3, ">>"),

        // Proportional
        LT(4, "<"),
        MT(4, ">"),
        LET(4, "<="),
        MET(4, ">="),

        // Equality
        EQ(5, "=="),
        NEQ(5, "!="),

        // Logic
        AND(6, "&"),
        XOR(7, "^"),
        OR(8, "|");

        private final int priority;
        private final String transcription;


        Operation(int priority, String transcription) {
            this.priority = priority;
            this.transcription = transcription;
        }

        public int getPriority() {
            return priority;
        }

        public String getTranscription() {
            return transcription;
        }
    }
}
