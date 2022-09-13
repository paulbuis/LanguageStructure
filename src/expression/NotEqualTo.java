package expression;


import token.Token;

public record NotEqualTo(Token token, Expression left, Expression right)
        implements ComparisonExpression, Desugarable {

    @Override
    public String toString() {
        return String.format("(%s) \u2260 (%s)", left, right);
    }
}

