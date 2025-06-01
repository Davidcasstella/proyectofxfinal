package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Asignacion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsignacionDAO {
    
    /**
     * Guarda una nueva asignación o actualiza una existente
     */
    Asignacion guardar(Asignacion asignacion);
    
    /**
     * Busca una asignación por su ID
     */
    Optional<Asignacion> buscarPorId(Long id);
    
    /**
     * Obtiene todas las asignaciones
     */
    List<Asignacion> obtenerTodas();
    
    /**
     * Busca asignaciones por donante ID
     */
    List<Asignacion> buscarPorDonanteId(Long donanteId);
    
    /**
     * Busca asignaciones por animal ID
     */
    List<Asignacion> buscarPorAnimalId(Long animalId);
    
    /**
     * Busca asignaciones por estado
     */
    List<Asignacion> buscarPorEstado(String estado);
    
    /**
     * Busca asignaciones por tipo de recurso
     */
    List<Asignacion> buscarPorTipoRecurso(String tipoRecurso);
    
    /**
     * Busca asignaciones en un rango de fechas
     */
    List<Asignacion> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Obtiene asignaciones pendientes
     */
    List<Asignacion> obtenerPendientes();
    
    /**
     * Obtiene asignaciones entregadas
     */
    List<Asignacion> obtenerEntregadas();
    
    /**
     * Elimina una asignación por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe una asignación con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza una asignación existente
     */
    boolean actualizar(Asignacion asignacion);
    
    /**
     * Obtiene la cantidad total de asignaciones
     */
    long contarTodas();
    
    /**
     * Obtiene la cantidad de asignaciones por estado
     */
    long contarPorEstado(String estado);
    
    /**
     * Obtiene el monto total de donaciones monetarias
     */
    double obtenerMontoTotalDonaciones();
    
    /**
     * Obtiene asignaciones recientes (últimos N días)
     */
    List<Asignacion> obtenerRecientes(int dias);
}