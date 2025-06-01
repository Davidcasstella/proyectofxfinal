package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Reporte;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReporteDAO {
    
    /**
     * Guarda un nuevo reporte o actualiza uno existente
     */
    Reporte guardar(Reporte reporte);
    
    /**
     * Busca un reporte por su ID
     */
    Optional<Reporte> buscarPorId(Long id);
    
    /**
     * Obtiene todos los reportes
     */
    List<Reporte> obtenerTodos();
    
    /**
     * Busca reportes por tipo
     */
    List<Reporte> buscarPorTipo(String tipoReporte);
    
    /**
     * Busca reportes por estado
     */
    List<Reporte> buscarPorEstado(String estado);
    
    /**
     * Busca reportes generados por un usuario específico
     */
    List<Reporte> buscarPorGenerador(String generadoPor);
    
    /**
     * Busca reportes en un rango de fechas
     */
    List<Reporte> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Obtiene reportes recientes (últimos N días)
     */
    List<Reporte> obtenerRecientes(int dias);
    
    /**
     * Elimina un reporte por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe un reporte con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza un reporte existente
     */
    boolean actualizar(Reporte reporte);
    
    /**
     * Obtiene la cantidad total de reportes
     */
    long contarTodos();
    
    /**
     * Obtiene la cantidad de reportes por tipo
     */
    long contarPorTipo(String tipoReporte);
    
    /**
     * Obtiene la cantidad de reportes por estado
     */
    long contarPorEstado(String estado);
}