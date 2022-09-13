package expression;

import token.Token;

public record Inversion(Token token, Expression operand)
        implements NumericExpression, Desugarable {

    @Override
    public String toString() {
        return String.format("inverse(%s)", operand.toString());
    }
}
