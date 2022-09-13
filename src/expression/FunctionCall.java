package expression;

import java.util.List;

import environment.Environment;
import evaluate.Evaluator;
import value.FunctionValue;
import value.Value;

import token.Token;

public record FunctionCall(Token token, FunctionValue function, List<Expression> actualParameters)
        implements Expression {

    public Value eval(Environment environment) {
        List<Value> values = actualParameters.stream().map(actual -> Evaluator.eval(actual, environment)).toList();
        return function.apply(values);
    }
}
