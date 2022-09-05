package expression;

import environment.Environment;
import value.Numeric;
import token.Token;

public record Inversion(Token token, Expression operand) implements NumericExpression {

    @Override
    public String toString() {
        return String.format("1/(%s)", operand.toString());
    }

    @Override
    public Numeric eval(final Environment environment) {
        return (Numeric)(deSugar().eval(environment));
    }

    @Override
    public NumericExpression deSugar() {
        return new Power(token, operand, -1);
    }
}
