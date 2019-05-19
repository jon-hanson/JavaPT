package io.nson.javapt.ui;

import io.nson.javapt.core.*;
import org.apache.logging.log4j.*;

import java.util.Optional;

public class Debug {
    private static final Logger logger = LogManager.getLogger(Debug.class);

    public static void main(String[] args) {
        final Config cfg = new Config(
                "scenes/cornell3.json",
                800, 800,
                16,
                0,
                1,
                true,
                Optional.empty(),
                Optional.empty()
        );
        //test(cfg, "scenes/cornell2.json");
        test(cfg, "scenes/cornell3.json");
    }

    static void test(Config cfg, String name) {
        final Renderer rdr = new MonteCarloRenderer(
                cfg.width,
                cfg.height,
                cfg.threads,
                SceneIO.load(name)
        );

//        for (long b = 0; b < 100000; ++b) {
//            for (long a = 0; a < 100000; ++a) {

                final RNG rng = new RNG(5, 99999);

                final int sx = 265;
                final int sy = 300;

                final RGB ss = rdr.subPixelRad(rng, sx, sy, 1, 1);
                logger.info("RGB={}", ss);
//
//                if (ss.max() > 1e-8) {
//                    logger.info("{}, {} -> {}", a, b, ss);
//                    break;
//                    //        logger.info("result.c10={}", ss.c10);
//                    //        logger.info("result.c01={}", ss.c01);
//                    //        logger.info("result.c11={}", ss.c11);
//                }
//            }
//        }
    }
}
