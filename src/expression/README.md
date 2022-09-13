

# Class Hierarchy Declarations

```mermaid
classDiagram
class Expression {
<<interface>>
+token()
}

class BinaryExpression {
<<interface>>
+left()
+right()
}

class UnaryExpression {
+operand()
}

Expression <|-- BinaryExpression
Expression <|-- NumericExpression
Expression <|-- UnaryExpression

class NumericExpression {
<<interface>>
}

class Sum
class Subtraction
class Multiplication
class Division
class NumericLiteral

class Grouping

class Power {
+exponent
}

class Negation

class Variable


NumericExpression <|-- Sum
NumericExpression <|-- Subtraction
NumericExpression <|-- Multiplication
NumericExpression <|-- Division
NumericExpression <|-- Variable
NumericExpression <|-- Power
NumericExpression <|-- Negation
NumericExpression <|-- NumericLiteral
NumericExpression <|-- Grouping

BinaryExpression <|-- Sum
BinaryExpression <|-- Subtraction
BinaryExpression <|-- Multiplication
BinaryExpression <|-- Division

UnaryExpression <|-- Power
UnaryExpression <|-- Negation
UnaryExpression <|-- Grouping
```

```mermaid
classDiagram

class Expression {
<<interface>>
+token()
}

class BinaryExpression {
<<interface>>
+left()
+right()
}

class UnaryExpression {
+operand()
}


Expression <|-- BinaryExpression
class BooleanExpression {
<<interface>>
}

class ComparisonExpression {
<<interface>>
}


Expression <|-- BooleanExpression
BooleanExpression <|-- ComparisonExpression
BinaryExpression <|-- ComparisonExpression

class And
class Or
class Variable
class Grouping
class Not

UnaryExpression <|-- Grouping

BooleanExpression <|-- Variable
BooleanExpression <|-- Grouping
BooleanExpression <|-- Not

BooleanExpression <|-- And
BooleanExpression <|-- Or
BinaryExpression <|-- Or
BinaryExpression <|-- And

UnaryExpression <|-- Not

class NotEqualTo
class EqualTo
class GreaterThan
class GreaterOrEqual
class LessThan
class LessOrEqual

ComparisonExpression <|-- EqualTo
ComparisonExpression <|-- NotEqualTo
ComparisonExpression <|-- GreaterThan
ComparisonExpression <|-- GreaterOrEqual
ComparisonExpression <|-- LessThan
ComparisonExpression <|-- LessOrEqual

```

```
interface Expression
    permits NumericExpression, BooleanExpression, FunctionLiteral, FunctionCall

    interface NumericExpression extends Expression
        permits Variable, NumericLiteral, Sum, Multiplication, Inversion, Power
        
    record NumericLiteral(Numeric value)
        implements NumericExpression
    
    record Sum(List<NumericExpression> terms)
        implements NumericExpression
    
    record Multiplication(NumericExpression left, NumericExpression right)
        implements NumericExpression
        
    record Power(NumericExpression operand, int exponent)
        implements NumericExpression 
    
interface BooleanExpression extends Expression
    permits ComparisonExpression, BooleanLiteral, Variable, Not, And, Or
    
    interface ComparisonExpression extends BooleanExpression
        permits LessThan, LessOrEqual, GreaterThan, GreaterOrEqual
        
        record GreaterOrEqual(NumericExpression left, NumericExpression right)
            implements ComparisonExpression
            
        record GreaterThan(NumericExpression left, NumericExpression right)
            implements ComparisonExpression
            
        record LessOrEqual(NumericExpression left, NumericExpression right)
            implements ComparisonExpression
            
        public record LessThan(NumericExpression left, NumericExpression right)
            implements ComparisonExpression
        
    record BooleanLiteral(Bool value)
        implements BooleanExpression
    
    record Not(BooleanExpression operand)
        implements BooleanExpression
    
    record And(BooleanExpression left, BooleanExpression right)
        implements BooleanExpression
        
    record Or(BooleanExpression left, BooleanExpression right)
        implements BooleanExpression
        
record FunctionLiteral(List<String> formalParameters, Expression body)
    implements Expression

record FunctionCall(FunctionValue function, List<Expression> actualParameters)
    implements Expression           
```