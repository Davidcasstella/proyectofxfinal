package co.edu.uptc.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import co.edu.uptc.App;

public class LoginController {

    @FXML
    private TextField emailField;  // Campo de correo electrónico

    @FXML
    private PasswordField passwordField;  // Campo de contraseña

    // Método para manejar el inicio de sesión
    @FXML
    private void handleLogin() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validación de credenciales (esto debe ser personalizado)
        if (validateCredentials(email, password)) {
            // Si las credenciales son correctas, cambiar a la siguiente pantalla
            App.setRoot("PantallaBienvenida");  // Cambia "PantallaBienvenida" por tu nombre de vista
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error
            System.out.println("Credenciales incorrectas");
        }
    }

    // Método de validación de credenciales (simulado)
    private boolean validateCredentials(String email, String password) {
        // Lógica de validación, aquí puedes verificar en una base de datos, archivo, etc.
        return email.equals("usuario@dominio.com") && password.equals("12345");
    }

    // Función de registro (puedes implementarla según tus necesidades)
    @FXML
    private void handleRegister() {
        System.out.println("Pantalla de Registro");
        // Lógica de navegación a la pantalla de registro
    }

    // Función para manejar login con Google (puedes integrar OAuth2 si lo deseas)
    @FXML
    private void handleGoogleLogin() {
        System.out.println("Login con Google");
        // Implementación del login con Google
    }
}
