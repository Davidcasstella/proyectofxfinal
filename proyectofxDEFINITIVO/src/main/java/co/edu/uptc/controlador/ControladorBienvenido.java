package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorBienvenido {

    @FXML
    private ImageView sideImage;  // Referencia al ImageView de la imagen en la vista
     @FXML
    private ImageView logo;  // Referencia al ImageView de la imagen en la vista

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("PantallaLogin");
    }


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
    private void reloadPage() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaBienvenido");  // Recargar la vista de la pantalla principal
    }
      @FXML
    private void Siguiente() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaLogin");  // Recargar la vista de la pantalla principal
    }
}
