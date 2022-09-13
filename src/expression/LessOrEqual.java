package expression;

import token.Token;

public record LessOrEqual(Token token, Expression left, Expression right)
        implements ComparisonExpression {


    @Override
    public String toString() {
        return String.format("(%s) \u2264 (%s)", left, right);
    }
}
