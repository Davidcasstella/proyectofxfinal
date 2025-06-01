package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Donante;
import co.edu.uptc.servicio.FundacionService;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ControladorDonantes {

    @FXML
    private Button btnNuevoDonante;

    @FXML
    private FlowPane flowPaneDonantes;

    private FundacionService fundacionService;

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();
        configurarEventos();
        cargarDonantes();
    }

    private void configurarEventos() {
        btnNuevoDonante.setOnAction(e -> mostrarFormularioDonante(null));
    }

    private void cargarDonantes() {
        flowPaneDonantes.getChildren().clear();
        List<Donante> donantes = fundacionService.obtenerTodosLosDonantes();
        
        for (Donante donante : donantes) {
            flowPaneDonantes.getChildren().add(crearTarjetaDonante(donante));
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
        foto.setFill(Color.LIGHTGRAY);

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

        // Información adicional
        String fechaUltima = donante.getUltimaDonacion() != null ? 
                donante.getUltimaDonacion().toString() : "--/--/----";
        Label ultima = new Label("Última donación: " + fechaUltima);
        ultima.setStyle("-fx-font-size: 11; -fx-text-fill: gray;");

        // Botones de acción
        HBox botones = new HBox(5);
        botones.setAlignment(Pos.CENTER);

        Button btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white; -fx-font-size: 10px;");
        btnEditar.setOnAction(e -> mostrarFormularioDonante(donante));

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 10px;");
        btnEliminar.setOnAction(e -> eliminarDonante(donante));

        botones.getChildren().addAll(btnEditar, btnEliminar);

        tarjeta.getChildren().addAll(info, tipos, ultima, botones);
        return tarjeta;
    }

    private void styleEtiquetaDonacion(Label label, boolean activo) {
        if (activo) {
            label.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-padding: 2 8; -fx-background-radius: 8;");
        } else {
            label.setStyle("-fx-background-color: #ddd; -fx-text-fill: gray; -fx-padding: 2 8; -fx-background-radius: 8;");
        }
    }

    private void mostrarFormularioDonante(Donante donante) {
        Dialog<Donante> dialog = new Dialog<>();
        dialog.setTitle(donante == null ? "Registrar Nuevo Donante" : "Editar Donante");
        dialog.setHeaderText(donante == null ? "Ingrese los datos del donante" : "Modifique los datos del donante");

        // Crear campos del formulario
        TextField txtNombre = new TextField(donante != null ? donante.getNombre() : "");
        txtNombre.setPromptText("Nombre completo");

        TextField txtDocumento = new TextField(donante != null ? donante.getDocumento() : "");
        txtDocumento.setPromptText("Número de documento");

        TextField txtEmail = new TextField(donante != null ? donante.getEmail() : "");
        txtEmail.setPromptText("Correo electrónico");

        TextField txtTelefono = new TextField(donante != null ? donante.getTelefono() : "");
        txtTelefono.setPromptText("Teléfono");

        TextField txtDireccion = new TextField(donante != null ? donante.getDireccion() : "");
        txtDireccion.setPromptText("Dirección");

        CheckBox chkDinero = new CheckBox("Dona dinero");
        chkDinero.setSelected(donante != null && donante.isDinero());

        CheckBox chkAlimento = new CheckBox("Dona alimento");
        chkAlimento.setSelected(donante != null && donante.isAlimento());

        CheckBox chkMedicamento = new CheckBox("Dona medicamento");
        chkMedicamento.setSelected(donante != null && donante.isMedicamento());

        // Layout del formulario
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Documento:"), 0, 1);
        grid.add(txtDocumento, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(txtEmail, 1, 2);
        grid.add(new Label("Teléfono:"), 0, 3);
        grid.add(txtTelefono, 1, 3);
        grid.add(new Label("Dirección:"), 0, 4);
        grid.add(txtDireccion, 1, 4);
        grid.add(new Label("Tipos de donación:"), 0, 5);
        
        VBox tiposDonacion = new VBox(5);
        tiposDonacion.getChildren().addAll(chkDinero, chkAlimento, chkMedicamento);
        grid.add(tiposDonacion, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Botones
        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // Validación
        Button guardarBtn = (Button) dialog.getDialogPane().lookupButton(btnGuardar);
        guardarBtn.setDisable(true);

        // Validador para habilitar/deshabilitar el botón guardar
        Runnable validador = () -> {
            boolean nombreValido = !txtNombre.getText().trim().isEmpty();
            boolean documentoValido = !txtDocumento.getText().trim().isEmpty();
            boolean alMenosUnTipo = chkDinero.isSelected() || chkAlimento.isSelected() || chkMedicamento.isSelected();
            
            guardarBtn.setDisable(!(nombreValido && documentoValido && alMenosUnTipo));
        };

        txtNombre.textProperty().addListener((obs, old, nuevo) -> validador.run());
        txtDocumento.textProperty().addListener((obs, old, nuevo) -> validador.run());
        chkDinero.selectedProperty().addListener((obs, old, nuevo) -> validador.run());
        chkAlimento.selectedProperty().addListener((obs, old, nuevo) -> validador.run());
        chkMedicamento.selectedProperty().addListener((obs, old, nuevo) -> validador.run());

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                // Validar documento único (solo para nuevos donantes)
                if (donante == null && fundacionService.existeDocumentoDonante(txtDocumento.getText().trim())) {
                    mostrarAlerta("Error", "Ya existe un donante con este documento.");
                    return null;
                }

                Donante resultado = donante != null ? donante : new Donante();
                resultado.setNombre(txtNombre.getText().trim());
                resultado.setDocumento(txtDocumento.getText().trim());
                resultado.setEmail(txtEmail.getText().trim());
                resultado.setTelefono(txtTelefono.getText().trim());
                resultado.setDireccion(txtDireccion.getText().trim());
                resultado.setDinero(chkDinero.isSelected());
                resultado.setAlimento(chkAlimento.isSelected());
                resultado.setMedicamento(chkMedicamento.isSelected());
                
                if (resultado.getFechaRegistro() == null) {
                    resultado.setFechaRegistro(LocalDate.now());
                }
                
                return resultado;
            }
            return null;
        });

        Optional<Donante> resultado = dialog.showAndWait();
        resultado.ifPresent(this::guardarDonante);
    }

    private void guardarDonante(Donante donante) {
        try {
            if (donante.getId() == null) {
                fundacionService.guardarDonante(donante);
                mostrarAlerta("Éxito", "Donante registrado correctamente.");
            } else {
                fundacionService.actualizarDonante(donante);
                mostrarAlerta("Éxito", "Donante actualizado correctamente.");
            }
            cargarDonantes();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar el donante: " + e.getMessage());
        }
    }

    private void eliminarDonante(Donante donante) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar este donante?");
        alert.setContentText("Donante: " + donante.getNombre() + "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean eliminado = fundacionService.eliminarDonante(donante.getId());
                if (eliminado) {
                    mostrarAlerta("Éxito", "Donante eliminado correctamente.");
                    cargarDonantes();
                } else {
                    mostrarAlerta("Error", "No se puede eliminar el donante. Puede tener asignaciones asociadas.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar el donante: " + e.getMessage());
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