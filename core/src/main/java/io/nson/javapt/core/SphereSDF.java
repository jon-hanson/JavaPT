package io.nson.javapt.core;

import io.nson.javapt.autodiff.*;
import org.apache.logging.log4j.*;

public class SphereSDF extends AbstractSDF {
    private static final Logger logger = LogManager.getLogger(SphereSDF.class);

    public final Material material;
    public final Point3d centre;
    public final double radius;

    public SphereSDF(String name, Material material, Point3d centre, double radius) {
        super(name, material);
        this.material = material;
        this.centre = centre;
        this.radius = radius;
    }

    private SphereSDF() {
        super(null, null);
        this.material = null;
        this.centre = null;
        this.radius = 0;
    }

    @Override
    public double distance(Point3d p) {
        return p.sub(centre).length() - radius;
    }

    @Override
    public DualNd distance(DualPoint3d p) {
        return p.sub(DualPoint3d.valueOf(centre)).length().sub(DualNd.real(radius));
    }
//
//    @Override
//    public Vector3d normal(Point3d p) {
//        //logger.info("Normal({}) -> {}", p, p.sub(centre).normalise());
//        return p.sub(centre).normalise();
//    }
}
