package uptc.edu.co.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

public class ControladorVista {

    private static final String FILE_PATH = "src/main/java/uptc/edu/co/persistencia/Productos10k.json";

    Grafo grafo = new Grafo();

    public List<Nodo> listadoNodos() {
        List<Nodo> nodos = new ArrayList<>();
        try {
            nodos = Persistencia.cargarNodos(FILE_PATH);
            // Construir el grafo a partir de los nodos
            grafo.setNodos(nodos);
            System.out.println("Nodos creados: " + nodos.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodos;
    }

    public List<Nodo> recomendaciones(String nombreProducto) {
        Nodo nodo = grafo.buscarNodoPorNombre(nombreProducto);
        if (nodo != null) {
            List<Nodo> nodos = listadoNodos();
            grafo.construirGrafoParaUnProducto(nodo, nodos);
            return grafo.recomendacionesNodos(nombreProducto); // Modificado para devolver una lista de Nodos
        }
        return new ArrayList<>();
    }
}
