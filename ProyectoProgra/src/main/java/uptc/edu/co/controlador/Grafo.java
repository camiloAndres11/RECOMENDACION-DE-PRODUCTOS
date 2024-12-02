package uptc.edu.co.controlador;

import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

import java.io.*;
import java.util.*;

public class Grafo {

    private Map<Nodo, List<Nodo>> adyacencias;

    public Grafo() {
        adyacencias = new HashMap<>();
    }

    public void agregarNodo(Nodo nodo) {
        if (!adyacencias.containsKey(nodo)) {
            adyacencias.put(nodo, new ArrayList<>());
        }
    }

    public void agregarAdyacencia(Nodo nodo1, Nodo nodo2) {
        adyacencias.get(nodo1).add(nodo2);
        adyacencias.get(nodo2).add(nodo1); // si quieres grafo no dirigido
    }

    // Método para construir el grafo (conectar nodos)
    public void construirGrafo(List<Nodo> nodos) {
        // Primero agregar los nodos al grafo
        for (Nodo nodo : nodos) {
            agregarNodo(nodo);
        }

        // Conectar nodos que tienen categorías comunes o tienen otras características similares
        for (Nodo nodo1 : nodos) {
            for (Nodo nodo2 : nodos) {
                if (!nodo1.equals(nodo2)) {
                    // Conectar por categorías comunes
                    if (tienenCategoriaComún(nodo1, nodo2)) {
                        agregarAdyacencia(nodo1, nodo2);
                    }
                    // Conectar por descripción similar
                    else if (tienenDescripcionSimilar(nodo1, nodo2)) {
                        agregarAdyacencia(nodo1, nodo2);
                    }
                }
            }
        }
    }

    private boolean tienenCategoriaComún(Nodo nodo1, Nodo nodo2) {
        for (String categoria1 : nodo1.getCategorias()) {
            for (String categoria2 : nodo2.getCategorias()) {
                if (categoria1.equals(categoria2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tienenDescripcionSimilar(Nodo nodo1, Nodo nodo2) {
        String descripcion1 = nodo1.getDescripcion().toLowerCase();
        String descripcion2 = nodo2.getDescripcion().toLowerCase();

        // Ejemplo de comparación: si una descripción contiene la palabra clave "chocolate"
        if (descripcion1.contains("chocolate") && descripcion2.contains("chocolate")) {
            return true;
        }

        return false;
    }

    public void imprimirGrafo() {
        for (Map.Entry<Nodo, List<Nodo>> entry : adyacencias.entrySet()) {
            Nodo nodo = entry.getKey();
            List<Nodo> adj = entry.getValue();
            System.out.println("Nodo: " + nodo.getNombre() + " -> Adyacentes: " + adj);
        }
    }

    // Métodos para guardar y cargar el grafo desde un archivo (si es necesario)
    public void guardarGrafo(String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(adyacencias);
            System.out.println("Grafo guardado en: " + path);
        } catch (IOException e) {
            System.out.println("Error al guardar el grafo: " + e.getMessage());
        }
    }

    public static Grafo cargarGrafo(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            Map<Nodo, List<Nodo>> adyacencias = (Map<Nodo, List<Nodo>>) in.readObject();
            Grafo grafo = new Grafo();
            grafo.adyacencias = adyacencias;
            System.out.println("Grafo cargado desde: " + path);
            return grafo;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el grafo: " + e.getMessage());
            return null;
        }
    }

    public List<String> recomendaciones(String producto) {
        List<String> recomendaciones = new ArrayList<>();
        for (Nodo nodo : adyacencias.keySet()) {
            if (nodo.getNombre().equalsIgnoreCase(producto)) {
                for (Nodo adyacente : adyacencias.get(nodo)) {
                    recomendaciones.add(adyacente.getNombre());
                }
            }
        }
        return recomendaciones;
    }
}



