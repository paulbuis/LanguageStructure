package expression;

public sealed interface BinaryExpression extends Expression
        permits And, Or, ComparisonExpression, Subtraction, Multiplication, Division {
    Expression left();
    Expression right();
}
