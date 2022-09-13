package expression;

public sealed interface UnaryExpression extends Expression
    permits Negation, Not, Power {

    Expression operand();
}
