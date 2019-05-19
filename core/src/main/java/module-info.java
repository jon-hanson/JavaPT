module javapt.core {
    requires org.apache.logging.log4j;
    requires org.typemeta.funcj.core;
    requires org.typemeta.funcj.codec;

    opens io.nson.javapt.core;

    exports io.nson.javapt.core;
}