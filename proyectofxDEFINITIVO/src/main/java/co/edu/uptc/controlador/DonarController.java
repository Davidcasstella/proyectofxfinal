package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Animall;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;

public class DonarController {

    @FXML
    private VBox animalList;

    @FXML
    private MainController mainController;

    @FXML
    public void initialize() {
        List<Animall> animales = Arrays.asList(
            new Animall("Max", "Cachorro activo", "Perro", "Disponible", "/co/edu/uptc/imagenes/images (1).jpg"),
            new Animall("Luna", "Gatita tierna", "Gato", "Adoptada", "/co/edu/uptc/imagenes/images (1).jpg"),
            new Animall("Peppa", "Cerdita valiente", "Cerdo", "Disponible", "/co/edu/uptc/imagenes/images (1).jpg")
        );

        for (Animall animal : animales) {
            animalList.getChildren().add(createAnimalCard(animal));
        }
    }

    private HBox createAnimalCard(Animall animal) {
        Label nameLabel = new Label(animal.getNombre());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label descLabel = new Label(animal.getDescripcion());
        descLabel.setWrapText(true);

        VBox infoBox = new VBox(5, nameLabel, descLabel);
        infoBox.setPrefWidth(400);

        Button btnSeleccionar = new Button("Seleccionar");
        btnSeleccionar.setOnAction(e -> {
            if (mainController != null) {
                mainController.loadDonacionView(animal);
            }
        });

        Button btnExpediente = new Button("Expediente");
        HBox buttonBox = new HBox(10, btnSeleccionar, btnExpediente);

        VBox rightBox = new VBox(10, buttonBox);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        HBox card = new HBox(20, infoBox, rightBox);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5px;");
        return card;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}

