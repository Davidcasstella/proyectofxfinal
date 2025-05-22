package co.edu.uptc.controlador;


import java.io.IOException;

import co.edu.uptc.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;

public class ControladorLogin {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Label emailErrorLabel; // Nuevo label para mostrar el error del correo

    @FXML
    private Label passwordErrorLabel; // Nuevo label para mostrar el error de la contraseña

    @FXML
    private ImageView imageView; // ImageView para la imagen en el FXML

    @FXML
    private StackPane root; // El StackPane que contiene todo

    // Ruta de la imagen
    private static final String IMAGE_PATH = "src\\main\\resources\\co\\edu\\uptc\\imagenes\\images (1).jpg";  // Reemplaza con la ruta correcta

    // Validar correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Validar contraseña (mínimo 6 caracteres, al menos una letra y un número)
    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }

    // Manejar el inicio de sesión
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validar el correo electrónico
        if (email.isEmpty()) {
            showError(emailField, "El email es requerido.", emailErrorLabel);
            return;
        } else if (!isValidEmail(email)) {
            showError(emailField, "El email no es válido.", emailErrorLabel);
            return;
        } else {
            resetFieldStyle(emailField, emailErrorLabel);
        }

        // Validar la contraseña
        if (password.isEmpty()) {
            showError(passwordField, "La contraseña es requerida.", passwordErrorLabel);
            return;
        } else if (!isValidPassword(password)) {
            showError(passwordField, "La contraseña debe tener al menos 6 caracteres, una letra y un número.", passwordErrorLabel);
            return;
        } else {
            resetFieldStyle(passwordField, passwordErrorLabel);
        }

        // Si las validaciones son exitosas
        showAlert("Éxito", "Inicio de sesión exitoso.", AlertType.INFORMATION);
        SiguientePa();
       
    }

    

    // Mostrar error en el campo de texto y en el label correspondiente
    private void showError(TextField field, String errorMessage, Label errorLabel) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        errorLabel.setText(errorMessage);
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
    }

    private void showError(PasswordField field, String errorMessage, Label errorLabel) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        errorLabel.setText(errorMessage);
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
    }

    // Restablecer estilo del campo de texto y del label de error
    private void resetFieldStyle(TextField field, Label errorLabel) {
        field.setStyle("-fx-border-color: transparent;");
        errorLabel.setText("");
    }

    private void resetFieldStyle(PasswordField field, Label errorLabel) {
        field.setStyle("-fx-border-color: transparent;");
        errorLabel.setText("");
    }

    // Mostrar alertas de error o éxito
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validación en tiempo real del correo electrónico
    @FXML
    private void validateEmail(KeyEvent event) {
        String email = emailField.getText();
        if (email.isEmpty()) {
            emailField.setStyle("-fx-border-color: red;");
            emailErrorLabel.setText("El email es requerido.");
            emailErrorLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else if (!isValidEmail(email)) {
            emailField.setStyle("-fx-border-color: red;");
            emailErrorLabel.setText("El email no es válido.");
            emailErrorLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else {
            emailField.setStyle("-fx-border-color: green;");
            emailErrorLabel.setText("");
        }
        
    }

    // Validación en tiempo real de la contraseña
    @FXML
    private void validatePassword(KeyEvent event) {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            passwordField.setStyle("-fx-border-color: red;");
            passwordErrorLabel.setText("La contraseña es requerida.");
            passwordErrorLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else if (!isValidPassword(password)) {
            passwordField.setStyle("-fx-border-color: red;");
            passwordErrorLabel.setText("Debe tener al menos 6 caracteres, una letra y un número.");
            passwordErrorLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else {
            passwordField.setStyle("-fx-border-color: green;");
            passwordErrorLabel.setText("");
        }
    }

    // Manejar el enlace "Olvidaste tu contraseña"
    @FXML
    public void handleForgotPassword() {
        showAlert("Recuperación de contraseña", "Por favor, sigue las instrucciones para recuperar tu contraseña.", AlertType.INFORMATION);
    }

    // Método que se llama al iniciar la aplicación para cargar la imagen
    @FXML
    public void initialize() {
        Image image = new Image("file:" + IMAGE_PATH); // Cargar la imagen desde el archivo
        imageView.setImage(image); // Establecer la imagen en el ImageView
    }
     @FXML
    private void RegarcarLogin() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaLogin");  // Recargar la vista de la pantalla principal
    }
     @FXML
    private void Regresar() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaBienvenido");  // Recargar la vista de la pantalla principal
    }
    

     @FXML
    private void SiguienteP() throws IOException {
        // Recarga la página actual
        App.setRoot("tercerapantalla");  // Recargar la vista de la pantalla principal
    }
     @FXML
    private void SiguientePa() throws IOException {
        // Recarga la página actual
        App.setRoot("tercerapantalla");  // Recargar la vista de la pantalla principal
    }
   
}
