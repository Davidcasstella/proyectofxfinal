package co.edu.uptc.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class ControladorAnimales {

    @FXML
    private Button btnRegistrarAnimal;

    @FXML
    private TableView<Animal> tablaAnimales;

    private ObservableList<Animal> listaAnimales = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tablaAnimales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Datos ejemplo
        listaAnimales.addAll(
            new Animal("foto1.png", "Max", "Perro", "Activo"),
            new Animal("foto2.png", "Luna", "Gato", "En tratamiento"),
            new Animal("foto3.png", "Peppa", "Gato", "Activo"),
            new Animal("foto4.png", "Lucas", "Gato", "Crítico"),
            new Animal("foto5.png", "Tobias", "Gato", "En observación")
        );

        tablaAnimales.setItems(listaAnimales);

        btnRegistrarAnimal.setOnAction(e -> {
            System.out.println("Botón Registrar animal presionado");
            // Aquí código para registrar animal nuevo
        });

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
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        // Columna Acciones: botón eliminar
        TableColumn<Animal, Void> colAcciones = (TableColumn<Animal, Void>) tablaAnimales.getColumns().get(4);
        colAcciones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Animal, Void> call(TableColumn<Animal, Void> param) {
                return new TableCell<>() {
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEliminar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEliminar.setOnAction(event -> {
                            Animal animal = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(animal);
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

    public static class Animal {
        private final String foto;
        private final String nombre;
        private final String especie;
        private final String estado;

        public Animal(String foto, String nombre, String especie, String estado) {
            this.foto = foto;
            this.nombre = nombre;
            this.especie = especie;
            this.estado = estado;
        }

        public String getFoto() {
            return foto;
        }

        public String getNombre() {
            return nombre;
        }

        public String getEspecie() {
            return especie;
        }

        public String getEstado() {
            return estado;
        }
    }
}
