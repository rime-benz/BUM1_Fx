module ehu.eus.bum1_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ehu.eus.bum1_fx to javafx.fxml;
    exports ehu.eus.bum1_fx;
    exports Clui;
    opens Clui to javafx.fxml;
}