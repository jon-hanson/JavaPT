package io.nson.javapt.sdf;

import io.nson.javapt.geom.Vector3d;

public interface SDFAlgebra<T> {
    T sphere(double radius);

    T displace(T target, Vector3d shift);

    T union(T lhs, T rhs);
}
