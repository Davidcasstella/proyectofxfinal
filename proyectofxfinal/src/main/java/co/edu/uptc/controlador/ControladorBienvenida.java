package co.edu.uptc.controlador;  // Cambiar al nuevo paquete

import java.io.IOException;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorBienvenida {

    @FXML
    private ImageView logo;  // Referencia al ImageView del logoo

    @FXML
    private ImageView sideImage;  // Referencia al ImageView de la imagen en la vista

    @FXML
    private void initialize() {
        // Cargar el logo desde los recursos del proyecto
        Image logoImage = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/Logo.png"));
        logo.setImage(logoImage);  // Asignar la imagen al ImageView del logo

        // Cargar la imagen grande (lateral)
        Image sideImageFile = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/images (1).jpg"));
        sideImage.setImage(sideImageFile);  // Asignar la imagen al ImageView lateral
    }

    @FXML
    private void switchToLogin() throws IOException {
        // Cambia a la pantalla de inicio de sesión
        App.setRoot("PantallaLogin");
    }

    @FXML
    private void switchToCreateAccount() throws IOException {
        // Cambia a la pantalla de creación de cuenta
        System.out.println("Pantalla de Crear Cuenta");
    }

    @FXML
    private void reloadPage() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaBienvenida");  // Recargar la vista de la pantalla principal
    }
}
