package co.edu.uptc.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ControladorAsignaciones {

    @FXML
    private Button btnAsignarRecurso;

    @FXML
    private TableView<Asignacion> tablaAsignaciones;

    private ObservableList<Asignacion> listaAsignaciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tablaAsignaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Datos ejemplo llenos con valores variados
        listaAsignaciones.addAll(
            new Asignacion("María García", "Luna (Gato)", "Alimento: 10kg", "Pendiente"),
            new Asignacion("Juan Pérez", "Max (Perro)", "Dinero: $500", "Entregado"),
            new Asignacion("Ana Martínez", "Peppa (Gato)", "Medicamento: Antipulgas", "En Proceso"),
            new Asignacion("Carlos López", "Lucas (Gato)", "Alimento: 5kg", "Pendiente"),
            new Asignacion("María García", "Tobias (Gato)", "Dinero: $200", "Entregado")
        );

        tablaAsignaciones.setItems(listaAsignaciones);

        btnAsignarRecurso.setOnAction(e -> {
            System.out.println("Botón Asignar Recurso presionado");
            // Código para asignar nuevo recurso
        });

        // Columna eliminar: botón para eliminar fila
        TableColumn<Asignacion, Void> colEliminar = (TableColumn<Asignacion, Void>) tablaAsignaciones.getColumns().get(4);
        colEliminar.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Asignacion, Void> call(TableColumn<Asignacion, Void> param) {
                return new TableCell<>() {
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEliminar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEliminar.setOnAction(event -> {
                            Asignacion asignacion = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(asignacion);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEliminar);
                        }
                    }
                };
            }
        });
    }

    public static class Asignacion {
        private final String donante;
        private final String animal;
        private final String recurso;
        private final String estado;

        public Asignacion(String donante, String animal, String recurso, String estado) {
            this.donante = donante;
            this.animal = animal;
            this.recurso = recurso;
            this.estado = estado;
        }

        public String getDonante() { return donante; }
        public String getAnimal() { return animal; }
        public String getRecurso() { return recurso; }
        public String getEstado() { return estado; }
    }
}
