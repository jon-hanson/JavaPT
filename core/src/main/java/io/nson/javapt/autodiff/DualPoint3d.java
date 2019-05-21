package io.nson.javapt.autodiff;

import io.nson.javapt.core.Point3d;

public final class DualPoint3d {
    public static final DualPoint3d ZERO = new DualPoint3d(DualNd.ZERO, DualNd.ZERO, DualNd.ZERO);

    public static final DualPoint3d NEG_INF = new DualPoint3d(
            DualNd.NEG_INF,
            DualNd.NEG_INF,
            DualNd.NEG_INF
    );

    public static final DualPoint3d POS_INF = new DualPoint3d(
            DualNd.POS_INF,
            DualNd.POS_INF,
            DualNd.POS_INF
    );

    public static DualPoint3d valueOf(Point3d p) {
        return new DualPoint3d(
                DualNd.real(p.x),
                DualNd.real(p.y),
                DualNd.real(p.z)
        );
    }

    public final DualNd x;
    public final DualNd y;
    public final DualNd z;

    public DualPoint3d(DualNd x, DualNd y, DualNd z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private DualPoint3d() {
        this.x = null;
        this.y = null;
        this.z = null;
    }

    public DualPoint3d(DualNd[] cs) {
        this(cs[0], cs[1], cs[2]);
        assert(cs.length == 3);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y, z);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        } else if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        } else {
            final DualPoint3d rhsT = (DualPoint3d) rhs;
            return x.equals(rhsT.x) && y.equals(rhsT.y) && z.equals(rhsT.z);
        }
    }

    @Override
    public int hashCode() {
        return x.hashCode() + 31 * (y.hashCode() + 31 * z.hashCode());
    }

    public DualNd c(int i) {
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

    public DualPoint3d withX(DualNd x) {
        return new DualPoint3d(x, y, z);
    }

    public DualPoint3d withY(DualNd y) {
        return new DualPoint3d(x, y, z);
    }

    public DualPoint3d withZ(DualNd z) {
        return new DualPoint3d(x, y, z);
    }

    public DualPoint3d neg() {
        return new DualPoint3d(x.neg(), y.neg(), z.neg());
    }

    public DualPoint3d add(DualVector3d rhs) {
        return new DualPoint3d(x.add(rhs.dx), y.add(rhs.dy), z.add(rhs.dz));
    }

    public DualPoint3d sub(DualVector3d rhs) {
        return new DualPoint3d(x.sub(rhs.dx), y.sub(rhs.dy), z.sub(rhs.dz));
    }

    public DualPoint3d mult(double s) {
        return new DualPoint3d(x.mult(s), y.mult(s), z.mult(s));
    }

    public DualPoint3d div(double s) {
        return new DualPoint3d(x.div(s), y.div(s), z.div(s));
    }

    public DualVector3d sub(DualPoint3d rhs) {
        return new DualVector3d(x.sub(rhs.x), y.sub(rhs.y), z.sub(rhs.z));
    }

    public DualNd length() {
        return lengthSquared().sqrt();
    }

    public DualNd lengthSquared() {
        return x.mult(x).add(y.mult(y)).add(z.mult(z));
    }

    public boolean hasNaNs() {
        return x.isNaN() || y.isNaN() || z.isNaN();
    }

    public DualVector3d asVector() {
        return new DualVector3d(x, y, z);
    }

    public DualPoint3d abs() {
        return new DualPoint3d(
                x.abs(),
                y.abs(),
                z.abs()
        );
    }

    public DualPoint3d permute(int ix, int iy, int iz) {
        return new DualPoint3d(c(ix), c(iy), c(iz));
    }

    public DualNd distance(DualPoint3d rhs) {
        return distanceSquared(rhs).sqrt();
    }

    public DualNd distanceSquared(DualPoint3d rhs) {
        final DualNd dx = x.sub(rhs.x);
        final DualNd dy = y.sub(rhs.y);
        final DualNd dz = z.sub(rhs.z);

        return dx.mult(dx).add(dy.mult(dy)).add(dz.mult(dz));
    }
}
