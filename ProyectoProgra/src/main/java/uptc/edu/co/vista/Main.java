package uptc.edu.co.vista;

import uptc.edu.co.controlador.Grafo;
import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "C:\\Users\\camil\\IdeaProjects\\ProyectoGrafosProgra3\\ProyectoProgra\\src\\main\\java\\uptc\\edu\\co\\persistencia\\Productos.json";

    public static void main(String[] args) {
        // Crear el objeto Grafo
        Grafo grafo = new Grafo();

        // Cargar los productos desde el archivo JSON
        List<Nodo> nodos = null;
        try {
            nodos = Persistencia.readJSON(FILE_PATH); // Leer los productos como una lista de Nodo
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
            return;
        }

        // Construir el grafo con los nodos cargados
        grafo.construirGrafo(nodos);

        // Menú de opciones
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Mostrar Grafo");
            System.out.println("2. Mostrar Recomendaciones");
            System.out.println("3. Guardar Grafo");
            System.out.println("4. Cargar Grafo");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Mostrar el grafo
                    grafo.imprimirGrafo();
                    break;

                case 2:
                    // Mostrar recomendaciones para "Gomas"
                    List<String> recomendaciones = grafo.recomendaciones("Gomas");
                    System.out.println("Recomendaciones: " + recomendaciones);
                    break;

                case 3:
                    // Guardar el grafo en un archivo JSON
                    System.out.print("Introduce el nombre del archivo para guardar el grafo (por ejemplo, 'grafo.json'): ");
                    String nombreArchivoGuardar = scanner.nextLine();
                    grafo.guardarGrafo(nombreArchivoGuardar);
                    System.out.println("Grafo guardado exitosamente en '" + nombreArchivoGuardar + "'.");
                    break;

                case 4:
                    // Cargar el grafo desde un archivo JSON
                    System.out.print("Introduce el nombre del archivo para cargar el grafo (por ejemplo, 'grafo.json'): ");
                    String nombreArchivoCargar = scanner.nextLine();
                    Grafo grafoCargado = Grafo.cargarGrafo(FILE_PATH);
                    if (grafoCargado != null) {
                        grafo = grafoCargado; // Actualizar el grafo con el cargado
                        System.out.println("Grafo cargado exitosamente desde '" + nombreArchivoCargar + "'.");
                    } else {
                        System.out.println("Error al cargar el grafo.");
                    }
                    break;

                case 5:
                    flag = false;
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, elige una opción válida.");
            }
        }

        scanner.close();
    }
}

