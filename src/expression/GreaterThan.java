package expression;


import token.Token;

public record GreaterThan(Token token, Expression left, Expression right)
        implements ComparisonExpression, Desugarable {

    @Override
    public String toString() {
        return String.format("(%s) > (%s)", left, right);
    }
}
