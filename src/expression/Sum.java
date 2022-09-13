package expression;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import token.Token;

public record Sum(Token token, List<Expression> terms) implements NumericExpression {
    @Override
    public @NotNull String toString() {
        List<String> strings = terms.stream().map(Expression::toString).toList();
        return String.join(" + ", strings);
    }
}
