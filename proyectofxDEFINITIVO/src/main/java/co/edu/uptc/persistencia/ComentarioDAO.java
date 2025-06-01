package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Comentario;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ComentarioDAO {
    
    /**
     * Guarda un nuevo comentario o actualiza uno existente
     */
    Comentario guardar(Comentario comentario);
    
    /**
     * Busca un comentario por su ID
     */
    Optional<Comentario> buscarPorId(Long id);
    
    /**
     * Obtiene todos los comentarios
     */
    List<Comentario> obtenerTodos();
    
    /**
     * Busca comentarios por voluntario ID
     */
    List<Comentario> buscarPorVoluntarioId(Long voluntarioId);
    
    /**
     * Busca comentarios por categoría
     */
    List<Comentario> buscarPorCategoria(String categoria);
    
    /**
     * Busca comentarios por estado
     */
    List<Comentario> buscarPorEstado(String estado);
    
    /**
     * Busca comentarios por prioridad
     */
    List<Comentario> buscarPorPrioridad(String prioridad);
    
    /**
     * Busca comentarios públicos
     */
    List<Comentario> obtenerPublicos();
    
    /**
     * Busca comentarios en un rango de fechas
     */
    List<Comentario> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Obtiene comentarios sin respuesta
     */
    List<Comentario> obtenerSinRespuesta();
    
    /**
     * Obtiene comentarios respondidos
     */
    List<Comentario> obtenerRespondidos();
    
    /**
     * Obtiene comentarios recientes (últimos N días)
     */
    List<Comentario> obtenerRecientes(int dias);
    
    /**
     * Busca comentarios por título (búsqueda parcial)
     */
    List<Comentario> buscarPorTitulo(String titulo);
    
    /**
     * Elimina un comentario por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe un comentario con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza un comentario existente
     */
    boolean actualizar(Comentario comentario);
    
    /**
     * Obtiene la cantidad total de comentarios
     */
    long contarTodos();
    
    /**
     * Obtiene la cantidad de comentarios por estado
     */
    long contarPorEstado(String estado);
    
    /**
     * Obtiene la cantidad de comentarios por categoría
     */
    long contarPorCategoria(String categoria);
    
    /**
     * Obtiene la cantidad de comentarios por voluntario
     */
    long contarPorVoluntario(Long voluntarioId);
}