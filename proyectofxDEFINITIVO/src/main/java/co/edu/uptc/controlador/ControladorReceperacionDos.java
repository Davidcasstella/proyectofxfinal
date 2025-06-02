package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.servicio.UsuarioService;
import co.edu.uptc.vista.RecuperacionVista;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class ControladorReceperacionDos {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private Button nextButton;
    @FXML private AnchorPane modalPane;
    @FXML private Button closeModalButton;
    @FXML private Button modalNextButton;

    private UsuarioService usuarioService;
    private RecuperacionVista recuperacionVista;
    private static Usuario usuarioRecuperacion;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();
        recuperacionVista = new RecuperacionVista();
        
        nextButton.setOnAction(event -> handleNext());
        closeModalButton.setOnAction(event -> hideModal());
        modalNextButton.setOnAction(event -> handleModalNext());
    }

    private void handleNext() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        if (fullName.isEmpty()) {
            recuperacionVista.mostrarAlerta("Error", "Por favor ingresa tu nombre completo.", AlertType.ERROR);
            return;
        }

        if (email.isEmpty()) {
            recuperacionVista.mostrarAlerta("Error", "Por favor ingresa tu correo electrónico registrado.", AlertType.ERROR);
            return;
        }

        // Buscar usuario por nombre y email
        Usuario usuario = usuarioService.buscarPorNombreYEmail(fullName, email);
        
        if (usuario != null) {
            usuarioRecuperacion = usuario;
            showModal();
        } else {
            recuperacionVista.mostrarAlerta("Error", "No se encontró ningún usuario con esos datos.", AlertType.ERROR);
        }
    }

    private void showModal() {
        recuperacionVista.mostrarModal(modalPane);
    }

    private void hideModal() {
        recuperacionVista.ocultarModal(modalPane);
    }

    private void handleModalNext() {
        hideModal();
        
        // Simulamos envío de código (en producción aquí se enviaría un email real)
        String codigo = generarCodigoRecuperacion();
        ControladorReceperacionTres.setCodigoRecuperacion(codigo);
        
        recuperacionVista.mostrarAlerta("Éxito", 
            "Código enviado a: " + usuarioRecuperacion.getCorreoRecuperacion() + 
            "\n(Código de prueba: " + codigo + ")", 
            AlertType.INFORMATION);

        try {
            App.setRoot("PatallaCodigoRecuperaciontres");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generarCodigoRecuperacion() {
        // Generar código de 6 dígitos
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    @FXML
    private void Siguienteee() throws IOException {
        App.setRoot("PatallaCodigoRecuperaciontres");
    }
    
    @FXML
    private void reloadPageee() throws IOException {
        App.setRoot("PatallaCodigoRecuperacionDos");
    }
    
    @FXML
    private void Antesss() throws IOException {
        App.setRoot("PatallaCodigoRecuperacion");
    }

    public static Usuario getUsuarioRecuperacion() {
        return usuarioRecuperacion;
    }
}