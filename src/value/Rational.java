package value;

import java.math.BigInteger;

final class Rational implements Numeric {
    final BigInteger top;
    final BigInteger bottom;

    static final Rational rationalZero = new Rational(BigInteger.ZERO, BigInteger.ONE);
    static final Rational rationalOne = new Rational(BigInteger.ONE, BigInteger.ONE);

    public Rational(long top, long bottom) {
        this.top = BigInteger.valueOf(top);
        this.bottom = BigInteger.valueOf(bottom);
    }

    public Rational(BigInteger top, BigInteger bottom) {
        BigInteger gcd = top.gcd(bottom);
        this.top = top.divide(gcd);
        this.bottom = bottom.divide(gcd);
    }

    @Override
    public int compareTo(Numeric nv) {
        return 0;
    }

    @Override
    public String toString() {
        if (bottom.equals(BigInteger.ONE)) {
            return String.format("%s", top);
        }
        return String.format("%s/%s", top, bottom);
    }

    public Numeric negate() {
        return new Rational(top.negate(), bottom);
    }

    public Numeric invert() {
        return new Rational(bottom, top);
    }

    public Numeric add(Numeric nv) {
        return switch (nv) {
            case Real real -> new Real(this).add(real);
            case Rational rational -> {
                BigInteger newTop = top.multiply(rational.bottom).add(bottom.multiply(rational.top));
                BigInteger newBottom = bottom.multiply(rational.bottom);
                yield new Rational(newTop, newBottom);
            }
        };
    }

    public Numeric multiply(Numeric nv){
        return switch (nv) {
            case Real real -> new Real(this).multiply(real);
            case Rational rational -> {
                BigInteger newTop = top.multiply(rational.top);
                BigInteger newBottom = bottom.multiply(rational.bottom);
                yield new Rational(newTop, newBottom);
            }
        };
    }

    public Numeric pow(int exponent) {
        if (exponent > 0) {
            return new Rational(top.pow(exponent), bottom.pow(exponent));
        } else if (exponent < 0) {
            return new Rational(bottom.pow(-exponent), top.pow(-exponent));
        }
        return Numeric.ONE;
    }

    public int intValue() {
        return top.divide(bottom).intValue();
    }

    public Real toReal() {
        return new Real(this);
    }

}
