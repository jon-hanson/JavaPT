package io.nson.javapt.core;

import io.nson.javapt.geom.*;

import java.util.OptionalDouble;

public abstract class AbstractShape implements Shape {
    protected static final double T_EPS = 1e-6;

    protected final String name;
    protected final Material material;

    protected AbstractShape(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    private AbstractShape() {
        this.name = null;
        this.material = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String name() {
        return name;
    }

    public Material material() {
        return material;
    }

    public abstract OptionalDouble intersect(Ray ray, double eps);

    public OptionalDouble intersect(Ray ray) {
        return intersect(ray, Shape.T_EPS);
    }

    public abstract Vector3d normal(Point3d p);
}
