package io.nson.javapt.sdf;

import io.nson.javapt.core.SDF;
import io.nson.javapt.geom.Vector3d;
import org.typemeta.funcj.json.model.*;

public enum SDFSerialise implements SDFAlgebra<JsValue> {
    INSTANCE;

    public static SDF decode(JsValue jsv) {
        final JsObject jso = jsv.asObject();
        final JsObject.Field field = jso.stream().findFirst().orElseThrow();
        switch (field.name()) {
            case "sphere": return decodeSphere(field.value().asObject());
            case "displace": return decodeDisplace(field.value().asObject());
            case "union": return decodeUnion(field.value().asObject());
            default: throw new IllegalArgumentException("Unrecognised SDF tag '" + field.name() + "'");
        }
    }

    private static JsValue encode(Vector3d v) {
        return JSAPI.obj(
                JSAPI.field("dx", JSAPI.num(v.dx)),
                JSAPI.field("dy", JSAPI.num(v.dy)),
                JSAPI.field("dz", JSAPI.num(v.dz))
        );
    }

    public static Vector3d decodeVector3d(JsValue jsv) {
        final JsObject jso = jsv.asObject();
        return new Vector3d(
                jso.get("dx").asNumber().value(),
                jso.get("dy").asNumber().value(),
                jso.get("dz").asNumber().value()
        );
    }

    @Override
    public JsValue sphere(double radius) {
        return JSAPI.obj(
                JSAPI.field("sphere", JSAPI.obj(
                        JSAPI.field("radius", JSAPI.num(radius))
                ))
        );
    }

    public static SDF decodeSphere(JsObject jso) {
        return SDFFactory.INSTANCE.sphere(jso.get("radius").asNumber().value());
    }

    @Override
    public JsValue displace(JsValue target, Vector3d shift) {
        return JSAPI.obj(
                JSAPI.field("displace", JSAPI.obj(
                        JSAPI.field("target", target),
                        JSAPI.field("shift", encode(shift))
                ))
        );
    }

    public static SDF decodeDisplace(JsObject jso) {
        return SDFFactory.INSTANCE.displace(
                decode(jso.get("target")),
                decodeVector3d(jso.get("shift"))
        );
    }

    @Override
    public JsValue union(JsValue lhs, JsValue rhs) {
        return JSAPI.obj(
                JSAPI.field("union", JSAPI.obj(
                        JSAPI.field("lhs", lhs),
                        JSAPI.field("rhs", rhs)
                ))
        );
    }

    public static SDF decodeUnion(JsObject jso) {
        return SDFFactory.INSTANCE.union(
                decode(jso.get("lhs")),
                decode(jso.get("rhs"))
        );
    }
}
