package demo;


import environment.Environment;
import evaluate.Evaluator;
import expression.Derivative;
import expression.Expression;
import expression.NumericExpression;
import org.jetbrains.annotations.NotNull;
import value.Numeric;
import value.Value;

import java.util.List;

public class NumericExpressionDemo {

    static Value eval(@NotNull Expression e, Environment env) {
        return Evaluator.eval(e, env);
    }

    public static void main(String[] args) {
        final Numeric three = Numeric.ONE.add(Numeric.ONE).add(Numeric.ONE);
        final Environment environment = new Environment(List.of("x"), List.of(three));
        final NumericExpression one = NumericExpression.makeConstant(null, Numeric.ONE);
        final NumericExpression two = NumericExpression.makeConstant(null, Numeric.ONE.add(Numeric.ONE));
        final Expression x = NumericExpression.makeVariable(null, "x");
        final Expression xPlus1 = NumericExpression.makeSum(null, x, one);
        System.out.printf("x+1      : %s ==> %s\n", xPlus1, eval(xPlus1, environment));

        Expression xPlus1Diff = Derivative.diff(xPlus1, "x");
        System.out.printf("diff(x+1): %s ==> %s\n", xPlus1Diff, eval(xPlus1Diff, environment));
        System.out.println();

        Expression twoXPlus1 = NumericExpression.makeProduct(null, two, xPlus1);
        System.out.printf("2*(x+1)      : %s ==> %s\n", twoXPlus1, eval(twoXPlus1, environment));

        Expression twoXPlus1Diff = Derivative.diff(twoXPlus1, "x");
        System.out.printf("diff(2*(x+1)): %s ==> %s\n", twoXPlus1Diff, eval(twoXPlus1Diff, environment));
        System.out.println();

        Expression xMinus1 = NumericExpression.makeDifference(null, x, one);
        System.out.printf("x-1: %s ==> %s\n", xMinus1, eval(xMinus1, environment));
        System.out.println();

        Expression difference = NumericExpression.makeDifference(null, xPlus1, xMinus1);
        System.out.printf("(x+1)-(x-1)      : %s ==> %s\n", difference, eval(difference, environment));
        Expression differenceDiff = Derivative.diff(difference, "x");
        System.out.printf("diff((x+1)-(x-1)): %s ==> %s\n", differenceDiff, eval(differenceDiff, environment));
        System.out.println();

        Expression ratio = NumericExpression.makeDivision(null, xPlus1, xMinus1);
        System.out.printf("(x+1)/(x-1): %s ==> %s\n", ratio, eval(ratio, environment));
        Expression ratioDiff = Derivative.diff(ratio, "x");
        System.out.printf("diff((x+1)/(x-1)): %s ==> %s\n", ratioDiff, eval(ratioDiff, environment));
    }
}
