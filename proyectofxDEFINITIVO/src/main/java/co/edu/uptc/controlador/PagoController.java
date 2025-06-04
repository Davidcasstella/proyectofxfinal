package co.edu.uptc.controlador;
import co.edu.uptc.modelo.Donacion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class PagoController {
    
    @FXML private VBox metodoPagoView;
    @FXML private VBox datosTarjetaView;
    @FXML private VBox resumenPagoView;
    
    @FXML private TextField txtTitular;
    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtFechaVencimiento;
    @FXML private TextField txtCVV;
    
    @FXML private Label lblMontoTotal;
    @FXML private Label lblDonacionTipo;
    @FXML private Label lblDonacionCantidad;
    @FXML private Label lblEntregaTipo;
    @FXML private Label lblTitularResumen;
    @FXML private Label lblTarjetaResumen;
    @FXML private Label lblExpiracionResumen;
    @FXML private Label lblTotalFinal;
    @FXML private Label lblMontoTotalResumen;
    
    private MainController mainController;
    private Donacion donacion;
    private double montoTotal;
    
    @FXML
    public void initialize() {
        // Configurar máscaras de entrada
        txtNumeroTarjeta.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                txtNumeroTarjeta.setText(oldText);
            }
            if (newText.length() > 16) {
                txtNumeroTarjeta.setText(oldText);
            }
        });
        
        txtCVV.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*") || newText.length() > 3) {
                txtCVV.setText(oldText);
            }
        });
    }
    
    @FXML
    private void selectPSE() {
        mostrarMensajeRedireccion();
    }
    
    @FXML
    private void selectCard() {
        metodoPagoView.setVisible(false);
        datosTarjetaView.setVisible(true);
        resumenPagoView.setVisible(false);
    }
    
    @FXML
    private void procesarPago() {
        if (validarDatosTarjeta()) {
            // Preparar resumen
            lblTitularResumen.setText(txtTitular.getText());
            lblTarjetaResumen.setText(ocultarNumeroTarjeta(txtNumeroTarjeta.getText()));
            lblExpiracionResumen.setText(txtFechaVencimiento.getText());
            
            // Calcular totales
            double subtotal = montoTotal;
            double iva = subtotal * 0.19;
            double envio = 12000;
            double total = subtotal + iva + envio;
            
            lblTotalFinal.setText(String.format("$ %.0f", total));
            lblMontoTotalResumen.setText(String.format("$%.0f COP", total));
            
            // Mostrar vista de resumen
            metodoPagoView.setVisible(false);
            datosTarjetaView.setVisible(false);
            resumenPagoView.setVisible(true);
        }
    }
    
    @FXML
    private void confirmarPago() {
        mostrarConfirmacionPago();
    }
    
    private boolean validarDatosTarjeta() {
        if (txtTitular.getText().isEmpty() || 
            txtNumeroTarjeta.getText().isEmpty() || 
            txtFechaVencimiento.getText().isEmpty() || 
            txtCVV.getText().isEmpty()) {
            
            mostrarAlerta("Error", "Por favor complete todos los campos");
            return false;
        }
        
        if (txtNumeroTarjeta.getText().length() != 16) {
            mostrarAlerta("Error", "El número de tarjeta debe tener 16 dígitos");
            return false;
        }
        
        if (!txtFechaVencimiento.getText().matches("\\d{2}/\\d{4}")) {
            mostrarAlerta("Error", "El formato de fecha debe ser MM/YYYY");
            return false;
        }
        
        return true;
    }
    
    private String ocultarNumeroTarjeta(String numero) {
        if (numero.length() >= 4) {
            return "XXX XXXX " + numero.substring(numero.length() - 4);
        }
        return numero;
    }
    
    private void mostrarMensajeRedireccion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirección de Pago");
        alert.setHeaderText("¡No te preocupes, pronto serás redirigido para continuar con el pago!");
        alert.setContentText("Por favor espera en línea, no cambies de pantalla mientras realizamos este proceso. Serás redirigido automáticamente.");
        
        ButtonType btnOk = new ButtonType("Entendido", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(btnOk);
        
        alert.showAndWait();
        
        // Simular redirección
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000); // Simular espera
                return null;
            }
        };
        
        task.setOnSucceeded(e -> {
            if (mainController != null) {
                mainController.loadView("PrincipalView.fxml");
            }
        });
        
        new Thread(task).start();
    }
    
    private void mostrarConfirmacionPago() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡MUCHAS GRACIAS!");
        alert.setHeaderText("Esta transacción está sujeta a confirmación");
        alert.setContentText("Por favor verifique su estado en el panel de 'Historial' en la app para descargar su recibo o comprobante de pago actualizado.");
        
        ButtonType btnOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(btnOk);
        
        alert.showAndWait().ifPresent(response -> {
            if (response == btnOk) {
                // Simular guardado de la donación
                guardarDonacion();
                
                // Volver a la vista principal
                if (mainController != null) {
                    mainController.loadView("PrincipalView.fxml");
                }
            }
        });
    }
    
    private void guardarDonacion() {
        // Aquí se guardaría la donación en la base de datos
        System.out.println("Donación guardada exitosamente");
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void setDonacion(Donacion donacion) {
        this.donacion = donacion;
        
        // Actualizar labels con información de la donación
        lblDonacionTipo.setText("Descripción: " + donacion.getTipo());
        lblDonacionCantidad.setText("Descripción: " + donacion.getCantidad());
        lblEntregaTipo.setText("Descripción: " + donacion.getMetodoEntrega());
        
        // Calcular monto total
        try {
            this.montoTotal = Double.parseDouble(donacion.getCantidad().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            this.montoTotal = 50000; // Valor por defecto
        }
        
        lblMontoTotal.setText(String.format("$%.0f COP", montoTotal));
    }
}