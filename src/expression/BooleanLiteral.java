package expression;

import value.Bool;

import token.Token;

public record BooleanLiteral(Token token, Bool value)
        implements BooleanExpression, Literal {

    @Override
    public String toString() {
        return value.bool() ? "true" : "false";
    };
}
