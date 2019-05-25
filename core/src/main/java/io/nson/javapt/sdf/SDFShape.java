package io.nson.javapt.sdf;

import io.nson.javapt.autodiff.*;
import io.nson.javapt.core.*;
import io.nson.javapt.geom.*;
import org.apache.logging.log4j.*;

import java.util.OptionalDouble;

public class SDFShape extends AbstractShape implements SDF {
    private static final Logger logger = LogManager.getLogger(SDFShape.class);

    private final SDF sdf;

    public SDFShape(String name, Material material, SDF sdf) {
        super(name, material);
        this.sdf = sdf;
    }

    private SDFShape() {
        super(null, null);
        this.sdf = null;
    }

    public SDFShape build() {
        if (sdf instanceof DeferredSDF) {
            final DeferredSDF deferredSDF = (DeferredSDF)sdf;
            return new SDFShape(name, material, deferredSDF.build());
        } else {
            return this;
        }
    }

    @Override
    public OptionalDouble intersect(Ray ray, double eps) {

        double t = eps * 100;

        for (int i = 0; i < maxSteps; ++i) {
            final Point3d p = ray.apply(t);
            final double dist = distance(p);
            if (dist < eps) {
                //logger.info("intersect({}) = {} dist={}", t, ray.apply(t), dist);
                return OptionalDouble.of(t);
            } else if (dist > Integer.MAX_VALUE) {
                return OptionalDouble.empty();
            }

            t += dist;
        }

        return OptionalDouble.of(t);
    }

    @Override
    public Vector3d normal(Point3d p) {
//        final Vector3d n = deriveNormal(p);
//        logger.info("Normal({}) -> {}", p, n);
        return deriveNormal(p);
    }

    private Vector3d deriveNormal(Point3d p) {
        final double dx = distance(new DualPoint3d(new DualNd(p.x, 1), DualNd.real(p.y), DualNd.real(p.z))).d;
        final double dy = distance(new DualPoint3d(DualNd.real(p.x), new DualNd(p.y, 1), DualNd.real(p.z))).d;
        final double dz = distance(new DualPoint3d(DualNd.real(p.x), DualNd.real(p.y), new DualNd(p.z, 1))).d;
        return new Vector3d(dx, dy, dz).normalise();
    }

    private Vector3d estimateNormal(Point3d p) {
        double dx;
        double dy;
        double dz;

        double eps = N_EPS;

        do {
            dx = distance(new Point3d(p.x + eps, p.y, p.z)) - distance(new Point3d(p.x - eps, p.y, p.z));
            dy = distance(new Point3d(p.x, p.y + eps, p.z)) - distance(new Point3d(p.x, p.y - eps, p.z));
            dz = distance(new Point3d(p.x, p.y, p.z + eps)) - distance(new Point3d(p.x, p.y, p.z - eps));

            eps *= 10f;
        } while (dx == 0.0f && dy == 0.0f && dz == 0.0f);

        return new Vector3d(dx, dy, dz).normalise();
    }

    @Override
    public DualNd distance(DualPoint3d p) {
        return sdf.distance(p);
    }
}
