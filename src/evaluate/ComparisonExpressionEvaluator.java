package evaluate;

import expression.*;

import static desugar.DeSugarer.deSugar;

import value.Bool;
import value.Numeric;

import environment.Environment;

public class ComparisonExpressionEvaluator {
    public static Bool eval(ComparisonExpression expression, Environment environment) {
        return switch(expression) {
            case NotEqualTo notEqualTo -> BooleanExpressionEvaluator.eval(deSugar(notEqualTo), environment);

            case GreaterThan gt -> BooleanExpressionEvaluator.eval(deSugar(gt), environment);

            case GreaterOrEqual goe -> BooleanExpressionEvaluator.eval(deSugar(goe), environment);

            case EqualTo equalTo ->  {
                Numeric leftValue = NumericExpressionEvaluator.eval(equalTo.left(), environment);
                Numeric rightValue = NumericExpressionEvaluator.eval(equalTo.right(), environment);
                int comparison = leftValue.compareTo(rightValue);
                yield Bool.makeBool(comparison == 0);
            }

            case LessThan lt -> {
                Numeric leftValue = NumericExpressionEvaluator.eval(lt.left(), environment);
                Numeric rightValue = NumericExpressionEvaluator.eval(lt.right(), environment);
                int comparison = leftValue.compareTo(rightValue);
                yield Bool.makeBool(comparison < 0);
            }

            case LessOrEqual le -> {
                Numeric leftValue = NumericExpressionEvaluator.eval(le.left(), environment);
                Numeric rightValue = NumericExpressionEvaluator.eval(le.right(), environment);
                int comparison = leftValue.compareTo(rightValue);
                yield Bool.makeBool(comparison <= 0);
            }
        };
    }
}
