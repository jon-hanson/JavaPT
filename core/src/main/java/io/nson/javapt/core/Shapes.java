package io.nson.javapt.core;

import java.util.Optional;

public abstract class Shapes {
    public static class Sphere extends Shape {
        final Material material;
        final Point3d centre;
        final double radius;

        public Sphere(String name, Material material, Point3d centre, double radius) {
            super(name);
            this.material = material;
            this.centre = centre;
            this.radius = radius;
        }

        private Sphere() {
            super(null);
            this.material = null;
            this.centre = null;
            this.radius = 0;
        }

        @Override
        public Material material() {
            return material;
        }

        @Override
        public Optional<Double> intersect(Ray ray, double eps) {
            final Vector3d e = ray.origin.sub(centre);
            final double f = ray.dir.dot(e);
            final double g = f * f - e.dot(e) + radius * radius;
            if (g > 0.0) {
                final double det = Math.sqrt(g);
                final double t = -f - det;
                if (t > eps) {
                    return Optional.of(t);
                } else {
                    final double t2 = -f + det;
                    if (t2 > eps) {
                        return Optional.of(t2);
                    } else {
                        return Optional.empty();
                    }
                }
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Vector3d normal(Point3d p) {
            return p.sub(centre).normalise();
        }
    }

    public static class Plane extends Shape {
        public enum Axis {X, Y, Z};

        final Material material;
        final Axis side;
        final boolean posFacing;
        final double v;

        public Plane(String name, Material material, Axis side, boolean posFacing, double v) {
            super(name);
            this.material = material;
            this.side = side;
            this.posFacing = posFacing;
            this.v = v;
        }

        private Plane() {
            super(null);
            this.material = null;
            this.side = null;
            this.posFacing = false;
            this.v = 0;
        }

        @Override
        public Material material() {
            return material;
        }

        @Override
        public Optional<Double> intersect(Ray ray, double eps) {
            final double rdv = ray.dir.c(side.ordinal());
            final double rov = ray.origin.c(side.ordinal());
            if ((Math.abs(rdv) > Double.MIN_VALUE) &&
                    ((ray.origin.c(side.ordinal()) > v) == posFacing)) {
                final double t = (v - rov) / rdv;
                if (t > eps) {
                    return Optional.of(t);
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Vector3d normal(Point3d p) {
            switch(side) {
                case X: return Vector3d.X_UNIT;
                case Y: return Vector3d.Y_UNIT;
                case Z: return Vector3d.Z_UNIT;
                default : throw null;
            }
        }
    }
}
