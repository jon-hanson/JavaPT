package io.nson.javapt.core;

import java.util.Optional;

public abstract class Shape {

    protected static final double T_EPS = 1e-6;

    protected final String name;

    protected Shape(String name) {
        this.name = name;
    }

    private Shape() {
        this.name = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String name() {
        return name;
    }

    public abstract Material material();

    public abstract Optional<Double> intersect(Ray ray, double eps);

    public Optional<Double> intersect(Ray ray) {
        return intersect(ray, Shape.T_EPS);
    }

    public abstract Vector3d normal(Point3d p);
}
