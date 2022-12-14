package expression;


import token.Token;

public record Multiplication(Token token, Expression left, Expression right)
        implements NumericExpression, BinaryExpression {

    @Override
    public String toString() {
        String leftStr = switch(left) {
            case Sum leftSum -> String.format("(%s)", leftSum);
            default -> left.toString();
        };
        String rightStr = switch(right) {
            case Sum rightSum -> String.format("(%s)", rightSum);
            default -> right.toString();
        };

        return String.format("%s \u22c5 %s", leftStr, rightStr);
    }

}
