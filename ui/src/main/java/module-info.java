module kalbarri.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires javapt.core;

    opens io.nson.javapt.ui to javafx.graphics, javafx.fxml;
}