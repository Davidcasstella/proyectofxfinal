package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Voluntario;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VoluntarioDAOImpl implements VoluntarioDAO {
    
    private static final String ARCHIVO_VOLUNTARIOS = "voluntarios.json";
    private static final Type TIPO_LISTA_VOLUNTARIOS = new TypeToken<List<Voluntario>>(){}.getType();
    
    private List<Voluntario> voluntarios;
    private Long siguienteId;
    
    public VoluntarioDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        voluntarios = JsonUtil.cargarLista(ARCHIVO_VOLUNTARIOS, TIPO_LISTA_VOLUNTARIOS);
        siguienteId = voluntarios.stream()
                .mapToLong(voluntario -> voluntario.getId() != null ? voluntario.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(voluntarios, ARCHIVO_VOLUNTARIOS);
    }
    
    @Override
    public Voluntario guardar(Voluntario voluntario) {
        if (voluntario.getId() == null) {
            voluntario.setId(siguienteId++);
            voluntarios.add(voluntario);
        } else {
            // Actualizar voluntario existente
            for (int i = 0; i < voluntarios.size(); i++) {
                if (voluntarios.get(i).getId().equals(voluntario.getId())) {
                    voluntarios.set(i, voluntario);
                    break;
                }
            }
        }
        guardarDatos();
        return voluntario;
    }
    
    @Override
    public Optional<Voluntario> buscarPorId(Long id) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Voluntario> obtenerTodos() {
        return List.copyOf(voluntarios);
    }
    
    @Override
    public Optional<Voluntario> buscarPorDocumento(String documento) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getDocumento().equals(documento))
                .findFirst();
    }
    
    @Override
    public Optional<Voluntario> buscarPorEmail(String email) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getEmail() != null && 
                        voluntario.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    @Override
    public List<Voluntario> buscarPorNombre(String nombre) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getNombreCompleto().toLowerCase()
                        .contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Voluntario> buscarPorEspecialidad(String especialidad) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getEspecialidad() != null &&
                        voluntario.getEspecialidad().equalsIgnoreCase(especialidad))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Voluntario> buscarPorEstado(String estado) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Voluntario> obtenerDisponibles() {
        return voluntarios.stream()
                .filter(Voluntario::isDisponible)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Voluntario> obtenerActivos() {
        return buscarPorEstado("ACTIVO");
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = voluntarios.removeIf(voluntario -> voluntario.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return voluntarios.stream().anyMatch(voluntario -> voluntario.getId().equals(id));
    }
    
    @Override
    public boolean existeDocumento(String documento) {
        return voluntarios.stream()
                .anyMatch(voluntario -> voluntario.getDocumento().equals(documento));
    }
    
    @Override
    public boolean existeEmail(String email) {
        return voluntarios.stream()
                .anyMatch(voluntario -> voluntario.getEmail() != null && 
                        voluntario.getEmail().equalsIgnoreCase(email));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Voluntario voluntario) {
        if (voluntario.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < voluntarios.size(); i++) {
            if (voluntarios.get(i).getId().equals(voluntario.getId())) {
                voluntarios.set(i, voluntario);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodos() {
        return voluntarios.size();
    }
    
    @Override
    public long contarPorEstado(String estado) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getEstado().equalsIgnoreCase(estado))
                .count();
    }
    
    @Override
    public long contarPorEspecialidad(String especialidad) {
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getEspecialidad() != null &&
                        voluntario.getEspecialidad().equalsIgnoreCase(especialidad))
                .count();
    }
}