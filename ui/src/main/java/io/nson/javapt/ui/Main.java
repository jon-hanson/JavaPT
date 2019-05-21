package io.nson.javapt.ui;

import io.nson.javapt.core.*;
import org.apache.logging.log4j.*;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.IntStream;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        final Config cfg = new Config(
                "scenes/cornell3.json",
                800, 800,
                64,
                0,
                8,
                true,
                Optional.empty(),
                Optional.empty()
        );

        final Main main = new WndMain(cfg);
        main.render();
        main.close();
    }

    protected final Config cfg;

    protected final int w;
    protected final int h;
    protected final Scene scene;
    protected final Renderer rdr;
    protected final Frame renderData;
    protected final BufferedImage image;
    protected final Consumer<Integer> renderFn;

    public Main(Config cfg) {
        this.cfg = cfg;
        this.w = cfg.width;
        this.h = cfg.height;
        this.scene = SceneIO.load(cfg.sceneFile);
        this.rdr = new MonteCarloRenderer(w, h, cfg.threads, scene);
        this.renderData = new Frame(cfg.seed, w, h);
        this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        this.renderFn = this::renderFrame;
    }

    protected boolean isClosing() {
        return false;
    }

    public void close() {
        rdr.close();
    }

    protected void render() {
        IntStream.range(0, cfg.frames)
                .forEach(this::render);

        logger.info("Done");

        cfg.imageFile.ifPresent(file -> saveImage(file, image));
    }

    private void render(int frameI) {
        renderFn.accept(frameI);
    }

    protected void renderFrame(int frameI) {
        if (!isClosing()) {
            rdr.render(frameI, (y, lss) -> mergeRow(frameI, y, lss));
        }
    }

    protected void mergeRow(int frameI, int y, SuperSamp[] cells) {
        final Frame.Row row = renderData.merge(y, new Frame.Row(cells), frameI);
        writeRow(y, row);
    }

    protected void writeRow(int y, Frame.Row row) {
        final int sy = h - y - 1;
        for (int sx = 0; sx < w; ++sx) {
            image.setRGB(sx, sy, ColourUtils.colVecToInt(row.columns[sx].clamp()));
        }
    }

    protected void saveImage(String name, RenderedImage image) {
        final File file = new File(name);
        final int dotPos = name.lastIndexOf('.');
        final String format = (dotPos != -1) ? name.substring(dotPos + 1) : "png";

        logger.info("Saving to file '" + name + "' as format " + format);
        try {
            if (!ImageIO.write(image, format, file)) {
                logger.info("ERROR: filename prefix '" + format + " not recognised by ImageIO as a format");
            }
        } catch (IOException ex) {
            logger.error("Failed to save file", ex);
            throw new RuntimeException("Failed to save file", ex);
        }
    }
}
