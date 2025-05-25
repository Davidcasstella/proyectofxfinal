package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ControladorReceperacion {

    @FXML
    private TextField recoveryField;

    @FXML
    private Button nextButton;

    @FXML
    public void initialize() {
        nextButton.setOnAction(event -> handleNext());
    }

    // Validar campo y acción siguiente
    private void handleNext() {
        String input = recoveryField.getText().trim();

        if (input.isEmpty()) {
            showAlert("Error", "Por favor ingrese su correo o número de recuperación.", AlertType.ERROR);
            return;
        }

        // Aquí puedes agregar validación más específica o enviar el dato a backend

        showAlert("Éxito", "Información recibida correctamente. Próximo paso.", AlertType.INFORMATION);

        // Ejemplo para cambiar a otra pantalla, si tienes:
        // try {
        //    App.setRoot("OtraPantalla");
        // } catch (IOException e) {
        //    e.printStackTrace();
        // }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


       @FXML
    private void Siguientee() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperacionDos");  // Recargar la vista de la pantalla principal
    }
    
       @FXML
    private void reloadPagee() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperacion");  // Recargar la vista de la pantalla principal
    }
        @FXML
    private void Antess() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaLogin");  // Recargar la vista de la pantalla principal
    }
}
