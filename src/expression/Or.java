package expression;

import token.Token;

public record Or(Token token, Expression left, Expression right)
        implements BooleanExpression, BinaryExpression {

    @Override
    public String toString() {
        return String.format("(%s or %s)", left, right);
    }

}
