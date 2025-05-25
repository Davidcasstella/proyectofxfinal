package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControladorReceperacionTres {

    @FXML
    private TextField codeField1, codeField2, codeField3, codeField4, codeField5, codeField6;

    @FXML
    private Button nextButton;

    @FXML
    private AnchorPane modalPane;

    @FXML
    private Label closeModalLabel;

    @FXML
    private Button modalNextButton;

    @FXML
    public void initialize() {
        // Configurar movimiento automático entre campos
        setupAutoMove(codeField1, codeField2);
        setupAutoMove(codeField2, codeField3);
        setupAutoMove(codeField3, codeField4);
        setupAutoMove(codeField4, codeField5);
        setupAutoMove(codeField5, codeField6);

        nextButton.setOnAction(event -> handleNext());
        closeModalLabel.setOnMouseClicked(event -> closeModal());
        modalNextButton.setOnAction(event -> handleModalNext());
    }

    private void setupAutoMove(TextField from, TextField to) {
        from.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 1) {
                to.requestFocus();
            }
            if (newText.length() > 1) {
                from.setText(newText.substring(0, 1));
            }
        });

        from.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (from.getText().length() >= 1) {
                event.consume();
            }
        });
    }

    private void handleNext() {
        String code =
            codeField1.getText().trim() +
            codeField2.getText().trim() +
            codeField3.getText().trim() +
            codeField4.getText().trim() +
            codeField5.getText().trim() +
            codeField6.getText().trim();

        if (code.length() < 6) {
            showAlert("Error", "Por favor ingresa el código completo de 6 dígitos.", Alert.AlertType.ERROR);
            return;
        }

        // Mostrar el modal de éxito
        modalPane.setVisible(true);
        modalPane.setManaged(true);
    }

    private void closeModal() {
        modalPane.setVisible(false);
        modalPane.setManaged(false);
    }

    private void handleModalNext() {
        // Aquí puedes agregar lógica para continuar (redirigir a otra pantalla)
        System.out.println("Continuar después de confirmar el código");

        // Por ejemplo, cerrar el modal
        closeModal();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

      @FXML
    private void Siguienteeee() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaCreaTuContraseña");  // Recargar la vista de la pantalla principal
    }
    
       @FXML
    private void reloadPageeee() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperaciontres");  // Recargar la vista de la pantalla principal
    }
        @FXML
    private void Antessss() throws IOException {
        // Recarga la página actual
        App.setRoot("PatallaCodigoRecuperacionDos");  // Recargar la vista de la pantalla principal
    }

}
