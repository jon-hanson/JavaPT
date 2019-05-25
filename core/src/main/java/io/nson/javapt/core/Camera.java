package io.nson.javapt.core;

import io.nson.javapt.geom.Ray;

public class Camera {
    public final Ray ray;
    public final double fov;

    public Camera(Ray ray, double fov) {
        this.ray = ray;
        this.fov = fov;
    }

    private Camera() {
        this.ray = null;
        this.fov = 0;
    }
}
