package expression;

import environment.Environment;
import value.Bool;
import token.Token;

public record And(Token token, BooleanExpression left, BooleanExpression right)
        implements BooleanExpression {

    @Override
    public String toString() {
        return String.format("(%s and %s)", left, right);
    }

    @Override
    public Bool eval(final Environment environment) {
        if (!left.booleanEval(environment).bool()) {
            return FALSE; // short-circuit
        }
        return right.booleanEval(environment);
    }
}
