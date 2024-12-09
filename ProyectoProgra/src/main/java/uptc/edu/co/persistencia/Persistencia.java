package uptc.edu.co.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import uptc.edu.co.controlador.Grafo;
import uptc.edu.co.modelo.Nodo;


public class Persistencia {
    private static final Gson gson = new Gson();

    // Método para cargar los nodos desde el JSON
    public static List<Nodo> cargarNodos(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            // Cargar el objeto raíz del JSON
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Extraer el array "nodos" y convertirlo a una lista de Nodo
            JsonArray nodosArray = jsonObject.getAsJsonArray("nodos");
            Type tipoListaNodos = new TypeToken<List<Nodo>>() {}.getType();
            return gson.fromJson(nodosArray, tipoListaNodos);
        }
    }

    // Método para cargar el grafo
    public static Grafo cargarGrafo(String filePath) throws IOException {
        List<Nodo> nodos = cargarNodos(filePath);  // Cargamos los nodos desde el JSON
        Grafo grafo = new Grafo();
        grafo.construirGrafo(nodos);  // Construimos el grafo usando los nodos
        return grafo;
    }

    // Método para guardar el grafo 
    public static void guardarGrafo(String filePath, Grafo grafo) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(grafo, writer);
        }
    }

    public static void agregarNodo(String filePath, Nodo nuevoNodo) throws IOException {
        JsonObject jsonObject;
        try (Reader reader = new FileReader(filePath)) {
            jsonObject = gson.fromJson(reader, JsonObject.class);
        } catch (FileNotFoundException e) {
            jsonObject = new JsonObject();
        }

        JsonArray nodosArray = jsonObject.getAsJsonArray("nodos");
        if (nodosArray == null) {
            nodosArray = new JsonArray();
            jsonObject.add("nodos", nodosArray);
        }

        // Convertir el nuevo nodo a JSON y añadirlo al array
        nodosArray.add(gson.toJsonTree(nuevoNodo));

        // Guardar el archivo actualizado
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(jsonObject, writer);
        }
    }

    public static void sobrescribirArchivoJson(String filePath, List<Nodo> nodos) throws IOException {
        // Crear un objeto JSON raíz
        JsonObject jsonObject = new JsonObject();

        // Convertir la lista de nodos a un array JSON
        Type tipoListaNodos = new TypeToken<List<Nodo>>() {}.getType();
        JsonArray nodosArray = gson.toJsonTree(nodos, tipoListaNodos).getAsJsonArray();

        // Añadir el array de nodos al objeto JSON raíz
        jsonObject.add("nodos", nodosArray);

        // Escribir el objeto JSON en el archivo
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(jsonObject, writer);
        }
    }



}

