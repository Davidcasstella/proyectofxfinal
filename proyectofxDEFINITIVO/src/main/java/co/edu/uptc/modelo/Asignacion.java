package co.edu.uptc.modelo;

import java.time.LocalDate;

public class Asignacion {
    private Long id;
    private Long donanteId;
    private Long animalId;
    private String donante; // Para mostrar en UI
    private String animal;  // Para mostrar en UI
    private String recurso;
    private String estado;
    private LocalDate fechaAsignacion;
    private LocalDate fechaEntrega;
    private String observaciones;
    private double monto;

    // Constructor vacío (necesario para JSON)
    public Asignacion() {}

    // Constructor completo
    public Asignacion(Long id, Long donanteId, Long animalId, String donante, String animal,
                      String recurso, String estado, LocalDate fechaAsignacion,
                      LocalDate fechaEntrega, String observaciones, double monto) {
        this.id = id;
        this.donanteId = donanteId;
        this.animalId = animalId;
        this.donante = donante;
        this.animal = animal;
        this.recurso = recurso;
        this.estado = estado;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaEntrega = fechaEntrega;
        this.observaciones = observaciones;
        this.monto = monto;
    }

    // Constructor básico (compatible con código existente)
    public Asignacion(String donante, String animal, String recurso, String estado) {
        this.donante = donante;
        this.animal = animal;
        this.recurso = recurso;
        this.estado = estado;
        this.fechaAsignacion = LocalDate.now();
        this.monto = 0.0;
    }

    // Constructor con IDs
    public Asignacion(Long donanteId, Long animalId, String recurso, String estado, double monto) {
        this.donanteId = donanteId;
        this.animalId = animalId;
        this.recurso = recurso;
        this.estado = estado;
        this.fechaAsignacion = LocalDate.now();
        this.monto = monto;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDonanteId() { return donanteId; }
    public void setDonanteId(Long donanteId) { this.donanteId = donanteId; }

    public Long getAnimalId() { return animalId; }
    public void setAnimalId(Long animalId) { this.animalId = animalId; }

    public String getDonante() { return donante; }
    public void setDonante(String donante) { this.donante = donante; }

    public String getAnimal() { return animal; }
    public void setAnimal(String animal) { this.animal = animal; }

    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    @Override
    public String toString() {
        return donante + " -> " + animal + " (" + recurso + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asignacion that = (Asignacion) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}