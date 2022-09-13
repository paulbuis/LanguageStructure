package optimize;

import environment.Environment;
import evaluate.BooleanExpressionEvaluator;
import evaluate.NumericExpressionEvaluator;
import expression.*;
import org.jetbrains.annotations.NotNull;

public class ConstantFolder {

    public static Expression fold(@NotNull Expression expression) {
        return switch (expression) {
            case Literal literal -> literal;
            case UnaryExpression ue -> foldUnary(ue);
            case BinaryExpression be -> foldBinary(be);
            case FunctionCall functionCall -> functionCall;
            case Variable variable -> variable;

            default -> expression;
        };
    }

    private static Expression foldUnary(@NotNull UnaryExpression ue) {
        Expression operandFolded = fold(ue.operand());
        return switch(ue) {
            case Negation negation -> {
                if (operandFolded instanceof NumericLiteral) {
                    yield NumericExpression.makeNumericLiteral(negation.token(),
                            NumericExpressionEvaluator.eval(negation, Environment.emptyEnvironment));
                }
                if (operandFolded instanceof Negation innerNegation) {
                    yield innerNegation.operand();
                }
                yield negation;
            }

            case Not not -> {
                if (operandFolded instanceof BooleanLiteral) {
                    yield BooleanExpression.makeBooleanLiteral(ue.token(),
                            BooleanExpressionEvaluator.eval(ue, Environment.emptyEnvironment));
                }
                if (operandFolded instanceof Not innerNot) {
                    yield innerNot.operand();
                }
                yield not;
            }

            case Power power -> {
                if (power.exponent() == 0) {
                    yield NumericExpression.ONE;
                }
                if (power.operand().equals(NumericExpression.ZERO)) {
                    yield NumericExpression.ZERO;
                }
                if (power.operand() instanceof Power p) {
                    // TODO: check is this right way to handle right associativity?
                    yield NumericExpression.makePower(p.token(), p.operand(), power.exponent()*p.exponent());
                }
                yield power;
            }
        };
    }

    private static Expression foldBinary(@NotNull BinaryExpression be) {
        return switch(be) {
            case NumericExpression numericExpression -> foldBinaryNumeric(numericExpression);
            case BooleanExpression booleanExpression -> foldBinaryBoolean(booleanExpression);
            default -> be;
        };
    }

    private static Expression foldBinaryNumeric(@NotNull NumericExpression numericExpression) {

        Expression leftFolded = fold(((BinaryExpression)numericExpression).left());
        Expression rightFolded = fold(((BinaryExpression)numericExpression).right());

        if (leftFolded instanceof NumericLiteral && rightFolded instanceof NumericLiteral) {
            return NumericExpression.makeNumericLiteral(numericExpression.token(),
                    evaluate.NumericExpressionEvaluator.eval(numericExpression, Environment.emptyEnvironment));
        }
        return switch(numericExpression) {
            case Multiplication multiplication -> {
                if (leftFolded.equals(NumericExpression.ZERO)) {
                    yield NumericExpression.ZERO;
                }
                if (rightFolded.equals(NumericExpression.ZERO)) {
                    yield NumericExpression.ZERO;
                }
                if (leftFolded.equals(NumericExpression.ONE)) {
                    yield rightFolded;
                }
                if (rightFolded.equals(NumericExpression.ONE)) {
                    yield leftFolded;
                }
                yield NumericExpression.makeProduct(multiplication.token(), leftFolded, rightFolded);
            }

            default -> numericExpression;
        };
    }

    private static Expression foldBinaryBoolean(BooleanExpression booleanExpression) {

        if (booleanExpression instanceof ComparisonExpression comparisonExpression) {
            return foldComparison(comparisonExpression);
        }
        Expression leftFolded = fold(((BinaryExpression)booleanExpression).left());
        Expression rightFolded = fold(((BinaryExpression)booleanExpression).right());

        if (leftFolded instanceof BooleanLiteral && rightFolded instanceof BooleanLiteral) {
            return BooleanExpression.makeBooleanLiteral(booleanExpression.token(),
                    evaluate.BooleanExpressionEvaluator.eval(booleanExpression, Environment.emptyEnvironment));
        }

        return switch (booleanExpression) {
            case And and -> {
                if (leftFolded instanceof BooleanLiteral booleanLiteral) {
                    if (!booleanLiteral.value().bool()) {
                        yield BooleanExpression.makeBooleanLiteral(and.token(), BooleanExpression.FALSE);
                    } else {
                        yield rightFolded;
                    }
                }
                if (rightFolded instanceof BooleanLiteral booleanLiteral) {
                    if (!booleanLiteral.value().bool()) {
                        yield BooleanExpression.makeBooleanLiteral(and.token(), BooleanExpression.FALSE);
                    } else {
                        yield leftFolded;
                    }
                }
                yield BooleanExpression.makeAnd(and.token(), leftFolded, rightFolded);
            }

            case Or or -> {

                if (leftFolded instanceof BooleanLiteral booleanLiteral) {
                    if (booleanLiteral.value().bool()) {
                        yield BooleanExpression.makeBooleanLiteral(or.token(), BooleanExpression.TRUE);
                    } else {
                        yield rightFolded;
                    }
                }
                if (rightFolded instanceof BooleanLiteral booleanLiteral) {
                    if (booleanLiteral.value().bool()) {
                        yield BooleanExpression.makeBooleanLiteral(or.token(), BooleanExpression.TRUE);
                    } else {
                        yield leftFolded;
                    }
                }
                yield BooleanExpression.makeOr(or.token(), leftFolded, rightFolded);
            }

            default -> booleanExpression;
        };
    }

    private static @NotNull Expression foldComparison(@NotNull ComparisonExpression comparisonExpression) {
        Expression leftFolded = fold(comparisonExpression.left());
        Expression rightFolded = fold(comparisonExpression.right());

        if ((leftFolded instanceof NumericLiteral) &&
                (rightFolded instanceof NumericLiteral)) {
            return BooleanExpression.makeBooleanLiteral(comparisonExpression.token(),
                    BooleanExpressionEvaluator.eval(comparisonExpression, Environment.emptyEnvironment));
        }

        return comparisonExpression;
    }
}
