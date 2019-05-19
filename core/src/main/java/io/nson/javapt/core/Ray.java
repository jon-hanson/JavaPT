package io.nson.javapt.core;

public class Ray {
    public static Ray of(Point3d origin, Vector3d dir) {
        return new Ray(origin, dir.normalise());
    }

    public final Point3d origin;
    public final Vector3d dir;

    private Ray(Point3d origin, Vector3d dir) {
        this.origin = origin;
        this.dir = dir;
    }

    private Ray() {
        this.origin = null;
        this.dir = null;
    }

    public Point3d apply(double t) {
        return origin.add(dir.mult(t));
    }
}
