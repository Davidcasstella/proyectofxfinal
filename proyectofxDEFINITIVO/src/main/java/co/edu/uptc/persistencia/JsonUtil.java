package co.edu.uptc.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();
    
    private static final String DATA_DIRECTORY = "data";
    
    static {
        // Crear directorio de datos si no existe
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    /**
     * Guarda una lista de objetos en un archivo JSON
     */
    public static <T> void guardarLista(List<T> lista, String nombreArchivo) {
        try {
            File archivo = new File(DATA_DIRECTORY, nombreArchivo);
            FileWriter writer = new FileWriter(archivo);
            gson.toJson(lista, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar archivo " + nombreArchivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carga una lista de objetos desde un archivo JSON
     */
    public static <T> List<T> cargarLista(String nombreArchivo, Type tipoLista) {
        try {
            File archivo = new File(DATA_DIRECTORY, nombreArchivo);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }
            
            FileReader reader = new FileReader(archivo);
            List<T> lista = gson.fromJson(reader, tipoLista);
            reader.close();
            
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error al cargar archivo " + nombreArchivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Convierte un objeto a JSON string
     */
    public static String toJson(Object objeto) {
        return gson.toJson(objeto);
    }
    
    /**
     * Convierte un JSON string a objeto
     */
    public static <T> T fromJson(String json, Class<T> clase) {
        try {
            return gson.fromJson(json, clase);
        } catch (JsonSyntaxException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifica si un archivo existe
     */
    public static boolean archivoExiste(String nombreArchivo) {
        File archivo = new File(DATA_DIRECTORY, nombreArchivo);
        return archivo.exists();
    }
    
    /**
     * Elimina un archivo
     */
    public static boolean eliminarArchivo(String nombreArchivo) {
        File archivo = new File(DATA_DIRECTORY, nombreArchivo);
        return archivo.delete();
    }
    
    /**
     * Obtiene el directorio de datos
     */
    public static String getDataDirectory() {
        return DATA_DIRECTORY;
    }
}