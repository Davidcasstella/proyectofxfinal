package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ControladorComentarios {

    @FXML
    private FlowPane flowPaneComentarios;

    @FXML
    public void initialize() {
        // Simulamos una lista de voluntarios
        List<String> voluntarios = List.of(
            "Juan Pérez", "Ana Gómez", "Luis Martínez", "María López", "Carlos Ruiz",
            "Sofía Díaz", "Andrés Torres", "Lucía Fernández", "Miguel Castillo", "Laura Mendoza"
        );

        for (String nombre : voluntarios) {
            flowPaneComentarios.getChildren().add(crearTarjetaVoluntario(nombre));
        }
    }

    private VBox crearTarjetaVoluntario(String nombre) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPrefWidth(130);
        tarjeta.setPrefHeight(180);
        tarjeta.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        tarjeta.setAlignment(Pos.CENTER);

        Rectangle foto = new Rectangle(100, 100);
        foto.setArcWidth(15);
        foto.setArcHeight(15);
        foto.setFill(Color.LIGHTGRAY);

        Label lblNombre = new Label(nombre);
        lblNombre.setStyle("-fx-font-weight: bold;");

        Label lblRol = new Label("Voluntario");
        lblRol.setStyle("-fx-text-fill: gray;");

        Button btnComentarios = new Button("Ver Comentarios");
        btnComentarios.setPrefWidth(110);
        btnComentarios.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        btnComentarios.setOnAction(e -> {
            System.out.println("Ver comentarios de: " + nombre);
            // Aquí puedes abrir un diálogo o pantalla para mostrar los comentarios del voluntario
        });

        tarjeta.getChildren().addAll(foto, lblNombre, lblRol, btnComentarios);
        return tarjeta;
    }
}
