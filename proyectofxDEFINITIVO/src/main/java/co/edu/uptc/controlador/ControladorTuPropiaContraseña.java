package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.servicio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ControladorTuPropiaContraseña {

    @FXML private PasswordField txtNuevaContrasena;
    @FXML private PasswordField txtConfirmaContrasena;
    @FXML private Label lblMensaje;
    @FXML private Button btnSiguiente;

    private UsuarioService usuarioService;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();
    }

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

        if (!nuevaContrasena.matches(".*[A-Za-z].*") || !nuevaContrasena.matches(".*\\d.*")) {
            lblMensaje.setText("La contraseña debe contener al menos una letra y un número.");
            return;
        }

        if (!nuevaContrasena.equals(confirmaContrasena)) {
            lblMensaje.setText("Las contraseñas no coinciden. Inténtelo de nuevo.");
            return;
        }

        // Obtener el usuario que está recuperando la contraseña
        Usuario usuario = ControladorReceperacionDos.getUsuarioRecuperacion();
        
        if (usuario != null) {
            // Actualizar la contraseña
            if (usuarioService.actualizarPassword(usuario.getEmail(), nuevaContrasena)) {
                lblMensaje.setStyle("-fx-text-fill: green;");
                lblMensaje.setText("¡Contraseña actualizada correctamente!");
                
                // Esperar un momento y luego redirigir al login
                try {
                    Thread.sleep(1000);
                    App.setRoot("PantallaLogin");
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                lblMensaje.setText("Error al actualizar la contraseña.");
            }
        } else {
            lblMensaje.setText("Error: No se encontró el usuario.");
        }
    }

    @FXML
    private void handleSiguienteBtn() throws IOException {
        App.setRoot("PantallaLogin");
    }
    
    @FXML
    private void handleRecargar() throws IOException {
        App.setRoot("PantallaCreaTuContraseña");
    }
    
    @FXML
    private void handleAnterior() throws IOException {
        App.setRoot("PatallaCodigoRecuperaciontres");
    }
}