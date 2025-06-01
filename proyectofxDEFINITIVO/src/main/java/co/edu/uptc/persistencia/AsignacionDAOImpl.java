package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Asignacion;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AsignacionDAOImpl implements AsignacionDAO {
    
    private static final String ARCHIVO_ASIGNACIONES = "asignaciones.json";
    private static final Type TIPO_LISTA_ASIGNACIONES = new TypeToken<List<Asignacion>>(){}.getType();
    
    private List<Asignacion> asignaciones;
    private Long siguienteId;
    
    public AsignacionDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        asignaciones = JsonUtil.cargarLista(ARCHIVO_ASIGNACIONES, TIPO_LISTA_ASIGNACIONES);
        siguienteId = asignaciones.stream()
                .mapToLong(asignacion -> asignacion.getId() != null ? asignacion.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(asignaciones, ARCHIVO_ASIGNACIONES);
    }
    
    @Override
    public Asignacion guardar(Asignacion asignacion) {
        if (asignacion.getId() == null) {
            asignacion.setId(siguienteId++);
            asignaciones.add(asignacion);
        } else {
            // Actualizar asignación existente
            for (int i = 0; i < asignaciones.size(); i++) {
                if (asignaciones.get(i).getId().equals(asignacion.getId())) {
                    asignaciones.set(i, asignacion);
                    break;
                }
            }
        }
        guardarDatos();
        return asignacion;
    }
    
    @Override
    public Optional<Asignacion> buscarPorId(Long id) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Asignacion> obtenerTodas() {
        return List.copyOf(asignaciones);
    }
    
    @Override
    public List<Asignacion> buscarPorDonanteId(Long donanteId) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getDonanteId() != null && 
                        asignacion.getDonanteId().equals(donanteId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Asignacion> buscarPorAnimalId(Long animalId) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getAnimalId() != null && 
                        asignacion.getAnimalId().equals(animalId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Asignacion> buscarPorEstado(String estado) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Asignacion> buscarPorTipoRecurso(String tipoRecurso) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getRecurso().toLowerCase()
                        .contains(tipoRecurso.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Asignacion> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getFechaAsignacion() != null &&
                        !asignacion.getFechaAsignacion().isBefore(fechaInicio) &&
                        !asignacion.getFechaAsignacion().isAfter(fechaFin))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Asignacion> obtenerPendientes() {
        return buscarPorEstado("Pendiente");
    }
    
    @Override
    public List<Asignacion> obtenerEntregadas() {
        return buscarPorEstado("Entregado");
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = asignaciones.removeIf(asignacion -> asignacion.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return asignaciones.stream().anyMatch(asignacion -> asignacion.getId().equals(id));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Asignacion asignacion) {
        if (asignacion.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < asignaciones.size(); i++) {
            if (asignaciones.get(i).getId().equals(asignacion.getId())) {
                asignaciones.set(i, asignacion);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodas() {
        return asignaciones.size();
    }
    
    @Override
    public long contarPorEstado(String estado) {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getEstado().equalsIgnoreCase(estado))
                .count();
    }
    
    @Override
    public double obtenerMontoTotalDonaciones() {
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getRecurso().toLowerCase().contains("dinero"))
                .mapToDouble(Asignacion::getMonto)
                .sum();
    }
    
    @Override
    public List<Asignacion> obtenerRecientes(int dias) {
        LocalDate fechaLimite = LocalDate.now().minusDays(dias);
        return asignaciones.stream()
                .filter(asignacion -> asignacion.getFechaAsignacion() != null &&
                        asignacion.getFechaAsignacion().isAfter(fechaLimite))
                .collect(Collectors.toList());
    }
}