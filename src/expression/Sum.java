package expression;


import java.util.List;
import java.util.function.Function;

import environment.Environment;
import value.Numeric;
import token.Token;

public record Sum(Token token, List<Expression> terms) implements NumericExpression {
    @Override
    public String toString() {
        List<String> strings = terms.stream().map(Expression::toString).toList();
        return String.join(" + ", strings);
    }

    @Override
    public Numeric eval(Environment environment) {
        Function<Expression, Numeric> termEval = term -> term.numericEval(environment);
        return terms.stream().map(termEval).reduce(Numeric.ZERO, Numeric::add);
    }
}
