package co.edu.uptc.controlador;

import java.io.IOException;
import java.util.List;

import co.edu.uptc.App;
import co.edu.uptc.modelo.Asignacion;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.servicio.FundacionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorDashboard {

    @FXML private Label labelDonantes, labelAnimales, labelDonaciones;
    @FXML private ListView<String> activityList;
    @FXML private ProgressBar progressBueno, progressRegular, progressCritico;
    @FXML private Label labelBueno, labelRegular, labelCritico;
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private TabPane tabPane;
    @FXML private Tab tabDonantes;
    @FXML private Tab tabAnimales;
    @FXML private Tab tabAsignaciones;
    @FXML private Tab tabReportes;
    @FXML private Tab tabComentarios;
    @FXML private ImageView logoImage;

    // Botones laterales para navegar entre pestañas
    @FXML private Button btnPrincipal;
    @FXML private Button btnDonantes;
    @FXML private Button btnAnimales;
    @FXML private Button btnAsignaciones;
    @FXML private Button btnReportes;
    @FXML private Button btnComentarios;

    // Labels para mostrar información del usuario
    @FXML private Label lblBienvenidoUsuario;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnCerrarSesion;

    // Control para no cargar la pantalla varias veces
    private boolean donantesCargado = false;
    private boolean animalesCargado = false;
    private boolean asignacionesCargado = false;
    private boolean reportesCargado = false;
    private boolean comentariosCargado = false;

    // Servicio de persistencia
    private FundacionService fundacionService;

    @FXML
    public void initialize() {
        fundacionService = FundacionService.getInstance();

        // Cargar información del usuario logueado
        cargarInfoUsuario();

        // Cargar la imagen desde recursos
        try {
            Image logo = new Image(getClass().getResourceAsStream("/co/edu/uptc/imagenes/Logo.png"));
            logoImage.setImage(logo);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // Configurar botón de cerrar sesión
        if (btnCerrarSesion != null) {
            btnCerrarSesion.setOnAction(e -> cerrarSesion());
        }

        // Cargar estadísticas reales desde la base de datos
        cargarEstadisticas();
        
        // Preparar la gráfica con datos simulados
        prepararGrafica();
        
        // Cargar actividad reciente real
        cargarActividadReciente();

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
                actualizarColorBotones(btnPrincipal);
                cargarEstadisticas();
                cargarActividadReciente();
            }
        });

        // Configurar botones laterales
        configurarBotonesLaterales();

        // Inicializar color botón principal al cargar la app
        actualizarColorBotones(btnPrincipal);
    }

    private void cargarInfoUsuario() {
        Usuario usuarioActual = ControladorLogin.getUsuarioActual();
        
        if (usuarioActual != null) {
            // Buscar los labels en el FXML para actualizar la información
            // En tu FXML debes buscar estos labels:
            // El label que dice "Bienvenido User1234 (Administrador)"
            // Y el label que dice "User1234" en la esquina superior derecha
            
            // Actualizar los labels si existen
            if (lblBienvenidoUsuario != null) {
                lblBienvenidoUsuario.setText("Bienvenido " + usuarioActual.getNombreCompleto() + " (Administrador)");
            }
            
            if (lblNombreUsuario != null) {
                lblNombreUsuario.setText(usuarioActual.getEmail().split("@")[0]); // Mostrar solo la parte antes del @
            }
            
            // Si no tienes estos fx:id en tu FXML, busca manualmente los labels
            // O actualiza tu FXML agregando estos fx:id a los labels correspondientes
        }
    }

    private void cerrarSesion() {
        Alert confirmacion = new Alert(AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Estás seguro de que quieres cerrar sesión?");
        confirmacion.setContentText("Serás redirigido a la pantalla de inicio.");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            // Limpiar usuario actual
            ControladorLogin.setUsuarioActual(null);
            
            try {
                // Redirigir al login
                App.setRoot("PantallaLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void configurarBotonesLaterales() {
        btnPrincipal.setOnAction(e -> {
            tabPane.getSelectionModel().select(0);
            actualizarColorBotones(btnPrincipal);
            cargarEstadisticas();
            cargarActividadReciente();
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
    }

    private void cargarEstadisticas() {
        try {
            // Obtener estadísticas reales desde el servicio
            long totalDonantes = fundacionService.contarDonantes();
            long totalAnimales = fundacionService.contarAnimales();
            double totalDonaciones = fundacionService.obtenerMontoTotalDonaciones();

            // Actualizar labels principales
            labelDonantes.setText(String.format("%,d", totalDonantes));
            labelAnimales.setText(String.format("%,d", totalAnimales));
            labelDonaciones.setText(String.format("$%,.2f", totalDonaciones));

            // Obtener estadísticas de animales por estado
            long animalesBuenos = fundacionService.contarAnimalesPorEstado("Activo");
            long animalesRegulares = fundacionService.contarAnimalesPorEstado("En tratamiento") + 
                                   fundacionService.contarAnimalesPorEstado("En observación");
            long animalesCriticos = fundacionService.contarAnimalesPorEstado("Crítico");

            // Actualizar barras de progreso y labels
            if (totalAnimales > 0) {
                double porcentajeBuenos = (double) animalesBuenos / totalAnimales;
                double porcentajeRegulares = (double) animalesRegulares / totalAnimales;
                double porcentajeCriticos = (double) animalesCriticos / totalAnimales;

                progressBueno.setProgress(porcentajeBuenos);
                progressRegular.setProgress(porcentajeRegulares);
                progressCritico.setProgress(porcentajeCriticos);

                labelBueno.setText(String.valueOf(animalesBuenos));
                labelRegular.setText(String.valueOf(animalesRegulares));
                labelCritico.setText(String.valueOf(animalesCriticos));
            } else {
                progressBueno.setProgress(0);
                progressRegular.setProgress(0);
                progressCritico.setProgress(0);
                labelBueno.setText("0");
                labelRegular.setText("0");
                labelCritico.setText("0");
            }

        } catch (Exception e) {
            System.err.println("Error cargando estadísticas: " + e.getMessage());
            labelDonantes.setText("0");
            labelAnimales.setText("0");
            labelDonaciones.setText("$0.00");
            labelBueno.setText("0");
            labelRegular.setText("0");
            labelCritico.setText("0");
        }
    }

    private void prepararGrafica() {
        lineChart.getData().clear();
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);

        // Datos simulados para la gráfica
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
    }

    private void cargarActividadReciente() {
        try {
            activityList.getItems().clear();
            
            // Obtener asignaciones recientes (últimos 7 días)
            List<Asignacion> asignacionesRecientes = fundacionService.obtenerAsignacionesRecientes(7);
            
            if (asignacionesRecientes.isEmpty()) {
                activityList.getItems().add("No hay actividad reciente");
            } else {
                int limite = Math.min(5, asignacionesRecientes.size());
                for (int i = 0; i < limite; i++) {
                    Asignacion asignacion = asignacionesRecientes.get(i);
                    String actividad = String.format("%s -> %s (%s) - %s", 
                            asignacion.getDonante(),
                            asignacion.getAnimal(),
                            asignacion.getRecurso(),
                            calcularTiempoTranscurrido(asignacion.getFechaAsignacion())
                    );
                    activityList.getItems().add(actividad);
                }
            }
            
            if (activityList.getItems().size() < 5) {
                activityList.getItems().add("Sistema iniciado - Hoy");
                activityList.getItems().add("Base de datos actualizada - Hace 1 hora");
            }
            
        } catch (Exception e) {
            System.err.println("Error cargando actividad reciente: " + e.getMessage());
            activityList.getItems().clear();
            activityList.getItems().addAll(
                "Sistema iniciado - Hoy",
                "Cargando datos - Hace 1 min",
                "Base de datos conectada - Hace 5 min"
            );
        }
    }

    private String calcularTiempoTranscurrido(java.time.LocalDate fecha) {
        if (fecha == null) return "Fecha desconocida";
        
        java.time.LocalDate hoy = java.time.LocalDate.now();
        long diasTranscurridos = java.time.temporal.ChronoUnit.DAYS.between(fecha, hoy);
        
        if (diasTranscurridos == 0) {
            return "Hoy";
        } else if (diasTranscurridos == 1) {
            return "Ayer";
        } else if (diasTranscurridos <= 7) {
            return "Hace " + diasTranscurridos + " días";
        } else {
            return "Hace más de una semana";
        }
    }

    private void actualizarColorBotones(Button activo) {
        Button[] botones = { btnPrincipal, btnDonantes, btnAnimales, btnAsignaciones, btnReportes, btnComentarios };
        for (Button btn : botones) {
            if (btn == activo) {
                btn.setStyle("-fx-background-color: #E9E9E9; -fx-text-fill: black;");
            } else {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
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
            System.err.println("Error cargando pantalla de donantes: " + e.getMessage());
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
            System.err.println("Error cargando pantalla de animales: " + e.getMessage());
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
            System.err.println("Error cargando pantalla de asignaciones: " + e.getMessage());
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
            System.err.println("Error cargando pantalla de reportes: " + e.getMessage());
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
            System.err.println("Error cargando pantalla de comentarios: " + e.getMessage());
        }
    }

    @FXML
    private void Siguienteeeee() throws IOException {
        App.setRoot("PantallaDashboard");
    }

    @FXML
    private void reloadPageeeee() throws IOException {
        App.setRoot("PantallaDashboard");
    }

    @FXML
    private void Antesssss() throws IOException {
        App.setRoot("PantallaCreaTuContraseña");
    }
}