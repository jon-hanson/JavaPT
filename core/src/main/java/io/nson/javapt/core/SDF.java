package io.nson.javapt.core;

public interface SDF extends Shape {
    double N_EPS = 1e-2;
    int maxSteps = 256;

    double distance(Point3d p);

    double distance(double x, double y, double z);
}
