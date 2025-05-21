package co.edu.uptc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Aseguramos que la ruta al archivo FXML sea correcta
        scene = new Scene(loadFXML("PantallaBienvenida"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        // Aseguramos que la ruta al archivo FXML sea correcta
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Cargamos los archivos FXML con la ruta correcta
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/edu/uptc/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
