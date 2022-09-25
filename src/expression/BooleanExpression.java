package expression;


import value.Bool;
import token.Token;

public sealed interface BooleanExpression extends Expression
        permits ComparisonExpression, BooleanLiteral, Variable, Not, And, Or {


    Bool TRUE = new Bool(true);
    Bool FALSE = new Bool(false);
    static Bool makeBool(boolean b) {
        return b ? TRUE : FALSE;
    }

    static BooleanLiteral makeBooleanLiteral(Token token, Bool value) {
        return new BooleanLiteral(token, value);
    }

    static Not makeNot(Token token, BooleanExpression operand) {
        return new Not(token, operand);
    }

    static And makeAnd(Token token, Expression left, Expression right) {
        return new And(token, left, right);
    }

    static Or makeOr(Token token, Expression left, Expression right) {
        return new Or(token, left, right);
    }
}






