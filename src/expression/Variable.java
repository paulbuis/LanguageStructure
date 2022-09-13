package expression;

import token.Token;


public record Variable(Token token, String name) implements BooleanExpression, NumericExpression {

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
