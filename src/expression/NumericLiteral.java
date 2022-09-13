package expression;

import value.Numeric;
import token.Token;

public record NumericLiteral(Token token, Numeric value)
        implements NumericExpression, Literal {

    @Override
    public String toString() {
        return value.toString();
    }
}
