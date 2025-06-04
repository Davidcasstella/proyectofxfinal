package co.edu.uptc.controlador;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.modelo.Animall;
import co.edu.uptc.modelo.ChatManager;
import co.edu.uptc.modelo.Donacion;
import co.edu.uptc.modelo.Usuarioo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnGestionPerfil;

    @FXML
    private Button btnPrincipal;

    @FXML
    private Button btnDonar;

    @FXML
    private Button btnHistorial;

    @FXML
    private Button btnForo;

    @FXML
    private Button btnMensajes;

    @FXML
    private Button btnLogout;
    @FXML private Tab tabDonar;
    @FXML
    private Tab tabPrincipal;
    @FXML
    private Tab tabHistorial;
    @FXML
    private Tab tabForo;
    @FXML
    private Tab tabGestionar;

    


    @FXML
    public void initialize() {
        loadView("PrincipalView.fxml");




       // Agregar listener a la pestaña 'Donar'
    tabDonar.setOnSelectionChanged(e -> {
        if (tabDonar.isSelected()) {
            loadDonarView();
        }
    });
     // Listener para la pestaña 'Principal'
    tabPrincipal.setOnSelectionChanged(e -> {
        if (tabPrincipal.isSelected()) {
            loadPrincipalView();
        }
    });
    
        // Listener para la pestaña 'Historial'
        tabHistorial.setOnSelectionChanged(e -> {
            if (tabHistorial.isSelected()) {
                loadHistorialView();
            }
        });

        // Listener para la pestaña 'Foro'
        tabForo.setOnSelectionChanged(e -> {
            if (tabForo.isSelected()) {
                loadForoView();
            }
        });

        // Listener para la pestaña 'Gestionar Cuenta'
        tabGestionar.setOnSelectionChanged(e -> {
            if (tabGestionar.isSelected()) {
                loadGestionPerfil();
            }
        });

    btnPrincipal.setOnAction(e -> loadView("PrincipalView.fxml"));
    btnGestionPerfil.setOnAction(e -> loadGestionPerfil());
    btnDonar.setOnAction(e -> loadDonarView());
    btnHistorial.setOnAction(e -> loadHistorialView());
    btnForo.setOnAction(e -> loadForoView());
    btnMensajes.setOnAction(e -> loadMensajesView());
    btnLogout.setOnAction(e -> handleLogout());
}


private void loadPrincipalView() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PrincipalView.fxml"));
        Pane view = loader.load();

        // Si es necesario, configura el controlador de la vista.
        Object controller = loader.getController();
        if (controller instanceof ChatController) {
            ((ChatController) controller).setMainController(this);
        } else if (controller instanceof DonarController) {
            ((DonarController) controller).setMainController(this);
        } else if (controller instanceof ForoController) {
            ((ForoController) controller).setMainController(this);
        }

        contentPane.getChildren().setAll(view);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    

    public void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/" + fxml));
            Pane view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ChatController) {
                ((ChatController) controller).setMainController(this);
            } else if (controller instanceof DonarController) {
                ((DonarController) controller).setMainController(this);
            } else if (controller instanceof ForoController) {
                ((ForoController) controller).setMainController(this);
            } else if (controller instanceof DonacionController) {
                ((DonacionController) controller).setMainController(this);
            }

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
    private void loadGestionPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/GestionPerfilView.fxml"));
            Pane view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDonarView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/DonarView.fxml"));
            Pane view = loader.load();

            DonarController controller = loader.getController();
            controller.setMainController(this);

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHistorialView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/HistorialView.fxml"));
            Pane view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadForoView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/ForoView.fxml"));
            Pane view = loader.load();

            ForoController controller = loader.getController();
            controller.setMainController(this);

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMensajesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/mensajes.fxml"));
            Pane view = loader.load();

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDonacionView(Animall animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/DonacionView.fxml"));
            Pane view = loader.load();

            DonacionController controller = loader.getController();
            controller.setAnimal(animal);
            controller.setMainController(this);

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadPagoView(Donacion donacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PagoView.fxml"));
            Pane view = loader.load();

            PagoController controller = loader.getController();
            controller.setDonacion(donacion);
            controller.setMainController(this);

            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarChatPrivado(Usuarioo usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/ChatPrivadoView.fxml"));
            Pane view = loader.load();

            ChatController controller = loader.getController();
            controller.setUsuarioConectado(usuario);
            controller.setMainController(this);
            controller.setHistorial(ChatManager.obtenerHistorial(usuario));

            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarPerfilPublico(Usuarioo usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PerfilPublicoView.fxml"));
            Pane view = loader.load();

            PerfilPublicoController controller = loader.getController();
            controller.setUsuario(usuario);

            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleLogout() {
        System.out.println("Cerrando sesión...");
        // Aquí puedes agregar la lógica para cerrar sesión
        // Por ejemplo, volver a la pantalla de login
        System.exit(0); // Por ahora solo cerramos la aplicación
    }
     @FXML
    private void Siguienteeeee() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void reloadPageeeee() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void Antesssss() throws IOException {
        App.setRoot("PantallaLogin");
    }
}