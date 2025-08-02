module com.example.quadclashjodumodhu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.quadclashjodumodhu to javafx.fxml;
    opens com.example.quadclashjodumodhu.MyGame.UI to javafx.fxml;

    exports com.example.quadclashjodumodhu;
    exports com.example.quadclashjodumodhu.MyGame.UI;
}