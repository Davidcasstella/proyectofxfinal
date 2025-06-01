package co.edu.uptc.modelo;

import java.time.LocalDateTime;
import java.util.Map;

public class Reporte {
    private Long id;
    private String titulo;
    private String tipoReporte; // "GENERAL", "DONACIONES", "ANIMALES", "VOLUNTARIOS"
    private String descripcion;
    private LocalDateTime fechaGeneracion;
    private String generadoPor; // Usuario que generó el reporte
    private String estado; // "GENERADO", "ENVIADO", "ARCHIVADO"
    private Map<String, Object> datos; // Datos específicos del reporte
    private String rutaArchivo; // Ruta donde se guardó el archivo (opcional)
    private String formato; // "PDF", "EXCEL", "CSV"

    // Constructor vacío (necesario para JSON)
    public Reporte() {}

    // Constructor completo
    public Reporte(Long id, String titulo, String tipoReporte, String descripcion,
                   LocalDateTime fechaGeneracion, String generadoPor, String estado,
                   Map<String, Object> datos, String rutaArchivo, String formato) {
        this.id = id;
        this.titulo = titulo;
        this.tipoReporte = tipoReporte;
        this.descripcion = descripcion;
        this.fechaGeneracion = fechaGeneracion;
        this.generadoPor = generadoPor;
        this.estado = estado;
        this.datos = datos;
        this.rutaArchivo = rutaArchivo;
        this.formato = formato;
    }

    // Constructor básico
    public Reporte(String titulo, String tipoReporte, String descripcion, String generadoPor) {
        this.titulo = titulo;
        this.tipoReporte = tipoReporte;
        this.descripcion = descripcion;
        this.generadoPor = generadoPor;
        this.fechaGeneracion = LocalDateTime.now();
        this.estado = "GENERADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public String getGeneradoPor() { return generadoPor; }
    public void setGeneradoPor(String generadoPor) { this.generadoPor = generadoPor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Map<String, Object> getDatos() { return datos; }
    public void setDatos(Map<String, Object> datos) { this.datos = datos; }

    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    @Override
    public String toString() {
        return titulo + " (" + tipoReporte + ") - " + fechaGeneracion.toLocalDate();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reporte reporte = (Reporte) obj;
        return id != null && id.equals(reporte.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}