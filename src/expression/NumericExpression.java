package expression;

import java.util.List;
import java.util.ArrayList;

import environment.Environment;
import value.Value;
import value.Numeric;
import token.Token;

public sealed interface NumericExpression extends Expression
        permits Variable, NumericLiteral, Sum, Multiplication, Inversion, Power {

    Value eval(Environment environment);


    NumericExpression ZERO = makeConstant(null, Numeric.ZERO);
    NumericExpression ONE = makeConstant(null, Numeric.ONE);
    NumericExpression MINUS_ONE = makeConstant(null, Numeric.ONE.negate());


    static NumericExpression makeConstant(Token token, int integer) {
        return makeConstant(token, Numeric.makeNumericValue(integer));
    }
    static NumericExpression makeConstant(Token token, Numeric value) {
        return new NumericLiteral(token, value);
    }


    static Expression makeVariable(Token token, String name) {
        return new Variable(token, name);
    }


    static Expression makeNegation(Token token,Expression operand) {
        return makeProduct(token, MINUS_ONE, operand);
    }


    static Expression makeInversion(Token token, Expression operand) {
        return switch (operand) {
            case NumericLiteral numericConstant -> makeConstant(token, numericConstant.value().invert());
            case Inversion inversion -> inversion.operand();
            default -> new Inversion(token, operand);
        };
    }


    static Expression makeDifference(Token token, Expression left, Expression right) {
        return makeSum(token, left, makeNegation(token, right));
    }


    static Expression makeDivision(Token token, Expression left, Expression right) {
        return makeProduct(token, left, makeInversion(token, right));
    }


    static Expression makeSum(Token token, Expression e1, Expression e2) {
        return makeSum(token, List.of(e1, e2));
    }


    static Expression makeSum(Token token, List<Expression> termList0) {
        List<Expression> termList = new ArrayList<>();
        for (Expression term : termList0) {
            switch (term) {
                case NumericLiteral numericConstant && numericConstant.equals(ZERO):
                    break;
                case Sum sum:
                    termList.addAll(sum.terms());
                    break;
                default:
                    termList.add(term);
            }
        }
        
        switch (termList.size()) {
            case 0:
                return ZERO;
            case 1:
                return termList.get(0);
            default:
                //termList.sort(sumComparator);
        }


        // loop and combine neighboring like terms
        Expression previousTerm = ZERO;
        ArrayList<Expression> termList2 = new ArrayList<>();
        for (Expression currentTerm : termList) {
            if (previousTerm instanceof NumericLiteral previousNumericConstant && currentTerm instanceof NumericLiteral currentNumericConstant) {
                NumericLiteral combinedNumericConstant = new NumericLiteral(token, previousNumericConstant.value().add(currentNumericConstant.value()));
                int index = termList2.size() - 1;
                if (index < 0) {
                    termList2.add(combinedNumericConstant);
                } else {
                    termList2.set(termList2.size() - 1, combinedNumericConstant);
                }
                previousTerm = combinedNumericConstant;
            } else {
                termList2.add(currentTerm);
                previousTerm = currentTerm;
            }

        }

        if (termList2.size() == 1) {
            return termList2.get(0);
        }
        return new Sum(token, termList2);
    }


    static Expression makeProduct(Token token, Expression left, Expression right) {
        return switch (left) {
            case NumericLiteral leftNumericConstant && leftNumericConstant.equals(ZERO) -> ZERO;
            case NumericLiteral leftNumericConstant && leftNumericConstant.equals(ONE) -> right;
            case NumericLiteral leftNumericConstant -> {
                if (right instanceof NumericLiteral rightNumericConstant) {
                    yield makeConstant(token, leftNumericConstant.value().multiply(rightNumericConstant.value()));
                } else {
                    yield new Multiplication(token, leftNumericConstant, right);
                }
            }
            default -> switch (right) {
                case NumericLiteral rightNumericConstant && rightNumericConstant.equals(ZERO) -> ZERO;
                case NumericLiteral rightNumericConstant && rightNumericConstant.equals(ONE) -> left;
                case NumericLiteral rightNumericConstant -> new Multiplication(token, rightNumericConstant, left);
                case Inversion rightInversion && left.equals(rightInversion.operand()) -> ONE;
                default -> new Multiplication(token, left, right);
            };
        };
    }


    static Expression makePower(Token token, Expression base, int exponent) {
        if (exponent < -1) {
            return makePower(token, base, -exponent);
        }
        return switch (exponent) {
            case 0 -> ONE;
            case -1 -> makeInversion(token, base);
            case 1 -> base;
            default -> switch (base) {
                case NumericLiteral numericConstant -> makeConstant(token, numericConstant.value().pow(exponent));
                case Inversion inversion -> makeInversion(token, makePower(token, inversion.operand(), exponent));
                default -> new Power(token, base, exponent);
            };
        };
    }

    //SumComparator sumComparator = new SumComparator();
}

/*
final class SumComparator implements java.util.Comparator<Expression> {

    @Override
    public int compare(NumericExpression e1, NumericExpression e2) {
        return switch (e1) {
            case NumericLiteral ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> 0;
                case Variable ignored2 -> 1;
                case Sum ignored2 -> 2;
                case Multiplication ignored2 -> 3;
                case Inversion ignored2 -> 4;
                case Power ignored2 -> 5;
            };
            case Variable ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> -1;
                case Variable ignored2 -> 0;
                case Sum ignored2 -> 1;
                case Multiplication ignored2 -> 2;
                case Inversion ignored2 -> 3;
                case Power ignored2 -> 4;
            };
            case Sum ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> -2;
                case Variable ignored2 -> -1;
                case Sum ignored2 -> 0;
                case Multiplication ignored2 -> 1;
                case Inversion ignored2 -> 2;
                case Power ignored2 -> 3;
            };
            case Multiplication ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> -3;
                case Variable ignored2 -> -2;
                case Sum ignored2 -> -1;
                case Multiplication ignored2 -> 0;
                case Inversion ignored2 -> 1;
                case Power ignored2 -> 2;
            };
            case Inversion ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> -4;
                case Variable ignored2 -> -3;
                case Sum ignored2 -> -2;
                case Multiplication ignored2 -> -1;
                case Inversion ignored2 -> 0;
                case Power ignored2 -> 1;
            };
            case Power ignored1 -> switch (e2) {
                case NumericLiteral ignored2 -> -5;
                case Variable ignored2 -> -4;
                case Sum ignored2 -> -3;
                case Multiplication ignored2 -> -2;
                case Inversion ignored2 -> -1;
                case Power ignored2 -> 0;
            };
        };
    }
}

*/