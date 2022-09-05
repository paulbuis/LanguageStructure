package expression;

import environment.Environment;
import value.Bool;
import token.Token;

public sealed interface ComparisonExpression extends BooleanExpression
        permits LessThan, LessOrEqual, GreaterThan, GreaterOrEqual, EqualTo, NotEqualTo {

    @Override
    Bool eval(Environment environment);

    static Expression makeLessThan(Token token, Expression left, Expression right) {
        return new LessThan(token, left, right);
    }

    static Expression makeLessOrEqual(Token token, Expression left, Expression right) {
        return new LessOrEqual(token, left, right);
    }

    static Expression makeGreaterThan(Token token, Expression left, Expression right) {
        return new GreaterThan(token, left, right);
    }

    static Expression makeGreaterOrEqual(Token token, Expression left, Expression right) {
        return new GreaterOrEqual(token, left, right);
    }

    static Expression makEqualTo(Token token, Expression left, Expression right) {
        return new EqualTo(token, left, right);
    }

    static Expression makNotEqualTo(Token token, Expression left, Expression right) {
        return new NotEqualTo(token, left, right);
    }

}








