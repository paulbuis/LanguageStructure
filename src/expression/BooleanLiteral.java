package expression;

import environment.Environment;
import org.jetbrains.annotations.NotNull;
import value.Bool;

import token.Token;

public record BooleanLiteral(Token token, Bool value)
        implements BooleanExpression, Literal {

    @Override
    public @NotNull String toString() {
        return value.bool() ? "true" : "false";
    };
}
