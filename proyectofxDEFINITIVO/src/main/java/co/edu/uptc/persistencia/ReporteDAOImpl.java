package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Reporte;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReporteDAOImpl implements ReporteDAO {
    
    private static final String ARCHIVO_REPORTES = "reportes.json";
    private static final Type TIPO_LISTA_REPORTES = new TypeToken<List<Reporte>>(){}.getType();
    
    private List<Reporte> reportes;
    private Long siguienteId;
    
    public ReporteDAOImpl() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        reportes = JsonUtil.cargarLista(ARCHIVO_REPORTES, TIPO_LISTA_REPORTES);
        siguienteId = reportes.stream()
                .mapToLong(reporte -> reporte.getId() != null ? reporte.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }
    
    private void guardarDatos() {
        JsonUtil.guardarLista(reportes, ARCHIVO_REPORTES);
    }
    
    @Override
    public Reporte guardar(Reporte reporte) {
        if (reporte.getId() == null) {
            reporte.setId(siguienteId++);
            reportes.add(reporte);
        } else {
            // Actualizar reporte existente
            for (int i = 0; i < reportes.size(); i++) {
                if (reportes.get(i).getId().equals(reporte.getId())) {
                    reportes.set(i, reporte);
                    break;
                }
            }
        }
        guardarDatos();
        return reporte;
    }
    
    @Override
    public Optional<Reporte> buscarPorId(Long id) {
        return reportes.stream()
                .filter(reporte -> reporte.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Reporte> obtenerTodos() {
        return List.copyOf(reportes);
    }
    
    @Override
    public List<Reporte> buscarPorTipo(String tipoReporte) {
        return reportes.stream()
                .filter(reporte -> reporte.getTipoReporte().equalsIgnoreCase(tipoReporte))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reporte> buscarPorEstado(String estado) {
        return reportes.stream()
                .filter(reporte -> reporte.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reporte> buscarPorGenerador(String generadoPor) {
        return reportes.stream()
                .filter(reporte -> reporte.getGeneradoPor().equalsIgnoreCase(generadoPor))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reporte> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reportes.stream()
                .filter(reporte -> reporte.getFechaGeneracion() != null &&
                        !reporte.getFechaGeneracion().isBefore(fechaInicio) &&
                        !reporte.getFechaGeneracion().isAfter(fechaFin))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reporte> obtenerRecientes(int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return reportes.stream()
                .filter(reporte -> reporte.getFechaGeneracion() != null &&
                        reporte.getFechaGeneracion().isAfter(fechaLimite))
                .sorted((r1, r2) -> r2.getFechaGeneracion().compareTo(r1.getFechaGeneracion()))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = reportes.removeIf(reporte -> reporte.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existe(Long id) {
        return reportes.stream().anyMatch(reporte -> reporte.getId().equals(id));
    }
    
    @Override
    public Long obtenerSiguienteId() {
        return siguienteId;
    }
    
    @Override
    public boolean actualizar(Reporte reporte) {
        if (reporte.getId() == null) {
            return false;
        }
        
        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).getId().equals(reporte.getId())) {
                reportes.set(i, reporte);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long contarTodos() {
        return reportes.size();
    }
    
    @Override
    public long contarPorTipo(String tipoReporte) {
        return reportes.stream()
                .filter(reporte -> reporte.getTipoReporte().equalsIgnoreCase(tipoReporte))
                .count();
    }
    
    @Override
    public long contarPorEstado(String estado) {
        return reportes.stream()
                .filter(reporte -> reporte.getEstado().equalsIgnoreCase(estado))
                .count();
    }
}