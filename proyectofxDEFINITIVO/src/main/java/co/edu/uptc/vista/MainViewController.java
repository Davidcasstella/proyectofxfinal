package co.edu.uptc.vista;

    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.layout.StackPane;

    public class MainViewController {

        @FXML
        private StackPane carouselPane;

        @FXML
        private Label slideLabel;

        private int currentSlide = 1;

        public void initialize() {
            updateSlide();
        }

        @FXML
        private void prevSlide() {
            currentSlide = currentSlide == 1 ? 4 : currentSlide - 1;
            updateSlide();
        }

        @FXML
        private void nextSlide() {
            currentSlide = currentSlide == 4 ? 1 : currentSlide + 1;
            updateSlide();
        }

        private void updateSlide() {
            slideLabel.setText("DIAPOSITIVA " + currentSlide);
        }

        @FXML
        private void handlePrincipal() {
            System.out.println("Ir a Principal");
        }

        @FXML
        private void handleDonar() {
            System.out.println("Ir a Donar");
        }

        @FXML
        private void handleHistorial() {
            System.out.println("Ir a Historial");
        }

        @FXML
        private void handleForo() {
            System.out.println("Ir a Foro");
        }

        @FXML
        private void handleCuenta() {
            System.out.println("Ir a Cuenta");
        }

        @FXML
        private void handleMensajes() {
            System.out.println("Mensajes abiertos");
        }
    }

