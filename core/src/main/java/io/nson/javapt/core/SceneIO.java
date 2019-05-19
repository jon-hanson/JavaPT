package io.nson.javapt.core;

import org.apache.logging.log4j.*;
import org.typemeta.funcj.codec.Codecs;
import org.typemeta.funcj.codec.json.JsonCodecCore;

import java.io.*;
import java.util.List;

public class SceneIO {
    private static final Logger logger = LogManager.getLogger(SceneIO.class);
    private static final JsonCodecCore codec;

    static {
        codec = Codecs.jsonCodec();
        codec.config().registerAllowedPackage(io.nson.javapt.core.Scene.class.getPackage());

    }

    public static Scene load(String fileName) {
        logger.info("Loading scene form " + fileName);
        try(final FileReader rdr = new FileReader(fileName)) {
            return codec.decode(Scene.class, rdr);
        } catch (IOException ex) {
            logger.error("Failed to load file", ex);
            throw new RuntimeException("Failed to load file", ex);
        }
    }

    public static void save(Scene scene, String fileName) {
        logger.info("Saving scene to " + fileName);
        try(final FileWriter wtr = new FileWriter(fileName)) {
            codec.encode(Scene.class, scene, wtr);
        } catch (IOException ex) {
            logger.error("Failed to save file", ex);
            throw new RuntimeException("Failed to save file", ex);
        }
    }
}
