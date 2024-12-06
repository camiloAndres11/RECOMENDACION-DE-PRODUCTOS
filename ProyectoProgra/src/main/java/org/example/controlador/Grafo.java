package org.example.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.modelo.Nodo;

public class Grafo {
    private List<Nodo> nodos;
    private Map<Nodo, List<Nodo>> adyacencias;

    public Grafo() {
        this.nodos = new ArrayList<>();
        this.adyacencias = new HashMap<>();
    }

    public void agregarNodo(Nodo n) {
        if(!adyacencias.containsKey(n)) { //si el nodo no existe
            this.nodos.add(n); //añade el nodo a la lista de nodos
            adyacencias.put(n, new ArrayList<>()); //añade el nodo a la lista de adyacencias
        }
    }

    public void agregarAdyacencia(Nodo n1, Nodo n2) {
        // Agregar n2 como vecino de n1 y viceversa (grafo no dirigido)
        adyacencias.computeIfAbsent(n1, k -> new ArrayList<>()).add(n2);
        adyacencias.computeIfAbsent(n2, k -> new ArrayList<>()).add(n1);
        n1.añadirNodoVecino(n2);
        n2.añadirNodoVecino(n1);
    }

    public void construccionGrafo() {
        for (int i = 0; i < nodos.size(); i++) { // Recorre nodo a nodo
            for (int j = i + 1; j < nodos.size(); j++) { // Recorre los nodos siguientes al actual
                Nodo n1 = nodos.get(i); // Nodo actual
                Nodo n2 = nodos.get(j); // Nodo(s) posterior(es)

                boolean categoriasCoinciden = false;

                // Comparar categorías
                do {
                    if (n1.getCategoria() != null && n2.getCategoria() != null) {
                        for (String c1 : n1.getCategoria()) {
                            for (String c2 : n2.getCategoria()) {
                                if (c1.equalsIgnoreCase(c2)) {
                                    categoriasCoinciden = true;
                                }
                            }
                        }
                    }
                }while (categoriasCoinciden);

                    int diferencia = Math.abs(n1.getNumeroCompras() - n2.getNumeroCompras());

                    // Agregar la arista si la diferencia de compras es menor o igual a 200
                    if (diferencia <= 200) {
                        agregarAdyacencia(n1, n2);
                    }
                }
            }
        }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public Map<Nodo, List<Nodo>> getAdyacencias() {
        return adyacencias;
    }

    public void imprimirGrafo() {
        for (Map.Entry<Nodo, List<Nodo>> entry : adyacencias.entrySet()) {
            Nodo nodo = entry.getKey();
            List<Nodo> vecinos = entry.getValue();
            System.out.print(nodo.getNombre() + "-> ");
            for (Nodo vecino : vecinos) {
                System.out.print(vecino.getNombre() + " ");
            }
            System.out.println();
        }
    }

    public Nodo buscarNodoPorNombre(String nombre) {
        for (Map.Entry<Nodo, List<Nodo>> entry : adyacencias.entrySet()) {
            Nodo nodo = entry.getKey();
            if(nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    public List<String> recomendaciones (String nombre){
        List<String> recomendados = new ArrayList<>();
        Nodo nodo = buscarNodoPorNombre(nombre);
        for (Nodo vecino : nodo.getAdyacencias()) {
            recomendados.add(vecino.getNombre());
        }
        return recomendados;
    }

}
