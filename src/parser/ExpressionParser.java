package parser;

import java.util.List;

import expression.Expression;
import token.Token;

import static token.Token.Name.*;


public class ExpressionParser extends NumericExpressionParser {

    public ExpressionParser(List<Token> tokens) {
        super(tokens);
    }

    private Expression comparison() {
        Expression left = term();

        if (match(GREATER_THAN, GREATER_OR_EQUAL, LESS_THAN, LESS_OR_EQUAL)) {
            final Token operator = previous();
            final Expression right = term();
            return switch (operator.name()) {
                case GREATER_THAN -> new expression.GreaterThan(operator, left, right);
                case GREATER_OR_EQUAL -> new expression.GreaterOrEqual(operator, left, right);
                case LESS_THAN -> new expression.LessThan(operator, left, right);
                default -> new expression.LessOrEqual(operator, left, right);
            };
        }
        return left;
    }
}
