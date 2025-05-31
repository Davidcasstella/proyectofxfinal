package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ControladorDonantes {

    @FXML
    private Button btnNuevoDonante;

    @FXML
    private FlowPane flowPaneDonantes;

    @FXML
    public void initialize() {
        btnNuevoDonante.setOnAction(e -> {
            System.out.println("Botón Nuevo Donante presionado");
            // Aquí puedes abrir un formulario para agregar donante o hacer otras acciones
        });

        // Simulamos varios donantes
        List<Donante> donantes = List.of(
            new Donante("Juan Pérez", "123-456", true, false, false),
            new Donante("Ana Gómez", "789-012", true, true, false),
            new Donante("Luis Martínez", "345-678", false, true, true),
            new Donante("María López", "901-234", true, false, true),
            new Donante("Carlos Ruiz", "567-890", false, true, false),
            new Donante("Sofía Díaz", "234-567", true, true, true)
        );

        for (Donante d : donantes) {
            flowPaneDonantes.getChildren().add(crearTarjetaDonante(d));
        }
    }

    private VBox crearTarjetaDonante(Donante donante) {
        VBox tarjeta = new VBox(8);
        tarjeta.setPrefWidth(200);
        tarjeta.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-radius: 8; -fx-background-radius: 8;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        // Info superior
        HBox info = new HBox(8);
        info.setAlignment(Pos.CENTER_LEFT);

        Rectangle foto = new Rectangle(40, 40);
        foto.setArcWidth(20);
        foto.setArcHeight(20);
        foto.setFill(Color.LIGHTGRAY); // Puedes reemplazar por ImageView si tienes foto

        VBox textos = new VBox(2);
        Label nombre = new Label(donante.getNombre());
        nombre.setStyle("-fx-font-weight: bold;");
        Label documento = new Label(donante.getDocumento());
        documento.setStyle("-fx-font-size: 11; -fx-text-fill: gray;");
        textos.getChildren().addAll(nombre, documento);

        info.getChildren().addAll(foto, textos);

        // Tipos de donación
        HBox tipos = new HBox(6);

        Label lblDinero = new Label("Dinero");
        styleEtiquetaDonacion(lblDinero, donante.isDinero());

        Label lblAlimento = new Label("Alimento");
        styleEtiquetaDonacion(lblAlimento, donante.isAlimento());

        Label lblMedicamento = new Label("Medicamento");
        styleEtiquetaDonacion(lblMedicamento, donante.isMedicamento());

        tipos.getChildren().addAll(lblDinero, lblAlimento, lblMedicamento);

        // Última donación
        Label ultima = new Label("Última donación: --/--/----");
        ultima.setStyle("-fx-font-size: 11; -fx-text-fill: gray;");

        tarjeta.getChildren().addAll(info, tipos, ultima);
        return tarjeta;
    }

    private void styleEtiquetaDonacion(Label label, boolean activo) {
        if (activo) {
            label.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-padding: 2 8; -fx-background-radius: 8;");
        } else {
            label.setStyle("-fx-background-color: #ddd; -fx-text-fill: gray; -fx-padding: 2 8; -fx-background-radius: 8;");
        }
    }

    // Clase interna para representar un donante
    private static class Donante {
        private final String nombre;
        private final String documento;
        private final boolean dinero;
        private final boolean alimento;
        private final boolean medicamento;

        public Donante(String nombre, String documento, boolean dinero, boolean alimento, boolean medicamento) {
            this.nombre = nombre;
            this.documento = documento;
            this.dinero = dinero;
            this.alimento = alimento;
            this.medicamento = medicamento;
        }

        public String getNombre() { return nombre; }
        public String getDocumento() { return documento; }
        public boolean isDinero() { return dinero; }
        public boolean isAlimento() { return alimento; }
        public boolean isMedicamento() { return medicamento; }
    }
}
