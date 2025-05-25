module co.edu.uptc {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires java.base;
    // Aquí agregas esta línea para leer el módulo anónimo, en Java 9+ es así:
    // pero java no soporta explícitamente esta sintaxis, así que en la práctica:
    // la librería no modular se considera en unnamed module y debe abrirse el paquete.

    opens co.edu.uptc.controlador to javafx.fxml;
    opens co.edu.uptc.persistencia;
    opens co.edu.uptc.modelo;

    exports co.edu.uptc;
}
