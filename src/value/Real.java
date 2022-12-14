package value;

import java.math.BigDecimal;
import java.math.MathContext;

final class Real implements Numeric {
    final BigDecimal value;

    static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    public Real(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Real(BigDecimal value) {
        this.value = value;
    }

    public Real(Rational value) {
        BigDecimal top = new BigDecimal(value.top);
        BigDecimal bottom = new BigDecimal(value.bottom);
        this.value = top.divide(bottom, MATH_CONTEXT);
    }


    @Override
    public int compareTo(Numeric nv) {
        return switch (nv) {
            case Real real -> value.compareTo(real.value);
            case Rational rational -> value.compareTo(rational.toReal().value);
        };
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Numeric negate() {
        return new Real(value.negate(MATH_CONTEXT));
    }

    public Numeric invert() {
        return new Real(BigDecimal.ONE.divide(this.value, MATH_CONTEXT));
    }

    public Real add(Numeric nv) {
        return switch(nv) {
            case Rational rational -> this.add(new Real(rational));
            case Real real -> new Real(this.value.add(real.value, MATH_CONTEXT));
        };
    }

    public Real multiply(Numeric nv){
        return switch(nv) {
            case Rational rational -> this.multiply(new Real(rational));
            case Real real -> new Real(this.value.multiply(real.value, MATH_CONTEXT));
        };
    }

    public Real pow(int exponent) {
        return new Real(value.pow(exponent, MATH_CONTEXT));
    }

    public int intValue() {
        return value.intValue();
    }
}

