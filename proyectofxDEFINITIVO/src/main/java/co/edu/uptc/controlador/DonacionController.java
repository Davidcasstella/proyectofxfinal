package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Animall;
import co.edu.uptc.modelo.Donacion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DonacionController {
    
    @FXML private Label lblAnimalNombre;
    @FXML private ImageView imgAnimal;
    @FXML private ComboBox<String> cmbTipoDonacion;
    @FXML private ComboBox<String> cmbMetodoEntrega;
    @FXML private TextField txtCantidad;
    @FXML private TextArea txtDescripcion;
    
    private MainController mainController;
    private Animall animal;
    
    @FXML
    public void initialize() {
        // Configurar los items de los ComboBox
        cmbTipoDonacion.getItems().addAll("Comida", "Medicamento", "Dinero", "Juguetes", "Mantas");
        cmbMetodoEntrega.getItems().addAll("Presencial", "Envío", "Virtual");
    }
    
    @FXML
    private void handleCancelar() {
        if (mainController != null) {
            mainController.loadView("DonarView.fxml");
        }
    }
    
    @FXML
    private void handleContinuar() {
        if (validarFormulario()) {
            // Crear objeto de donación
            Donacion donacion = new Donacion();
            donacion.setAnimal(animal);
            donacion.setTipo(cmbTipoDonacion.getValue());
            donacion.setMetodoEntrega(cmbMetodoEntrega.getValue());
            donacion.setCantidad(txtCantidad.getText());
            donacion.setDescripcion(txtDescripcion.getText());
            
            // Ir a la vista de pago
            if (mainController != null) {
                mainController.loadPagoView(donacion);
            }
        }
    }
    
    private boolean validarFormulario() {
        if (cmbTipoDonacion.getValue() == null) {
            mostrarAlerta("Error", "Por favor seleccione el tipo de donación");
            return false;
        }
        
        if (cmbMetodoEntrega.getValue() == null) {
            mostrarAlerta("Error", "Por favor seleccione el método de entrega");
            return false;
        }
        
        if (txtCantidad.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese la cantidad");
            return false;
        }
        
        // Validar que sea un número si el tipo es "Dinero"
        if ("Dinero".equals(cmbTipoDonacion.getValue())) {
            try {
                Double.parseDouble(txtCantidad.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Por favor ingrese un monto válido");
                return false;
            }
        }
        
        return true;
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void setAnimal(Animall animal) {
        this.animal = animal;
        lblAnimalNombre.setText(animal.getNombre());
        
        // Cargar imagen del animal
        try {
            Image image = new Image(getClass().getResource("/co/edu/uptc/imagenes/images (1).jpg").toExternalForm());
            imgAnimal.setImage(image);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del animal");
        }
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}