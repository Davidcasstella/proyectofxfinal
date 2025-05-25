package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import co.edu.uptc.persistencia.DonanteDAO;

import co.edu.uptc.persistencia.DonacionDAO;

public class ControladorDashboard {

    @FXML
    private Label labelDonantes;

    @FXML
    private Label labelAnimales;

    @FXML
    private Label labelDonaciones;

    @FXML
    private LineChart<String, Number> graficaResumen;

    @FXML
    private ListView<String> listaActividadReciente;

    public void initialize() {
        cargarDatosDashboard();
    }

    private void cargarDatosDashboard() {
        // Obtener datos desde los DAOs
        int totalDonantes = DonanteDAO.obtenerCantidadDonantes();
       
       

        // Mostrar en labels
        labelDonantes.setText(String.valueOf(totalDonantes));
        
        

        // Cargar gráfica y actividad
        cargarGrafica();
        cargarActividadReciente();
    }

    private void cargarGrafica() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.getData().add(new XYChart.Data<>("23 Nov", 22000));
        serie.getData().add(new XYChart.Data<>("24 Nov", 23000));
        serie.getData().add(new XYChart.Data<>("25 Nov", 27000));
        serie.getData().add(new XYChart.Data<>("29 Nov", 47000));
        graficaResumen.getData().clear();  // limpiar antes de agregar
        graficaResumen.getData().add(serie);
    }

    private void cargarActividadReciente() {
        listaActividadReciente.getItems().clear(); // limpiar antes de agregar
        listaActividadReciente.getItems().addAll(
            "Nuevo donante registrado - Hace 5 min",
            "Asignación de medicamentos - Hace 1 hora",
            "Animal ingresado al sistema - Hace 3 horas",
            "Reporte generado - Ayer",
            "Donación confirmada - Hace 2 días"
        );
    }
}
