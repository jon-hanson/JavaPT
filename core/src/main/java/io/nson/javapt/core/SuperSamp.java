package io.nson.javapt.core;

public class SuperSamp {
    public static final SuperSamp BLACK = new SuperSamp(RGB.BLACK, RGB.BLACK, RGB.BLACK, RGB.BLACK);

    public RGB c00;
    public RGB c10;
    public RGB c01;
    public RGB c11;

    public SuperSamp(RGB c00, RGB c10, RGB c01, RGB c11) {
        this.c00 = c00;
        this.c10 = c10;
        this.c01 = c01;
        this.c11 = c11;
    }

    @Override
    public String toString() {
        return "SuperSamp{" +
                "c00=" + c00 +
                ", c10=" + c10 +
                ", c01=" + c01 +
                ", c11=" + c11 +
                '}';
    }

    public RGB elem(int x, int y) {
        switch (x) {
            case 0:
                switch (y) {
                    case 0: return c00;
                    case 1: return c01;
                }
            case 1:
                switch (y) {
                    case 0: return c10;
                    case 1: return c11;
                }
        }

        throw new IllegalArgumentException("SuperSamp.apply(" + x + "," + y + ") called");
    }

    public SuperSamp merge(SuperSamp rhs, int n) {
        return new SuperSamp(
                c00.mult(n).add(rhs.c00).div(n + 1),
                c10.mult(n).add(rhs.c10).div(n + 1),
                c01.mult(n).add(rhs.c01).div(n + 1),
                c11.mult(n).add(rhs.c11).div(n + 1)
        );
    }

    public void mergeFrom(SuperSamp rhs, int n) {
        c00 = c00.mult(n).add(rhs.c00).div(n + 1);
        c10 = c10.mult(n).add(rhs.c10).div(n + 1);
        c01 = c01.mult(n).add(rhs.c01).div(n + 1);
        c11 = c11.mult(n).add(rhs.c11).div(n + 1);
    }

    public RGB clamp() {
        return c00.clamp()
                .add(c10.clamp())
                .add(c01.clamp())
                .add(c11.clamp())
                .div(4);
    }

    public SuperSamp add(SuperSamp rhs) {
        return new SuperSamp(
                c00.add(rhs.c00),
                c10.add(rhs.c10),
                c01.add(rhs.c01),
                c11.add(rhs.c11)
        );
    }
}
