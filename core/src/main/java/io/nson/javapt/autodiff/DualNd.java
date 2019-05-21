package io.nson.javapt.autodiff;

import java.util.Objects;

public class DualNd {
    public static final DualNd ZERO = new DualNd(0, 0);
    public static final DualNd NEG_INF = new DualNd(Double.NEGATIVE_INFINITY, 0);
    public static final DualNd POS_INF = new DualNd(Double.POSITIVE_INFINITY, 0);

    public static DualNd real(double d) {
        return new DualNd(d, 0);
    }

    public final double v;
    public final double d;

    public DualNd(double v, double d) {
        this.v = v;
        this.d = d;
    }

    @Override
    public String toString() {
        return "DualNd{" +
                "v=" + v +
                ", d=" + d +
                '}';
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        } else if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        } else {
            final DualNd rhsT = (DualNd) rhs;
            return Double.compare(rhsT.v, v) == 0 &&
                    Double.compare(rhsT.d, d) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, d);
    }

    public boolean isNaN() {
        return Double.isNaN(v) || Double.isNaN(d);
    }

    public DualNd abs() {
        if (v > 0) {
            return this;
        } else {
            return neg();
        }
    }

    public DualNd neg() {
        return new DualNd(-v, -d);
    }

    public DualNd add(DualNd rhs) {
        return new DualNd(v + rhs.v, d + rhs.d);
    }

    public DualNd add(double rhs) {
        return add(DualNd.real(rhs));
    }

    public DualNd sub(DualNd rhs) {
        return new DualNd(v - rhs.v, d - rhs.d);
    }

    public DualNd sub(double rhs) {
        return sub(DualNd.real(rhs));
    }

    public DualNd mult(DualNd rhs) {
        return new DualNd(v * rhs.v, d * rhs.v + v * rhs.d);
    }

    public DualNd mult(double rhs) {
        return mult(DualNd.real(rhs));
    }

    public DualNd div(DualNd rhs) {
        return new DualNd(
                v / rhs.v,
                (d * rhs.v - v * rhs.d) / (rhs.v * rhs.v)
        );
    }

    public DualNd div(double rhs) {
        return div(DualNd.real(rhs));
    }

    public DualNd sin() {
        return new DualNd(Math.sin(v), d * Math.cos(v));
    }

    public DualNd cos() {
        return new DualNd(Math.cos(v), -d * Math.sin(v));
    }

    public DualNd sqrt() {
        final double sqrt = Math.sqrt(v);
        return new DualNd(sqrt, d / (sqrt + sqrt));
    }

    public DualNd pow(double e) {
        return new DualNd(
                Math.pow(v, e),
                e * Math.pow(v, e - 1) * d
        );
    }

    public static void main(String[] args) {
        test(0);
        test(1);
        test(2);
    }

    static void test(double x) {
        System.out.println(x + " -> " + f(x));
    }

    // f(x) = 3x^2 + 5
    // f'(x) = 6x
    static DualNd f2(double d) {
        final DualNd x = new DualNd(d, 1);
        return real(3).mult(x).mult(x)
                .add(real(5));
    }

    // f(x) = 2x^2 + 3x + 5
    // f'(x) = 4x + 3
    static DualNd f(double d) {
        final DualNd x = new DualNd(d, 1);
        return real(2).mult(x).mult(x)
                .add(real(3).mult(x))
                .add(real(5));
    }
}
