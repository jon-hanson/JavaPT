package io.nson.javapt.sdf;

import io.nson.javapt.autodiff.*;
import io.nson.javapt.core.SDF;
import org.typemeta.funcj.json.model.JsValue;

public class DeferredSDF implements SDF {
    private final JsValue value;

    public DeferredSDF(JsValue value) {
        this.value = value;
    }

    private DeferredSDF() {
        this.value = null;
    }

    public SDF build() {
        return SDFSerialise.decode(value);
    }

    @Override
    public DualNd distance(DualPoint3d p) {
        throw new IllegalStateException();
    }
}
