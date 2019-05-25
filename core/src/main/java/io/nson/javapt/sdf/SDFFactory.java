package io.nson.javapt.sdf;

import io.nson.javapt.autodiff.*;
import io.nson.javapt.core.SDF;
import io.nson.javapt.geom.*;

public enum SDFFactory implements SDFAlgebra<SDF> {
    INSTANCE;

    @Override
    public SDF sphere(double radius) {
        return p -> p.length().sub(DualNd.real(radius));
    }

    @Override
    public SDF displace(SDF target, Vector3d shift) {
        final DualVector3d dv = DualVector3d.valueOf(shift);
        return p -> target.distance(p.sub(dv));
    }

    @Override
    public SDF union(SDF lhs, SDF rhs) {
        return p -> lhs.distance(p).min(rhs.distance(p));
    }
}
