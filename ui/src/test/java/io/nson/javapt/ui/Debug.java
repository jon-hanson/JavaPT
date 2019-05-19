package io.nson.javapt.ui;

import io.nson.javapt.core.*;
import org.apache.logging.log4j.*;

import java.util.Optional;

public class Debug {
    private static final Logger logger = LogManager.getLogger(Debug.class);

    public static void main(String[] args) {
        final Config cfg = new Config(
                "scenes/cornell2.json",
                800, 800,
                16,
                0,
                1,
                true,
                Optional.empty(),
                Optional.empty()
        );

        final Renderer rdr = new MonteCarloRenderer(
                cfg.width,
                cfg.height,
                cfg.threads,
                SceneIO.load(cfg.sceneFile)
        );

        final RNG rng = new RNG(0, 0);
        final int sx = 265;
        final int sy = 300;

        final SuperSamp ss = rdr.render(rng, sx, sy);

        logger.info("result.c00={}", ss.c00);
        logger.info("result.c10={}", ss.c10);
        logger.info("result.c01={}", ss.c01);
        logger.info("result.c11={}", ss.c11);
    }
}
