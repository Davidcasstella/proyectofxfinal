package co.edu.uptc.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class PrincipalController {
    
    @FXML
    private Label slideTitle;
    
    @FXML
    private Label slideContent;
    
    @FXML
    private Circle dot1, dot2, dot3, dot4, dot5;
    
    private int currentSlide = 1;
    private Circle[] dots;
    
    private String[] titles = {
        "DIAPOSITIVA 1",
        "DIAPOSITIVA 2",
        "DIAPOSITIVA 3",
        "DIAPOSITIVA 4",
        "DIAPOSITIVA 5"
    };
    
    private String[] contents = {
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\nVestibulum ut tortor metus. Maecenas elit arcu, faucibus\na dolor vel, ornare porttitor nisl.",
        "Contenido de la segunda diapositiva.\nAquí puedes mostrar información sobre adopciones.",
        "Contenido de la tercera diapositiva.\nInformación sobre donaciones y cómo ayudar.",
        "Contenido de la cuarta diapositiva.\nHistorias de éxito y testimonios.",
        "Contenido de la quinta diapositiva.\nÚnete como voluntario y marca la diferencia."
    };
    
    @FXML
    public void initialize() {
        dots = new Circle[]{dot1, dot2, dot3, dot4, dot5};
        updateSlide();
    }
    
    @FXML
    private void prevSlide() {
        currentSlide = currentSlide == 1 ? 5 : currentSlide - 1;
        updateSlide();
    }
    
    @FXML
    private void nextSlide() {
        currentSlide = currentSlide == 5 ? 1 : currentSlide + 1;
        updateSlide();
    }
    
    private void updateSlide() {
        slideTitle.setText(titles[currentSlide - 1]);
        slideContent.setText(contents[currentSlide - 1]);
        
        // Actualizar indicadores
        for (int i = 0; i < dots.length; i++) {
            if (i == currentSlide - 1) {
                dots[i].setFill(Color.WHITE);
            } else {
                dots[i].setFill(Color.web("#666666"));
            }
        }
    }
}