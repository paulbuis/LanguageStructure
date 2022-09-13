package evaluate;

import environment.Environment;
import org.jetbrains.annotations.NotNull;
import value.Bool;
import expression.*;

public class BooleanExpressionEvaluator {
    public static Bool eval(Expression expression, @NotNull Environment environment) {
        BooleanExpression be = (BooleanExpression) expression;
        return switch(be) {

            case BooleanLiteral booleanLiteral -> booleanLiteral.value();

            case Variable variable -> environment.lookupBool(variable.name());

            case And and -> {
                if (!eval(and.left(), environment).bool()) {
                    yield Bool.FALSE; // short-circuit
                }
                yield  eval(and.right(), environment);
            }

            case Or or -> {
                if (BooleanExpressionEvaluator.eval(or.left(), environment).bool()) {
                    yield Bool.TRUE;  // short-circuit
                }
                yield eval(or.right(), environment);
            }

            case Not not -> eval(not.operand(), environment);

            case ComparisonExpression ce -> ComparisonExpressionEvaluator.eval(ce, environment);
        };
    }

}
