package co.edu.uptc.controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import co.edu.uptc.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;  // Importamos TextArea

public class ControladorBienvenido {

    @FXML
    private ImageView sideImage;  // Referencia al ImageView de la imagen en la vista
    @FXML
    private ImageView logo;  // Referencia al ImageView de la imagen en la vista

    // Referencias a los componentes de la interfaz que se van a actualizar con el idioma
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelSlogan;
    @FXML
    private Label labelWhoWeAre;
    @FXML
    private Label labelJoinUs;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCreateAccount;
    @FXML
    private Label labelThankYou;
    @FXML
    private TextArea textAreaWhoWeAre;
    @FXML
    private TextArea textAreaJoinUs;

    private ResourceBundle bundle; // Recurso de texto
    private Locale locale; // Idioma

    // Inicializar con el idioma por defecto (por ejemplo, en español)
    @FXML
    private void initialize() {
        // Establecer idioma (por ejemplo, 'es' para español o 'en' para inglés)
        locale = new Locale("es");  // Usar 'es' para español o 'en' para inglés
        bundle = ResourceBundle.getBundle("messages", locale); // Cargar el archivo de recursos según el idioma

        // Cargar las imágenes desde los recursos
        Image logoImage = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/Logo.png"));
        logo.setImage(logoImage);

        Image sideImageFile = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/images (1).jpg"));
        sideImage.setImage(sideImageFile);

        // Inicializar los textos en español (por defecto)
        updateLanguage();
    }

    // Método para cambiar el idioma
    @FXML
    public void changeLanguage() {
        if (locale.getLanguage().equals("es")) {
            locale = Locale.ENGLISH;  // Cambiar a inglés
        } else {
            locale = new Locale("es"); // Cambiar a español
        }
        bundle = ResourceBundle.getBundle("messages", locale);  // Cargar el nuevo archivo de recursos
        updateLanguage();  // Actualizar los textos con el nuevo idioma
    }

    // Actualiza los textos de la interfaz según el idioma
    private void updateLanguage() {
        labelTitle.setText(bundle.getString("title"));
        labelSlogan.setText(bundle.getString("slogan"));
        labelWhoWeAre.setText(bundle.getString("who_we_are"));
        labelJoinUs.setText(bundle.getString("join_us"));
        btnLogin.setText(bundle.getString("login"));
        btnCreateAccount.setText(bundle.getString("create_account"));
        labelThankYou.setText(bundle.getString("thank_you"));
        
        // Actualizar los textos grandes de los TextArea
        textAreaWhoWeAre.setText(bundle.getString("who_we_are_text"));
        textAreaJoinUs.setText(bundle.getString("join_us_text"));
    }

    // Métodos de navegación
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("PantallaLogin");  // Navegar a la pantalla de inicio de sesión
    }

    @FXML
    private void reloadPage() throws IOException {
        App.setRoot("PantallaBienvenido");  // Recargar la pantalla principal
    }

    @FXML
    private void Siguiente() throws IOException {
        App.setRoot("PantallaLogin");  // Navegar a la pantalla siguiente
    }
}
