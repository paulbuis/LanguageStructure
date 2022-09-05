package expression;

import environment.Environment;
import value.Numeric;
import token.Token;

public record Power(Token token, Expression operand, int exponent) implements NumericExpression {

    @Override
    public String toString() {
        return String.format("(%s)^%d", operand(), exponent);
    }

    @Override
    public Numeric eval(final Environment environment) {
        final Numeric base = operand.numericEval(environment);
        return base.pow(exponent);
    }

}
