package io.nson.javapt.core;

public abstract class ColourUtils {
    public static int colVecToInt(RGB colour) {
        return colDblToInt(colour.blue) |
                (colDblToInt(colour.green) << 8) |
                (colDblToInt(colour.red) << 16);
    }

    public static int colDblToInt(double d) {
        final double i = MathUtil.gammaCorr(d);
        final double j = i * 255.0 + 0.5;
        return (int)MathUtil.clamp(j, 0, 255);
    }
}
