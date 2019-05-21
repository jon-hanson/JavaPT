package io.nson.javapt.autodiff;

import io.nson.javapt.core.Vector3d;

public final class DualVector3d {

    public static final DualVector3d ZERO = new DualVector3d(DualNd.ZERO, DualNd.ZERO, DualNd.ZERO);

    public static final DualVector3d X_UNIT = new DualVector3d(DualNd.real(1), DualNd.ZERO, DualNd.ZERO);

    public static final DualVector3d Y_UNIT = new DualVector3d(DualNd.ZERO, DualNd.real(1), DualNd.ZERO);

    public static final DualVector3d Z_UNIT = new DualVector3d(DualNd.ZERO, DualNd.ZERO, DualNd.real(1));

    public static DualVector3d valueOf(Vector3d p) {
        return new DualVector3d(
                DualNd.real(p.dx),
                DualNd.real(p.dy),
                DualNd.real(p.dz)
        );
    }

    public final DualNd dx;
    public final DualNd dy;
    public final DualNd dz;

    public DualVector3d(DualNd dx, DualNd dy, DualNd dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    private DualVector3d() {
        this.dx = null;
        this.dy = null;
        this.dz = null;
    }

    public DualVector3d(DualNd[] cs) {
        this(cs[0], cs[1], cs[2]);
        assert(cs.length == 3);
    }

    @Override
    public String toString() {
        return String.format("<%s, %s, %s>", dx, dy, dz);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        } else if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        } else {
            final DualVector3d rhsT = (DualVector3d) rhs;
            return dx.equals(rhsT.dx) && dy.equals(rhsT.dy) && dz.equals(rhsT.dz);
        }
    }

    @Override
    public int hashCode() {
        return dx.hashCode() + 31 * (dy.hashCode() + 31 * dz.hashCode());
    }

    public DualNd c(int i) {
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

    public DualVector3d withX(DualNd dx) {
        return new DualVector3d(dx, dy, dz);
    }

    public DualVector3d withY(DualNd dy) {
        return new DualVector3d(dx, dy, dz);
    }

    public DualVector3d withZ(DualNd dz) {
        return new DualVector3d(dx, dy, dz);
    }

    public DualVector3d neg() {
        return new DualVector3d(dx.neg(), dy.neg(), dz.neg());
    }

    public DualVector3d add(DualVector3d rhs) {
        return new DualVector3d(dx.add(rhs.dx), dy.add(rhs.dy), dz.add(rhs.dz));
    }

    public DualVector3d sub(DualVector3d rhs) {
        return new DualVector3d(dx.sub(rhs.dx), dy.sub(rhs.dy), dz.sub(rhs.dz));
    }

    public DualVector3d mult(DualNd s) {
        return new DualVector3d(dx.mult(s), dy.mult(s), dz.mult(s));
    }

    public DualVector3d div(DualNd s) {
        return new DualVector3d(dx.div(s), dy.div(s), dz.div(s));
    }

    public DualNd dot(DualVector3d rhs) {
        return dx.mult(rhs.dx)
                .add(dy.mult(rhs.dy))
                .add(dz.mult(rhs.dz));
    }

    public DualNd absDot(DualVector3d rhs) {
        return this.dot(rhs).abs();
    }

    public DualVector3d cross(DualVector3d rhs) {
        return new DualVector3d(
                dy.mult(rhs.dz).sub(dz.mult(rhs.dy)),
                dz.mult(rhs.dx).sub(dx.mult(rhs.dz)),
                dx.mult(rhs.dy).sub(dy.mult(rhs.dx))
        );
    }

    public DualNd length() {
        return lengthSquared().sqrt();
    }

    public DualNd lengthSquared() {
        return dx.mult(dx).add(dy.mult(dy)).add(dz.mult(dz));
    }

    public DualVector3d normalise() {
        final DualNd l = length();
        return new DualVector3d(dx.div(l), dy.div(l), dz.div(l));
    }

    public DualVector3d abs() {
        return new DualVector3d(dx.abs(), dy.abs(), dz.abs());
    }

    public boolean hasNaNs() {
        return dx.isNaN() || dy.isNaN() || dz.isNaN();
    }

    public DualPoint3d asPoint() {
        return new DualPoint3d(dx, dy, dz);
    }
}
