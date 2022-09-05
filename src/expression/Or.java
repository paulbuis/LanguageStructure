package expression;

import environment.Environment;
import value.Bool;
import token.Token;

public record Or(Token token, BooleanExpression left, BooleanExpression right)
        implements BooleanExpression {

    @Override
    public String toString() {
        return String.format("(%s or %s)", left, right);
    }

    @Override
    public Bool eval(final Environment environment) {
        if (left.booleanEval(environment).bool()) {
            return TRUE;  // short-circuit
        }
        return right.booleanEval(environment);
    }
}
