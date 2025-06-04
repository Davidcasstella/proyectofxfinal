package co.edu.uptc.modelo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            // Integrar la hoja de estilos CSS
            scene.getStylesheets().add(getClass().getResource("/co/edu/uptc/css/application.css").toExternalForm());

            primaryStage.setTitle("Fundación Garras y Bigotes");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
