package co.edu.uptc.modelo;

public class Usuario {
    private String nombreCompleto;
    private String email;
    private String password;
    private String correoRecuperacion;
    
    public Usuario() {
    }
    
    public Usuario(String nombreCompleto, String email, String password, String correoRecuperacion) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.correoRecuperacion = correoRecuperacion;
    }
    
    // Getters y Setters
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCorreoRecuperacion() {
        return correoRecuperacion;
    }
    
    public void setCorreoRecuperacion(String correoRecuperacion) {
        this.correoRecuperacion = correoRecuperacion;
    }
}