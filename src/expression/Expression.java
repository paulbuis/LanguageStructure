package expression;


import environment.Environment;
import value.Numeric;
import value.Value;
import token.Token;

public sealed interface Expression permits NumericExpression, BooleanExpression, FunctionLiteral, FunctionCall {
    Token token();
    Value eval(Environment environment);

    default Numeric numericEval(Environment environment) {
        Value value = eval(environment);
        if (value instanceof Numeric n) {
            return n;
        }
        throw new RuntimeException(String.format("%s must evaluate to Numeric value", this));
    }

    default Expression deSugar() {
        return this;
    }
}
