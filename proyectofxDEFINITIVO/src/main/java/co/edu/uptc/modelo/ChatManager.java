package co.edu.uptc.modelo;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import  co.edu.uptc.controlador.ChatController;

public class ChatManager {

    private static final Map<String, StringBuilder> historialMensajes = new HashMap<>();

    public static void abrirChatPrivadoCon(Usuarioo usuario, ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(ChatManager.class.getResource("/com/example/view/ChatPrivadoView.fxml"));
        Parent root = loader.load();

        ChatController controller = loader.getController();
        controller.setUsuarioConectado(usuario);

        // Obtener la ventana actual desde el evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static void guardarMensaje(Usuarioo usuario, String mensaje) {
        StringBuilder historial = historialMensajes.computeIfAbsent(usuario.getCorreo(), k -> cargarHistorialDesdeArchivo(k));
        historial.append(mensaje).append("\n");
        guardarHistorialEnArchivo(usuario.getCorreo(), historial);
    }

    public static StringBuilder obtenerHistorial(Usuarioo usuario) {
        return historialMensajes.computeIfAbsent(usuario.getCorreo(), k -> cargarHistorialDesdeArchivo(k));
    }

    private static StringBuilder cargarHistorialDesdeArchivo(String correo) {
        File archivo = new File("chat_" + correo + ".txt");
        StringBuilder historial = new StringBuilder();
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    historial.append(linea).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return historial;
    }

    private static void guardarHistorialEnArchivo(String correo, StringBuilder historial) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_" + correo + ".txt"))) {
            writer.write(historial.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 

