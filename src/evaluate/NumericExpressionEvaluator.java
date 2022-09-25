package evaluate;


import java.util.List;
import java.util.stream.Stream;

import environment.Environment;
import expression.*;

import value.Numeric;
import desugar.DeSugarer;


public class NumericExpressionEvaluator {
    public static Numeric eval(Expression expression, Environment environment) {
        NumericExpression ne = (NumericExpression)expression;
        return switch(ne) {
            case NumericLiteral numericLiteral -> numericLiteral.value();

            case Variable v -> environment.lookupNumeric(v.name());

            case Multiplication m -> {
                final Numeric leftValue = eval(m.left(), environment);
                final Numeric rightValue = eval(m.right(), environment);
                yield leftValue.multiply(rightValue);
            }

            case Sum s -> {
                final Stream<Numeric> termValues = s.terms().stream().map(term->eval(term, environment));
                yield termValues.reduce(Numeric.ZERO, (a,b)->a.add(b));
            }

            case Power p -> {
                final Numeric base = eval(p.operand(), environment);
                yield base.pow(p.exponent());
            }

            default -> {
                if (ne instanceof Desugarable) {
                    Expression di = DeSugarer.deSugar(ne);
                    System.out.printf("Evaluation of desugared inversion: di=%s\n", di);
                    yield eval(di, environment);
                }
                else {
                    yield null; // TODO: fix this!!!
                }
            }
            case Negation negation -> null;
            case Subtraction subtraction -> null;
            case Division division -> null;
        };
    }
}
