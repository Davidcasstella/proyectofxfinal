package co.edu.uptc.modelo;

import java.time.LocalDateTime;

public class Donacion {
    private Long id;
    private Animall animal;
    private String tipo;
    private String metodoEntrega;
    private String cantidad;
    private String descripcion;
    private String estado;
    private LocalDateTime fecha;
    private Usuarioo usuario;
    
    // Constructores
    public Donacion() {
        this.fecha = LocalDateTime.now();
        this.estado = "Pendiente";
    }
    
    public Donacion(Animall animal, String tipo, String metodoEntrega, String cantidad) {
        this();
        this.animal = animal;
        this.tipo = tipo;
        this.metodoEntrega = metodoEntrega;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Animall getAnimal() {
        return animal;
    }
    
    public void setAnimal(Animall animal) {
        this.animal = animal;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getMetodoEntrega() {
        return metodoEntrega;
    }
    
    public void setMetodoEntrega(String metodoEntrega) {
        this.metodoEntrega = metodoEntrega;
    }
    
    public String getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public Usuarioo getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuarioo usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "Donacion{" +
                "id=" + id +
                ", animal=" + (animal != null ? animal.getNombre() : "null") +
                ", tipo='" + tipo + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    public String getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }
}