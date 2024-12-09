package uptc.edu.co.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Nodo{
    private String nombre;
    private int numeroCompras;
    private String descripcion;
    private int id;
    private List<String> categoria;
    private List<Nodo> adyacencias;
    private double precio;

    public Nodo(String nombre, int numeroCompras, String descripcion, List<String> categoria, int id, double precio){
        this.nombre = nombre;
        this.numeroCompras = numeroCompras;
        this.descripcion = descripcion;
        this.categoria = categoria != null ? categoria : new ArrayList<>(); // Aseguramos que la lista no sea null
        this.adyacencias = new ArrayList<>();
        this.id = id;
        this.precio = precio;
    }
    public Nodo(){

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumeroCompras(int numeroCompras) {
        this.numeroCompras = numeroCompras;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoria(List<String> categoria) {
        this.categoria = categoria;
    }

    public void setAdyacencias(List<Nodo> adyacencias) {
        this.adyacencias = adyacencias;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
        return id == nodo.id;  
    }

    @Override
    public int hashCode() {
    return Objects.hash(id);  // Usar id para calcular el hash
}

    public int getId() {
        return id;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }
}
