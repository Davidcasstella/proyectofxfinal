package co.edu.uptc.servicio;

import co.edu.uptc.modelo.*;
import co.edu.uptc.persistencia.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FundacionService {
    
    private static FundacionService instancia;
    
    private final AnimalDAO animalDAO;
    private final DonanteDAO donanteDAO;
    private final AsignacionDAO asignacionDAO;
    private final ReporteDAO reporteDAO;
    private final VoluntarioDAO voluntarioDAO;
    private final ComentarioDAO comentarioDAO;
    
    private FundacionService() {
        this.animalDAO = new AnimalDAOImpl();
        this.donanteDAO = new DonanteDAOImpl();
        this.asignacionDAO = new AsignacionDAOImpl();
        this.reporteDAO = new ReporteDAOImpl();
        this.voluntarioDAO = new VoluntarioDAOImpl();
        this.comentarioDAO = new ComentarioDAOImpl();
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
        List<Asignacion> asignacionesAnimal = asignacionDAO.buscarPorAnimalId(id);
        if (!asignacionesAnimal.isEmpty()) {
            return false;
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
        List<Asignacion> asignacionesDonante = asignacionDAO.buscarPorDonanteId(id);
        if (!asignacionesDonante.isEmpty()) {
            return false;
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
    
    // ==================== MÉTODOS PARA REPORTES ====================
    
    public Reporte generarReporte(String titulo, String tipo, String descripcion, String generadoPor) {
        Reporte reporte = new Reporte(titulo, tipo, descripcion, generadoPor);
        
        // Generar datos según el tipo de reporte
        Map<String, Object> datos = new HashMap<>();
        switch (tipo.toUpperCase()) {
            case "GENERAL":
                datos = generarDatosReporteGeneral();
                break;
            case "DONACIONES":
                datos = generarDatosReporteDonaciones();
                break;
            case "ANIMALES":
                datos = generarDatosReporteAnimales();
                break;
            case "VOLUNTARIOS":
                datos = generarDatosReporteVoluntarios();
                break;
        }
        
        reporte.setDatos(datos);
        return reporteDAO.guardar(reporte);
    }
    
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteDAO.obtenerTodos();
    }
    
    public List<Reporte> obtenerReportesPorTipo(String tipo) {
        return reporteDAO.buscarPorTipo(tipo);
    }
    
    public List<Reporte> obtenerReportesRecientes(int dias) {
        return reporteDAO.obtenerRecientes(dias);
    }
    
    public boolean eliminarReporte(Long id) {
        return reporteDAO.eliminar(id);
    }
    
    public boolean actualizarReporte(Reporte reporte) {
        return reporteDAO.actualizar(reporte);
    }
    
    // ==================== MÉTODOS PARA VOLUNTARIOS ====================
    
    public Voluntario guardarVoluntario(Voluntario voluntario) {
        return voluntarioDAO.guardar(voluntario);
    }
    
    public List<Voluntario> obtenerTodosLosVoluntarios() {
        return voluntarioDAO.obtenerTodos();
    }
    
    public Optional<Voluntario> buscarVoluntarioPorId(Long id) {
        return voluntarioDAO.buscarPorId(id);
    }
    
    public Optional<Voluntario> buscarVoluntarioPorDocumento(String documento) {
        return voluntarioDAO.buscarPorDocumento(documento);
    }
    
    public boolean eliminarVoluntario(Long id) {
        // Verificar si hay comentarios asociados
        List<Comentario> comentariosVoluntario = comentarioDAO.buscarPorVoluntarioId(id);
        if (!comentariosVoluntario.isEmpty()) {
            return false; // No permitir eliminar si hay comentarios
        }
        return voluntarioDAO.eliminar(id);
    }
    
    public boolean actualizarVoluntario(Voluntario voluntario) {
        return voluntarioDAO.actualizar(voluntario);
    }
    
    public List<Voluntario> buscarVoluntariosPorNombre(String nombre) {
        return voluntarioDAO.buscarPorNombre(nombre);
    }
    
    public List<Voluntario> obtenerVoluntariosActivos() {
        return voluntarioDAO.obtenerActivos();
    }
    
    public boolean existeDocumentoVoluntario(String documento) {
        return voluntarioDAO.existeDocumento(documento);
    }
    
    public boolean existeEmailVoluntario(String email) {
        return voluntarioDAO.existeEmail(email);
    }
    
    // ==================== MÉTODOS PARA COMENTARIOS ====================
    
    public Comentario crearComentario(Long voluntarioId, String titulo, String contenido, String categoria) {
        Optional<Voluntario> voluntario = voluntarioDAO.buscarPorId(voluntarioId);
        if (voluntario.isEmpty()) {
            throw new IllegalArgumentException("Voluntario no encontrado");
        }
        
        Comentario comentario = new Comentario(voluntarioId, titulo, contenido, categoria);
        comentario.setNombreVoluntario(voluntario.get().getNombreCompleto());
        
        return comentarioDAO.guardar(comentario);
    }
    
    public List<Comentario> obtenerTodosLosComentarios() {
        return comentarioDAO.obtenerTodos();
    }
    
    public List<Comentario> obtenerComentariosPorVoluntario(Long voluntarioId) {
        return comentarioDAO.buscarPorVoluntarioId(voluntarioId);
    }
    
    public List<Comentario> obtenerComentariosPublicos() {
        return comentarioDAO.obtenerPublicos();
    }
    
    public List<Comentario> obtenerComentariosSinRespuesta() {
        return comentarioDAO.obtenerSinRespuesta();
    }
    
    public boolean responderComentario(Long comentarioId, String respuesta, String respondidoPor) {
        Optional<Comentario> comentarioOpt = comentarioDAO.buscarPorId(comentarioId);
        if (comentarioOpt.isEmpty()) {
            return false;
        }
        
        Comentario comentario = comentarioOpt.get();
        comentario.setRespuesta(respuesta);
        comentario.setFechaRespuesta(LocalDateTime.now());
        comentario.setRespondidoPor(respondidoPor);
        comentario.setEstado("RESPONDIDO");
        
        return comentarioDAO.actualizar(comentario);
    }
    
    public boolean eliminarComentario(Long id) {
        return comentarioDAO.eliminar(id);
    }
    
    public boolean actualizarComentario(Comentario comentario) {
        return comentarioDAO.actualizar(comentario);
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
    
    public long contarVoluntarios() {
        return voluntarioDAO.contarTodos();
    }
    
    public long contarComentarios() {
        return comentarioDAO.contarTodos();
    }
    
    public long contarReportes() {
        return reporteDAO.contarTodos();
    }
    
    public double obtenerMontoTotalDonaciones() {
        return asignacionDAO.obtenerMontoTotalDonaciones();
    }
    
    // ==================== MÉTODOS PRIVADOS DE GENERACIÓN DE DATOS ====================
    
    private Map<String, Object> generarDatosReporteGeneral() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("totalAnimales", contarAnimales());
        datos.put("totalDonantes", contarDonantes());
        datos.put("totalVoluntarios", contarVoluntarios());
        datos.put("totalAsignaciones", contarAsignaciones());
        datos.put("totalComentarios", contarComentarios());
        datos.put("montoTotalDonaciones", obtenerMontoTotalDonaciones());
        datos.put("fechaGeneracion", LocalDateTime.now());
        return datos;
    }
    
    private Map<String, Object> generarDatosReporteDonaciones() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("totalDonantes", contarDonantes());
        datos.put("totalAsignaciones", contarAsignaciones());
        datos.put("montoTotal", obtenerMontoTotalDonaciones());
        datos.put("asignacionesPendientes", asignacionDAO.contarPorEstado("Pendiente"));
        datos.put("asignacionesEntregadas", asignacionDAO.contarPorEstado("Entregado"));
        return datos;
    }
    
    private Map<String, Object> generarDatosReporteAnimales() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("totalAnimales", contarAnimales());
        datos.put("animalesActivos", contarAnimalesPorEstado("Activo"));
        datos.put("animalesEnTratamiento", contarAnimalesPorEstado("En tratamiento"));
        datos.put("animalesCriticos", contarAnimalesPorEstado("Crítico"));
        return datos;
    }
    
    private Map<String, Object> generarDatosReporteVoluntarios() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("totalVoluntarios", contarVoluntarios());
        datos.put("voluntariosActivos", voluntarioDAO.contarPorEstado("ACTIVO"));
        datos.put("totalComentarios", contarComentarios());
        datos.put("comentariosSinRespuesta", comentarioDAO.contarPorEstado("NUEVO"));
        return datos;
    }
    
    // ==================== INICIALIZACIÓN DE DATOS ====================
    
    private void inicializarDatosPrueba() {
        if (animalDAO.contarTodos() == 0) {
            crearAnimalesIniciales();
        }
        if (donanteDAO.contarTodos() == 0) {
            crearDonantesIniciales();
        }
        if (asignacionDAO.contarTodas() == 0) {
            crearAsignacionesIniciales();
        }
        if (voluntarioDAO.contarTodos() == 0) {
            crearVoluntariosIniciales();
        }
        if (comentarioDAO.contarTodos() == 0) {
            crearComentariosIniciales();
        }
        if (reporteDAO.contarTodos() == 0) {
            crearReportesIniciales();
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
    
    private void crearVoluntariosIniciales() {
        List<String> nombres = Arrays.asList(
            "Juan Pérez", "Ana Gómez", "Luis Martínez", "María López", "Carlos Ruiz",
            "Sofía Díaz", "Andrés Torres", "Lucía Fernández", "Miguel Castillo", "Laura Mendoza"
        );
        
        String[] especialidades = {"CUIDADO_ANIMALES", "VETERINARIA", "ADMINISTRACION", "LIMPIEZA"};
        
        for (int i = 0; i < nombres.size(); i++) {
            String[] nombreCompleto = nombres.get(i).split(" ");
            Voluntario voluntario = new Voluntario(nombreCompleto[0], nombreCompleto[1], 
                    nombreCompleto[0].toLowerCase() + "@voluntarios.com", "DOC" + (100 + i));
            voluntario.setEspecialidad(especialidades[i % especialidades.length]);
            voluntario.setTelefono("555-" + (1000 + i));
            voluntario.setHorasVoluntariado(i * 10);
            voluntarioDAO.guardar(voluntario);
        }
    }
    
    private void crearComentariosIniciales() {
        try {
            crearComentario(1L, "Sugerencia de mejora", "Sería bueno tener más horarios disponibles para el cuidado de animales", "SUGERENCIA");
            crearComentario(2L, "Reporte de actividad", "Esta semana ayudé con la limpieza y alimentación de 5 gatos", "REPORTE");
            crearComentario(3L, "Felicitación", "Excelente trabajo del equipo veterinario con Luna", "FELICITACION");
            crearComentario(4L, "Pregunta sobre protocolo", "¿Cuál es el protocolo para animales críticos?", "PREGUNTA");
            crearComentario(5L, "Disponibilidad", "Estaré disponible los fines de semana este mes", "REPORTE");
        } catch (Exception e) {
            System.err.println("Error creando comentarios iniciales: " + e.getMessage());
        }
    }
    
    private void crearReportesIniciales() {
        generarReporte("Reporte General Mensual", "GENERAL", "Reporte completo de actividades del mes", "Sistema");
        generarReporte("Reporte de Donaciones", "DONACIONES", "Análisis de donaciones recibidas", "Sistema");
        generarReporte("Estado de Animales", "ANIMALES", "Resumen del estado de salud de todos los animales", "Sistema");
        generarReporte("Actividad de Voluntarios", "VOLUNTARIOS", "Reporte de participación de voluntarios", "Sistema");
    }
}