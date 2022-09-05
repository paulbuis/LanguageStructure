package expression;

import environment.Environment;
import value.Bool;

import token.Token;

public record BooleanLiteral(Token token, Bool value)
        implements BooleanExpression {

    @Override
    public String toString() {
        return value.bool() ? "true" : "false";
    }

    @Override
    public Bool eval(final Environment environment) {
        return BooleanExpression.makeBool(value.equals(TRUE));
    }

}
