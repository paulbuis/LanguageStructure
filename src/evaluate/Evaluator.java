package evaluate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import value.Value;
import environment.Environment;
import expression.*;

public class Evaluator {
    public static @Nullable Value eval(@NotNull Expression expression, @NotNull Environment environment) {
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
