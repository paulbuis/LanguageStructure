package expression;

import token.Token;

public record Division(Token token, Expression left, Expression right)
    implements NumericExpression, BinaryExpression, Desugarable {

    @Override
    public String toString() {
        String leftStr = switch(left) {
            case null -> "null";
            case Sum leftSum -> String.format("(%s)", leftSum);
            default -> left.toString();
        };
        String rightStr = switch(right) {
            case null -> "null";
            case Sum rightSum -> String.format("(%s)", rightSum);
            default -> right.toString();
        };

        return String.format("%s \u00f7 %s", leftStr, rightStr);
    }
}
