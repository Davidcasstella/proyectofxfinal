package co.edu.uptc.servicio;

import co.edu.uptc.modelo.Animal;
import co.edu.uptc.modelo.Donante;
import co.edu.uptc.modelo.Asignacion;
import co.edu.uptc.persistencia.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FundacionService {
    
    private static FundacionService instancia;
    
    private final AnimalDAO animalDAO;
    private final DonanteDAO donanteDAO;
    private final AsignacionDAO asignacionDAO;
    
    private FundacionService() {
        this.animalDAO = new AnimalDAOImpl();
        this.donanteDAO = new DonanteDAOImpl();
        this.asignacionDAO = new AsignacionDAOImpl();
        inicializarDatosPrueba();
    }
    
    public static FundacionService getInstance() {
        if (instancia == null) {
            instancia = new FundacionService();
        }
        return instancia;
    }
    
    // ==================== MÉTODOS PARA ANIMALES ====================
    
    public Animal guardarAnimal(Animal animal) {
        return animalDAO.guardar(animal);
    }
    
    public List<Animal> obtenerTodosLosAnimales() {
        return animalDAO.obtenerTodos();
    }
    
    public Optional<Animal> buscarAnimalPorId(Long id) {
        return animalDAO.buscarPorId(id);
    }
    
    public boolean eliminarAnimal(Long id) {
        // Verificar si hay asignaciones asociadas
        List<Asignacion> asignacionesAnimal = asignacionDAO.buscarPorAnimalId(id);
        if (!asignacionesAnimal.isEmpty()) {
            // Opcional: eliminar asignaciones asociadas o rechazar eliminación
            return false; // No permitir eliminar si hay asignaciones
        }
        return animalDAO.eliminar(id);
    }
    
    public boolean actualizarAnimal(Animal animal) {
        return animalDAO.actualizar(animal);
    }
    
    public List<Animal> buscarAnimalesPorNombre(String nombre) {
        return animalDAO.buscarPorNombre(nombre);
    }
    
    public List<Animal> buscarAnimalesPorEspecie(String especie) {
        return animalDAO.buscarPorEspecie(especie);
    }
    
    public long contarAnimalesPorEstado(String estado) {
        return animalDAO.contarPorEstado(estado);
    }
    
    // ==================== MÉTODOS PARA DONANTES ====================
    
    public Donante guardarDonante(Donante donante) {
        return donanteDAO.guardar(donante);
    }
    
    public List<Donante> obtenerTodosLosDonantes() {
        return donanteDAO.obtenerTodos();
    }
    
    public Optional<Donante> buscarDonantePorId(Long id) {
        return donanteDAO.buscarPorId(id);
    }
    
    public Optional<Donante> buscarDonantePorDocumento(String documento) {
        return donanteDAO.buscarPorDocumento(documento);
    }
    
    public boolean eliminarDonante(Long id) {
        // Verificar si hay asignaciones asociadas
        List<Asignacion> asignacionesDonante = asignacionDAO.buscarPorDonanteId(id);
        if (!asignacionesDonante.isEmpty()) {
            return false; // No permitir eliminar si hay asignaciones
        }
        return donanteDAO.eliminar(id);
    }
    
    public boolean actualizarDonante(Donante donante) {
        return donanteDAO.actualizar(donante);
    }
    
    public List<Donante> buscarDonantesPorNombre(String nombre) {
        return donanteDAO.buscarPorNombre(nombre);
    }
    
    public boolean existeDocumentoDonante(String documento) {
        return donanteDAO.existeDocumento(documento);
    }
    
    // ==================== MÉTODOS PARA ASIGNACIONES ====================
    
    public Asignacion crearAsignacion(Long donanteId, Long animalId, String recurso, double monto) {
        // Validar que existan el donante y el animal
        Optional<Donante> donante = donanteDAO.buscarPorId(donanteId);
        Optional<Animal> animal = animalDAO.buscarPorId(animalId);
        
        if (donante.isEmpty() || animal.isEmpty()) {
            throw new IllegalArgumentException("Donante o animal no encontrado");
        }
        
        Asignacion asignacion = new Asignacion(donanteId, animalId, recurso, "Pendiente", monto);
        asignacion.setDonante(donante.get().getNombre());
        asignacion.setAnimal(animal.get().getNombre() + " (" + animal.get().getEspecie() + ")");
        
        return asignacionDAO.guardar(asignacion);
    }
    
    public List<Asignacion> obtenerTodasLasAsignaciones() {
        return asignacionDAO.obtenerTodas();
    }
    
    public Optional<Asignacion> buscarAsignacionPorId(Long id) {
        return asignacionDAO.buscarPorId(id);
    }
    
    public boolean eliminarAsignacion(Long id) {
        return asignacionDAO.eliminar(id);
    }
    
    public boolean actualizarAsignacion(Asignacion asignacion) {
        return asignacionDAO.actualizar(asignacion);
    }
    
    public List<Asignacion> obtenerAsignacionesPendientes() {
        return asignacionDAO.obtenerPendientes();
    }
    
    public List<Asignacion> obtenerAsignacionesRecientes(int dias) {
        return asignacionDAO.obtenerRecientes(dias);
    }
    
    // ==================== MÉTODOS DE ESTADÍSTICAS ====================
    
    public long contarDonantes() {
        return donanteDAO.contarTodos();
    }
    
    public long contarAnimales() {
        return animalDAO.contarTodos();
    }
    
    public long contarAsignaciones() {
        return asignacionDAO.contarTodas();
    }
    
    public double obtenerMontoTotalDonaciones() {
        return asignacionDAO.obtenerMontoTotalDonaciones();
    }
    
    // ==================== INICIALIZACIÓN DE DATOS ====================
    
    private void inicializarDatosPrueba() {
        // Solo agregar datos si no existen
        if (animalDAO.contarTodos() == 0) {
            crearAnimalesIniciales();
        }
        if (donanteDAO.contarTodos() == 0) {
            crearDonantesIniciales();
        }
        if (asignacionDAO.contarTodas() == 0) {
            crearAsignacionesIniciales();
        }
    }
    
    private void crearAnimalesIniciales() {
        Animal animal1 = new Animal("foto1.png", "Max", "Perro", "Activo");
        animal1.setRaza("Labrador");
        animal1.setEdad(3);
        animal1.setSexo("Macho");
        animal1.setDescripcion("Perro muy juguetón y cariñoso");
        animalDAO.guardar(animal1);
        
        Animal animal2 = new Animal("foto2.png", "Luna", "Gato", "En tratamiento");
        animal2.setRaza("Persa");
        animal2.setEdad(2);
        animal2.setSexo("Hembra");
        animal2.setDescripcion("Gata tranquila en recuperación");
        animalDAO.guardar(animal2);
        
        Animal animal3 = new Animal("foto3.png", "Peppa", "Gato", "Activo");
        animal3.setRaza("Mestizo");
        animal3.setEdad(1);
        animal3.setSexo("Hembra");
        animalDAO.guardar(animal3);
        
        Animal animal4 = new Animal("foto4.png", "Lucas", "Gato", "Crítico");
        animal4.setRaza("Siamés");
        animal4.setEdad(5);
        animal4.setSexo("Macho");
        animalDAO.guardar(animal4);
        
        Animal animal5 = new Animal("foto5.png", "Tobias", "Gato", "En observación");
        animal5.setRaza("Mestizo");
        animal5.setEdad(4);
        animal5.setSexo("Macho");
        animalDAO.guardar(animal5);
    }
    
    private void crearDonantesIniciales() {
        Donante donante1 = new Donante("Juan Pérez", "123-456", true, false, false);
        donante1.setEmail("juan@email.com");
        donante1.setTelefono("555-1234");
        donanteDAO.guardar(donante1);
        
        Donante donante2 = new Donante("Ana Gómez", "789-012", true, true, false);
        donante2.setEmail("ana@email.com");
        donante2.setTelefono("555-5678");
        donanteDAO.guardar(donante2);
        
        Donante donante3 = new Donante("Luis Martínez", "345-678", false, true, true);
        donante3.setEmail("luis@email.com");
        donante3.setTelefono("555-9012");
        donanteDAO.guardar(donante3);
        
        Donante donante4 = new Donante("María López", "901-234", true, false, true);
        donante4.setEmail("maria@email.com");
        donante4.setTelefono("555-3456");
        donanteDAO.guardar(donante4);
        
        Donante donante5 = new Donante("Carlos Ruiz", "567-890", false, true, false);
        donante5.setEmail("carlos@email.com");
        donante5.setTelefono("555-7890");
        donanteDAO.guardar(donante5);
        
        Donante donante6 = new Donante("Sofía Díaz", "234-567", true, true, true);
        donante6.setEmail("sofia@email.com");
        donante6.setTelefono("555-2345");
        donanteDAO.guardar(donante6);
    }
    
    private void crearAsignacionesIniciales() {
        // Crear algunas asignaciones de ejemplo
        try {
            crearAsignacion(1L, 2L, "Alimento: 10kg", 0);
            crearAsignacion(2L, 1L, "Dinero: $500", 500);
            crearAsignacion(3L, 3L, "Medicamento: Antipulgas", 0);
            crearAsignacion(4L, 4L, "Alimento: 5kg", 0);
            crearAsignacion(5L, 5L, "Dinero: $200", 200);
        } catch (Exception e) {
            System.err.println("Error creando asignaciones iniciales: " + e.getMessage());
        }
    }
}