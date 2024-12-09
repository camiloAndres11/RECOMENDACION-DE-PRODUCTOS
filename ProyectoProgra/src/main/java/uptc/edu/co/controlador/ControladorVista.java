package uptc.edu.co.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

public class ControladorVista {


    private static final String FILE_PATH = "ProyectoProgra\\src\\main\\java\\uptc\\edu\\co\\persistencia\\Productos10k.json";

    Persistencia persistencia = new Persistencia();
    Grafo grafo = new Grafo();
    List<Nodo> nodos = new ArrayList<>();

    public List<Nodo> listadoNodos() {
        try {
            nodos = Persistencia.cargarNodos(FILE_PATH);
            // Construir el grafo a partir de los nodos
            grafo.setNodos(nodos);
          
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodos;
    }

    public void editarNodoCompra(Nodo nodo) throws IOException {
        for(Nodo n: nodos){
            if(nodo.getId() == n.getId()){
                
                n.setNumeroCompras(n.getNumeroCompras()+1);
               
                persistencia.actualizarNodoEnArchivoJson(FILE_PATH, nodo.getId(), nodo);
               
            }
        }
    }

    public Nodo buscarNodoPorNombre(String nombre){
        return grafo.buscarNodoPorNombre(nombre);
    }

    public List<Nodo> recomendaciones(Nodo nodoR) {
        if (nodoR != null) {
            List<Nodo> nodos = listadoNodos();
            grafo.construirGrafoParaUnProducto(nodoR, nodos);
            return grafo.recomendacionesNodos(nodoR); // Modificado para devolver una lista de Nodos
        }
        return new ArrayList<>();
    }
    public String returnPath (){
        return FILE_PATH;
    }

}
