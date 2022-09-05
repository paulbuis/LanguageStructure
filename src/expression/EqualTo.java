package expression;

import environment.Environment;
import value.Bool;
import token.Token;

public record EqualTo(Token token, Expression left, Expression right)
        implements ComparisonExpression {

    @Override
    public Bool eval(Environment environment) {
        return (Bool)(deSugar().eval(environment));
    }

    @Override
    public BooleanExpression deSugar() {
        return new Not(token, new LessThan(token, left, right));
    }

    @Override
    public String toString() {
        return String.format("(%s) >= (%s)", left, right);
    }
}
