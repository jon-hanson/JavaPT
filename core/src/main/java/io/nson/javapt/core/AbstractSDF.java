package io.nson.javapt.core;

import java.util.OptionalDouble;

public class AbstractSDF extends AbstractShape implements SDF {

    public AbstractSDF(String name, Material material) {
        super(name, material);
    }

    public double distance(Point3d p) {
        return distance(p.x, p.y, p.z);
    }

    public double distance(double x, double y, double z) {
        return distance(new Point3d(x, y, z));
    }

    @Override
    public OptionalDouble intersect(Ray ray, double eps) {

        float t = 0.0f;

        for (int i = 0; i < maxSteps; ++i) {
            final Point3d p = ray.apply(t);
            final double dist = distance(p);
            if (dist < eps) {
                return OptionalDouble.of(t);
            } else if (dist > Integer.MAX_VALUE) {
                return OptionalDouble.empty();
            }

            t += dist;
        }

        return OptionalDouble.empty();
    }

    @Override
    public Vector3d normal(Point3d p) {
        double x;
        double y;
        double z;

        double eps = N_EPS;

        do {
            x = distance(p.x + eps, p.y, p.z) - distance(p.x - eps, p.y, p.z);
            y = distance(p.x, p.y + eps, p.z) - distance(p.x, p.y - eps, p.z);
            z = distance(p.x, p.y, p.z + eps) - distance(p.x, p.y, p.z - eps);

            eps *= 10f;
        } while (x == 0.0f && y == 0.0f && z == 0.0f);

        return new Vector3d(x, y, z).normalise();
    }
}
