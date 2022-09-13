package expression;

import token.Token;

public record Subtraction(Token token, Expression left, Expression right)
    implements NumericExpression, BinaryExpression, Desugarable {
}
