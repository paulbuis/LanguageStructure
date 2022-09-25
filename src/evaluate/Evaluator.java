package evaluate;


import value.Value;
import environment.Environment;
import expression.*;

public class Evaluator {
    public static Value eval(Expression expression, Environment environment) {
        return switch(expression) {
            case NumericExpression ne -> NumericExpressionEvaluator.eval(ne, environment);
            case BooleanExpression be -> BooleanExpressionEvaluator.eval(be, environment);
            case FunctionCall ignored -> {
                System.out.println("FunctionCall not implemented");
                yield null;
            }
            case FunctionLiteral ignored -> {
                System.out.println("FunctionLiteral not implemented");
                yield null;
            }
            default -> {
                System.err.println("Unanticipated expression type");
                yield null;
            }
        };
    }
}
