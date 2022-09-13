package parser;


import expression.NumericExpression;
import expression.Expression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import token.Token;

import java.util.List;

import static token.Token.Name.*;

public class NumericExpressionParser extends Parser {

    public Expression parse() {
        return term();
    }

    public NumericExpressionParser(@NotNull List<Token> tokens) {
        super(tokens);
    }


    protected @Nullable Expression term() {
        Expression left = factor();

        if (match(PLUS, MINUS)) {  // TODO: make this a while
            final Token operator = previous();
            final Expression right = factor();
            left = switch(operator.name()) {
                case PLUS -> new expression.Sum(operator, List.of(left, right));
                default -> null; // TODO: Fix!!!

            };
        }
        return left;
    }

    private Expression factor() {
        Expression left = unary();

        while (match(MULTIPLY, DIVIDE)) {
            final Token operator = previous();
            final Expression right = unary();
            left = switch (operator.name()) {
                case MULTIPLY -> NumericExpression.makeProduct(operator, left, right);
                default -> NumericExpression.makeProduct(operator, left, NumericExpression.makePower(operator, right, -1));
            };
        }

        return left;
    }

    public Expression unary() {
        if (match(MINUS)) {
            Token operator = previous();
            Expression operand = unary();
            return NumericExpression.makeProduct(operator, NumericExpression.MINUS_ONE, operand);
        }

        return primary();
    }

    public @Nullable Expression primary() {
        return null;
    }
}
