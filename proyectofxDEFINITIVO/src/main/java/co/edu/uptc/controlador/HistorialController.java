package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Animall;
import co.edu.uptc.modelo.Donacion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class HistorialController {

    @FXML
    private FlowPane contenedorTarjetas;

    @FXML
    public void initialize() {
        System.out.println("HistorialController cargado...");

        // Lista de donaciones simuladas
        List<Donacion> donaciones = Arrays.asList(
                crearDonacionEjemplo("Max", "Comida", "5kg de croquetas", "Completado"),
                crearDonacionEjemplo("Luna", "Medicamento", "Vacunas y desparasitante", "Pendiente"),
                crearDonacionEjemplo("Peppa", "Dinero", "$50000", "Completado")
        );

        for (Donacion d : donaciones) {
            VBox tarjeta = crearTarjeta(d);
            contenedorTarjetas.getChildren().add(tarjeta);
        }
    }

    private Donacion crearDonacionEjemplo(String nombreAnimal, String tipo, String cantidad, String estado) {
        Donacion donacion = new Donacion();
        Animall animal = new Animall(nombreAnimal, "Descripción", "Tipo", estado, "");
        donacion.setAnimal(animal);
        donacion.setTipo(tipo);
        donacion.setCantidad(cantidad);
        donacion.setEstado(estado);
        donacion.setDescripcion("Donación para " + nombreAnimal);
        return donacion;
    }

    private VBox crearTarjeta(Donacion donacion) {
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-padding: 15; " +
                        "-fx-background-color: white; -fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        tarjeta.setPrefWidth(250);
        tarjeta.setMaxWidth(250);

        // Placeholder en lugar de imagen
        Region imagenPlaceholder = new Region();
        imagenPlaceholder.setPrefSize(220, 120);
        imagenPlaceholder.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #DDDDDD; " +
                                 "-fx-border-radius: 5; -fx-background-radius: 5;");

        // Información del animal
        Label nombre = new Label(donacion.getAnimal() != null ? donacion.getAnimal().getNombre() : "Sin nombre");
        nombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Tipo de donación
        Label tipo = new Label("Donación: " + donacion.getTipo());
        tipo.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        // Cantidad
        Label cantidad = new Label("Cantidad: " + donacion.getCantidad());
        cantidad.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        // Descripción
        Label descripcion = new Label(donacion.getDescripcion());
        descripcion.setWrapText(true);
        descripcion.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        // Estado
        Label estado = new Label("Estado: " + donacion.getEstado());
        String colorEstado = "Completado".equals(donacion.getEstado()) ? "#4CAF50" : "#FF9800";
        estado.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + colorEstado + ";");

        // Fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Label fecha = new Label("Fecha: " + donacion.getFecha().format(formatter));
        fecha.setStyle("-fx-font-size: 12px; -fx-text-fill: #999;");

        // Botón de recibo
        Button botonRecibo = new Button("📄 Ver Recibo");
        botonRecibo.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                           "-fx-padding: 8 16; -fx-background-radius: 5; -fx-cursor: hand;");
        botonRecibo.setOnAction(e -> mostrarRecibo(donacion));

        // Hover effect
        tarjeta.setOnMouseEntered(e -> 
            tarjeta.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-padding: 15; " +
                           "-fx-background-color: white; -fx-background-radius: 10; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);")
        );
        
        tarjeta.setOnMouseExited(e -> 
            tarjeta.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-padding: 15; " +
                           "-fx-background-color: white; -fx-background-radius: 10; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);")
        );

        // Agregar al VBox
        tarjeta.getChildren().addAll(imagenPlaceholder, nombre, tipo, cantidad, descripcion, estado, fecha, botonRecibo);
        return tarjeta;
    }

    private void mostrarRecibo(Donacion donacion) {
        // Aquí puedes implementar la lógica para mostrar el recibo
        System.out.println("Mostrando recibo para: " + donacion.getAnimal().getNombre());
    }
}