package uptc.edu.co.modelo;
import java.util.ArrayList;
import java.util.List;

public class Nodo{
    private String nombre;
    private int numeroCompras;
    private String descripcion;
    private List<String> categoria;
    private List<Nodo> adyacencias;

    public Nodo(String nombre, int numeroCompras, String descripcion, List<String> categoria){
        this.nombre = nombre;
        this.numeroCompras = numeroCompras;
        this.descripcion = descripcion;
        this.categoria = categoria != null ? categoria : new ArrayList<>(); // Aseguramos que la lista no sea null
        this.adyacencias = new ArrayList<>();
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public List<String> getCategorias(){
        return categoria;
    }

    public int getNumeroCompras(){
        return numeroCompras;
    }

    public List<Nodo> getAdyacencias(){
        return adyacencias;
    }

    public void añadirNodoVecino(Nodo nodo){
        adyacencias.add(nodo);
    }

    public String toString(){
        return nombre +" "+ numeroCompras +" "+ descripcion +" "+ categoria + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodo nodo = (Nodo) o;
        return nombre.equals(nodo.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

}
