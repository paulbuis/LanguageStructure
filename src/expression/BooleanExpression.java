package expression;

import environment.Environment;
import value.Bool;
import value.Value;

public sealed interface BooleanExpression extends Expression
        permits ComparisonExpression, BooleanLiteral, Variable, Not, And, Or {
    Value eval(Environment environment);

    default Bool booleanEval(Environment environment) {
        Value v = eval(environment);
        if (v instanceof Bool b) {
            return b;
        }
        throw new RuntimeException("Boolean expression evaluated to non-boolean");
    }

    Bool TRUE = new Bool(true);
    Bool FALSE = new Bool(false);
    static Bool makeBool(boolean b) {
        return b ? TRUE : FALSE;
    }
}






