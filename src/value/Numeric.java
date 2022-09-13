package value;

import org.jetbrains.annotations.NotNull;

public sealed interface Numeric extends Value, Comparable<Numeric> permits Real, Rational {
    Numeric negate();
    Numeric invert();
    Numeric add(Numeric nv);
    Numeric multiply(Numeric nv);

    Numeric pow(int exponent);

    int intValue();

    default Numeric subtract(@NotNull Numeric nv) {
        return add(nv.negate());
    }

    default Numeric divide(@NotNull Numeric nv) {
        return multiply(nv.invert());
    }

    // static fields
    Numeric ZERO = Rational.rationalZero;
    Numeric ONE = Rational.rationalOne;

    static @NotNull Numeric makeNumericValue(long value) {
        return new Rational(value, 1);
    }

}



