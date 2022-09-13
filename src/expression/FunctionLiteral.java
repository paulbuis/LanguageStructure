package expression;

import environment.Environment;
import org.jetbrains.annotations.NotNull;
import value.FunctionValue;
import token.Token;

import java.util.List;


public record FunctionLiteral(Token token, List<String> formalParameters, Expression body)
        implements Expression, Literal {
    public @NotNull FunctionValue eval(final Environment environment) {
        return new FunctionValue(formalParameters, body, environment);
    }

    public @NotNull FunctionValue value() {
        return new FunctionValue(formalParameters, body, null);
    }

    @Override
    public String toString() {
        String nameList = "(" + String.join(", ", formalParameters) + ")";
        return String.format("lambda(%s){%s}", nameList, body);
    }
}
