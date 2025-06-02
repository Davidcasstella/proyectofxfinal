package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.vista.RecuperacionVista;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ControladorReceperacionTres {

    @FXML private TextField codeField1, codeField2, codeField3, codeField4, codeField5, codeField6;
    @FXML private Button nextButton;
    @FXML private AnchorPane modalPane;
    @FXML private Label closeModalLabel;
    @FXML private Button modalNextButton;

    private RecuperacionVista recuperacionVista;
    private static String codigoRecuperacion;

    @FXML
    public void initialize() {
        recuperacionVista = new RecuperacionVista();
        
        // Configurar movimiento automático entre campos
        TextField[] campos = {codeField1, codeField2, codeField3, codeField4, codeField5, codeField6};
        recuperacionVista.configurarCamposCodigo(campos);
        
        // Limitar a un solo dígito por campo
        for (TextField campo : campos) {
            campo.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                if (campo.getText().length() >= 1) {
                    event.consume();
                }
            });
        }

        nextButton.setOnAction(event -> handleNext());
        closeModalLabel.setOnMouseClicked(event -> closeModal());
        modalNextButton.setOnAction(event -> handleModalNext());
    }

    private void handleNext() {
        String codigo = recuperacionVista.obtenerCodigo(codeField1, codeField2, codeField3, 
                                                       codeField4, codeField5, codeField6);

        if (codigo.length() < 6) {
            recuperacionVista.mostrarAlerta("Error", "Por favor ingresa el código completo de 6 dígitos.", Alert.AlertType.ERROR);
            return;
        }

        // Verificar código
        if (codigo.equals(codigoRecuperacion)) {
            // Mostrar el modal de éxito
            recuperacionVista.mostrarModal(modalPane);
        } else {
            recuperacionVista.mostrarAlerta("Error", "El código ingresado es incorrecto.", Alert.AlertType.ERROR);
            recuperacionVista.limpiarCamposCodigo(codeField1, codeField2, codeField3, 
                                                codeField4, codeField5, codeField6);
        }
    }

    private void closeModal() {
        recuperacionVista.ocultarModal(modalPane);
    }

    private void handleModalNext() {
        closeModal();
        
        try {
            App.setRoot("PantallaCreaTuContraseña");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Siguienteeee() throws IOException {
        App.setRoot("PantallaCreaTuContraseña");
    }
    
    @FXML
    private void reloadPageeee() throws IOException {
        App.setRoot("PatallaCodigoRecuperaciontres");
    }
    
    @FXML
    private void Antessss() throws IOException {
        App.setRoot("PatallaCodigoRecuperacionDos");
    }

    public static void setCodigoRecuperacion(String codigo) {
        codigoRecuperacion = codigo;
    }
}