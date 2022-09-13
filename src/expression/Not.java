package expression;

import token.Token;

public record Not(Token token, BooleanExpression operand) implements BooleanExpression, UnaryExpression {
    @Override
    public String toString() {
        return String.format("\\u00ac(%s)", operand);
    }
}

