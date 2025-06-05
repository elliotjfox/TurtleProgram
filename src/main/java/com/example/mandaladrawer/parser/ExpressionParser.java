package com.example.mandaladrawer.parser;

import com.example.mandaladrawer.exception.UnknownTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionParser {

    private final List<String> tokens;
    private final List<ExpressionObject> objects;
    private ExpressionTreeNode root;

    private final VariableScope variableScope;

    public ExpressionParser(VariableScope variables, List<String> tokens) {
        this.variableScope = variables;
        this.tokens = tokens;
        objects = new ArrayList<>(tokens.size());
    }

    public double parseExpression() {
        toObjects();

        createTree();

        Stack<Double> stack = new Stack<>();
        evaluate(stack, root);

        return stack.pop();
    }

    private int count(ExpressionTreeNode node) {
        if (node == null) return 0;

        return 1 + count(node.getLeft()) + count(node.getRight());
    }

    private void toObjects() {
        for (String token : tokens) {
            objects.add(toExpressionObject(token));
        }
    }

    private ExpressionObject toExpressionObject(String token) {
        return switch (token) {
            case "+" -> new AdditionOperator();
            case "-" -> new SubtractionOperator();
            case "*" -> new MultiplicationOperator();
            case "/" -> new DivisionOperator();
            default -> {
                try {
                    double value = Double.parseDouble(token);
                    yield new ExpressionDouble(value);
                } catch (NumberFormatException ignored) {}

                if (variableScope.containsKey(token)) {
                    yield new ExpressionDouble(variableScope.get(token).getValue());
                }

                throw new UnknownTokenException("Unknown token \"" + token + "\"");
            }
        };
    }

    private void createTree() {
        root = createTree(0, objects.size() - 1);
    }

    // [from, to]
    private ExpressionTreeNode createTree(int from, int to) {
        if (from > to) return null;
        ExpressionObject highest = objects.get(from);
        int highestIndex = from;
        for (int i = from; i <= to; i++) {
            if (objects.get(i).getPriority() > highest.getPriority()) {
                highest = objects.get(i);
                highestIndex = i;
            }
        }

        ExpressionTreeNode node = new ExpressionTreeNode(highest);

        node.setLeft(createTree(from, highestIndex - 1));
        node.setRight(createTree(highestIndex + 1, to));

        return node;
    }

    private void evaluate(Stack<Double> stack, ExpressionTreeNode node) {
        if (node == null) return;
        evaluate(stack, node.getLeft());
        evaluate(stack, node.getRight());

        if (node.getValue() instanceof ExpressionDouble expressionDouble) {
            stack.push(expressionDouble.getValue());
        } else if (node.getValue() instanceof ExpressionOperator operator) {
            double a = stack.pop();
            double b = stack.pop();
            double result = operator.operate(b, a);
            stack.push(result);
        }
    }

    public static abstract class ExpressionObject {
        public abstract int getPriority();
    }

    private static abstract class ExpressionOperator extends ExpressionObject {
        public abstract double operate(double val1, double val2);
    }

    private static class AdditionOperator extends ExpressionOperator {

        @Override
        public double operate(double val1, double val2) {
            return val1 + val2;
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public String toString() {
            return "+";
        }
    }

    private static class SubtractionOperator extends ExpressionOperator {

        @Override
        public double operate(double val1, double val2) {
            return val1 - val2;
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    private static class MultiplicationOperator extends ExpressionOperator {

        @Override
        public double operate(double val1, double val2) {
            return val1 * val2;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public String toString() {
            return "*";
        }
    }

    private static class DivisionOperator extends ExpressionOperator {

        @Override
        public double operate(double val1, double val2) {
            return val1 / val2;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public String toString() {
            return "/";
        }
    }

    private static abstract class ExpressionValue extends ExpressionObject {

        // Lowest priority
        @Override
        public int getPriority() {
            return 0;
        }

        public abstract double getValue();
    }

    private static class ExpressionDouble extends ExpressionValue {
        private final double value;

        public ExpressionDouble(double value) {
            this.value = value;
        }

        @Override
        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return Double.toString(value);
        }
    }


}
