package co.edu.uptc.modelo;

import java.time.LocalDateTime;

public class Comentario {
    private Long id;
    private Long voluntarioId;
    private String nombreVoluntario; // Para mostrar en UI
    private String titulo;
    private String contenido;
    private String categoria; // "SUGERENCIA", "REPORTE", "FELICITACION", "QUEJA", "PREGUNTA"
    private LocalDateTime fechaCreacion;
    private String estado; // "NUEVO", "LEIDO", "RESPONDIDO", "CERRADO"
    private String prioridad; // "BAJA", "MEDIA", "ALTA", "URGENTE"
    private String respuesta;
    private LocalDateTime fechaRespuesta;
    private String respondidoPor;
    private boolean publico; // Si se muestra en el foro público

    // Constructor vacío (necesario para JSON)
    public Comentario() {}

    // Constructor completo
    public Comentario(Long id, Long voluntarioId, String nombreVoluntario, String titulo,
                      String contenido, String categoria, LocalDateTime fechaCreacion,
                      String estado, String prioridad, String respuesta,
                      LocalDateTime fechaRespuesta, String respondidoPor, boolean publico) {
        this.id = id;
        this.voluntarioId = voluntarioId;
        this.nombreVoluntario = nombreVoluntario;
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.respuesta = respuesta;
        this.fechaRespuesta = fechaRespuesta;
        this.respondidoPor = respondidoPor;
        this.publico = publico;
    }

    // Constructor básico
    public Comentario(Long voluntarioId, String titulo, String contenido, String categoria) {
        this.voluntarioId = voluntarioId;
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "NUEVO";
        this.prioridad = "MEDIA";
        this.publico = true;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVoluntarioId() { return voluntarioId; }
    public void setVoluntarioId(Long voluntarioId) { this.voluntarioId = voluntarioId; }

    public String getNombreVoluntario() { return nombreVoluntario; }
    public void setNombreVoluntario(String nombreVoluntario) { this.nombreVoluntario = nombreVoluntario; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(LocalDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }

    public String getRespondidoPor() { return respondidoPor; }
    public void setRespondidoPor(String respondidoPor) { this.respondidoPor = respondidoPor; }

    public boolean isPublico() { return publico; }
    public void setPublico(boolean publico) { this.publico = publico; }

    // Métodos de utilidad
    public String getFechaFormateada() {
        return fechaCreacion.toLocalDate().toString() + " " + 
               fechaCreacion.toLocalTime().toString().substring(0, 5);
    }

    public boolean tieneRespuesta() {
        return respuesta != null && !respuesta.trim().isEmpty();
    }

    @Override
    public String toString() {
        return titulo + " - " + nombreVoluntario + " (" + categoria + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comentario comentario = (Comentario) obj;
        return id != null && id.equals(comentario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}