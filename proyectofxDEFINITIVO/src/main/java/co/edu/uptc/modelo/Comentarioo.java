package co.edu.uptc.modelo;

public class Comentarioo {
    private String autor;
    private String contenido;

    public Comentarioo(String autor, String contenido) {
        this.autor = autor;
        this.contenido = contenido;
    }

    public String getAutor() {
        return autor;
    }

    public String getContenido() {
        return contenido;
    }
}
