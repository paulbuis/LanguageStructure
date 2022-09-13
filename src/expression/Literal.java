package expression;

import value.*;

public sealed interface Literal
        extends Expression
        permits NumericLiteral, BooleanLiteral, FunctionLiteral {
    Value value();
}
