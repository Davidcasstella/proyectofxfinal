package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Voluntario;
import java.util.List;
import java.util.Optional;

public interface VoluntarioDAO {
    
    /**
     * Guarda un nuevo voluntario o actualiza uno existente
     */
    Voluntario guardar(Voluntario voluntario);
    
    /**
     * Busca un voluntario por su ID
     */
    Optional<Voluntario> buscarPorId(Long id);
    
    /**
     * Obtiene todos los voluntarios
     */
    List<Voluntario> obtenerTodos();
    
    /**
     * Busca un voluntario por su documento
     */
    Optional<Voluntario> buscarPorDocumento(String documento);
    
    /**
     * Busca un voluntario por su email
     */
    Optional<Voluntario> buscarPorEmail(String email);
    
    /**
     * Busca voluntarios por nombre (búsqueda parcial)
     */
    List<Voluntario> buscarPorNombre(String nombre);
    
    /**
     * Busca voluntarios por especialidad
     */
    List<Voluntario> buscarPorEspecialidad(String especialidad);
    
    /**
     * Busca voluntarios por estado
     */
    List<Voluntario> buscarPorEstado(String estado);
    
    /**
     * Obtiene voluntarios disponibles
     */
    List<Voluntario> obtenerDisponibles();
    
    /**
     * Obtiene voluntarios activos
     */
    List<Voluntario> obtenerActivos();
    
    /**
     * Elimina un voluntario por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe un voluntario con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Verifica si existe un voluntario con el documento dado
     */
    boolean existeDocumento(String documento);
    
    /**
     * Verifica si existe un voluntario con el email dado
     */
    boolean existeEmail(String email);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza un voluntario existente
     */
    boolean actualizar(Voluntario voluntario);
    
    /**
     * Obtiene la cantidad total de voluntarios
     */
    long contarTodos();
    
    /**
     * Obtiene la cantidad de voluntarios por estado
     */
    long contarPorEstado(String estado);
    
    /**
     * Obtiene la cantidad de voluntarios por especialidad
     */
    long contarPorEspecialidad(String especialidad);
}