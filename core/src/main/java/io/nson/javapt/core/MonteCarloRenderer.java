package io.nson.javapt.core;

import io.nson.javapt.geom.*;
import org.apache.logging.log4j.*;

public class MonteCarloRenderer extends Renderer {
    private static final Logger logger = LogManager.getLogger(MonteCarloRenderer.class);

    public MonteCarloRenderer(int width, int height, int threads, Scene scene) {
        super(width, height, threads, scene);
    }

    @Override
    public RGB radiance(RNG rng, Ray ray, int depth, RGB acc, RGB att) {
        return scene.intersect(ray)
                .map(isectP -> {
                    final Vector3d n = isectP.shape.normal(isectP.t);
                    final Vector3d nl = n.dot(ray.dir) < 0 ? n : n.neg();
//logger.info("ray={} p={} isect={} acc={} nl={}", ray, isectP.t, isectP.shape, acc, nl);
                    final int newDepth = depth + 1;

                    final RGB colour = isectP.shape.material().colour().mult(att);
                    final RGB acc2 = acc.add(isectP.shape.material().emission().mult(att));


                    if (newDepth > 10) {
                        // Modified Russian roulette.
                        final double max = colour.max() * MathUtil.sqr(1.0 - (double)(depth - 10) / Renderer.MAX_DEPTH);
                        final double rnd = rng.nextDouble01();
                        if (rnd >= max) {
                            return acc2;
                        } else {
                            return isectP.shape.material()
                                    .radiance(rng, this, ray, newDepth, isectP.t, n, nl, acc2, colour.div(max));
                        }
                    } else {
                        return isectP.shape.material()
                                .radiance(rng, this, ray, newDepth, isectP.t, n, nl, acc2, colour);
                    }
                }).orElseGet(() -> acc);
    }
}
