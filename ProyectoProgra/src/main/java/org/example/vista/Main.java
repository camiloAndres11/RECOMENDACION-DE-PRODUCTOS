package org.example.vista;

import java.util.ArrayList;
import java.util.List;

import org.example.controlador.Grafo;
import org.example.modelo.Nodo;

public class Main {

    public static void main(String[] args) {

        System.out.println("Introduciendo grafos..");

        List<String> categoria1 = crearListaDeCategorias("Dulces", "Galletas");
        List<String> categoria2 = crearListaDeCategorias("Bebida", "Gaseosa");
        List<String> categoria3 = crearListaDeCategorias("Electrodomestico", "Hogar");
        List<String> categoria4 = crearListaDeCategorias("dulces");


        //Creacion de productos (por ahora al ser pruebas se crearán manualmente)
        Nodo nodo1 = new Nodo("Oreo", 400, "Galletas de chocolate", categoria1);
        Nodo nodo2 = new Nodo("Glasitas", 350, "Galletas de marca Glasitas", categoria1);
        Nodo nodo3 = new Nodo("Cocacola", 900, "Gaseosa cocacola", categoria2);
        Nodo nodo4 = new Nodo("Pepsi", 800, "Copia de cocacola", categoria2);
        Nodo nodo5 = new Nodo("Nevera", 1200, "Nevera para la casa", categoria3);
        Nodo nodo6 = new Nodo("Milo", 400, "Galletas todas feas de milo", categoria1);
        Nodo nodo7 = new Nodo("Gomas", 400, "Gomitas en forma de osos", categoria4);

        Grafo grafo = new Grafo();

        //Agregar los nodos al grafo
        grafo.agregarNodo(nodo1);
        grafo.agregarNodo(nodo2);
        grafo.agregarNodo(nodo3);
        grafo.agregarNodo(nodo4);
        grafo.agregarNodo(nodo5);
        grafo.agregarNodo(nodo6);
        grafo.agregarNodo(nodo7);


        //Construccion del grafo
        grafo.construccionGrafo();

        grafo.imprimirGrafo();

        System.out.println("Recomendaciones de gomas");

        System.out.println("-> " + grafo.recomendaciones("Gomas"));

    }

    // Método para generar listas de categorías
    public static List<String> crearListaDeCategorias(String... categorias) {
        List<String> listaCategorias = new ArrayList<>();
        for (String categoria : categorias) {
            listaCategorias.add(categoria);
        }
        return listaCategorias;
    }
    
}
