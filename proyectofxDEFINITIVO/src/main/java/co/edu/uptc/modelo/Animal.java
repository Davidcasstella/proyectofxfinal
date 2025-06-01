package co.edu.uptc.modelo;

import java.time.LocalDate;

public class Animal {
    private Long id;
    private String nombre;
    private String especie;
    private String estado;
    private String foto;
    private String raza;
    private int edad;
    private String sexo;
    private String descripcion;
    private LocalDate fechaIngreso;
    private boolean adoptado;

    // Constructor vacío (necesario para JSON)
    public Animal() {}

    // Constructor completo
    public Animal(Long id, String nombre, String especie, String estado, String foto, 
                  String raza, int edad, String sexo, String descripcion, 
                  LocalDate fechaIngreso, boolean adoptado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.foto = foto;
        this.raza = raza;
        this.edad = edad;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.fechaIngreso = fechaIngreso;
        this.adoptado = adoptado;
    }

    // Constructor básico (compatible con código existente)
    public Animal(String foto, String nombre, String especie, String estado) {
        this.foto = foto;
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.fechaIngreso = LocalDate.now();
        this.adoptado = false;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public boolean isAdoptado() { return adoptado; }
    public void setAdoptado(boolean adoptado) { this.adoptado = adoptado; }

    @Override
    public String toString() {
        return nombre + " (" + especie + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return id != null && id.equals(animal.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}