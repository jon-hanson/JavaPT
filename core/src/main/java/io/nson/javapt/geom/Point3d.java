package io.nson.javapt.geom;

import static java.lang.Double.doubleToLongBits;

public final class Point3d {
    public static final Point3d ZERO = new Point3d(0.0, 0.0, 0.0);

    public static final Point3d NEG_INF = new Point3d(
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY
    );

    public static final Point3d POS_INF = new Point3d(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
    );

    public static Point3d linInterp(Point3d p0, Point3d p1, double t) {
        final double u = 1 - t;
        return new Point3d(
                u * p0.x + t * p1.x,
                u * p0.y + t * p1.y,
                u * p0.z + t * p1.z
        );
    }

    public static Point3d min(Point3d p0, Point3d p1) {
        return new Point3d(
                Math.min(p0.x, p1.x),
                Math.min(p0.y, p1.y),
                Math.min(p0.z, p1.z)
        );
    }

    public static Point3d max(Point3d p0, Point3d p1) {
        return new Point3d(
                Math.max(p0.x, p1.x),
                Math.max(p0.y, p1.y),
                Math.max(p0.z, p1.z)
        );
    }

    public final double x;
    public final double y;
    public final double z;

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Point3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point3d(double[] cs) {
        this(cs[0], cs[1], cs[2]);
        assert(cs.length == 3);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        } else if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        } else {
            final Point3d rhsT = (Point3d) rhs;
            return doubleToLongBits(x) == doubleToLongBits(rhsT.x) &&
                    doubleToLongBits(y) == doubleToLongBits(rhsT.y) &&
                    doubleToLongBits(z) == doubleToLongBits(rhsT.z);
        }
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) + 31 * (Double.hashCode(y) + 31 * Double.hashCode(z));
    }

    public double c(int i) {
        switch (i)  {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            default: throw new RuntimeException("Invalid index: " + i);
        }
    }

    public boolean isZero() {
        return equals(ZERO);
    }

    public Point3d withX(double x) {
        return new Point3d(x, y, z);
    }

    public Point3d withY(double y) {
        return new Point3d(x, y, z);
    }

    public Point3d withZ(double z) {
        return new Point3d(x, y, z);
    }

    public Point3d neg() {
        return new Point3d(-x, -y, -z);
    }

    public Point3d add(Vector3d rhs) {
        return new Point3d(x + rhs.dx, y + rhs.dy, z + rhs.dz);
    }

    public Point3d sub(Vector3d rhs) {
        return new Point3d(x - rhs.dx, y - rhs.dy, z - rhs.dz);
    }

    public Point3d mult(double s) {
        return new Point3d(x * s, y * s, z * s);
    }

    public Point3d div(double s) {
        return new Point3d(x / s, y / s, z / s);
    }

    public Vector3d sub(Point3d rhs) {
        return new Vector3d(x - rhs.x, y - rhs.y, z - rhs.z);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public boolean hasNaNs() {
        return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z);
    }

    public Vector3d asVector() {
        return new Vector3d(x, y, z);
    }

    public Point3d floor() {
        return new Point3d(
                Math.floor(x),
                Math.floor(y),
                Math.floor(z)
        );
    }

    public Point3d ceil() {
        return new Point3d(
                Math.ceil(x),
                Math.ceil(y),
                Math.ceil(z)
        );
    }

    public Point3d abs() {
        return new Point3d(
                Math.abs(x),
                Math.abs(y),
                Math.abs(z)
        );
    }

    public Point3d permute(int ix, int iy, int iz) {
        return new Point3d(c(ix), c(iy), c(iz));
    }

    public double distance(Point3d rhs) {
        final double dx = x - rhs.x;
        final double dy = y - rhs.y;
        final double dz = z - rhs.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSquared(Point3d rhs) {
        final double dx = x - rhs.x;
        final double dy = y - rhs.y;
        final double dz = z - rhs.z;

        return dx * dx + dy * dy + dz * dz;
    }
}
