package uptc.edu.co.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

public class ControladorVista {

    private static final String FILE_PATH = "ProyectoProgra\\src\\main\\java\\uptc\\edu\\co\\persistencia\\Productos.json";

    Grafo grafo = new Grafo();

    public List<Nodo> listadoNodos() {
        List<Nodo> nodos = new ArrayList<>();
        try{
        nodos = Persistencia.cargarNodos(FILE_PATH);
         // Construir el grafo a partir de los nodos
        grafo.construirGrafo(nodos);
        } catch (IOException e) {
        }
        return  nodos;
    }
    
}
