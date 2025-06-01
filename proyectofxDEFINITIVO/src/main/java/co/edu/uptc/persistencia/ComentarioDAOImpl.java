package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Comentario;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ComentarioDAOImpl implements ComentarioDAO {
    
    private static final String ARCHIVO_COMENTARIOS = "comentarios.json";
    private static final Type TIPO_LISTA_COMENTARIOS = new TypeToken<List<Comentario>>(){}.getType();
    
    private List<Comentario> comentarios;
    private Long siguienteId;
    
    public ComentarioDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        comentarios = JsonUtil.cargarLista(ARCHIVO_COMENTARIOS, TIPO_LISTA_COMENTARIOS);
        siguienteId = comentarios.stream()
                .mapToLong(comentario -> comentario.getId() != null ? comentario.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(comentarios, ARCHIVO_COMENTARIOS);
    }
    
    @Override
    public Comentario guardar(Comentario comentario) {
        if (comentario.getId() == null) {
            comentario.setId(siguienteId++);
            comentarios.add(comentario);
        } else {
            // Actualizar comentario existente
            for (int i = 0; i < comentarios.size(); i++) {
                if (comentarios.get(i).getId().equals(comentario.getId())) {
                    comentarios.set(i, comentario);
                    break;
                }
            }
        }
        guardarDatos();
        return comentario;
    }
    
    @Override
    public Optional<Comentario> buscarPorId(Long id) {
        return comentarios.stream()
                .filter(comentario -> comentario.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Comentario> obtenerTodos() {
        return List.copyOf(comentarios);
    }
    
    @Override
    public List<Comentario> buscarPorVoluntarioId(Long voluntarioId) {
        return comentarios.stream()
                .filter(comentario -> comentario.getVoluntarioId() != null &&
                        comentario.getVoluntarioId().equals(voluntarioId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> buscarPorCategoria(String categoria) {
        return comentarios.stream()
                .filter(comentario -> comentario.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> buscarPorEstado(String estado) {
        return comentarios.stream()
                .filter(comentario -> comentario.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> buscarPorPrioridad(String prioridad) {
        return comentarios.stream()
                .filter(comentario -> comentario.getPrioridad().equalsIgnoreCase(prioridad))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> obtenerPublicos() {
        return comentarios.stream()
                .filter(Comentario::isPublico)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return comentarios.stream()
                .filter(comentario -> comentario.getFechaCreacion() != null &&
                        !comentario.getFechaCreacion().isBefore(fechaInicio) &&
                        !comentario.getFechaCreacion().isAfter(fechaFin))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> obtenerSinRespuesta() {
        return comentarios.stream()
                .filter(comentario -> !comentario.tieneRespuesta())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> obtenerRespondidos() {
        return comentarios.stream()
                .filter(Comentario::tieneRespuesta)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> obtenerRecientes(int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return comentarios.stream()
                .filter(comentario -> comentario.getFechaCreacion() != null &&
                        comentario.getFechaCreacion().isAfter(fechaLimite))
                .sorted((c1, c2) -> c2.getFechaCreacion().compareTo(c1.getFechaCreacion()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Comentario> buscarPorTitulo(String titulo) {
        return comentarios.stream()
                .filter(comentario -> comentario.getTitulo().toLowerCase()
                        .contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = comentarios.removeIf(comentario -> comentario.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return comentarios.stream().anyMatch(comentario -> comentario.getId().equals(id));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Comentario comentario) {
        if (comentario.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < comentarios.size(); i++) {
            if (comentarios.get(i).getId().equals(comentario.getId())) {
                comentarios.set(i, comentario);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodos() {
        return comentarios.size();
    }
    
    @Override
    public long contarPorEstado(String estado) {
        return comentarios.stream()
                .filter(comentario -> comentario.getEstado().equalsIgnoreCase(estado))
                .count();
    }
    
    @Override
    public long contarPorCategoria(String categoria) {
        return comentarios.stream()
                .filter(comentario -> comentario.getCategoria().equalsIgnoreCase(categoria))
                .count();
    }
    
    @Override
    public long contarPorVoluntario(Long voluntarioId) {
        return comentarios.stream()
                .filter(comentario -> comentario.getVoluntarioId() != null &&
                        comentario.getVoluntarioId().equals(voluntarioId))
                .count();
    }
}