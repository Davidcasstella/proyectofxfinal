package co.edu.uptc.controlador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;


import co.edu.uptc.App;

public class ControladorDashboard {

    @FXML
    private Label labelDonantes, labelAnimales, labelDonaciones;

    @FXML
    private ListView<String> activityList;

    @FXML
    private ProgressBar progressBueno, progressRegular, progressCritico;

    @FXML
    private Label labelBueno, labelRegular, labelCritico;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabDonantes;
    @FXML
    private Tab tabAnimales;
    @FXML
    private Tab tabAsignaciones;
    @FXML
    private Tab tabReportes;
    @FXML
    private Tab tabComentarios;
    @FXML
private ImageView logoImage;

    // Botones laterales para navegar entre pestañas
    @FXML private Button btnPrincipal;
    @FXML private Button btnDonantes;
    @FXML private Button btnAnimales;
    @FXML private Button btnAsignaciones;
    @FXML private Button btnReportes;
    @FXML private Button btnComentarios;

    // Control para no cargar la pantalla varias veces
    private boolean donantesCargado = false;
    private boolean animalesCargado = false;
    private boolean asignacionesCargado = false;
    private boolean reportesCargado = false;
    private boolean comentariosCargado = false;

    @FXML
    public void initialize() {

         // Cargar la imagen desde recursos
       Image logo = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/Logo.png"));

        logoImage.setImage(logo);
        // Inicializar estadísticas con valores simulados
        labelDonantes.setText("10,353");
        labelAnimales.setText("2,405");
        labelDonaciones.setText("$45,678.90");

        labelBueno.setText("50");
        labelRegular.setText("30");
        labelCritico.setText("10");

        progressBueno.setProgress(0.50);
        progressRegular.setProgress(0.30);
        progressCritico.setProgress(0.10);

        // Preparar la gráfica
        lineChart.getData().clear();
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(23, 25000));
        series.getData().add(new XYChart.Data<>(24, 28000));
        series.getData().add(new XYChart.Data<>(25, 32000));
        series.getData().add(new XYChart.Data<>(26, 35000));
        series.getData().add(new XYChart.Data<>(27, 33000));
        series.getData().add(new XYChart.Data<>(28, 40000));
        series.getData().add(new XYChart.Data<>(29, 42000));
        series.getData().add(new XYChart.Data<>(30, 48000));
        lineChart.getData().add(series);

        // Listado de actividades recientes (simulado)
        activityList.getItems().clear();
        activityList.getItems().addAll(
            "Nuevo donante registrado - Hace 5 min",
            "Asignación de medicamentos - Hace 1 hora",
            "Animal ingresado al sistema - Hace 3 horas",
            "Reporte Generado - Ayer",
            "Donación confirmada - Hace 2 días"
        );

        // Listener único para cargar pantallas según pestaña seleccionada
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabDonantes) {
                cargarPantallaDonantes();
                actualizarColorBotones(btnDonantes);
            } else if (newTab == tabAnimales) {
                cargarPantallaAnimales();
                actualizarColorBotones(btnAnimales);
            } else if (newTab == tabAsignaciones) {
                cargarPantallaAsignaciones();
                actualizarColorBotones(btnAsignaciones);
            } else if (newTab == tabReportes) {
                cargarPantallaReportes();
                actualizarColorBotones(btnReportes);
            } else if (newTab == tabComentarios) {
                cargarPantallaComentarios();
                actualizarColorBotones(btnComentarios);
            } else {
                // Si seleccionan la pestaña principal (índice 0)
                actualizarColorBotones(btnPrincipal);
            }
        });

        // Configurar botones laterales para cambiar pestaña y actualizar color
        btnPrincipal.setOnAction(e -> {
            tabPane.getSelectionModel().select(0);
            actualizarColorBotones(btnPrincipal);
        });
        btnDonantes.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabDonantes);
            actualizarColorBotones(btnDonantes);
        });
        btnAnimales.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabAnimales);
            actualizarColorBotones(btnAnimales);
        });
        btnAsignaciones.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabAsignaciones);
            actualizarColorBotones(btnAsignaciones);
        });
        btnReportes.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabReportes);
            actualizarColorBotones(btnReportes);
        });
        btnComentarios.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabComentarios);
            actualizarColorBotones(btnComentarios);
        });

        // Inicializar color botón principal al cargar la app
        actualizarColorBotones(btnPrincipal);
    }

    private void actualizarColorBotones(Button activo) {
        Button[] botones = { btnPrincipal, btnDonantes, btnAnimales, btnAsignaciones, btnReportes, btnComentarios };
        for (Button btn : botones) {
            if (btn == activo) {
                btn.setStyle("-fx-background-color: #E9E9E9; -fx-text-fill: black;"); // color activo
            } else {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: black;"); // color normal
            }
        }
    }

    private void cargarPantallaDonantes() {
        if (donantesCargado) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaDonantes.fxml"));
            Pane contenidoDonantes = loader.load();
            tabDonantes.setContent(contenidoDonantes);
            donantesCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPantallaAnimales() {
        if (animalesCargado) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaAnimales.fxml"));
            Pane contenidoAnimales = loader.load();
            tabAnimales.setContent(contenidoAnimales);
            animalesCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPantallaAsignaciones() {
        if (asignacionesCargado) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaAsignaciones.fxml"));
            Pane contenidoAsignaciones = loader.load();
            tabAsignaciones.setContent(contenidoAsignaciones);
            asignacionesCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPantallaReportes() {
        if (reportesCargado) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaReportes.fxml"));
            Pane contenidoReportes = loader.load();
            tabReportes.setContent(contenidoReportes);
            reportesCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPantallaComentarios() {
        if (comentariosCargado) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaComentarios.fxml"));
            Pane contenidoComentarios = loader.load();
            tabComentarios.setContent(contenidoComentarios);
            comentariosCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Siguienteeeee() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaDashboard");  // Recargar la vista de la pantalla principal
    }

    @FXML
    private void reloadPageeeee() throws IOException {
        // Recarga la página actual
        App.setRoot("PantallaDashboard");  // Recargar la vista de la pantalla principal
    }

    @FXML
    private void Antesssss() throws IOException {
        // Cambia a la pantalla anterior
        App.setRoot("PantallaCreaTuContraseña");  // Cambia la vista a otra pantalla
    }
}
