package co.edu.uptc.controlador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

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


    // Control para no cargar la pantalla varias veces
    private boolean donantesCargado = false;

    @FXML
    public void initialize() {
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

        // Limpiar y preparar la gráfica
        lineChart.getData().clear();
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);

        // Datos simulados para gráfico
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

        // Listener para carga dinámica de la pestaña Donantes
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabDonantes) {
                cargarPantallaDonantes();
            }
        });



        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabDonantes) {
                cargarPantallaDonantes();
            } else if (newTab == tabAnimales) {
                cargarPantallaAnimales();
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabDonantes) {
                cargarPantallaDonantes();
            } else if (newTab == tabAnimales) {
                cargarPantallaAnimales();
            } else if (newTab == tabAsignaciones) {
                cargarPantallaAsignaciones();
            }
        });
         tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabReportes) {
                cargarPantallaReportes();
            }
            // Otros if para otras pestañas...
        });
         // Listener para cargar la pestaña Comentarios solo cuando se seleccione
            tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                if (newTab == tabComentarios) {
                    cargarPantallaComentarios();
                }
            });

    }
        
    private void cargarPantallaDonantes() {
        if (donantesCargado) {
            return; // Ya cargamos la pantalla, no cargar de nuevo
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaDonantes.fxml"));
            Pane contenidoDonantes = loader.load();

            tabDonantes.setContent(contenidoDonantes);

            donantesCargado = true;
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí puedes agregar manejo de errores, alertas, etc.
        }
    }
    private boolean animalesCargado = false; // Variable para controlar carga única

        private void cargarPantallaAnimales() {
            if (animalesCargado) {
                return; // Ya cargamos la pantalla, no cargar de nuevo
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaAnimales.fxml"));
                Pane contenidoAnimales = loader.load();

                tabAnimales.setContent(contenidoAnimales);

                animalesCargado = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de errores, alertas, etc.
            }
        }
                private boolean asignacionesCargado = false; // Controla carga única

        private void cargarPantallaAsignaciones() {
            if (asignacionesCargado) {
                return; // Ya cargamos la pantalla, no cargar de nuevo
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaAsignaciones.fxml"));
                Pane contenidoAsignaciones = loader.load();

                tabAsignaciones.setContent(contenidoAsignaciones);

                asignacionesCargado = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de errores, alertas, etc.
            }
        }

        private boolean reportesCargado = false;

        private void cargarPantallaReportes() {
            if (reportesCargado) {
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaReportes.fxml"));
                Pane contenidoReportes = loader.load();

                tabReportes.setContent(contenidoReportes);

                reportesCargado = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Variable para controlar carga única de Comentarios
private boolean comentariosCargado = false;
        // Método para cargar contenido de PantallaComentarios.fxml en la pestaña Comentarios
        private void cargarPantallaComentarios() {
            if (comentariosCargado) {
                return; // Ya cargamos la pantalla, no cargar de nuevo
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/PantallaComentarios.fxml"));
                Pane contenidoComentarios = loader.load();

                tabComentarios.setContent(contenidoComentarios);

                comentariosCargado = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de errores, alertas, etc.
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
