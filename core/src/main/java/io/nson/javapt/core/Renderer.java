package io.nson.javapt.core;

import io.nson.javapt.geom.*;
import org.apache.logging.log4j.*;
import org.typemeta.funcj.util.Exceptions;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

public abstract class Renderer implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(Renderer.class);

    protected static final int MAX_DEPTH = 512;

    protected final int width;
    protected final int height;
    protected final ExecutorService executor;
    protected final Scene scene;

    private final Vector3d cx;
    private final Vector3d cy;

    protected Renderer(int width, int height, int threads, Scene scene) {
        this.width = width;
        this.height = height;
        this.executor = Executors.newFixedThreadPool(threads);
        this.scene = scene;
        this.cx = new Vector3d(width * scene.camera.fov / height, 0.0, 0.0);
        this.cy = cx.cross(scene.camera.ray.dir)
                .normalise()
                .mult(scene.camera.fov);
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }

    public Vector3d camRay(double xs, double ys) {
        return cx.mult(xs / width - 0.5)
                .add(cy.mult(ys / height - 0.5));
    }

    public interface RowSink {
        void accept(int y, SuperSamp[] cells);
    }

    public void render(
            int frameI,
            RowSink rowSink
    ) {
        logger.info("Frame " + frameI);

        final List<Future<?>> futs = IntStream.range(0, height)
                .map(h -> height - 1 - h)
                .mapToObj(y -> executor.submit(() -> {
                    final SuperSamp[] row = render(frameI, y);
                    rowSink.accept(y, row);
                })).collect(toList());

        futs.forEach(f -> Exceptions.wrap(() -> f.get()));
    }

    public SuperSamp[] render(int i, int y) {
        final RNG rng = new RNG(i, y);
        return IntStream.range(0, width)
                .mapToObj(x -> render(rng, x, y))
                .toArray(SuperSamp[]::new);
    }

    public SuperSamp render(RNG rng, int x, int y) {
//        if (x != 265 && y != 300) {
//            return SuperSamp.BLACK;
//        }
        return new SuperSamp(
            subPixelRad(rng, x, y, 0, 0),
            subPixelRad(rng, x, y, 1, 0),
            subPixelRad(rng, x, y, 0, 1),
            subPixelRad(rng, x, y, 1, 1)
        );
    }

    public RGB subPixelRad(RNG rng, int x, int y, double cx, double cy) {

        final double d1 = rng.nextDouble01();
        final double d2 = rng.nextDouble01();

        final double dx = MathUtil.tent(d1);
        final double dy = MathUtil.tent(d2);

        final double sx = x + (0.5 + cx + dx) * 0.5;
        final double sy = y + (0.5 + cy + dy) * 0.5;

        final Vector3d dir = scene.camera.ray.dir.add(camRay(sx, sy));
        final Point3d origin = scene.camera.ray.origin;

        final Ray ray = Ray.of(origin, dir);
        return radiance(rng, ray, 0, RGB.BLACK, RGB.WHITE);
    }

    public abstract RGB radiance(
            RNG rng,
            Ray ray,
            int depth,
            RGB acc,
            RGB att
    );
}
