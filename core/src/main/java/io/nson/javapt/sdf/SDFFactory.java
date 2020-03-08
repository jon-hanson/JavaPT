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
    public SDF torus(double inR, double outR) {
        return p -> {
            final DualNd l = p.x.sqr().add(p.z.sqr()).sqrt();
            final DualNd q = l.sub(DualNd.real(inR));
            final DualNd l2 = q.sqr().add(p.y.sqr()).sqrt();
            return l2.sub(DualNd.real(outR));
        };
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

    @Override
    public SDF intersect(SDF lhs, SDF rhs) {
        return p -> lhs.distance(p).max(rhs.distance(p));
    }

    @Override
    public SDF subtract(SDF lhs, SDF rhs) {
        return p -> lhs.distance(p).max(rhs.distance(p).neg());
    }
}
