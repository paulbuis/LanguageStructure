package expression;


import org.jetbrains.annotations.NotNull;
import value.Bool;
import token.Token;

public sealed interface BooleanExpression extends Expression
        permits ComparisonExpression, BooleanLiteral, Variable, Not, And, Or {


    Bool TRUE = new Bool(true);
    Bool FALSE = new Bool(false);
    static @NotNull Bool makeBool(boolean b) {
        return b ? TRUE : FALSE;
    }

    static @NotNull BooleanLiteral makeBooleanLiteral(Token token, Bool value) {
        return new BooleanLiteral(token, value);
    }

    static @NotNull Not makeNot(Token token, BooleanExpression operand) {
        return new Not(token, operand);
    }

    static @NotNull And makeAnd(Token token, Expression left, Expression right) {
        return new And(token, left, right);
    }

    static @NotNull Or makeOr(Token token, Expression left, Expression right) {
        return new Or(token, left, right);
    }
}






