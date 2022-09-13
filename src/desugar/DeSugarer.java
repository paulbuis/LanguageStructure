package desugar;

import expression.*;
import value.Numeric;

public class DeSugarer {
    public static Expression deSugar(Expression e) {
        if (e instanceof Desugarable d) {
            switch (d) {
                case Inversion inversion:
                    Expression power = NumericExpression.makePower(inversion.token(), inversion.operand(), -1);
                    return power;

                case GreaterThan gt:
                    Expression not = BooleanExpression.makeNot(gt.token(),
                            ComparisonExpression.makeLessOrEqual(gt.token(), gt.left(), gt.right()));
                    return not;

                case GreaterOrEqual ge:
                    Expression not2 = BooleanExpression.makeNot(ge.token(),
                            ComparisonExpression.makeLessThan(ge.token(), ge.left(), ge.right()));
                    return not2;

                case NotEqualTo ne:
                    Expression not3 = BooleanExpression.makeNot(ne.token(),
                            ComparisonExpression.makeEqualTo(ne.token(), ne.left(), ne.right()));
                    return not3;

                case Negation negation:
                    return NumericExpression.makeProduct(negation.token(),
                            new NumericLiteral(negation.token(), Numeric.makeNumericValue(-1)),
                            negation.operand());

                case Subtraction subtraction:
                    return NumericExpression.makeSum(subtraction.token(), subtraction.left(),
                            NumericExpression.makeProduct(subtraction.token(),
                                    new NumericLiteral(subtraction.token(), Numeric.makeNumericValue(-1)),
                                    subtraction.right()));


                case Division division:
                    return NumericExpression.makeProduct(division.token(), division.left(),
                            NumericExpression.makePower(division.token(), division.right(), -1));
            }
        } else {
            return e;
        }
        return null;
    }
}
