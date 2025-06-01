package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Reporte;
import co.edu.uptc.servicio.FundacionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorReportes {

    @FXML
    private Button btnDescargarReporte;

    @FXML
    private Button btnReciboVoluntarios;

    @FXML
    private VBox contenedorReportes; // Contenedor para mostrar reportes

    private FundacionService fundacionService;
    private ObservableList<Reporte> listaReportes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();
        configurarEventos();
        cargarReportes();
    }

    private void configurarEventos() {
        btnDescargarReporte.setOnAction(e -> mostrarDialogoGenerarReporte());
        btnReciboVoluntarios.setOnAction(e -> generarReporteVoluntarios());
    }

    private void cargarReportes() {
        if (contenedorReportes == null) {
            // Si no hay contenedor en el FXML, crear uno
            return;
        }
        
        contenedorReportes.getChildren().clear();
        List<Reporte> reportes = fundacionService.obtenerTodosLosReportes();
        
        if (reportes.isEmpty()) {
            Label lblSinReportes = new Label("No hay reportes generados");
            lblSinReportes.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            contenedorReportes.getChildren().add(lblSinReportes);
            return;
        }

        // Mostrar los últimos 5 reportes
        int limite = Math.min(5, reportes.size());
        for (int i = reportes.size() - 1; i >= reportes.size() - limite; i--) {
            Reporte reporte = reportes.get(i);
            VBox tarjetaReporte = crearTarjetaReporte(reporte);
            contenedorReportes.getChildren().add(tarjetaReporte);
        }

        // Botón para ver todos los reportes
        Button btnVerTodos = new Button("Ver Todos los Reportes");
        btnVerTodos.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");
        btnVerTodos.setOnAction(e -> mostrarTodosLosReportes());
        contenedorReportes.getChildren().add(btnVerTodos);
    }

    private VBox crearTarjetaReporte(Reporte reporte) {
        VBox tarjeta = new VBox(8);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " +
                "-fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblTitulo = new Label(reporte.getTitulo());
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblTipo = new Label("Tipo: " + reporte.getTipoReporte());
        lblTipo.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");

        Label lblFecha = new Label("Generado: " + 
                reporte.getFechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblFecha.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");

        Label lblEstado = new Label("Estado: " + reporte.getEstado());
        lblEstado.setStyle("-fx-text-fill: " + obtenerColorEstado(reporte.getEstado()) + 
                "; -fx-font-weight: bold; -fx-font-size: 12px;");

        // Botones de acción
        javafx.scene.layout.HBox botones = new javafx.scene.layout.HBox(10);
        Button btnVer = new Button("Ver");
        Button btnEliminar = new Button("Eliminar");
        
        btnVer.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 10px;");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 10px;");
        
        btnVer.setOnAction(e -> mostrarDetalleReporte(reporte));
        btnEliminar.setOnAction(e -> eliminarReporte(reporte));
        
        botones.getChildren().addAll(btnVer, btnEliminar);

        tarjeta.getChildren().addAll(lblTitulo, lblTipo, lblFecha, lblEstado, botones);
        return tarjeta;
    }

    private String obtenerColorEstado(String estado) {
        switch (estado.toUpperCase()) {
            case "GENERADO": return "#28a745";
            case "ENVIADO": return "#17a2b8";
            case "ARCHIVADO": return "#6c757d";
            default: return "#6c757d";
        }
    }

    private void mostrarDialogoGenerarReporte() {
        Dialog<Reporte> dialog = new Dialog<>();
        dialog.setTitle("Generar Nuevo Reporte");
        dialog.setHeaderText("Seleccione el tipo de reporte a generar");

        // Crear campos del formulario
        ComboBox<String> cmbTipoReporte = new ComboBox<>();
        cmbTipoReporte.getItems().addAll("GENERAL", "DONACIONES", "ANIMALES", "VOLUNTARIOS");
        cmbTipoReporte.setValue("GENERAL");

        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título del reporte");

        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción del reporte");
        txtDescripcion.setPrefRowCount(3);

        TextField txtGeneradoPor = new TextField("Usuario1234");
        txtGeneradoPor.setPromptText("Generado por");

        // Actualizar título automáticamente según el tipo
        cmbTipoReporte.valueProperty().addListener((obs, old, nuevo) -> {
            if (txtTitulo.getText().isEmpty() || txtTitulo.getText().startsWith("Reporte")) {
                txtTitulo.setText("Reporte de " + nuevo.toLowerCase());
            }
        });

        // Layout del formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Tipo de Reporte:"), 0, 0);
        grid.add(cmbTipoReporte, 1, 0);
        grid.add(new Label("Título:"), 0, 1);
        grid.add(txtTitulo, 1, 1);
        grid.add(new Label("Descripción:"), 0, 2);
        grid.add(txtDescripcion, 1, 2);
        grid.add(new Label("Generado por:"), 0, 3);
        grid.add(txtGeneradoPor, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Botones
        ButtonType btnGenerar = new ButtonType("Generar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGenerar, btnCancelar);

        // Validación
        Button generarBtn = (Button) dialog.getDialogPane().lookupButton(btnGenerar);
        generarBtn.setDisable(true);

        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            generarBtn.setDisable(newValue.trim().isEmpty());
        });

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGenerar) {
                try {
                    return fundacionService.generarReporte(
                            txtTitulo.getText().trim(),
                            cmbTipoReporte.getValue(),
                            txtDescripcion.getText().trim(),
                            txtGeneradoPor.getText().trim()
                    );
                } catch (Exception e) {
                    mostrarAlerta("Error", "Error al generar el reporte: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Reporte> resultado = dialog.showAndWait();
        if (resultado.isPresent()) {
            mostrarAlerta("Éxito", "Reporte generado correctamente.");
            cargarReportes();
        }
    }

    private void generarReporteVoluntarios() {
        try {
            Reporte reporte = fundacionService.generarReporte(
                    "Reporte de Actividad de Voluntarios",
                    "VOLUNTARIOS",
                    "Reporte detallado de la actividad y comentarios de voluntarios",
                    "Usuario1234"
            );
            mostrarAlerta("Éxito", "Reporte de voluntarios generado correctamente.");
            cargarReportes();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void mostrarDetalleReporte(Reporte reporte) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Detalle del Reporte");
        dialog.setHeaderText(reporte.getTitulo());

        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(20));

        // Información básica
        contenido.getChildren().addAll(
                new Label("Tipo: " + reporte.getTipoReporte()),
                new Label("Descripción: " + reporte.getDescripcion()),
                new Label("Generado por: " + reporte.getGeneradoPor()),
                new Label("Fecha: " + reporte.getFechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),
                new Label("Estado: " + reporte.getEstado())
        );

        // Mostrar datos del reporte si existen
        if (reporte.getDatos() != null && !reporte.getDatos().isEmpty()) {
            contenido.getChildren().add(new Label("Datos del Reporte:"));
            
            VBox datosBox = new VBox(5);
            datosBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-border-radius: 5;");
            
            for (Map.Entry<String, Object> entry : reporte.getDatos().entrySet()) {
                Label lblDato = new Label(entry.getKey() + ": " + entry.getValue());
                lblDato.setStyle("-fx-font-family: monospace;");
                datosBox.getChildren().add(lblDato);
            }
            
            ScrollPane scrollDatos = new ScrollPane(datosBox);
            scrollDatos.setPrefHeight(200);
            scrollDatos.setFitToWidth(true);
            contenido.getChildren().add(scrollDatos);
        }

        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void eliminarReporte(Reporte reporte) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar este reporte?");
        alert.setContentText("Reporte: " + reporte.getTitulo() + "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean eliminado = fundacionService.eliminarReporte(reporte.getId());
                if (eliminado) {
                    mostrarAlerta("Éxito", "Reporte eliminado correctamente.");
                    cargarReportes();
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el reporte.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar el reporte: " + e.getMessage());
            }
        }
    }

    private void mostrarTodosLosReportes() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Todos los Reportes");
        dialog.setHeaderText("Historial completo de reportes generados");

        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(20));

        // Crear tabla de reportes
        TableView<Reporte> tabla = new TableView<>();
        
        TableColumn<Reporte, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        colTitulo.setPrefWidth(200);

        TableColumn<Reporte, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoReporte()));
        colTipo.setPrefWidth(100);

        TableColumn<Reporte, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colFecha.setPrefWidth(120);

        TableColumn<Reporte, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));
        colEstado.setPrefWidth(100);

        tabla.getColumns().addAll(colTitulo, colTipo, colFecha, colEstado);
        tabla.setItems(FXCollections.observableArrayList(fundacionService.obtenerTodosLosReportes()));

        contenido.getChildren().add(tabla);

        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setPrefSize(600, 400);
        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}