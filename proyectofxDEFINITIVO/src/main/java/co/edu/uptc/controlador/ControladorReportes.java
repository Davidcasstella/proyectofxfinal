package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControladorReportes {

    @FXML
    private Button btnDescargarReporte;

    @FXML
    private Button btnReciboVoluntarios;

    @FXML
    public void initialize() {
        btnDescargarReporte.setOnAction(e -> {
            System.out.println("Descargando reporte general...");
            // Aquí agrega la lógica para descargar el reporte general
        });

        btnReciboVoluntarios.setOnAction(e -> {
            System.out.println("Mostrando recibos de voluntarios...");
            // Aquí agrega la lógica para mostrar recibos o descargar recibos de donación
        });
    }
}
