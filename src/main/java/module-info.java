module com.example.quadclashjodumodhu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.quadclashjodumodhu to javafx.fxml;
    exports com.example.quadclashjodumodhu;
}