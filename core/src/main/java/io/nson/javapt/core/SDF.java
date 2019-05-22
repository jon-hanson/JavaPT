package io.nson.javapt.core;

import io.nson.javapt.autodiff.*;

public interface SDF extends Shape {
    double N_EPS = 1e-2;
    int maxSteps = 64;

    default double distance(Point3d p) {
        return distance(DualPoint3d.valueOf(p)).v;
    }

    DualNd distance(DualPoint3d p);
}
