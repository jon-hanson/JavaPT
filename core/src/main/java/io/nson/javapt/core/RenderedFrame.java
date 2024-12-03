package io.nson.javapt.core;

public class RenderedFrame {

    public static class Row {
        public final SuperSamp[] columns;

        public Row(SuperSamp[] columns) {
            this.columns = columns;
        }
    }

    public final long seed;
    public final int width;
    public final int height;
    public final Row[] rows;

    public RenderedFrame(long seed, int width, int height, Row[] rows) {
        this.seed = seed;
        this.width = width;
        this.height = height;
        this.rows = rows;
    }

    public RenderedFrame(long seed, int width, int height) {
        this(seed, width, height, new Row[height]);
    }

    public Row apply(int r) {
        return rows[r];
    }

    public SuperSamp apply(int r, int c) {
        return rows[r].columns[c];
    }

    public Row merge(int y, RenderedFrame.Row row, int n) {
        if (n == 0) {
            rows[y] = row;
        } else {
            final Row row2 = rows[y];
            for (int x = 0; x < row2.columns.length; ++x) {
                row2.columns[x].mergeFrom(row.columns[x], n);
            }
        }
        return rows[y];
    }
}
