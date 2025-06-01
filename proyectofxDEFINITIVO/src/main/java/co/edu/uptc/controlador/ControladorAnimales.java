package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Animal;
import co.edu.uptc.servicio.FundacionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Optional;

public class ControladorAnimales {

    @FXML
    private Button btnRegistrarAnimal;

    @FXML
    private TableView<Animal> tablaAnimales;

    private ObservableList<Animal> listaAnimales = FXCollections.observableArrayList();
    private FundacionService fundacionService;

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();
        tablaAnimales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarAnimales();
        configurarColumnas();
        configurarEventos();
    }

    private void cargarAnimales() {
        listaAnimales.clear();
        listaAnimales.addAll(fundacionService.obtenerTodosLosAnimales());
        tablaAnimales.setItems(listaAnimales);
    }

    private void configurarColumnas() {
        // Columna Foto: mostrar imagen
        TableColumn<Animal, String> colFoto = (TableColumn<Animal, String>) tablaAnimales.getColumns().get(0);
        colFoto.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Animal, String> call(TableColumn<Animal, String> param) {
                return new TableCell<>() {
                    private final ImageView imageView = new ImageView();

                    {
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        imageView.setPreserveRatio(true);
                    }

                    @Override
                    protected void updateItem(String fotoPath, boolean empty) {
                        super.updateItem(fotoPath, empty);
                        if (empty || fotoPath == null) {
                            setGraphic(null);
                        } else {
                            try {
                                Image img = new Image(getClass().getResourceAsStream("/images/" + fotoPath));
                                imageView.setImage(img);
                                setGraphic(imageView);
                            } catch (Exception e) {
                                // Si no se encuentra la imagen, mostrar una por defecto o nada
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        // Columna Acciones: botones editar y eliminar
        TableColumn<Animal, Void> colAcciones = (TableColumn<Animal, Void>) tablaAnimales.getColumns().get(4);
        colAcciones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Animal, Void> call(TableColumn<Animal, Void> param) {
                return new TableCell<>() {
                    private final Button btnEditar = new Button("Editar");
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEditar.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
                        btnEliminar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
                        
                        btnEditar.setOnAction(event -> {
                            Animal animal = getTableView().getItems().get(getIndex());
                            editarAnimal(animal);
                        });
                        
                        btnEliminar.setOnAction(event -> {
                            Animal animal = getTableView().getItems().get(getIndex());
                            eliminarAnimal(animal);
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
        btnRegistrarAnimal.setOnAction(e -> mostrarFormularioAnimal(null));
    }

    private void mostrarFormularioAnimal(Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle(animal == null ? "Registrar Nuevo Animal" : "Editar Animal");
        dialog.setHeaderText(animal == null ? "Ingrese los datos del animal" : "Modifique los datos del animal");

        // Crear campos del formulario
        TextField txtNombre = new TextField(animal != null ? animal.getNombre() : "");
        txtNombre.setPromptText("Nombre del animal");

        ComboBox<String> cmbEspecie = new ComboBox<>();
        cmbEspecie.getItems().addAll("Perro", "Gato", "Conejo", "Hamster", "Otro");
        cmbEspecie.setValue(animal != null ? animal.getEspecie() : "Perro");

        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("Activo", "En tratamiento", "Crítico", "En observación", "Recuperándose");
        cmbEstado.setValue(animal != null ? animal.getEstado() : "Activo");

        TextField txtRaza = new TextField(animal != null ? animal.getRaza() : "");
        txtRaza.setPromptText("Raza");

        TextField txtEdad = new TextField(animal != null ? String.valueOf(animal.getEdad()) : "");
        txtEdad.setPromptText("Edad en años");

        ComboBox<String> cmbSexo = new ComboBox<>();
        cmbSexo.getItems().addAll("Macho", "Hembra");
        cmbSexo.setValue(animal != null ? animal.getSexo() : "Macho");

        TextArea txtDescripcion = new TextArea(animal != null ? animal.getDescripcion() : "");
        txtDescripcion.setPromptText("Descripción del animal");
        txtDescripcion.setPrefRowCount(3);

        CheckBox chkAdoptado = new CheckBox("Adoptado");
        chkAdoptado.setSelected(animal != null && animal.isAdoptado());

        // Layout del formulario
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Especie:"), 0, 1);
        grid.add(cmbEspecie, 1, 1);
        grid.add(new Label("Estado:"), 0, 2);
        grid.add(cmbEstado, 1, 2);
        grid.add(new Label("Raza:"), 0, 3);
        grid.add(txtRaza, 1, 3);
        grid.add(new Label("Edad:"), 0, 4);
        grid.add(txtEdad, 1, 4);
        grid.add(new Label("Sexo:"), 0, 5);
        grid.add(cmbSexo, 1, 5);
        grid.add(new Label("Descripción:"), 0, 6);
        grid.add(txtDescripcion, 1, 6);
        grid.add(chkAdoptado, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Botones
        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // Validación
        Button guardarBtn = (Button) dialog.getDialogPane().lookupButton(btnGuardar);
        guardarBtn.setDisable(true);

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            guardarBtn.setDisable(newValue.trim().isEmpty());
        });

        // Resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    Animal resultado = animal != null ? animal : new Animal();
                    resultado.setNombre(txtNombre.getText().trim());
                    resultado.setEspecie(cmbEspecie.getValue());
                    resultado.setEstado(cmbEstado.getValue());
                    resultado.setRaza(txtRaza.getText().trim());
                    resultado.setEdad(txtEdad.getText().isEmpty() ? 0 : Integer.parseInt(txtEdad.getText()));
                    resultado.setSexo(cmbSexo.getValue());
                    resultado.setDescripcion(txtDescripcion.getText().trim());
                    resultado.setAdoptado(chkAdoptado.isSelected());
                    
                    if (resultado.getFechaIngreso() == null) {
                        resultado.setFechaIngreso(LocalDate.now());
                    }
                    
                    return resultado;
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "La edad debe ser un número válido.");
                    return null;
                }
            }
            return null;
        });

        Optional<Animal> resultado = dialog.showAndWait();
        resultado.ifPresent(this::guardarAnimal);
    }

    private void guardarAnimal(Animal animal) {
        try {
            if (animal.getId() == null) {
                fundacionService.guardarAnimal(animal);
                mostrarAlerta("Éxito", "Animal registrado correctamente.");
            } else {
                fundacionService.actualizarAnimal(animal);
                mostrarAlerta("Éxito", "Animal actualizado correctamente.");
            }
            cargarAnimales();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar el animal: " + e.getMessage());
        }
    }

    private void editarAnimal(Animal animal) {
        mostrarFormularioAnimal(animal);
    }

    private void eliminarAnimal(Animal animal) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar este animal?");
        alert.setContentText("Animal: " + animal.getNombre() + "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean eliminado = fundacionService.eliminarAnimal(animal.getId());
                if (eliminado) {
                    mostrarAlerta("Éxito", "Animal eliminado correctamente.");
                    cargarAnimales();
                } else {
                    mostrarAlerta("Error", "No se puede eliminar el animal. Puede tener asignaciones asociadas.");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar el animal: " + e.getMessage());
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