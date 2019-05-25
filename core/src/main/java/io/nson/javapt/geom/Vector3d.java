package io.nson.javapt.geom;

public final class Vector3d {

    public static final Vector3d ZERO = new Vector3d(0.0, 0.0, 0.0);

    public static final Vector3d X_UNIT = new Vector3d(1.0, 0.0, 0.0);

    public static final Vector3d Y_UNIT = new Vector3d(0.0, 1.0, 0.0);

    public static final Vector3d Z_UNIT = new Vector3d(0.0, 0.0, 1.0);

    public final double dx;
    public final double dy;
    public final double dz;

    public Vector3d(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    private Vector3d() {
        this.dx = 0;
        this.dy = 0;
        this.dz = 0;
    }

    public Vector3d(double[] cs) {
        this(cs[0], cs[1], cs[2]);
        assert(cs.length == 3);
    }

    @Override
    public String toString() {
        return String.format("<%f, %f, %f>", dx, dy, dz);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        } else if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        } else {
            final Vector3d rhsT = (Vector3d) rhs;
            return Double.compare(rhsT.dx, dx) == 0 &&
                    Double.compare(rhsT.dy, dy) == 0 &&
                    Double.compare(rhsT.dz, dz) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Double.hashCode(dx) + 31 * (Double.hashCode(dy) + 31 * Double.hashCode(dz));
    }

    public double c(int i) {
        switch (i)  {
            case 0: return dx;
            case 1: return dy;
            case 2: return dz;
            default: throw new RuntimeException("Invalid index: " + i);
        }
    }

    public boolean isZero() {
        return equals(ZERO);
    }

    public Vector3d withX(double dx) {
        return new Vector3d(dx, dy, dz);
    }

    public Vector3d withY(double dy) {
        return new Vector3d(dx, dy, dz);
    }

    public Vector3d withZ(double dz) {
        return new Vector3d(dx, dy, dz);
    }

    public Vector3d neg() {
        return new Vector3d(-dx, -dy, -dz);
    }

    public Vector3d add(Vector3d rhs) {
        return new Vector3d(dx + rhs.dx, dy + rhs.dy, dz + rhs.dz);
    }

    public Vector3d sub(Vector3d rhs) {
        return new Vector3d(dx - rhs.dx, dy - rhs.dy, dz - rhs.dz);
    }

    public Vector3d mult(double s) {
        return new Vector3d(dx * s, dy * s, dz * s);
    }

    public Vector3d div(double s) {
        return new Vector3d(dx / s, dy / s, dz / s);
    }

    public double dot(Vector3d rhs) {
        return dx * rhs.dx + dy * rhs.dy + dz * rhs.dz;
    }

    public double absDot(Vector3d rhs) {
        return Math.abs(this.dot(rhs));
    }

    public Vector3d cross(Vector3d rhs) {
        return new Vector3d(
                dy * rhs.dz - dz * rhs.dy,
                dz * rhs.dx - dx * rhs.dz,
                dx * rhs.dy - dy * rhs.dx
        );
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return dx * dx + dy * dy + dz * dz;
    }

    public Vector3d normalise() {
        final double s = 1.0 / length();
        return new Vector3d(dx * s, dy * s, dz * s);
    }

    public Vector3d abs() {
        return new Vector3d(Math.abs(dx), Math.abs(dy), Math.abs(dz));
    }

    public boolean hasNaNs() {
        return Double.isNaN(dx) || Double.isNaN(dy) || Double.isNaN(dz);
    }

    public Point3d asPoint() {
        return new Point3d(dx, dy, dz);
    }
}
