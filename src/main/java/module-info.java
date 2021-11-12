module com.example.ai_assingment1_8puzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.ai_assingment1_8puzzle to javafx.fxml;
    exports com.example.ai_assingment1_8puzzle;
}