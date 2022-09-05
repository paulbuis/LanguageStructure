package expression;

import environment.Environment;
import value.Bool;
import token.Token;


public record Not(Token token, BooleanExpression operand) implements BooleanExpression {
    @Override
    public String toString() {
        return String.format("not(%s)", operand);
    }

    @Override
    public Bool eval(Environment environment) {
        return new Bool(!operand.booleanEval(environment).bool());
    }
}

