module proyectofxdefinitivo {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires javafx.graphics;
    requires com.google.gson;
    
    // Exportar paquetes principales
    exports co.edu.uptc;
    exports co.edu.uptc.controlador;
    exports co.edu.uptc.modelo;
    exports co.edu.uptc.servicio;
    exports co.edu.uptc.persistencia;
    exports co.edu.uptc.vista;

    // Abrir paquetes para JavaFX FXML reflection
    opens co.edu.uptc to javafx.fxml;
    opens co.edu.uptc.controlador to javafx.fxml;
    
    // Abrir paquetes para Gson reflection (todos los modelos)
    opens co.edu.uptc.modelo to com.google.gson;
    opens co.edu.uptc.persistencia to com.google.gson;
    
}