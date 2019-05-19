package io.nson.javapt.core;

import java.util.OptionalDouble;

public interface Shape {

    double T_EPS = 1e-6;

    String name();
    
    Material material();

    OptionalDouble intersect(Ray ray, double eps);

    default OptionalDouble intersect(Ray ray) {
        return intersect(ray, Shape.T_EPS);
    }

    Vector3d normal(Point3d p);
}
