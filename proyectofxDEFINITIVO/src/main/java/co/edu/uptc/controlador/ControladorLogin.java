package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ControladorLogin {

    // Login fields
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMeCheckBox;
    @FXML private Button loginButton;
    @FXML private Label emailErrorLabel;
    @FXML private Label passwordErrorLabel;
    
    // Register fields
    @FXML private VBox loginForm;
    @FXML private VBox registerForm;
    @FXML private Label tabLogin;
    @FXML private Label tabRegister;

    @FXML private TextField fullNameField;
    @FXML private TextField emailRegisterField;
    @FXML private TextField recoveryField;
    @FXML private PasswordField passwordRegisterField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;

    @FXML private ImageView imageView;

    // Ruta imagen, ajústala a tu proyecto
    private static final String IMAGE_PATH = "/co/edu/uptc/imagenes/images (1).jpg";

    @FXML
    public void initialize() {
        // Cargar imagen
        Image img = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        imageView.setImage(img);

        // Inicial: mostrar formulario login
        mostrarLogin();
    }

    // Mostrar formulario login y ocultar registro
    @FXML
    private void mostrarLogin() {
        tabLogin.setStyle("-fx-text-fill: #0066cc; -fx-font-weight: bold;");
        tabRegister.setStyle("-fx-text-fill: #999999; -fx-font-weight: normal;");
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        registerForm.setVisible(false);
        registerForm.setManaged(false);
        limpiarErroresLogin();
        limpiarErroresRegistro();
    }

    // Mostrar formulario registro y ocultar login
    @FXML
    private void mostrarRegistro() {
        tabRegister.setStyle("-fx-text-fill: #0066cc; -fx-font-weight: bold;");
        tabLogin.setStyle("-fx-text-fill: #999999; -fx-font-weight: normal;");
        registerForm.setVisible(true);
        registerForm.setManaged(true);
        loginForm.setVisible(false);
        loginForm.setManaged(false);
        limpiarErroresLogin();
        limpiarErroresRegistro();
    }

    // Validar email (simple)
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Validar contraseña (mínimo 6 chars, al menos una letra y un número)
    private boolean isValidPassword(String password) {
        return password.length() >= 6 &&
               password.matches(".*[A-Za-z].*") &&
               password.matches(".*\\d.*");
    }

    // Validación en tiempo real email login
    @FXML
    private void validateEmail(KeyEvent event) {
        String email = emailField.getText();
        if (email.isEmpty()) {
            setError(emailField, emailErrorLabel, "El email es requerido.");
        } else if (!isValidEmail(email)) {
            setError(emailField, emailErrorLabel, "El email no es válido.");
        } else {
            clearError(emailField, emailErrorLabel);
        }
    }

    // Validación en tiempo real password login
    @FXML
    private void validatePassword(KeyEvent event) {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            setError(passwordField, passwordErrorLabel, "La contraseña es requerida.");
        } else if (!isValidPassword(password)) {
            setError(passwordField, passwordErrorLabel, "Debe tener al menos 6 caracteres, una letra y un número.");
        } else {
            clearError(passwordField, passwordErrorLabel);
        }
    }

    // Manejar login
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty()) {
            setError(emailField, emailErrorLabel, "El email es requerido.");
            return;
        } else if (!isValidEmail(email)) {
            setError(emailField, emailErrorLabel, "El email no es válido.");
            return;
        } else {
            clearError(emailField, emailErrorLabel);
        }

        if (password.isEmpty()) {
            setError(passwordField, passwordErrorLabel, "La contraseña es requerida.");
            return;
        } else if (!isValidPassword(password)) {
            setError(passwordField, passwordErrorLabel, "Debe tener al menos 6 caracteres, una letra y un número.");
            return;
        } else {
            clearError(passwordField, passwordErrorLabel);
        }

        // TODO: Lógica real login
        mostrarAlerta("Éxito", "Inicio de sesión exitoso.", AlertType.INFORMATION);
        // Cambiar pantalla o lo que quieras
        App.setRoot("tercerapantalla");
    }

    // Manejar registro
    @FXML
    private void handleRegister() {
        String nombre = fullNameField.getText().trim();
        String email = emailRegisterField.getText().trim();
        String recovery = recoveryField.getText().trim();
        String pass = passwordRegisterField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre completo es obligatorio.", AlertType.ERROR);
            return;
        }
        if (email.isEmpty() || !isValidEmail(email)) {
            mostrarAlerta("Error", "Ingrese un correo válido.", AlertType.ERROR);
            return;
        }
        if (recovery.isEmpty()) {
            mostrarAlerta("Error", "El correo o número de recuperación es obligatorio.", AlertType.ERROR);
            return;
        }
        if (pass.isEmpty() || !isValidPassword(pass)) {
            mostrarAlerta("Error", "Contraseña inválida. Debe tener al menos 6 caracteres, una letra y un número.", AlertType.ERROR);
            return;
        }
        if (!pass.equals(confirmPass)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden.", AlertType.ERROR);
            return;
        }

        // TODO: Guardar registro en base de datos o backend

        mostrarAlerta("Éxito", "Registro exitoso.", AlertType.INFORMATION);
        limpiarCamposRegistro();
        mostrarLogin();
    }

    // Mensajes de alerta
    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Mostrar mensaje error y poner borde rojo
    private void setError(TextField field, Label label, String mensaje) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        label.setText(mensaje);
        label.setTextFill(javafx.scene.paint.Color.RED);
    }

    private void clearError(TextField field, Label label) {
        field.setStyle("-fx-border-color: transparent;");
        label.setText("");
    }

    private void limpiarErroresLogin() {
        clearError(emailField, emailErrorLabel);
        clearError(passwordField, passwordErrorLabel);
    }

    private void limpiarErroresRegistro() {
        // Aquí podrías limpiar errores específicos del registro si los agregas
    }

    private void limpiarCamposRegistro() {
        fullNameField.clear();
        emailRegisterField.clear();
        recoveryField.clear();
        passwordRegisterField.clear();
        confirmPasswordField.clear();
    }

    // Manejar enlace "Olvidaste tu contraseña"
    @FXML
    private void handleForgotPassword() {
        mostrarAlerta("Recuperación de contraseña", "Por favor, sigue las instrucciones para recuperar tu contraseña.", AlertType.INFORMATION);
    }

    // Manejar botón Google
    @FXML
    private void continuarConGoogle() {
        mostrarAlerta("Google", "Funcionalidad para continuar con Google aún no implementada.", AlertType.INFORMATION);
    }

    // Navegación botones (si los tienes en pantalla)
    @FXML
    private void RegarcarLogin() throws IOException {
        App.setRoot("PantallaLogin");
    }

    @FXML
    private void Regresar() throws IOException {
        App.setRoot("PantallaBienvenido");
    }

    @FXML
    private void SiguienteP() throws IOException {
        App.setRoot("tercerapantalla");
    }

    @FXML
    private void SiguientePa() throws IOException {
        App.setRoot("tercerapantalla");
    }



      @FXML
    private void Siguiente() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperacion");  // Recargar la vista de la pantalla principal
    }
      @FXML
    private void reloadPage() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaLogin");  // Recargar la vista de la pantalla principal
    }

       @FXML
    private void Antes() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaBienvenido");  // Recargar la vista de la pantalla principal
    }


    
}
