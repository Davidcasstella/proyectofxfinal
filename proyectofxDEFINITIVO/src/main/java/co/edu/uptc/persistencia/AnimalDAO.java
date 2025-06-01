package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalDAO {
    
    /**
     * Guarda un nuevo animal o actualiza uno existente
     */
    Animal guardar(Animal animal);
    
    /**
     * Busca un animal por su ID
     */
    Optional<Animal> buscarPorId(Long id);
    
    /**
     * Obtiene todos los animales
     */
    List<Animal> obtenerTodos();
    
    /**
     * Busca animales por nombre (búsqueda parcial)
     */
    List<Animal> buscarPorNombre(String nombre);
    
    /**
     * Busca animales por especie
     */
    List<Animal> buscarPorEspecie(String especie);
    
    /**
     * Busca animales por estado
     */
    List<Animal> buscarPorEstado(String estado);
    
    /**
     * Obtiene animales disponibles para adopción
     */
    List<Animal> obtenerDisponiblesParaAdopcion();
    
    /**
     * Elimina un animal por su ID
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si existe un animal con el ID dado
     */
    boolean existe(Long id);
    
    /**
     * Obtiene el siguiente ID disponible
     */
    Long obtenerSiguienteId();
    
    /**
     * Actualiza un animal existente
     */
    boolean actualizar(Animal animal);
    
    /**
     * Obtiene la cantidad total de animales
     */
    long contarTodos();
    
    /**
     * Obtiene la cantidad de animales por estado
     */
    long contarPorEstado(String estado);
}