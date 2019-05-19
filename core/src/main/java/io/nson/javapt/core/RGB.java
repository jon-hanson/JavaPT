package io.nson.javapt.core;

public class RGB {
    public static final RGB BLACK = new RGB(0.0, 0.0, 0.0);
    public static final RGB WHITE = new RGB(1.0, 1.0, 1.0);

    public static final RGB RED = new RGB(1.0, 0.0, 0.0);
    public static final RGB GREEN = new RGB(0.0, 1.0, 0.0);
    public static final RGB BLUE = new RGB(0.0, 0.0, 1.0);

    public final double red;
    public final double green;
    public final double blue;

    public RGB(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private RGB() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    @Override
    public String toString() {
        return "{R : " + red + ", G : " + green + ", B : " + blue + "}";
    }

    public double apply(int i) {
        switch (i) {
            case 0: return red;
            case 1: return green;
            case 2: return blue;
            default: throw null;
        }
    }

    public RGB neg() {
        return new RGB(
                -red,
                -green,
                -blue
        );
    }

    public RGB add(RGB rhs) {
        return new RGB(
            red + rhs.red,
            green + rhs.green,
            blue + rhs.blue
        );
    }

    public RGB sub(RGB rhs) {
        return new RGB(
                red - rhs.red,
                green - rhs.green,
                blue - rhs.blue
        );
    }

    public RGB mult(RGB rhs) {
        return new RGB(
                red * rhs.red,
                green * rhs.green,
                blue * rhs.blue
        );
    }

    public RGB div(RGB rhs) {
        return new RGB(
                red / rhs.red,
                green / rhs.green,
                blue / rhs.blue
        );
    }

    public RGB mult(double s) {
        return new RGB(
                red * s,
                green * s,
                blue * s
        );
    }

    public RGB div(double d) {
        return new RGB(
                red / d,
                green / d,
                blue / d
        );
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return red * red + green * green + blue * blue;
    }

    public RGB normalise() {
        final double s = 1.0 / length();
        return new RGB(red * s, green * s, blue * s);
    }

    public boolean hasNaNs() {
        return Double.isNaN(red) || Double.isNaN(green) || Double.isNaN(blue);
    }

    public RGB clamp() {
        return new RGB(
                MathUtil.clamp(red),
                MathUtil.clamp(green),
                MathUtil.clamp(blue)
        );
    }

    public double max() {
        return Math.max(red, Math.max(green, blue));
    }
}
