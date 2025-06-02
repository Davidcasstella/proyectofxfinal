package co.edu.uptc.vista;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginVista {
    
    // Cambiar entre formularios
    public void mostrarFormularioLogin(VBox loginForm, VBox registerForm, 
                                     Label tabLogin, Label tabRegister) {
        tabLogin.setStyle("-fx-text-fill: #0066cc; -fx-font-weight: bold;");
        tabRegister.setStyle("-fx-text-fill: #999999; -fx-font-weight: normal;");
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        registerForm.setVisible(false);
        registerForm.setManaged(false);
    }
    
    public void mostrarFormularioRegistro(VBox loginForm, VBox registerForm, 
                                        Label tabLogin, Label tabRegister) {
        tabRegister.setStyle("-fx-text-fill: #0066cc; -fx-font-weight: bold;");
        tabLogin.setStyle("-fx-text-fill: #999999; -fx-font-weight: normal;");
        registerForm.setVisible(true);
        registerForm.setManaged(true);
        loginForm.setVisible(false);
        loginForm.setManaged(false);
    }
    
    // Validaciones
    public boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public boolean validarPassword(String password) {
        return password.length() >= 6 &&
               password.matches(".*[A-Za-z].*") &&
               password.matches(".*\\d.*");
    }
    
    // Mostrar errores en campos
    public void mostrarError(TextField field, Label label, String mensaje) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-radius: 20px; -fx-border-radius: 20px;");
        label.setText(mensaje);
        label.setTextFill(Color.RED);
    }
    
    public void limpiarError(TextField field, Label label) {
        field.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #cccccc;");
        label.setText("");
    }
    
    // Mostrar alertas
    public void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Limpiar campos del formulario de registro
    public void limpiarCamposRegistro(TextField nombreField, TextField emailField, 
                                    TextField recoveryField, PasswordField passwordField, 
                                    PasswordField confirmPasswordField) {
        nombreField.clear();
        emailField.clear();
        recoveryField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
    
    // Limpiar campos del formulario de login
    public void limpiarCamposLogin(TextField emailField, PasswordField passwordField) {
        emailField.clear();
        passwordField.clear();
    }
}