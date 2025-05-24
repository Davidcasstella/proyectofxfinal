module co.edu.uptc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens co.edu.uptc.controlador to javafx.fxml;
    exports co.edu.uptc;
    
}
