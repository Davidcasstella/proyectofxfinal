package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Animal;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnimalDAOImpl implements AnimalDAO {
    
    private static final String ARCHIVO_ANIMALES = "animales.json";
    private static final Type TIPO_LISTA_ANIMALES = new TypeToken<List<Animal>>(){}.getType();
    
    private List<Animal> animales;
    private Long siguienteId;
    
    public AnimalDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        animales = JsonUtil.cargarLista(ARCHIVO_ANIMALES, TIPO_LISTA_ANIMALES);
        siguienteId = animales.stream()
                .mapToLong(animal -> animal.getId() != null ? animal.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(animales, ARCHIVO_ANIMALES);
    }
    
    @Override
    public Animal guardar(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(siguienteId++);
            animales.add(animal);
        } else {
            // Actualizar animal existente
            for (int i = 0; i < animales.size(); i++) {
                if (animales.get(i).getId().equals(animal.getId())) {
                    animales.set(i, animal);
                    break;
                }
            }
        }
        guardarDatos();
        return animal;
    }
    
    @Override
    public Optional<Animal> buscarPorId(Long id) {
        return animales.stream()
                .filter(animal -> animal.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Animal> obtenerTodos() {
        return List.copyOf(animales);
    }
    
    @Override
    public List<Animal> buscarPorNombre(String nombre) {
        return animales.stream()
                .filter(animal -> animal.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Animal> buscarPorEspecie(String especie) {
        return animales.stream()
                .filter(animal -> animal.getEspecie().equalsIgnoreCase(especie))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Animal> buscarPorEstado(String estado) {
        return animales.stream()
                .filter(animal -> animal.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Animal> obtenerDisponiblesParaAdopcion() {
        return animales.stream()
                .filter(animal -> !animal.isAdoptado() && 
                        (animal.getEstado().equalsIgnoreCase("Activo") || 
                         animal.getEstado().equalsIgnoreCase("Bueno")))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = animales.removeIf(animal -> animal.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return animales.stream().anyMatch(animal -> animal.getId().equals(id));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Animal animal) {
        if (animal.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < animales.size(); i++) {
            if (animales.get(i).getId().equals(animal.getId())) {
                animales.set(i, animal);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodos() {
        return animales.size();
    }
    
    @Override
    public long contarPorEstado(String estado) {
        return animales.stream()
                .filter(animal -> animal.getEstado().equalsIgnoreCase(estado))
                .count();
    }
}