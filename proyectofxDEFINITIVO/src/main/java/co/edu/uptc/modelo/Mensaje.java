package co.edu.uptc.modelo;

public class Mensaje {
    private String autor;
    private String contenido;
    private boolean esPropio;

    public Mensaje(String autor, String contenido, boolean esPropio) {
        this.autor = autor;
        this.contenido = contenido;
        this.esPropio = esPropio;
    }

    public String getAutor() { return autor; }
    public String getContenido() { return contenido; }
    public boolean esPropio() { return esPropio; }
}
