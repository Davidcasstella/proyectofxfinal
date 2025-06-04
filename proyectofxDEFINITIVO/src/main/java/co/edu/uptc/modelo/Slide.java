package co.edu.uptc.modelo;

public class Slide {
    private String titulo;
    private String descripcion;

    public Slide(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
