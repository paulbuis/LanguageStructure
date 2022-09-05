package evaluate;

import environment.Environment;
import value.Bool;
import expression.*;

public class BooleanExpressionEvaluator {
    public static Bool eval(Expression expression, Environment environment) {
        BooleanExpression be = (BooleanExpression) expression;
        return switch(be) {

            case BooleanLiteral booleanLiteral -> booleanLiteral.value();

            case Variable variable -> environment.lookupBool(variable.name());

            case And and -> {
                if (!BooleanExpressionEvaluator.eval(and.left(), environment).bool()) {
                    yield Bool.FALSE; // short-circuit
                }
                yield  and.right().booleanEval(environment);
            }

            case Or or -> {
                if (BooleanExpressionEvaluator.eval(or.left(), environment).bool()) {
                    yield Bool.TRUE;  // short-circuit
                }
                yield or.right().booleanEval(environment);
            }

            case Not not -> BooleanExpressionEvaluator.eval(not.operand(), environment);

            case ComparisonExpression ce -> ComparisonExpressionEvaluator.eval(ce, environment);
        };
    }

}
