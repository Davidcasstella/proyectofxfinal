package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ControladorTuPropiaContraseña {

    @FXML
    private PasswordField txtNuevaContrasena;

    @FXML
    private PasswordField txtConfirmaContrasena;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnSiguiente;

    // Método que se llama al presionar el botón "Siguiente" del formulario
    @FXML
    private void handleSiguiente() {
        lblMensaje.setStyle("-fx-text-fill: red;");
        String nuevaContrasena = txtNuevaContrasena.getText();
        String confirmaContrasena = txtConfirmaContrasena.getText();

        if (nuevaContrasena.isEmpty() || confirmaContrasena.isEmpty()) {
            lblMensaje.setText("Por favor, complete ambos campos.");
            return;
        }

        if (nuevaContrasena.length() < 6) {
            lblMensaje.setText("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        if (!nuevaContrasena.equals(confirmaContrasena)) {
            lblMensaje.setText("Las contraseñas no coinciden. Inténtelo de nuevo.");
            return;
        }

        // Si pasa todas las validaciones
        lblMensaje.setStyle("-fx-text-fill: green;");
        lblMensaje.setText("¡Contraseña actualizada correctamente!");

        // Aquí podrías agregar la lógica para guardar la contraseña o navegar a otra pantalla
    }

      @FXML
    private void handleSiguienteBtn() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaDashboard");  // Recargar la vista de la pantalla principal
    }
    
       @FXML
    private void handleRecargar() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaCreaTuContraseña");  // Recargar la vista de la pantalla principal
    }
        @FXML
    private void handleAnterior() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperaciontres");  // Recargar la vista de la pantalla principal
    }

}
