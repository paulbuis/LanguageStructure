package expression;

import environment.Environment;
import value.Numeric;
import token.Token;

public record Multiplication(Token token, Expression left, Expression right)
        implements NumericExpression {

    @Override
    public String toString() {
        String leftStr = switch(left) {
            case Sum leftSum -> String.format("(%s)", leftSum);
            default -> left.toString();
        };
        String rightStr = switch(right) {
            case Sum rightSum -> String.format("(%s)", rightSum);
            default -> right.toString();
        };

        return String.format("%s * %s", leftStr, rightStr);
    }

    @Override
    public Numeric eval(final Environment environment) {
        final Numeric leftValue = left.numericEval(environment);
        final Numeric rightValue = right.numericEval(environment);
        return leftValue.multiply(rightValue);
    }
}
