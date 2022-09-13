package expression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Derivative {
    public static @Nullable Expression diff(@NotNull Expression e, String varName) {
        return switch (e) {
            case NumericLiteral ignored -> NumericExpression.ZERO;

            case Variable variable -> variable.name().equals(varName) ? NumericExpression.ONE : variable;

            case Inversion inversion -> {
                Expression operandDiff = diff(inversion.operand(), varName);
                Expression negOperandDiff = NumericExpression.makeNegation(null, operandDiff);
                Expression operandSquared = NumericExpression.makePower(null, inversion.operand(), 2);
                yield NumericExpression.makeDivision(null, negOperandDiff, operandSquared);
            }

            case Sum sum -> {
                List<Expression> list = sum.terms().stream().map(term -> diff(term, varName)).toList();
                yield NumericExpression.makeSum(null, list );
            }

            case Multiplication m -> {
                Expression leftDiff = diff(m.left(), varName);
                Expression rightDiff = diff(m.right(), varName);
                yield NumericExpression.makeSum(null,
                        NumericExpression.makeProduct(null, leftDiff, m.right()),
                        NumericExpression.makeProduct(null, m.left(), rightDiff));
            }

            case Power p -> {
                Expression diff = diff(p.operand(), varName);
                Expression product = NumericExpression.makeProduct(null, NumericExpression.makeConstant(null, p.exponent()), diff);
                yield NumericExpression.makeProduct(null, product, NumericExpression.makePower(null, p.operand(), p.exponent()-1));
            }

            default -> null;
        };
    }

}
