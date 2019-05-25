module javapt.core {
    requires org.apache.logging.log4j;
    requires org.typemeta.funcj.core;
    requires org.typemeta.funcj.codec;
    requires org.typemeta.funcj.json;

    opens io.nson.javapt.core;
    opens io.nson.javapt.geom;
    opens io.nson.javapt.sdf;

    exports io.nson.javapt.core;
}