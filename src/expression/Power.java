package expression;


import token.Token;

public record Power(Token token, Expression operand, int exponent)
        implements NumericExpression, UnaryExpression {

    @Override
    public String toString() {
        return switch (exponent) {
            case 0 -> String.format("(%s)\u2070", operand());
            case 1 -> String.format("(%s)\u00b9", operand());
            case -1 -> String.format("(%s)\u207b\u00b9", operand());
            case 2 -> String.format("(%s)\u00b2", operand());
            case -2 -> String.format("(%s)\u207b\u00b2", operand());
            case 3 -> String.format("(%s)\u00b3", operand());
            case -3 -> String.format("(%s)\u207b\u00b3", operand());

            default -> String.format("(%s)^%d", operand(), exponent);
        };
    }
}
