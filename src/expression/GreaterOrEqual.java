package expression;

import token.Token;

public record GreaterOrEqual(Token token, Expression left, Expression right)
        implements ComparisonExpression, Desugarable {

    @Override
    public String toString() {
        return String.format("(%s) \u2265 (%s)", left, right);
    }
}
