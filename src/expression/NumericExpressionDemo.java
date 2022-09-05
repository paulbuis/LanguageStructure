package expression;


import java.util.List;

import environment.Environment;
import value.Numeric;

public class NumericExpressionDemo {


    public static void main(String[] args) {
        final Numeric three = Numeric.ONE.add(Numeric.ONE).add(Numeric.ONE);
        final Environment environment = new Environment(List.of("x"), List.of(three));
        final NumericExpression one = NumericExpression.makeConstant(null, Numeric.ONE);
        final NumericExpression two = NumericExpression.makeConstant(null, Numeric.ONE.add(Numeric.ONE));
        final Expression x = NumericExpression.makeVariable(null, "x");
        final Expression xPlus1 = NumericExpression.makeSum(null, x, one);
        System.out.printf("x+1      : %s ==> %s\n", xPlus1, xPlus1.eval(environment));

        Expression xPlus1Diff = Derivative.diff(xPlus1, "x");
        System.out.printf("diff(x+1): %s ==> %s\n", xPlus1Diff, xPlus1Diff.eval(environment));
        System.out.println();

        Expression twoXPlus1 = NumericExpression.makeProduct(null, two, xPlus1);
        System.out.printf("2*(x+1)      : %s ==> %s\n", twoXPlus1, twoXPlus1.eval(environment));

        Expression twoXPlus1Diff = Derivative.diff(twoXPlus1, "x");
        System.out.printf("diff(2*(x+1)): %s ==> %s\n", twoXPlus1Diff, twoXPlus1Diff.eval(environment));
        System.out.println();

        Expression xMinus1 = NumericExpression.makeDifference(null, x, one);
        System.out.printf("x-1: %s ==> %s\n", xMinus1, xMinus1.eval(environment));
        System.out.println();

        Expression difference = NumericExpression.makeDifference(null, xPlus1, xMinus1);
        System.out.printf("(x+1)-(x-1)      : %s ==> %s\n", difference, difference.eval(environment));
        Expression differenceDiff = Derivative.diff(difference, "x");
        System.out.printf("diff((x+1)-(x-1)): %s ==> %s\n", differenceDiff, differenceDiff.eval(environment));
        System.out.println();

        Expression ratio = NumericExpression.makeDivision(null, xPlus1, xMinus1);
        System.out.printf("(x+1)/(x-1): %s ==> %s\n", ratio, ratio.eval(environment));
        Expression ratioDiff = Derivative.diff(ratio, "x");
        System.out.printf("diff((x+1)/(x-1)): %s ==> %s\n", ratioDiff, ratioDiff.eval(environment));
    }
}
