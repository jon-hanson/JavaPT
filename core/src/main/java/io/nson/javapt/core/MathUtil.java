package io.nson.javapt.core;

public abstract class MathUtil {

    /**
     * Tent function
     */
    public static double tent(double x)  {
        final double x2 = x+ x;
        if (x2 < 1.0) {
            return Math.sqrt(x2) - 1.0;
        } else {
            return 1.0 - Math.sqrt(2.0 - x2);
        }
    }

    /**
     * Clamp a value to a range.
     */
    public static double clamp(double x, double low, double high) {
        if (x > high) {
            return high;
        } else if (x < low) {
            return low;
        } else {
            return x;
        }
    }

    public static double clamp(double x) {
        return clamp(x, 0.0, 1.0);
    }

    /**
     * Square function.
     */
    public static double sqr(double x) {
        return x * x;
    }

    // Standard gamma correction value.
    static final double  GAMMA = 2.2;

    /**
     * Standard gamma correction.
     */
    public static double gammaCorr(double x) {
        return Math.pow(x, 1.0 / GAMMA);
    }
}
