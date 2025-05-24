package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class ControladorReceperacionDos {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button nextButton;

    @FXML
    private AnchorPane modalPane;

    @FXML
    private Button closeModalButton;

    @FXML
    private Button modalNextButton;

    @FXML
    public void initialize() {
        nextButton.setOnAction(event -> handleNext());

        closeModalButton.setOnAction(event -> hideModal());
        modalNextButton.setOnAction(event -> handleModalNext());
    }

    private void handleNext() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        if (fullName.isEmpty()) {
            showAlert("Error", "Por favor ingresa tu nombre completo.", AlertType.ERROR);
            return;
        }

        if (email.isEmpty()) {
            showAlert("Error", "Por favor ingresa tu correo electrónico registrado.", AlertType.ERROR);
            return;
        }

        // Mostrar modal si validaciones correctas
        showModal();
    }

    private void showModal() {
        modalPane.setVisible(true);
        modalPane.setManaged(true);
    }

    private void hideModal() {
        modalPane.setVisible(false);
        modalPane.setManaged(false);
    }

    private void handleModalNext() {
        // Aquí manejas el siguiente paso después del modal
        hideModal();
        showAlert("Éxito", "Código enviado. Por favor revisa tu correo o teléfono.", AlertType.INFORMATION);

        // Ejemplo: ir a otra pantalla, etc.
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
