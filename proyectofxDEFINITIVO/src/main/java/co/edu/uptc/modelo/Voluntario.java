package co.edu.uptc.modelo;

import java.time.LocalDate;
import java.util.List;

public class Voluntario {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String documento;
    private LocalDate fechaRegistro;
    private String estado; // "ACTIVO", "INACTIVO", "SUSPENDIDO"
    private String especialidad; // "CUIDADO_ANIMALES", "VETERINARIA", "ADMINISTRACION", "LIMPIEZA"
    private String foto;
    private String direccion;
    private boolean disponible;
    private int horasVoluntariado;
    private List<String> habilidades;

    // Constructor vacío (necesario para JSON)
    public Voluntario() {}

    // Constructor completo
    public Voluntario(Long id, String nombre, String apellido, String email, String telefono,
                      String documento, LocalDate fechaRegistro, String estado, String especialidad,
                      String foto, String direccion, boolean disponible, int horasVoluntariado,
                      List<String> habilidades) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.especialidad = especialidad;
        this.foto = foto;
        this.direccion = direccion;
        this.disponible = disponible;
        this.horasVoluntariado = horasVoluntariado;
        this.habilidades = habilidades;
    }

    // Constructor básico
    public Voluntario(String nombre, String apellido, String email, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.documento = documento;
        this.fechaRegistro = LocalDate.now();
        this.estado = "ACTIVO";
        this.disponible = true;
        this.horasVoluntariado = 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombreCompleto() {
        return nombre + " " + (apellido != null ? apellido : "");
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public int getHorasVoluntariado() { return horasVoluntariado; }
    public void setHorasVoluntariado(int horasVoluntariado) { this.horasVoluntariado = horasVoluntariado; }

    public List<String> getHabilidades() { return habilidades; }
    public void setHabilidades(List<String> habilidades) { this.habilidades = habilidades; }

    @Override
    public String toString() {
        return getNombreCompleto() + " (" + especialidad + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Voluntario voluntario = (Voluntario) obj;
        return id != null && id.equals(voluntario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}