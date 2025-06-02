package co.edu.uptc.vista;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class RecuperacionVista {
    
    // Validar entrada de recuperación
    public boolean validarEntradaRecuperacion(String entrada) {
        return entrada != null && !entrada.trim().isEmpty();
    }
    
    // Validar si es email o teléfono
    public boolean esEmail(String entrada) {
        return entrada.contains("@");
    }
    
    // Validar formato de teléfono
    public boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{10}");
    }
    
    // Mostrar/ocultar modal
    public void mostrarModal(AnchorPane modal) {
        modal.setVisible(true);
        modal.setManaged(true);
    }
    
    public void ocultarModal(AnchorPane modal) {
        modal.setVisible(false);
        modal.setManaged(false);
    }
    
    // Mostrar alertas
    public void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Configurar campo de código para auto-movimiento
    public void configurarCamposCodigo(TextField... campos) {
        for (int i = 0; i < campos.length - 1; i++) {
            configurarAutoMovimiento(campos[i], campos[i + 1]);
        }
    }
    
    private void configurarAutoMovimiento(TextField desde, TextField hacia) {
        desde.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 1) {
                hacia.requestFocus();
            }
            if (newText.length() > 1) {
                desde.setText(newText.substring(0, 1));
            }
        });
    }
    
    // Validar código completo
    public boolean validarCodigoCompleto(String codigo) {
        return codigo.length() == 6 && codigo.matches("\\d{6}");
    }
    
    // Obtener código de los campos
    public String obtenerCodigo(TextField... campos) {
        StringBuilder codigo = new StringBuilder();
        for (TextField campo : campos) {
            codigo.append(campo.getText().trim());
        }
        return codigo.toString();
    }
    
    // Limpiar campos de código
    public void limpiarCamposCodigo(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
        if (campos.length > 0) {
            campos[0].requestFocus();
        }
    }
}