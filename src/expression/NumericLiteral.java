package expression;

import environment.Environment;
import value.Numeric;
import token.Token;

public record NumericLiteral(Token token, Numeric value) implements NumericExpression {

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Numeric eval(final Environment environment) {
        return value;
    }
}
