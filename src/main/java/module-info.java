module dev.labs.s3.anonymousclassexample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens dev.labs.s3.anonymousclassexample to javafx.fxml;
    exports dev.labs.s3.anonymousclassexample;
}