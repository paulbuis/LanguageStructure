package expression;

import environment.Environment;
import value.Value;
import token.Token;


public record Variable(Token token, String name) implements BooleanExpression, NumericExpression {

    @Override
    public String toString() {
        return String.format("%s", name);
    }

    @Override
    public Value eval(Environment environment) {
        return environment.lookupNumeric(name);
    }
}
