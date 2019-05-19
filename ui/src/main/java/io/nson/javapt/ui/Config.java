package io.nson.javapt.ui;

import java.util.Optional;

public class Config {
    public final String sceneFile;
    public final int width;
    public final int height;
    public final int frames;
    public final long seed;
    public final int threads;
    public final boolean display;
    public final Optional<String> imageFile;
    public final Optional<String> framesDir;

    public Config(
            String sceneFile,
            int width,
            int height,
            int frames,
            long seed,
            int threads,
            boolean display,
            Optional<String> imageFile,
            Optional<String> framesDir
    ) {
        this.sceneFile = sceneFile;
        this.width = width;
        this.height = height;
        this.frames = frames;
        this.seed = seed;
        this.threads = threads;
        this.display = display;
        this.imageFile = imageFile;
        this.framesDir = framesDir;
    }
}
