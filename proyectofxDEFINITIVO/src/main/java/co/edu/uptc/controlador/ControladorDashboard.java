package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

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
    }
}
