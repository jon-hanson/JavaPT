package io.nson.javapt.sdf;

import io.nson.javapt.autodiff.*;
import io.nson.javapt.core.SDF;
import io.nson.javapt.geom.Point3d;

public class SierpinskiSDF implements SDF {

    private static final int DEPTH = 8;

    private final Point3d a0;
    private final Point3d a1;
    private final Point3d a2;
    private final Point3d a3;

    public SierpinskiSDF(Point3d a0, Point3d a1, Point3d a2, Point3d a3) {
        this.a0 = a0;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }

    private SierpinskiSDF() {
        this.a0 = null;
        this.a1 = null;
        this.a2 = null;
        this.a3 = null;
    }

    @Override
        public DualNd distance(DualPoint3d p) {
        final DualPoint3d a0d = DualPoint3d.valueOf(a0);
        final DualPoint3d a1d = DualPoint3d.valueOf(a1);
        final DualPoint3d a2d = DualPoint3d.valueOf(a2);
        final DualPoint3d a3d = DualPoint3d.valueOf(a3);

        for (int n = 0; n < DEPTH; ++n) {
            DualPoint3d c = a0d;
            DualNd dist = p.sub(a0d).lengthSquared();

            DualNd d = p.sub(a1d).lengthSquared();
            if (d.v < dist.v) {
                c = a1d;
            }

            d = p.sub(a2d).lengthSquared();
            if (d.v < dist.v) {
                c = a2d;
            }

            d = p.sub(a3d).lengthSquared();
            if (d.v < dist.v) {
                c = a3d;
            }


            p = p.mult(2).sub(c).asPoint();
        }

        return p.length().mult(DualNd.real(2).pow(-DEPTH));
    }
}
