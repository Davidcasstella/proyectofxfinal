module co.edu.uptc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens co.edu.uptc.controlador to javafx.fxml; // Asegúrate de permitir el acceso a tu paquete controlador
    exports co.edu.uptc;  // Exporta el paquete para que sea accesible
}
