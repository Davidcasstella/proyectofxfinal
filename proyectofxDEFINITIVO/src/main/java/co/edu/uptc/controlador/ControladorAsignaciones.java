package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Animal;
import co.edu.uptc.modelo.Asignacion;
import co.edu.uptc.modelo.Donante;
import co.edu.uptc.servicio.FundacionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ControladorAsignaciones {

    @FXML
    private Button btnAsignarRecurso;

    @FXML
    private TableView<Asignacion> tablaAsignaciones;

    private ObservableList<Asignacion> listaAsignaciones = FXCollections.observableArrayList();
    private FundacionService fundacionService;

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();
        tablaAsignaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarAsignaciones();
        configurarColumnas();
        configurarEventos();
    }

    private void cargarAsignaciones() {
        listaAsignaciones.clear();
        listaAsignaciones.addAll(fundacionService.obtenerTodasLasAsignaciones());
        tablaAsignaciones.setItems(listaAsignaciones);
    }

    private void configurarColumnas() {
        // Columna eliminar: botones editar y eliminar
        TableColumn<Asignacion, Void> colAcciones = (TableColumn<Asignacion, Void>) tablaAsignaciones.getColumns().get(4);
        colAcciones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Asignacion, Void> call(TableColumn<Asignacion, Void> param) {
                return new TableCell<>() {
                    private final Button btnEditar = new Button("Editar");
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEditar.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
                        btnEliminar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
                        
                        btnEditar.setOnAction(event -> {
                            Asignacion asignacion = getTableView().getItems().get(getIndex());
                            editarAsignacion(asignacion);
                        });
                        
                        btnEliminar.setOnAction(event -> {
                            Asignacion asignacion = getTableView().getItems().get(getIndex());
                            eliminarAsignacion(asignacion);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(5, btnEditar, btnEliminar));
                        }
                    }
                };
            }
        });
    }

    private void configurarEventos() {
        btnAsignarRecurso.setOnAction(e -> mostrarFormularioAsignacion(null));
    }

    private void mostrarFormularioAsignacion(Asignacion asignacion) {
        Dialog<Asignacion> dialog = new Dialog<>();
        dialog.setTitle(asignacion == null ? "Nueva Asignación" : "Editar Asignación");
        dialog.setHeaderText(asignacion == null ? "Crear nueva asignación de recurso" : "Modificar asignación existente");

        // Cargar listas para ComboBoxes
        List<Donante> donantes = fundacionService.obtenerTodosLosDonantes();
        List<Animal> animales = fundacionService.obtenerTodosLosAnimales();

        // Crear campos del formulario
        ComboBox<Donante> cmbDonante = new ComboBox<>();
        cmbDonante.getItems().addAll(donantes);
        cmbDonante.setConverter(new javafx.util.StringConverter<Donante>() {
            @Override
            public String toString(Donante donante) {
                return donante != null ? donante.getNombre() + " (" + donante.getDocumento() + ")" : "";
            }

            @Override
            public Donante fromString(String string) {
                return null;
            }
        });

        ComboBox<Animal> cmbAnimal = new ComboBox<>();
        cmbAnimal.getItems().addAll(animales);
        cmbAnimal.setConverter(new javafx.util.StringConverter<Animal>() {
            @Override
            public String toString(Animal animal) {
                return animal != null ? animal.getNombre() + " (" + animal.getEspecie() + ")" : "";
            }

            @Override
            public Animal fromString(String string) {
                return null;
            }
        });

        ComboBox<String> cmbTipoRecurso = new ComboBox<>();
        cmbTipoRecurso.getItems().addAll("Dinero", "Alimento", "Medicamento", "Cuidado Veterinario", "Otro");

        TextField txtDetalle = new TextField();
        txtDetalle.setPromptText("Detalles del recurso (ej: 10kg, $500, Vacunas)");

        TextField txtMonto = new TextField("0");
        txtMonto.setPromptText("Monto (solo para donaciones monetarias)");

        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("Pendiente", "En Proceso", "Entregado", "Cancelado");
        cmbEstado.setValue("Pendiente");

        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Observaciones adicionales");
        txtObservaciones.setPrefRowCount(3);

        // Si es edición, cargar datos existentes
        if (asignacion != null) {
            // Buscar y seleccionar donante
            donantes.stream()
                    .filter(d -> d.getId().equals(asignacion.getDonanteId()))
                    .findFirst()
                    .ifPresent(cmbDonante::setValue);

            // Buscar y seleccionar animal
            animales.stream()
                    .filter(a -> a.getId().equals(asignacion.getAnimalId()))
                    .findFirst()
                    .ifPresent(cmbAnimal::setValue);

            // Cargar otros datos
            String recurso = asignacion.getRecurso();
            if (recurso.toLowerCase().contains("dinero")) {
                cmbTipoRecurso.setValue("Dinero");
            } else if (recurso.toLowerCase().contains("alimento")) {
                cmbTipoRecurso.setValue("Alimento");
            } else if (recurso.toLowerCase().contains("medicamento")) {
                cmbTipoRecurso.setValue("Medicamento");
            } else {
                cmbTipoRecurso.setValue("Otro");
            }

            txtDetalle.setText(recurso);
            txtMonto.setText(String.valueOf(asignacion.getMonto()));
            cmbEstado.setValue(asignacion.getEstado());
            txtObservaciones.setText(asignacion.getObservaciones() != null ? asignacion.getObservaciones() : "");
        }

        // Layout del formulario
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Donante:"), 0, 0);
        grid.add(cmbDonante, 1, 0);
        grid.add(new Label("Animal:"), 0, 1);
        grid.add(cmbAnimal, 1, 1);
        grid.add(new Label("Tipo de recurso:"), 0, 2);
        grid.add(cmbTipoRecurso, 1, 2);
        grid.add(new Label("Detalle:"), 0, 3);
        grid.add(txtDetalle, 1, 3);
        grid.add(new Label("Monto:"), 0, 4);
        grid.add(txtMonto, 1, 4);
        grid.add(new Label("Estado:"), 0, 5);
        grid.add(cmbEstado, 1, 5);
        grid.add(new Label("Observaciones:"), 0, 6);
        grid.add(txtObservaciones, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Botones
        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // Validación
        Button guardarBtn = (Button) dialog.getDialogPane().lookupButton(btnGuardar);
        guardarBtn.setDisable(true);

        Runnable validador = () -> {
            boolean donanteSeleccionado = cmbDonante.getValue() != null;
            boolean animalSeleccionado = cmbAnimal.getValue() != null;
            boolean tipoRecursoSeleccionado = cmbTipoRecurso.getValue() != null;
            boolean detalleValido = !txtDetalle.getText().trim().isEmpty();
            
            guardarBtn.setDisable(!(donanteSeleccionado && animalSeleccionado && 
                                  tipoRecursoSeleccionado && detalleValido));
        };

        cmbDonante.valueProperty().addListener((obs, old, nuevo) -> validador.run());
        cmbAnimal.valueProperty().addListener((obs, old, nuevo) -> validador.run());
        cmbTipoRecurso.valueProperty().addListener((obs, old, nuevo) -> validador.run());
        txtDetalle.textProperty().addListener((obs, old, nuevo) -> validador.run());

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    Donante donanteSeleccionado = cmbDonante.getValue();
                    Animal animalSeleccionado = cmbAnimal.getValue();
                    double monto = txtMonto.getText().isEmpty() ? 0 : Double.parseDouble(txtMonto.getText());

                    if (asignacion == null) {
                        // Nueva asignación
                        Asignacion nueva = new Asignacion(
                                donanteSeleccionado.getId(),
                                animalSeleccionado.getId(),
                                txtDetalle.getText().trim(),
                                cmbEstado.getValue(),
                                monto
                        );
                        nueva.setDonante(donanteSeleccionado.getNombre());
                        nueva.setAnimal(animalSeleccionado.getNombre() + " (" + animalSeleccionado.getEspecie() + ")");
                        nueva.setObservaciones(txtObservaciones.getText().trim());
                        return nueva;
                    } else {
                        // Editar asignación existente
                        asignacion.setDonanteId(donanteSeleccionado.getId());
                        asignacion.setAnimalId(animalSeleccionado.getId());
                        asignacion.setDonante(donanteSeleccionado.getNombre());
                        asignacion.setAnimal(animalSeleccionado.getNombre() + " (" + animalSeleccionado.getEspecie() + ")");
                        asignacion.setRecurso(txtDetalle.getText().trim());
                        asignacion.setEstado(cmbEstado.getValue());
                        asignacion.setMonto(monto);
                        asignacion.setObservaciones(txtObservaciones.getText().trim());
                        
                        if (cmbEstado.getValue().equals("Entregado") && asignacion.getFechaEntrega() == null) {
                            asignacion.setFechaEntrega(LocalDate.now());
                        }
                        
                        return asignacion;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El monto debe ser un número válido.");
                    return null;
                }
            }
            return null;
        });

        Optional<Asignacion> resultado = dialog.showAndWait();
        resultado.ifPresent(this::guardarAsignacion);
    }

    private void guardarAsignacion(Asignacion asignacion) {
        try {
            if (asignacion.getId() == null) {
                fundacionService.crearAsignacion(
                        asignacion.getDonanteId(),
                        asignacion.getAnimalId(),
                        asignacion.getRecurso(),
                        asignacion.getMonto()
                );
                mostrarAlerta("Éxito", "Asignación creada correctamente.");
            } else {
                fundacionService.actualizarAsignacion(asignacion);
                mostrarAlerta("Éxito", "Asignación actualizada correctamente.");
            }
            cargarAsignaciones();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar la asignación: " + e.getMessage());
        }
    }

    private void editarAsignacion(Asignacion asignacion) {
        mostrarFormularioAsignacion(asignacion);
    }

    private void eliminarAsignacion(Asignacion asignacion) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar esta asignación?");
        alert.setContentText("Asignación: " + asignacion.getDonante() + " -> " + asignacion.getAnimal() + 
                "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean eliminado = fundacionService.eliminarAsignacion(asignacion.getId());
                if (eliminado) {
                    mostrarAlerta("Éxito", "Asignación eliminada correctamente.");
                    cargarAsignaciones();
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar la asignación.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar la asignación: " + e.getMessage());
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