package evaluate;

import environment.Environment;
import expression.*;
import value.Numeric;

import java.util.function.Function;

public class NumericExpressionEvaluator {
    public static Numeric eval(Expression expression, Environment environment) {
        NumericExpression ne = (NumericExpression)expression;
        return switch(ne) {
            case NumericLiteral numericLiteral -> numericLiteral.value();

            case Variable v -> environment.lookupNumeric(v.name());

            case Inversion ignored ->
                NumericExpressionEvaluator.eval(ne.deSugar(), environment);


            case Multiplication m -> {
                final Numeric leftValue = NumericExpressionEvaluator.eval(m.left(), environment);
                final Numeric rightValue = NumericExpressionEvaluator.eval(m.right(), environment);
                yield leftValue.multiply(rightValue);
            }

            case Sum s -> {
                Function<Expression, Numeric> termEval = term -> term.numericEval(environment);
                yield s.terms().stream().map(termEval).reduce(Numeric.ZERO, Numeric::add);
            }

            case Power p -> {
                final Numeric base = p.operand().numericEval(environment);
                yield base.pow(p.exponent());
            }


        };
    }
}
