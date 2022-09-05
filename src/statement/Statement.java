package statement;

import expression.NumericExpression;

public sealed interface Statement permits ExpressionStatement, LetStatement {
}

record ExpressionStatement(NumericExpression expression) implements Statement {

}

record LetStatement(String name, NumericExpression expression) implements Statement {

}

