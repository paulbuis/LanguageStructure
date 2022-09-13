package expression;

import org.jetbrains.annotations.NotNull;
import token.Token;

public sealed interface ComparisonExpression extends BooleanExpression, BinaryExpression
        permits LessThan, LessOrEqual, GreaterThan, GreaterOrEqual, EqualTo, NotEqualTo {


    static @NotNull ComparisonExpression makeLessThan(Token token, Expression left, Expression right) {
        return new LessThan(token, left, right);
    }

    static @NotNull ComparisonExpression makeLessOrEqual(Token token, Expression left, Expression right) {
        return new LessOrEqual(token, left, right);
    }

    static @NotNull ComparisonExpression makeGreaterThan(Token token, Expression left, Expression right) {
        return new GreaterThan(token, left, right);
    }

    static @NotNull ComparisonExpression makeGreaterOrEqual(Token token, Expression left, Expression right) {
        return new GreaterOrEqual(token, left, right);
    }

    static @NotNull ComparisonExpression makeEqualTo(Token token, Expression left, Expression right) {
        return new EqualTo(token, left, right);
    }

    static @NotNull ComparisonExpression makNotEqualTo(Token token, Expression left, Expression right) {
        return new NotEqualTo(token, left, right);
    }

}








