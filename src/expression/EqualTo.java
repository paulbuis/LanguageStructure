package expression;

import token.Token;

public record EqualTo(Token token, Expression left, Expression right)
        implements ComparisonExpression {


    @Override
    public String toString() {
        return String.format("(%s) == (%s)", left, right);
    }
}
