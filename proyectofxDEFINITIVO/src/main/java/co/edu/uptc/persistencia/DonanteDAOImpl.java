package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Donante;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DonanteDAOImpl implements DonanteDAO {
    
    private static final String ARCHIVO_DONANTES = "donantes.json";
    private static final Type TIPO_LISTA_DONANTES = new TypeToken<List<Donante>>(){}.getType();
    
    private List<Donante> donantes;
    private Long siguienteId;
    
    public DonanteDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        donantes = JsonUtil.cargarLista(ARCHIVO_DONANTES, TIPO_LISTA_DONANTES);
        siguienteId = donantes.stream()
                .mapToLong(donante -> donante.getId() != null ? donante.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(donantes, ARCHIVO_DONANTES);
    }
    
    @Override
    public Donante guardar(Donante donante) {
        if (donante.getId() == null) {
            donante.setId(siguienteId++);
            donantes.add(donante);
        } else {
            // Actualizar donante existente
            for (int i = 0; i < donantes.size(); i++) {
                if (donantes.get(i).getId().equals(donante.getId())) {
                    donantes.set(i, donante);
                    break;
                }
            }
        }
        guardarDatos();
        return donante;
    }
    
    @Override
    public Optional<Donante> buscarPorId(Long id) {
        return donantes.stream()
                .filter(donante -> donante.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Donante> obtenerTodos() {
        return List.copyOf(donantes);
    }
    
    @Override
    public Optional<Donante> buscarPorDocumento(String documento) {
        return donantes.stream()
                .filter(donante -> donante.getDocumento().equals(documento))
                .findFirst();
    }
    
    @Override
    public List<Donante> buscarPorNombre(String nombre) {
        return donantes.stream()
                .filter(donante -> donante.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Donante> buscarPorEmail(String email) {
        return donantes.stream()
                .filter(donante -> donante.getEmail() != null && donante.getEmail().equals(email))
                .findFirst();
    }
    
    @Override
    public List<Donante> obtenerDonantesDinero() {
        return donantes.stream()
                .filter(Donante::isDinero)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Donante> obtenerDonantesAlimento() {
        return donantes.stream()
                .filter(Donante::isAlimento)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Donante> obtenerDonantesMedicamento() {
        return donantes.stream()
                .filter(Donante::isMedicamento)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = donantes.removeIf(donante -> donante.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return donantes.stream().anyMatch(donante -> donante.getId().equals(id));
    }
    
    @Override
    public boolean existeDocumento(String documento) {
        return donantes.stream().anyMatch(donante -> donante.getDocumento().equals(documento));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Donante donante) {
        if (donante.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < donantes.size(); i++) {
            if (donantes.get(i).getId().equals(donante.getId())) {
                donantes.set(i, donante);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodos() {
        return donantes.size();
    }
    
    @Override
    public List<Donante> obtenerDonantesActivos() {
        LocalDate hace30Dias = LocalDate.now().minusDays(30);
        return donantes.stream()
                .filter(donante -> donante.getUltimaDonacion() != null && 
                        donante.getUltimaDonacion().isAfter(hace30Dias))
                .collect(Collectors.toList());
    }
}