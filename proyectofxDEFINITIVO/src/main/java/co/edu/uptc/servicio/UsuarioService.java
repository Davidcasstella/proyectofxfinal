package co.edu.uptc.servicio;

import co.edu.uptc.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private static final String ARCHIVO_USUARIOS = "usuarios.json";
    private final Gson gson;
    private List<Usuario> usuarios;
    
    public UsuarioService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.usuarios = cargarUsuarios();
    }
    
    // Cargar usuarios desde el archivo JSON
    private List<Usuario> cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(archivo)) {
            Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            List<Usuario> listaUsuarios = gson.fromJson(reader, listType);
            return listaUsuarios != null ? listaUsuarios : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Guardar usuarios en el archivo JSON
    private void guardarUsuarios() {
        try (Writer writer = new FileWriter(ARCHIVO_USUARIOS)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        // Verificar si el email ya existe
        if (existeEmail(usuario.getEmail())) {
            return false;
        }
        
        usuarios.add(usuario);
        guardarUsuarios();
        return true;
    }
    
    // Verificar si un email ya existe
    public boolean existeEmail(String email) {
        return usuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
    
    // Autenticar usuario
    public Usuario autenticar(String email, String password) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    
    // Buscar usuario por email
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    // Buscar usuario por nombre y email
    public Usuario buscarPorNombreYEmail(String nombre, String email) {
        return usuarios.stream()
                .filter(u -> u.getNombreCompleto().equalsIgnoreCase(nombre) 
                        && u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    // Actualizar contraseña
    public boolean actualizarPassword(String email, String nuevaPassword) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null) {
            usuario.setPassword(nuevaPassword);
            guardarUsuarios();
            return true;
        }
        return false;
    }
    
    // Obtener todos los usuarios (solo para depuración)
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }
}