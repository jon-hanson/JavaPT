package io.nson.javapt.core;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Scene {
    public static class Intersection<T> {
        public final Shape shape;
        public final T t;

        public Intersection(Shape shape, T t) {
            this.shape = shape;
            this.t = t;
        }
    }

    public final Camera camera;
    public final List<Shape> shapes;

    public Scene(Camera camera, List<Shape> shapes) {
        this.camera = camera;
        this.shapes = shapes;
    }

    private Scene() {
        this.camera = null;
        this.shapes = null;
    }

    public Optional<Intersection<Point3d>> intersect(Ray ray) {
        List<Scene.Intersection<Double>> isects = shapes.stream()
                .map(shp -> shp.intersect(ray).map(t -> new Intersection<>(shp, t)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return isects.stream()
                .min(Comparator.comparingDouble(l -> l.t))
                .map(isectT -> new Intersection<>(isectT.shape, ray.apply(isectT.t)));
    }
}
