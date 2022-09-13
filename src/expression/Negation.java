package expression;

import token.Token;

public record Negation(Token token, Expression operand)
    implements NumericExpression, UnaryExpression, Desugarable {

    @Override
    public String toString() {
        return String.format("-(%s)", operand);
    }
}
