package expression;


import token.Token;

public record And(Token token, Expression left, Expression right)
        implements BooleanExpression, BinaryExpression {

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left, token.lexeme(), right);
    }
}
