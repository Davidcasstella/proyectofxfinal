package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Comentarioo;
import co.edu.uptc.modelo.Usuarioo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ForoController implements Initializable {

    @FXML
    private VBox comentariosContainer;

    @FXML
    private TextField autorField;

    @FXML
    private TextArea contenidoArea;

    private List<Comentarioo> comentarios = new ArrayList<>();
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comentarios.add(new Comentarioo("Juan Pérez", "¡Qué buena actividad! Me encantó ayudar."));
        comentarios.add(new Comentarioo("Laura Ramírez", "¿Cuándo será la próxima reunión?"));

        cargarComentarios();
    }

    private void cargarComentarios() {
        comentariosContainer.getChildren().clear();
        for (Comentarioo comentario : comentarios) {
            VBox card = crearCardComentario(comentario);
            comentariosContainer.getChildren().add(card);
        }
    }

    private VBox crearCardComentario(Comentarioo comentario) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #EEEEEE; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label avatar = new Label("👤");
        avatar.setStyle("-fx-font-size: 24px; -fx-cursor: hand;");

        Label contenido = new Label(comentario.getContenido());
        contenido.setWrapText(true);

        Label autor = new Label("– " + comentario.getAutor());
        autor.setStyle("-fx-font-weight: bold; -fx-text-fill: #3F51B5; -fx-cursor: hand;");

        avatar.setOnMouseClicked(e -> abrirPerfil(comentario.getAutor()));
        autor.setOnMouseClicked(e -> abrirPerfil(comentario.getAutor()));

        card.getChildren().addAll(avatar, contenido, autor);
        return card;
    }

    private void abrirPerfil(String nombreAutor) {
    if (mainController != null) {
        // Aquí se crea un usuario simulado con información "realista"
        Usuarioo usuario = new Usuarioo(nombreAutor);
        usuario.setCorreo("correo@" + nombreAutor.toLowerCase().replace(" ", "") + ".com");
        usuario.setTelefono("1234567890");
        usuario.setUbicacion("Ciudad de Ejemplo");
        usuario.setEdad(25);
        usuario.setBiografia("Soy un voluntario entusiasta que ama ayudar en actividades comunitarias.");
        usuario.setFrustraciones("Falta de tiempo y poca organización en algunos eventos.");
        usuario.setMotivaciones("Ver sonrisas en las personas que ayudamos.");
        usuario.setMetas("Organizar un evento mensual\nReclutar más voluntarios\nCrear una red de apoyo");
        

        mainController.mostrarPerfilPublico(usuario);
    }
}

    @FXML
    private void agregarComentario() {
        String autor = autorField.getText();
        String contenido = contenidoArea.getText();

        if (autor.isEmpty() || contenido.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor completa todos los campos.");
            alert.show();
            return;
        }

        Comentarioo nuevo = new Comentarioo(autor, contenido);
        comentarios.add(nuevo);
        cargarComentarios();

        autorField.clear();
        contenidoArea.clear();
    }
}