package expression;

import java.util.List;
import java.util.ArrayList;

import environment.Environment;
import value.FunctionValue;
import value.Value;

import token.Token;

public record FunctionCall(Token token, FunctionValue function, List<Expression> actualParameters)
        implements Expression {

    @Override
    public Value eval(Environment environment) {
        List<Value> values = new ArrayList<>();
        return function.apply(values);
    }
}
