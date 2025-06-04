package co.edu.uptc.modelo;

public class Usuarioo {
    private String nombre;
    private String correo;
    private String telefono;
    private String ubicacion;
    private int edad;
    private String biografia;
    private String metas;
    private String frustraciones;
    private String motivaciones;
    private String[] personalidad;

    // Constructor completo
    public Usuarioo(String nombre, String correo, String telefono, String ubicacion, int edad) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.edad = edad;
        this.biografia = "";
        this.metas = "";
        this.frustraciones = "";
        this.motivaciones = "";
        this.personalidad = new String[0];
    }

    
    public Usuarioo(String nombre) {
        this.nombre = nombre;
        this.correo = "";
        this.telefono = "";
        this.ubicacion = "";
        this.edad = 0;
        this.biografia = "";
        this.metas = "";
        this.frustraciones = "";
        this.motivaciones = "";
        this.personalidad = new String[0];
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getFrustraciones() {
        return frustraciones;
    }

    public void setFrustraciones(String frustraciones) {
        this.frustraciones = frustraciones;
    }

    public String getMotivaciones() {
        return motivaciones;
    }

    public void setMotivaciones(String motivaciones) {
        this.motivaciones = motivaciones;
    }

    public String[] getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String[] personalidad) {
        this.personalidad = personalidad;
    }
}
