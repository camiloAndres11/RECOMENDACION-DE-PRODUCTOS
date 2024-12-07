package uptc.edu.co.controlador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import uptc.edu.co.modelo.Arista;
import uptc.edu.co.modelo.Nodo;

public class Grafo {
    private List<Nodo> nodos;
    private Map<Nodo, List<Arista>> adyacencias;

    public Grafo() {
        this.nodos = new ArrayList<>();
        this.adyacencias = new HashMap<>();
    }

    // Método para agregar un nodo al grafo
    public void agregarNodo(Nodo n) {
        if (!adyacencias.containsKey(n)) {
            this.nodos.add(n);
            adyacencias.put(n, new ArrayList<>());
        }
    }

    // Método para agregar una arista entre dos nodos
    private void agregarAdyacencia(Nodo n1, Nodo n2, int peso) {
        if (n1.getId() != n2.getId()) {  // Asegurarse de no agregar aristas hacia el mismo nodo
            Arista arista1 = new Arista(n1, n2, peso);
            Arista arista2 = new Arista(n2, n1, peso);  // Arista inversa para la bidirección
            adyacencias.computeIfAbsent(n1, k -> new ArrayList<>()).add(arista1);
            adyacencias.computeIfAbsent(n2, k -> new ArrayList<>()).add(arista2);
        }
    }

   // Método para construir el grafo a partir de la lista de nodos
public void construirGrafo(List<Nodo> nodos) {
    // Agregar nodos a la lista del grafo
    for (Nodo nodo : nodos) {
        agregarNodo(nodo);
    }

    // Construir las adyacencias
    for (int i = 0; i < nodos.size(); i++) {
        for (int j = i + 1; j < nodos.size(); j++) {
            Nodo n1 = nodos.get(i); // Nodo actual
            Nodo n2 = nodos.get(j); // Nodo(s) posterior(es)

            boolean categoriasCoinciden = false;

            // Comparar categorías
            if (n1.getCategorias() != null && n2.getCategorias() != null) {
                for (String c1 : n1.getCategorias()) {
                    for (String c2 : n2.getCategorias()) {
                        if (c1.equalsIgnoreCase(c2)) {
                            categoriasCoinciden = true;
                            break;  // Salir del bucle cuando se encuentra una coincidencia
                        }
                    }
                    if (categoriasCoinciden) {
                        break;  // Salir del bucle cuando se encuentra una coincidencia
                    }
                }
            }

            // Si las categorías coinciden, se calcula la diferencia de compras
            if (categoriasCoinciden) {
                int diferencia = Math.abs(n1.getNumeroCompras() - n2.getNumeroCompras());

                // Solo agregar la arista si la diferencia de compras es menor o igual a 200
                if (diferencia <= 200) {
                    agregarAdyacencia(n1, n2, diferencia);
                }
            }
        }
    }
}
    

    // Método para imprimir el grafo
    public void imprimirGrafo() {
        adyacencias.forEach((nodo, listaAristas) -> {
            System.out.println("Nodo: " + nodo.getNombre());
            listaAristas.forEach(arista -> {
                System.out.println("arista -> " + arista.getDestino().getNombre() + " (Peso: " + arista.getPeso() + ")" );
            });
        });
    }

    // Método para obtener los nodos del grafo
    public List<Nodo> getNodos() {
        return nodos;
    }

    // Método para obtener las adyacencias del grafo
    public Map<Nodo, List<Arista>> getAdyacencias() {
        return adyacencias;
    }

    // Método para buscar un nodo por su nombre
    public Nodo buscarNodoPorNombre(String nombre) {
        System.out.println("Producto nodo para buscar recomendacioens: "+ nombre);
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                System.out.println("Id del nodo encontrado por nombre: " + nodo.getId());
                return nodo;
            }
        }

        return null;
    }
    public Nodo buscarNodoPorId(int id){
        for (Nodo nodo: nodos){
            if(nodo.getId()==(id)){
                return nodo;
            }
        }
        return null;
    }

/*
    // Método para obtener recomendaciones de nodos basados en la adyacencia
    public List<String> recomendaciones(String nombre) {
        List<String> recomendados = new ArrayList<>();
        Nodo nodo = buscarNodoPorNombre(nombre);
        if (nodo != null) {
            for (Arista arista : adyacencias.get(nodo)) {
                recomendados.add(arista.getDestino().getNombre());
            }
        }
        return recomendados;
    }

 */
public List<Nodo> recomendacionesNodos(String nombre) {
    List<Nodo> recomendados = new ArrayList<>();
    Nodo nodo = buscarNodoPorNombre(nombre);
    System.out.println("Nodo del producto: " + nodo.getNombre() + " id del producto: " + nodo.getId());
    Nodo nodo1 = buscarNodoPorId(nodo.getId());
    if (nodo1 != null) {
        // Obtener las aristas del nodo y ordenarlas por peso ascendente
        List<Arista> aristas = new ArrayList<>(adyacencias.get(nodo1));
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Agregar los nodos destino a la lista de recomendados
        for (Arista arista : aristas) {
            if (recomendados.size() >= 10) {
                break;
            }
            recomendados.add(arista.getDestino());
        }
    }
    return recomendados;
}


    // Método para obtener recomendaciones de nodos basados en la adyacencia y el peso
public List<String> recomendaciones(String nombre) {
    List<String> recomendados = new ArrayList<>();
    Nodo nodo = buscarNodoPorNombre(nombre);
    System.out.println("Node del producto: " + nodo.getNombre()+"id del producto: "+ nodo.getId());
    Nodo  nodo1 = buscarNodoPorId(nodo.getId());
    if (nodo1 != null) {
        // Obtener las aristas del nodo y ordenarlas por peso ascendente
        List<Arista> aristas = new ArrayList<>(adyacencias.get(nodo1));
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Agregar los nombres de los nodos destino a la lista de recomendados
        for (Arista arista : aristas) {
            if (recomendados.size() >= 10) {
                break;
            }
            recomendados.add(arista.getDestino().getNombre());
        }
    }
    return recomendados;
}





    public void construirGrafoParaUnProducto(Nodo nodo, List<Nodo> nodos) {
    // Agregar el nodo específico al grafo
    agregarNodo(nodo);


    // Construir las adyacencias solo para el nodo específico
    for (Nodo n2 : nodos) {
        if (nodo.equals(n2)) {
            continue; // Saltar el mismo nodo
        }

        boolean categoriasCoinciden = false;

        // Comparar categorías
        if (nodo.getCategorias() != null && n2.getCategorias() != null) {
            for (String c1 : nodo.getCategorias()) {
                for (String c2 : n2.getCategorias()) {
                    if (c1.equalsIgnoreCase(c2)) {
                        categoriasCoinciden = true;
                        break;  // Salir del bucle cuando se encuentra una coincidencia
                    }
                }
                if (categoriasCoinciden) {
                    break;  // Salir del bucle cuando se encuentra una coincidencia
                }
            }
        }

        // Si las categorías coinciden, se calcula la diferencia de compras
        if (categoriasCoinciden) {
            int diferencia = Math.abs(nodo.getNumeroCompras() - n2.getNumeroCompras());

            // Solo agregar la arista si la diferencia de compras es menor o igual a 200
            if (diferencia <= 200) {
                agregarAdyacencia(nodo, n2, diferencia);
            }
        }
    }
    //Graficar el grafo cada que se construyó y guardar un json con ese grafo
}

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    public void setAdyacencias(Map<Nodo, List<Arista>> adyacencias) {
        this.adyacencias = adyacencias;
    }
}
