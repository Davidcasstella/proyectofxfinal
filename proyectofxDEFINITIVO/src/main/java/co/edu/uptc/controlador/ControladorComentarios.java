package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Comentario;
import co.edu.uptc.modelo.Voluntario;
import co.edu.uptc.servicio.FundacionService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ControladorComentarios {

    @FXML
    private FlowPane flowPaneComentarios;

    private FundacionService fundacionService;

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();
        cargarVoluntarios();
    }

    private void cargarVoluntarios() {
        flowPaneComentarios.getChildren().clear();
        List<Voluntario> voluntarios = fundacionService.obtenerTodosLosVoluntarios();
        
        if (voluntarios.isEmpty()) {
            Label lblSinVoluntarios = new Label("No hay voluntarios registrados");
            lblSinVoluntarios.setStyle("-fx-text-fill: gray; -fx-font-style: italic; -fx-font-size: 16px;");
            flowPaneComentarios.getChildren().add(lblSinVoluntarios);
            return;
        }

        for (Voluntario voluntario : voluntarios) {
            flowPaneComentarios.getChildren().add(crearTarjetaVoluntario(voluntario));
        }
    }

    private VBox crearTarjetaVoluntario(Voluntario voluntario) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPrefWidth(140);
        tarjeta.setPrefHeight(200);
        tarjeta.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-radius: 8; " +
                "-fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        tarjeta.setAlignment(Pos.CENTER);

        // Foto placeholder
        Rectangle foto = new Rectangle(80, 80);
        foto.setArcWidth(15);
        foto.setArcHeight(15);
        foto.setFill(Color.LIGHTGRAY);

        // Nombre del voluntario
        Label lblNombre = new Label(voluntario.getNombreCompleto());
        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        lblNombre.setWrapText(true);
        lblNombre.setAlignment(Pos.CENTER);
        lblNombre.setPrefWidth(120);

        // Especialidad
        String especialidadTexto = voluntario.getEspecialidad() != null ? 
                formatearEspecialidad(voluntario.getEspecialidad()) : "Voluntario";
        Label lblEspecialidad = new Label(especialidadTexto);
        lblEspecialidad.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

        // Contador de comentarios
        long numComentarios = fundacionService.obtenerComentariosPorVoluntario(voluntario.getId()).size();
        Label lblContador = new Label(numComentarios + " comentario" + (numComentarios != 1 ? "s" : ""));
        lblContador.setStyle("-fx-text-fill: #007bff; -fx-font-size: 10px; -fx-font-weight: bold;");

        // Botón para ver comentarios
        Button btnComentarios = new Button("Ver Comentarios");
        btnComentarios.setPrefWidth(120);
        btnComentarios.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 10px;");
        btnComentarios.setOnAction(e -> mostrarComentariosVoluntario(voluntario));

        tarjeta.getChildren().addAll(foto, lblNombre, lblEspecialidad, lblContador, btnComentarios);
        return tarjeta;
    }

    private String formatearEspecialidad(String especialidad) {
        switch (especialidad.toUpperCase()) {
            case "CUIDADO_ANIMALES": return "Cuidado";
            case "VETERINARIA": return "Veterinaria";
            case "ADMINISTRACION": return "Admin";
            case "LIMPIEZA": return "Limpieza";
            default: return "Voluntario";
        }
    }

    private void mostrarComentariosVoluntario(Voluntario voluntario) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Comentarios - " + voluntario.getNombreCompleto());
        dialog.setHeaderText("Gestión de comentarios del voluntario");

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setPrefWidth(700);

        // Información del voluntario
        VBox infoVoluntario = new VBox(5);
        infoVoluntario.setStyle("-fx-background-color: #e9ecef; -fx-padding: 10; -fx-border-radius: 5;");
        infoVoluntario.getChildren().addAll(
                new Label("Voluntario: " + voluntario.getNombreCompleto()),
                new Label("Email: " + (voluntario.getEmail() != null ? voluntario.getEmail() : "No especificado")),
                new Label("Especialidad: " + (voluntario.getEspecialidad() != null ? voluntario.getEspecialidad() : "No especificada")),
                new Label("Estado: " + voluntario.getEstado())
        );

        // Botones de acción
        HBox botonesAccion = new HBox(10);
        Button btnNuevoComentario = new Button("Nuevo Comentario");
        Button btnRefrescar = new Button("Refrescar");
        
        btnNuevoComentario.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnRefrescar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");
        
        btnNuevoComentario.setOnAction(e -> crearNuevoComentario(voluntario, dialog));
        
        botonesAccion.getChildren().addAll(btnNuevoComentario, btnRefrescar);

        // Lista de comentarios
        VBox listaComentarios = new VBox(10);
        cargarComentariosEnLista(voluntario, listaComentarios);

        // Scroll para los comentarios
        ScrollPane scrollComentarios = new ScrollPane(listaComentarios);
        scrollComentarios.setPrefHeight(400);
        scrollComentarios.setFitToWidth(true);
        scrollComentarios.setStyle("-fx-background-color: transparent;");

        // Refrescar lista al hacer clic en refrescar
        btnRefrescar.setOnAction(e -> cargarComentariosEnLista(voluntario, listaComentarios));

        contenido.getChildren().addAll(infoVoluntario, botonesAccion, scrollComentarios);

        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void cargarComentariosEnLista(Voluntario voluntario, VBox listaComentarios) {
        listaComentarios.getChildren().clear();
        List<Comentario> comentarios = fundacionService.obtenerComentariosPorVoluntario(voluntario.getId());

        if (comentarios.isEmpty()) {
            Label lblSinComentarios = new Label("No hay comentarios para este voluntario");
            lblSinComentarios.setStyle("-fx-text-fill: gray; -fx-font-style: italic; -fx-padding: 20;");
            listaComentarios.getChildren().add(lblSinComentarios);
            return;
        }

        // Ordenar comentarios por fecha (más recientes primero)
        comentarios.sort((c1, c2) -> c2.getFechaCreacion().compareTo(c1.getFechaCreacion()));

        for (Comentario comentario : comentarios) {
            VBox tarjetaComentario = crearTarjetaComentario(comentario, listaComentarios);
            listaComentarios.getChildren().add(tarjetaComentario);
        }
    }

    private VBox crearTarjetaComentario(Comentario comentario, VBox contenedorPadre) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; " +
                "-fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        // Encabezado del comentario
        HBox encabezado = new HBox(10);
        encabezado.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label(comentario.getTitulo());
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblCategoria = new Label(comentario.getCategoria());
        lblCategoria.setStyle("-fx-background-color: " + obtenerColorCategoria(comentario.getCategoria()) + 
                "; -fx-text-fill: white; -fx-padding: 2 8; -fx-background-radius: 12; -fx-font-size: 10px;");

        Label lblEstado = new Label(comentario.getEstado());
        lblEstado.setStyle("-fx-background-color: " + obtenerColorEstado(comentario.getEstado()) + 
                "; -fx-text-fill: white; -fx-padding: 2 8; -fx-background-radius: 12; -fx-font-size: 10px;");

        // Spacer
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Label lblFecha = new Label(comentario.getFechaFormateada());
        lblFecha.setStyle("-fx-text-fill: gray; -fx-font-size: 11px;");

        encabezado.getChildren().addAll(lblTitulo, lblCategoria, lblEstado, spacer, lblFecha);

        // Contenido del comentario
        Label lblContenido = new Label(comentario.getContenido());
        lblContenido.setWrapText(true);
        lblContenido.setStyle("-fx-font-size: 12px; -fx-padding: 5 0;");

        // Respuesta (si existe)
        VBox respuestaBox = new VBox(5);
        if (comentario.tieneRespuesta()) {
            respuestaBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-border-radius: 5;");
            
            Label lblRespuestaHeader = new Label("Respuesta:");
            lblRespuestaHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: #495057;");
            
            Label lblRespuesta = new Label(comentario.getRespuesta());
            lblRespuesta.setWrapText(true);
            lblRespuesta.setStyle("-fx-font-size: 11px;");
            
            String infoRespuesta = "Por: " + comentario.getRespondidoPor() + 
                    " - " + comentario.getFechaRespuesta().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Label lblInfoRespuesta = new Label(infoRespuesta);
            lblInfoRespuesta.setStyle("-fx-text-fill: gray; -fx-font-size: 10px; -fx-font-style: italic;");
            
            respuestaBox.getChildren().addAll(lblRespuestaHeader, lblRespuesta, lblInfoRespuesta);
        }

        // Botones de acción
        HBox botones = new HBox(10);
        
        Button btnResponder = new Button(comentario.tieneRespuesta() ? "Editar Respuesta" : "Responder");
        Button btnEliminar = new Button("Eliminar");
        
        btnResponder.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-size: 10px;");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 10px;");
        
        btnResponder.setOnAction(e -> responderComentario(comentario, contenedorPadre));
        btnEliminar.setOnAction(e -> eliminarComentario(comentario, contenedorPadre));
        
        botones.getChildren().addAll(btnResponder, btnEliminar);

        tarjeta.getChildren().addAll(encabezado, lblContenido);
        if (comentario.tieneRespuesta()) {
            tarjeta.getChildren().add(respuestaBox);
        }
        tarjeta.getChildren().add(botones);

        return tarjeta;
    }

    private String obtenerColorCategoria(String categoria) {
        switch (categoria.toUpperCase()) {
            case "SUGERENCIA": return "#28a745";
            case "REPORTE": return "#17a2b8";
            case "FELICITACION": return "#ffc107";
            case "QUEJA": return "#dc3545";
            case "PREGUNTA": return "#6f42c1";
            default: return "#6c757d";
        }
    }

    private String obtenerColorEstado(String estado) {
        switch (estado.toUpperCase()) {
            case "NUEVO": return "#dc3545";
            case "LEIDO": return "#ffc107";
            case "RESPONDIDO": return "#28a745";
            case "CERRADO": return "#6c757d";
            default: return "#6c757d";
        }
    }

    private void crearNuevoComentario(Voluntario voluntario, Dialog<?> dialogPadre) {
        Dialog<Comentario> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Comentario");
        dialog.setHeaderText("Crear comentario para " + voluntario.getNombreCompleto());

        // Campos del formulario
        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título del comentario");

        ComboBox<String> cmbCategoria = new ComboBox<>();
        cmbCategoria.getItems().addAll("SUGERENCIA", "REPORTE", "FELICITACION", "QUEJA", "PREGUNTA");
        cmbCategoria.setValue("REPORTE");

        TextArea txtContenido = new TextArea();
        txtContenido.setPromptText("Contenido del comentario");
        txtContenido.setPrefRowCount(4);

        ComboBox<String> cmbPrioridad = new ComboBox<>();
        cmbPrioridad.getItems().addAll("BAJA", "MEDIA", "ALTA", "URGENTE");
        cmbPrioridad.setValue("MEDIA");

        CheckBox chkPublico = new CheckBox("Comentario público");
        chkPublico.setSelected(true);

        // Layout del formulario
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Título:"), 0, 0);
        grid.add(txtTitulo, 1, 0);
        grid.add(new Label("Categoría:"), 0, 1);
        grid.add(cmbCategoria, 1, 1);
        grid.add(new Label("Contenido:"), 0, 2);
        grid.add(txtContenido, 1, 2);
        grid.add(new Label("Prioridad:"), 0, 3);
        grid.add(cmbPrioridad, 1, 3);
        grid.add(chkPublico, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Botones
        ButtonType btnCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCrear, btnCancelar);

        // Validación
        Button crearBtn = (Button) dialog.getDialogPane().lookupButton(btnCrear);
        crearBtn.setDisable(true);

        Runnable validador = () -> {
            boolean tituloValido = !txtTitulo.getText().trim().isEmpty();
            boolean contenidoValido = !txtContenido.getText().trim().isEmpty();
            crearBtn.setDisable(!(tituloValido && contenidoValido));
        };

        txtTitulo.textProperty().addListener((obs, old, nuevo) -> validador.run());
        txtContenido.textProperty().addListener((obs, old, nuevo) -> validador.run());

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnCrear) {
                try {
                    Comentario comentario = fundacionService.crearComentario(
                            voluntario.getId(),
                            txtTitulo.getText().trim(),
                            txtContenido.getText().trim(),
                            cmbCategoria.getValue()
                    );
                    comentario.setPrioridad(cmbPrioridad.getValue());
                    comentario.setPublico(chkPublico.isSelected());
                    fundacionService.actualizarComentario(comentario);
                    return comentario;
                } catch (Exception e) {
                    mostrarAlerta("Error", "Error al crear el comentario: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Comentario> resultado = dialog.showAndWait();
        if (resultado.isPresent()) {
            mostrarAlerta("Éxito", "Comentario creado correctamente.");
            // Refrescar la vista del diálogo padre
            if (dialogPadre.getDialogPane().getContent() instanceof VBox) {
                // Buscar y refrescar la lista de comentarios
                // Esto se haría más elegantemente con un patrón Observer
            }
        }
    }

    private void responderComentario(Comentario comentario, VBox contenedorPadre) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Responder Comentario");
        dialog.setHeaderText("Respuesta a: " + comentario.getTitulo());

        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(20));

        // Mostrar comentario original
        Label lblOriginal = new Label("Comentario original:");
        lblOriginal.setStyle("-fx-font-weight: bold;");
        
        TextArea txtOriginal = new TextArea(comentario.getContenido());
        txtOriginal.setEditable(false);
        txtOriginal.setPrefRowCount(2);
        txtOriginal.setStyle("-fx-background-color: #f8f9fa;");

        // Campo para la respuesta
        Label lblRespuesta = new Label("Respuesta:");
        lblRespuesta.setStyle("-fx-font-weight: bold;");
        
        TextArea txtRespuesta = new TextArea(comentario.getRespuesta() != null ? comentario.getRespuesta() : "");
        txtRespuesta.setPromptText("Escriba su respuesta aquí...");
        txtRespuesta.setPrefRowCount(4);

        contenido.getChildren().addAll(lblOriginal, txtOriginal, lblRespuesta, txtRespuesta);
        dialog.getDialogPane().setContent(contenido);

        // Botones
        ButtonType btnResponder = new ButtonType("Responder", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnResponder, btnCancelar);

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnResponder && !txtRespuesta.getText().trim().isEmpty()) {
                return txtRespuesta.getText().trim();
            }
            return null;
        });

        Optional<String> resultado = dialog.showAndWait();
        if (resultado.isPresent()) {
            try {
                boolean respondido = fundacionService.responderComentario(
                        comentario.getId(), 
                        resultado.get(), 
                        "Usuario1234"
                );
                if (respondido) {
                    mostrarAlerta("Éxito", "Respuesta guardada correctamente.");
                    // Recargar la tarjeta del comentario
                    Optional<Voluntario> voluntario = fundacionService.buscarVoluntarioPorId(comentario.getVoluntarioId());
                    if (voluntario.isPresent()) {
                        cargarComentariosEnLista(voluntario.get(), contenedorPadre);
                    }
                } else {
                    mostrarAlerta("Error", "No se pudo guardar la respuesta.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al responder: " + e.getMessage());
            }
        }
    }

    private void eliminarComentario(Comentario comentario, VBox contenedorPadre) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar este comentario?");
        alert.setContentText("Comentario: " + comentario.getTitulo() + "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean eliminado = fundacionService.eliminarComentario(comentario.getId());
                if (eliminado) {
                    mostrarAlerta("Éxito", "Comentario eliminado correctamente.");
                    // Recargar la lista
                    Optional<Voluntario> voluntario = fundacionService.buscarVoluntarioPorId(comentario.getVoluntarioId());
                    if (voluntario.isPresent()) {
                        cargarComentariosEnLista(voluntario.get(), contenedorPadre);
                    }
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el comentario.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}