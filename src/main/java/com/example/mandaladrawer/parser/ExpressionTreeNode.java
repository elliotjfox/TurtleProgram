package com.example.mandaladrawer.parser;

public class ExpressionTreeNode {

    private ExpressionTreeNode left;
    private ExpressionTreeNode right;
    private ExpressionParser.ExpressionObject value;

    public ExpressionTreeNode(ExpressionTreeNode left, ExpressionTreeNode right, ExpressionParser.ExpressionObject value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public ExpressionTreeNode(ExpressionParser.ExpressionObject value) {
        this(null, null, value);
    }

    public ExpressionTreeNode getLeft() {
        return left;
    }

    public ExpressionParser.ExpressionObject getValue() {
        return value;
    }

    public ExpressionTreeNode getRight() {
        return right;
    }

    public void setLeft(ExpressionTreeNode left) {
        this.left = left;
    }

    public void setRight(ExpressionTreeNode right) {
        this.right = right;
    }
}
