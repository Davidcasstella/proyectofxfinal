package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class MensajesController {

    @FXML
    private ListView<String> contactList;

    @FXML
    private VBox messageContainer;

    @FXML
    private TextField messageField;

    private String usuarioSeleccionado;

    @FXML
    public void initialize() {
        // Lista de usuarios simulados
        contactList.getItems().addAll("Helena Hills", "Carlo Emilio", "Oscar Davis");

        // Listener para cuando se selecciona un usuario
        contactList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                usuarioSeleccionado = newVal;
                mostrarMensajesDeUsuario(newVal);
            }
        });
    }

    private void mostrarMensajesDeUsuario(String usuario) {
        messageContainer.getChildren().clear();

        // Simulación de mensajes del usuario
        agregarMensaje(usuario, "gray", "Hola, ¿cómo estás?");
        agregarMensaje(usuario, "gray", "¿Qué tal tu día?");
        agregarMensaje(usuario, "gray", "¿Has visto el último post en el foro?");
        agregarMensaje("Tú", "black", "¡Hola! Estoy bien, gracias.");
        agregarMensaje("Tú", "black", "Sí, lo vi. Muy interesante.");
        agregarMensaje(usuario, "gray", "Y que tal tu trabajo?");
        agregarMensaje("Tú", "black", "Todo bien, gracias por preguntar.");
        agregarMensaje(usuario, "gray", "Me alegra saberlo.");
        agregarMensaje("Tú", "black", "¿Y tú, cómo has estado?");
        agregarMensaje(usuario, "gray", "Todo bien, gracias. He estado ocupado con el trabajo.");
    }

    private void agregarMensaje(String autor, String color, String contenido) {
        HBox mensajeBox = new HBox();
        mensajeBox.setPadding(new Insets(5));
        mensajeBox.setMaxWidth(Double.MAX_VALUE);

        Label bubble = new Label(contenido);
        bubble.setWrapText(true);
        bubble.setMaxWidth(300);
        bubble.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 15;"
        );

        if (color.equals("black")) {
            mensajeBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            mensajeBox.setAlignment(Pos.CENTER_LEFT);
        }

        mensajeBox.getChildren().add(bubble);
        messageContainer.getChildren().add(mensajeBox);
    }

    @FXML
    private void enviarMensaje() {
        String texto = messageField.getText().trim();
        if (!texto.isEmpty() && usuarioSeleccionado != null) {
            agregarMensaje("Tú", "black", texto);
            messageField.clear();
        }
    }
}
