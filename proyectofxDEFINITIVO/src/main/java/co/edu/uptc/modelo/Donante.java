package co.edu.uptc.modelo;

import java.time.LocalDate;

public class Donante {
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private String direccion;
    private boolean dinero;
    private boolean alimento;
    private boolean medicamento;
    private LocalDate fechaRegistro;
    private LocalDate ultimaDonacion;
    private double montoTotalDonado;

    // Constructor vacío (necesario para JSON)
    public Donante() {}

    // Constructor completo
    public Donante(Long id, String nombre, String documento, String email, String telefono,
                   String direccion, boolean dinero, boolean alimento, boolean medicamento,
                   LocalDate fechaRegistro, LocalDate ultimaDonacion, double montoTotalDonado) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dinero = dinero;
        this.alimento = alimento;
        this.medicamento = medicamento;
        this.fechaRegistro = fechaRegistro;
        this.ultimaDonacion = ultimaDonacion;
        this.montoTotalDonado = montoTotalDonado;
    }

    // Constructor básico (compatible con código existente)
    public Donante(String nombre, String documento, boolean dinero, boolean alimento, boolean medicamento) {
        this.nombre = nombre;
        this.documento = documento;
        this.dinero = dinero;
        this.alimento = alimento;
        this.medicamento = medicamento;
        this.fechaRegistro = LocalDate.now();
        this.montoTotalDonado = 0.0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public boolean isDinero() { return dinero; }
    public void setDinero(boolean dinero) { this.dinero = dinero; }

    public boolean isAlimento() { return alimento; }
    public void setAlimento(boolean alimento) { this.alimento = alimento; }

    public boolean isMedicamento() { return medicamento; }
    public void setMedicamento(boolean medicamento) { this.medicamento = medicamento; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public LocalDate getUltimaDonacion() { return ultimaDonacion; }
    public void setUltimaDonacion(LocalDate ultimaDonacion) { this.ultimaDonacion = ultimaDonacion; }

    public double getMontoTotalDonado() { return montoTotalDonado; }
    public void setMontoTotalDonado(double montoTotalDonado) { this.montoTotalDonado = montoTotalDonado; }

    @Override
    public String toString() {
        return nombre + " (" + documento + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Donante donante = (Donante) obj;
        return id != null && id.equals(donante.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}