package evaluate;

import expression.*;

import value.Bool;
import value.Numeric;

import environment.Environment;

public class ComparisonExpressionEvaluator {
    public static Bool eval(ComparisonExpression expression, Environment environment) {
        return switch(expression) {
            case GreaterThan gt -> BooleanExpressionEvaluator.eval(gt.deSugar(), environment);

            case GreaterOrEqual goe -> BooleanExpressionEvaluator.eval(goe.deSugar(), environment);

            case LessThan lt -> {
                Numeric leftValue = NumericExpressionEvaluator.eval(lt.left(), environment);
                Numeric rightValue = NumericExpressionEvaluator.eval(lt.right(), environment);
                int comparison = leftValue.compareTo(rightValue);
                yield Bool.makeBool(comparison < 0);
            }

            case LessOrEqual le -> {
                Numeric leftValue = le.left().numericEval(environment);
                Numeric rightValue = le.right().numericEval(environment);
                int comparison = leftValue.compareTo(rightValue);
                yield Bool.makeBool(comparison <= 0);
            }
        };
    }
}
