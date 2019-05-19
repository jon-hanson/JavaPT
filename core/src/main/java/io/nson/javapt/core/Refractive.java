package io.nson.javapt.core;

public class Refractive implements Material {
    public final RGB colour;
    public final RGB emColour;

    public Refractive(RGB colour, RGB emColour) {
        this.colour = colour;
        this.emColour = emColour;
    }

    private Refractive() {
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
        final double nc = 1.0;
        final double nt = 1.5;
        final Ray reflRay = Ray.of(p, ray.dir.sub(n.mult(2.0 * n.dot(ray.dir))));
        final boolean into = n.dot(nl) > 0.0;
        final double nnt = into ? nc / nt : nt / nc;
        final double ddn = ray.dir.dot(nl);
        final double cos2t = 1.0 - nnt * nnt * (1.0 - ddn * ddn);

        if (cos2t < 0.0) {
            // Total internal reflection.
            return rdr.radiance(rng, reflRay, depth, acc, att);
        } else {
            final double sign = into ? 1.0 : -1.0;
            final Vector3d tdir = (ray.dir.mult(nnt).sub(n.mult(sign * (ddn * nnt + Math.sqrt(cos2t))))).normalise();
            final double a = nt - nc;
            final double b = nt + nc;
            final double r0 = a * a / (b * b);
            final double c = 1.0 - (into ? -ddn : tdir.dot(n));
            final double re = r0 + (1.0 - r0) * c * c * c * c * c;
            final double tr = 1.0 - re;
            final double q = 0.25 + re / 2.0;
            final double rp = re / q;
            final double tp = tr / (1.0 - q);

            final double rnd = rng.nextDouble01();
            if (rnd < q) {
                return rdr.radiance(rng, reflRay, depth, acc, att.mult(rp));
            } else {
                return rdr.radiance(rng, Ray.of(p, tdir), depth, acc, att.mult(tp));
            }
        }
    }
}
