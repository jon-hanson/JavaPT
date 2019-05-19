package io.nson.javapt.core;

import java.util.*;

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
        return shapes.stream()
                .map(shp -> {
                    final OptionalDouble optIsect = shp.intersect(ray);
                    return optIsect.isPresent() ?
                            Optional.of(new Intersection<>(shp, optIsect.getAsDouble())) :
                            Optional.<Scene.Intersection<Double>>empty();
                }).filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingDouble(l -> l.t))
                .map(isectT -> new Intersection<>(isectT.shape, ray.apply(isectT.t)));
    }
}
