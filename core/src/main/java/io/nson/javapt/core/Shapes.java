package io.nson.javapt.core;

import org.apache.logging.log4j.*;

import java.util.*;

public abstract class Shapes {
    private static final Logger logger = LogManager.getLogger(Shapes.class);

    public static class Sphere extends AbstractShape {
        final Point3d centre;
        final double radius;

        public Sphere(String name, Material material, Point3d centre, double radius) {
            super(name, material);
            this.centre = centre;
            this.radius = radius;
        }

        private Sphere() {
            super(null, null);
            this.centre = null;
            this.radius = 0;
        }

        @Override
        public OptionalDouble intersect(Ray ray, double eps) {
            final Vector3d e = ray.origin.sub(centre);
            final double f = ray.dir.dot(e);
            final double g = f * f - e.dot(e) + radius * radius;
            if (g > 0.0) {
                final double det = Math.sqrt(g);
                final double t = -f - det;
                if (t > eps) {
                    return OptionalDouble.of(t);
                } else {
                    final double t2 = -f + det;
                    if (t2 > eps) {
                        return OptionalDouble.of(t2);
                    } else {
                        return OptionalDouble.empty();
                    }
                }
            } else {
                return OptionalDouble.empty();
            }
        }

        @Override
        public Vector3d normal(Point3d p) {
            //logger.info("Normal({}) -> {}", p, p.sub(centre).normalise());
            return p.sub(centre).normalise();
        }
    }

    public static class Plane extends AbstractShape {
        public enum Axis {X, Y, Z};

        final Axis side;
        final boolean posFacing;
        final double v;

        public Plane(String name, Material material, Axis side, boolean posFacing, double v) {
            super(name, material);
            this.side = side;
            this.posFacing = posFacing;
            this.v = v;
        }

        private Plane() {
            super(null, null);
            this.side = null;
            this.posFacing = false;
            this.v = 0;
        }

        @Override
        public OptionalDouble intersect(Ray ray, double eps) {
            final double rdv = ray.dir.c(side.ordinal());
            final double rov = ray.origin.c(side.ordinal());
            if ((Math.abs(rdv) > Double.MIN_VALUE) &&
                    ((ray.origin.c(side.ordinal()) > v) == posFacing)) {
                final double t = (v - rov) / rdv;
                if (t > eps) {
                    return OptionalDouble.of(t);
                } else {
                    return OptionalDouble.empty();
                }
            } else {
                return OptionalDouble.empty();
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
