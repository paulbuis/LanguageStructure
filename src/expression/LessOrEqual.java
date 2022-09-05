package expression;

import environment.Environment;
import value.Bool;
import value.Numeric;
import token.Token;

public record LessOrEqual(Token token, Expression left, Expression right)
        implements ComparisonExpression {
    @Override
    public Bool eval(Environment environment) {
        Numeric leftValue = left.numericEval(environment);
        Numeric rightValue = right.numericEval(environment);
        int comparison = leftValue.compareTo(rightValue);
        return Bool.makeBool(comparison <= 0);
    }

    @Override
    public String toString() {
        return String.format("(%s) <= (%s)", left, right);
    }
}
