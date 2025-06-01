package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Donante;
import java.util.List;
import java.util.Optional;

public interface DonanteDAO {
    
    /**
     * Guarda un nuevo donante o actualiza uno existente
     */
    Donante guardar(Donante donante);
    
    /**
     * Busca un donante por su ID
     */
    Optional<Donante> buscarPorId(Long id);
    
    /**
     * Obtiene todos los donantes
     */
    List<Donante> obtenerTodos();
    
    /**
     * Busca un donante por su documento
     */
    Optional<Donante> buscarPorDocumento(String documento);
    
    /**
     * Busca donantes por nombre (búsqueda parcial)
     */
    List<Donante> buscarPorNombre(String nombre);
    
    /**
     * Busca donantes por email
     */
    Optional<Donante> buscarPorEmail(String email);
    
    /**
     * Obtiene donantes que donan dinero
     */
    List<Donante> obtenerDonantesDinero();
    
    /**
     * Obtiene donantes que donan alimento
     */
    List<Donante> obtenerDonantesAlimento();
    
    /**
     * Obtiene donantes que donan medicamento
     */
    List<Donante> obtenerDonantesMedicamento();
    
    /**
     * Elimina un donante por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe un donante con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Verifica si existe un donante con el documento dado
     */
    boolean existeDocumento(String documento);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza un donante existente
     */
    boolean actualizar(Donante donante);
    
    /**
     * Obtiene la cantidad total de donantes
     */
    long contarTodos();
    
    /**
     * Obtiene donantes activos (que han donado recientemente)
     */
    List<Donante> obtenerDonantesActivos();
}