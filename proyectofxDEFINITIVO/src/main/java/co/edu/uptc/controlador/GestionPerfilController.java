package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Usuarioo;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GestionPerfilController {

    @FXML private TextArea txtBiografia;
    @FXML private TextArea txtMetas;
    @FXML private TextArea txtFrustraciones;
    @FXML private TextArea txtMotivaciones;
    @FXML private ListView<String> lstPersonalidad;

    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtUbicacion;
    @FXML private TextField txtEdad;

    @FXML private Button btnEditar;
    @FXML private Button btnActualizar;

    private Usuarioo usuario;

    @FXML
    public void initialize() {
        // Datos de ejemplo
        usuario = new Usuarioo("Juan Pérez", "juan.perez@gmail.com", "1234-5678", "Sogamoso", 25);
        usuario.setBiografia("Lorem ipsum dolor sit amet...");
        usuario.setMetas("• Ser mejor voluntario\n• Ayudar más");
        usuario.setFrustraciones("Falta de recursos");
        usuario.setMotivaciones("Contribuir al bienestar animal");
        usuario.setPersonalidad(new String[]{"Empático", "Organizado"});

        // Cargar datos a los campos
        txtCorreo.setText(usuario.getCorreo());
        txtTelefono.setText(usuario.getTelefono());
        txtUbicacion.setText(usuario.getUbicacion());
        txtEdad.setText(String.valueOf(usuario.getEdad()));

        txtBiografia.setText(usuario.getBiografia());
        txtMetas.setText(usuario.getMetas());
        txtFrustraciones.setText(usuario.getFrustraciones());
        txtMotivaciones.setText(usuario.getMotivaciones());

        lstPersonalidad.getItems().addAll(usuario.getPersonalidad());

        // Desactivar edición al inicio
        alternarEdicion(false);

        // Acciones de botones
        btnEditar.setOnAction(e -> alternarEdicion(!txtCorreo.isEditable()));
        btnActualizar.setOnAction(e -> guardarCambios());
    }

    private void alternarEdicion(boolean editable) {
        txtCorreo.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtUbicacion.setEditable(editable);
        txtEdad.setEditable(editable);

        txtBiografia.setEditable(editable);
        txtMetas.setEditable(editable);
        txtFrustraciones.setEditable(editable);
        txtMotivaciones.setEditable(editable);

        btnActualizar.setDisable(!editable);
        btnEditar.setText(editable ? "Cancelar" : "Editar");

        if (!editable) {
            // Restaurar los datos si se canceló
            txtCorreo.setText(usuario.getCorreo());
            txtTelefono.setText(usuario.getTelefono());
            txtUbicacion.setText(usuario.getUbicacion());
            txtEdad.setText(String.valueOf(usuario.getEdad()));

            txtBiografia.setText(usuario.getBiografia());
            txtMetas.setText(usuario.getMetas());
            txtFrustraciones.setText(usuario.getFrustraciones());
            txtMotivaciones.setText(usuario.getMotivaciones());
        }
    }

    private void guardarCambios() {
        usuario.setCorreo(txtCorreo.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setUbicacion(txtUbicacion.getText());
        try {
            usuario.setEdad(Integer.parseInt(txtEdad.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida");
            usuario.setEdad(0); // Valor por defecto
        }

        usuario.setBiografia(txtBiografia.getText());
        usuario.setMetas(txtMetas.getText());
        usuario.setFrustraciones(txtFrustraciones.getText());
        usuario.setMotivaciones(txtMotivaciones.getText());

        // Confirmación (opcional)
        System.out.println("Datos actualizados.");

        alternarEdicion(false);
    }
}

