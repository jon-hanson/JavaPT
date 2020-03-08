package io.nson.javapt.core;

import io.nson.javapt.autodiff.*;
import io.nson.javapt.geom.Point3d;

public interface SDF {
    double N_EPS = 1e-2;
    int maxSteps = 256;

    default double distance(Point3d p) {
        return distance(DualPoint3d.valueOf(p)).v;
    }

    DualNd distance(DualPoint3d p);
}
