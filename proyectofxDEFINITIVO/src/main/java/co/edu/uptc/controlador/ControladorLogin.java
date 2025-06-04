package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.servicio.UsuarioService;
import co.edu.uptc.vista.LoginVista;
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

    // Servicios y Vistas
    private UsuarioService usuarioService;
    private LoginVista loginVista;
    private static Usuario usuarioActual;

    // Ruta imagen
    private static final String IMAGE_PATH = "/co/edu/uptc/imagenes/images (1).jpg";

    @FXML
    public void initialize() {
        // Inicializar servicios y vistas
        usuarioService = new UsuarioService();
        loginVista = new LoginVista();
        
        // Cargar imagen
        Image img = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        imageView.setImage(img);

        // Inicial: mostrar formulario login
        mostrarLogin();
    }

    // Mostrar formulario login
    @FXML
    private void mostrarLogin() {
        loginVista.mostrarFormularioLogin(loginForm, registerForm, tabLogin, tabRegister);
        limpiarErroresLogin();
        limpiarErroresRegistro();
    }

    // Mostrar formulario registro
    @FXML
    private void mostrarRegistro() {
        loginVista.mostrarFormularioRegistro(loginForm, registerForm, tabLogin, tabRegister);
        limpiarErroresLogin();
        limpiarErroresRegistro();
    }

    // Validación en tiempo real email login
    @FXML
    private void validateEmail(KeyEvent event) {
        String email = emailField.getText();
        if (email.isEmpty()) {
            loginVista.mostrarError(emailField, emailErrorLabel, "El email es requerido.");
        } else if (!loginVista.validarEmail(email)) {
            loginVista.mostrarError(emailField, emailErrorLabel, "El email no es válido.");
        } else {
            loginVista.limpiarError(emailField, emailErrorLabel);
        }
    }

    // Validación en tiempo real password login
    @FXML
    private void validatePassword(KeyEvent event) {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            loginVista.mostrarError(passwordField, passwordErrorLabel, "La contraseña es requerida.");
        } else if (!loginVista.validarPassword(password)) {
            loginVista.mostrarError(passwordField, passwordErrorLabel, "Debe tener al menos 6 caracteres, una letra y un número.");
        } else {
            loginVista.limpiarError(passwordField, passwordErrorLabel);
        }
    }

    // Manejar login
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty()) {
            loginVista.mostrarError(emailField, emailErrorLabel, "El email es requerido.");
            return;
        } else if (!loginVista.validarEmail(email)) {
            loginVista.mostrarError(emailField, emailErrorLabel, "El email no es válido.");
            return;
        } else {
            loginVista.limpiarError(emailField, emailErrorLabel);
        }

        if (password.isEmpty()) {
            loginVista.mostrarError(passwordField, passwordErrorLabel, "La contraseña es requerida.");
            return;
        } else if (!loginVista.validarPassword(password)) {
            loginVista.mostrarError(passwordField, passwordErrorLabel, "Debe tener al menos 6 caracteres, una letra y un número.");
            return;
        } else {
            loginVista.limpiarError(passwordField, passwordErrorLabel);
        }

        // Autenticar usuario
        Usuario usuario = usuarioService.autenticar(email, password);
        
        if (usuario != null) {
            usuarioActual = usuario;
            loginVista.mostrarAlerta("Éxito", "Inicio de sesión exitoso. Bienvenido " + usuario.getNombreCompleto(), AlertType.INFORMATION);
            App.setRoot("PantallaDashboard");
        } else {
            loginVista.mostrarAlerta("Error", "Email o contraseña incorrectos.", AlertType.ERROR);
        }
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
            loginVista.mostrarAlerta("Error", "El nombre completo es obligatorio.", AlertType.ERROR);
            return;
        }
        if (email.isEmpty() || !loginVista.validarEmail(email)) {
            loginVista.mostrarAlerta("Error", "Ingrese un correo válido.", AlertType.ERROR);
            return;
        }
        if (recovery.isEmpty()) {
            loginVista.mostrarAlerta("Error", "El correo o número de recuperación es obligatorio.", AlertType.ERROR);
            return;
        }
        if (pass.isEmpty() || !loginVista.validarPassword(pass)) {
            loginVista.mostrarAlerta("Error", "Contraseña inválida. Debe tener al menos 6 caracteres, una letra y un número.", AlertType.ERROR);
            return;
        }
        if (!pass.equals(confirmPass)) {
            loginVista.mostrarAlerta("Error", "Las contraseñas no coinciden.", AlertType.ERROR);
            return;
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombre, email, pass, recovery);
        
        // Registrar en el servicio
        if (usuarioService.registrarUsuario(nuevoUsuario)) {
            loginVista.mostrarAlerta("Éxito", "Registro exitoso. Ya puedes iniciar sesión.", AlertType.INFORMATION);
            loginVista.limpiarCamposRegistro(fullNameField, emailRegisterField, recoveryField, 
                                            passwordRegisterField, confirmPasswordField);
            mostrarLogin();
        } else {
            loginVista.mostrarAlerta("Error", "El email ya está registrado.", AlertType.ERROR);
        }
    }

    // Manejar enlace "Olvidaste tu contraseña"
    @FXML
    private void handleForgotPassword() throws IOException {
        App.setRoot("PatallaCodigoRecuperacion");
    }

    // Manejar botón Google - Ir directo a Dashboard
    @FXML
    private void continuarConGoogle() throws IOException {
        loginVista.mostrarAlerta("Google", "Iniciando sesión con Google...", AlertType.INFORMATION);
        App.setRoot("PantallaDashboard");
    }

    // Navegación
    @FXML
    private void reloadPage() throws IOException {
        App.setRoot("PantallaLogin");
    }

    @FXML
    private void Antes() throws IOException {
        App.setRoot("PantallaBienvenido");
    }

    @FXML
    private void Siguiente() throws IOException {
        App.setRoot("MainView");
    }

    // Métodos auxiliares
    private void limpiarErroresLogin() {
        loginVista.limpiarError(emailField, emailErrorLabel);
        loginVista.limpiarError(passwordField, passwordErrorLabel);
    }

    private void limpiarErroresRegistro() {
        // Limpiar campos de registro si es necesario
    }

    // Getter para usuario actual
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}