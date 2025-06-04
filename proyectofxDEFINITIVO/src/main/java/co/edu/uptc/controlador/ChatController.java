package co.edu.uptc.controlador;

import co.edu.uptc.modelo.ChatManager;
import co.edu.uptc.modelo.Usuarioo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML
    private VBox messagesBox;

    @FXML
    private TextField messageField;

    @FXML
    private Label nombreUsuarioLabel;

    @FXML
    private Label estadoLabel;

    private Usuarioo usuarioConectado;
    private MainController mainController;

    public void setUsuarioConectado(Usuarioo usuario) {
        this.usuarioConectado = usuario;
        if (usuario != null) {
            nombreUsuarioLabel.setText(usuario.getNombre());
            estadoLabel.setText("Activo recientemente");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setHistorial(StringBuilder historial) {
        messagesBox.getChildren().clear();
        if (historial != null) {
            for (String linea : historial.toString().split("\n")) {
                if (!linea.isBlank()) {
                    agregarBurbujaMensaje(linea);
                }
            }
        }
    }

    @FXML
    private void enviarMensaje() {
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            agregarBurbujaMensaje(mensaje);
            ChatManager.guardarMensaje(usuarioConectado, mensaje);
            messageField.clear();
        }
    }

    private void agregarBurbujaMensaje(String mensaje) {
        Label burbuja = new Label(mensaje);
        burbuja.setWrapText(true);
        burbuja.setMaxWidth(300);
        burbuja.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;");
        messagesBox.getChildren().add(burbuja);
    }

    @FXML
    private void verPerfil() {
        if (mainController != null && usuarioConectado != null) {
            mainController.mostrarPerfilPublico(usuarioConectado);
        }
    }

    @FXML
    private void irMenuPrincipal() {
        if (mainController != null) {
            mainController.loadView("PrincipalView.fxml");
        }
    }
}
