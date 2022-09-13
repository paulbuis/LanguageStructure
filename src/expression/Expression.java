package expression;


import token.Token;

public sealed interface Expression permits BinaryExpression, UnaryExpression, Literal,
        NumericExpression, BooleanExpression, FunctionLiteral, FunctionCall {
    Token token();
}
