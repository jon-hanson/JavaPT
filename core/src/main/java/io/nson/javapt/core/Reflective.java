package io.nson.javapt.core;

import io.nson.javapt.geom.*;

public class Reflective implements Material {
    public final RGB colour;
    public final RGB emColour;

    public Reflective(RGB colour, RGB emColour) {
        this.colour = colour;
        this.emColour = emColour;
    }

    private Reflective() {
        this.colour = null;
        this.emColour = null;
    }

    @Override
    public RGB colour() {
        return colour;
    }

    @Override
    public RGB emColour() {
        return emColour;
    }

    @Override
    public RGB radiance(
            RNG rng,
            Renderer rdr,
            Ray ray,
            int depth,
            Point3d p,
            Vector3d n,
            Vector3d nl,
            RGB acc,
            RGB att
    ) {
        final Vector3d d = ray.dir.sub(n.mult(2 * n.dot(ray.dir)));
        return rdr.radiance(rng, Ray.of(p, d), depth, acc, att);
    }
}
