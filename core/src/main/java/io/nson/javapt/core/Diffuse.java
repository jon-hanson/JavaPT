package io.nson.javapt.core;

import io.nson.javapt.geom.*;

public class Diffuse implements Material {

    public final RGB colour;
    public final RGB emColour;
    public final boolean emis;

    public Diffuse(RGB colour, RGB emColour, boolean emis) {
        this.colour = colour;
        this.emColour = emColour;
        this.emis = emis;
    }

    private Diffuse() {
        this.colour = null;
        this.emColour = null;
        this.emis = false;
    }

    public Diffuse(RGB colour, RGB emColour) {
        this(colour, emColour, false);
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
    public RGB emission() {
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
        final double d1 = rng.nextDouble01();
        final double r2 = rng.nextDouble01();
        final double r1 = 2.0 * Math.PI * d1;
        final double r2s = Math.sqrt(r2);

        final Vector3d w = nl;
        final Vector3d u = (Math.abs(w.dx) > 0.1 ? Vector3d.Y_UNIT : Vector3d.X_UNIT).cross(w).normalise();
        final Vector3d v = w.cross(u);

        final Vector3d dir =
                u.mult(Math.cos(r1) * r2s)
                        .add(v.mult(Math.sin(r1) * r2s))
                        .add(w.mult(Math.sqrt(1.0 - r2))
                ).normalise();

        return rdr.radiance(rng, Ray.of(p, dir), depth, acc, att);
    }
}
