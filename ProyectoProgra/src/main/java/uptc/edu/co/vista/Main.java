package uptc.edu.co.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uptc.edu.co.controlador.ControladorVista;
import uptc.edu.co.controlador.Grafo;
import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

import static uptc.edu.co.controlador.ControladorVista.*;

public class Main extends Application {
    private Stage primaryStage;


    ControladorVista controladorVista = new ControladorVista();
    Grafo miGrafo = new Grafo();

    // Suponiendo que el filepath es el siguiente
    //private static final String FILE_PATH = "src/main/java/uptc/edu/co/persistencia/Productos10k.json";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage; // Asignar el Stage a la variable de instancia

        // Título
        Label titulo = new Label("ShopSation");
        titulo.setId("titulo");  // ID para el CSS

        // Espacio de Busqueda
        TextField campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Productos, marcas y más...");
        campoBusqueda.setPrefWidth(300);
        campoBusqueda.setId("campoBusqueda");

        // ComboBox Categorías
        ComboBox<String> comboCategoria = new ComboBox<>();
        comboCategoria.getItems().addAll("Todo", "Ropa", "Alimentos", "Electrodomestico", "Juguetes");
        comboCategoria.setValue("Todo");

        // Botón para Buscar
        Button buscarButton = new Button("Buscar");
        buscarButton.setId("botonBuscar");

        // Crear un botón para agregar producto
        Button agregarProductoButton = new Button("Agregar Producto");
        agregarProductoButton.setId("agregarProductoButton");




        // HBox para la parte de búsqueda (campo de texto, ComboBox y botón)
        HBox hboxBusqueda = new HBox(10);
        hboxBusqueda.setAlignment(Pos.CENTER);
        hboxBusqueda.getChildren().addAll(titulo, campoBusqueda, comboCategoria, buscarButton, agregarProductoButton);
        hboxBusqueda.setId("barraSuperior");




        List<Nodo> productos = new ArrayList<>();
        productos.addAll(controladorVista.listadoNodos());

        //empezar a graficar el grafo grande en otro hilo porque si no se demora en cargar xd, por eso está comentado
        System.out.println("Se esta construyendo el gran grafo...");
        //miGrafo.construirGrafo(productos);

        // FlowPane para mostrar los productos y tener la barra para bajar
        FlowPane areaRecomendaciones = new FlowPane();
        areaRecomendaciones.setId("areaRecomendaciones");

        // FlowPane en un ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(areaRecomendaciones);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  // Barra para desplazarse verticalmente

        // Actualizar productos cuando cambie la categoría o se presione buscar
        actualizarRecomendaciones(areaRecomendaciones, comboCategoria.getValue(), productos);

        // Acción del botón de búsqueda que aparece al presionarlo
        buscarButton.setOnAction(e -> {
            String busqueda = campoBusqueda.getText().toLowerCase();
            String categoriaSeleccionada = comboCategoria.getValue();

            // Filtrar productos por búsqueda y categoría
            List<Nodo> productosFiltrados = new ArrayList<>();

            // Recorrer la lista de productos (supongamos que la lista se llama 'productos')
            for (Nodo nodo : productos) {
                boolean perteneceACategoria = categoriaSeleccionada.equals("Todo") ||
                        nodo.getCategorias().contains(categoriaSeleccionada);

                // Filtrar por búsqueda en el nombre y la categoría
                if (perteneceACategoria && nodo.getNombre().toLowerCase().contains(busqueda)) {
                    productosFiltrados.add(nodo);
                }
            }

            // Actualizar área de productos
            areaRecomendaciones.getChildren().clear();
            if (productosFiltrados.isEmpty()) {
                areaRecomendaciones.getChildren().add(new Label("No se encontraron productos."));
            } else {
                for (Nodo nodo : productosFiltrados) {
                    Label productoCard = new Label(nodo.getNombre()); // Muestra el nombre del producto

                    productoCard.getStyleClass().add("producto-card"); // Para el estilo

                    // Agregar evento al hacer click en el card
                    productoCard.setOnMouseClicked(event -> {
                        DetallesDeProducto ventanaDetalles = new DetallesDeProducto();
                        primaryStage.hide(); // Ocultar la ventana principal
                        ventanaDetalles.show(nodo.getNombre(), String.format("$%.2f", nodo.getPrecio()), nodo.getDescripcion(), primaryStage,controladorVista);
                    });

                    areaRecomendaciones.getChildren().add(productoCard);
                }
            }
        });

        // Actualizar recomendaciones según categoría
        comboCategoria.setOnAction(e -> {
            actualizarRecomendaciones(areaRecomendaciones, comboCategoria.getValue(), productos);
        });

        // Configurar el botón de agregar producto para abrir la ventana correspondiente
        agregarProductoButton.setOnAction(e -> {
            primaryStage.hide(); // Ocultar la ventana principal
            VentanaAgregarProducto ventanaAgregarProducto = new VentanaAgregarProducto();
            ventanaAgregarProducto.show(miGrafo, primaryStage, controladorVista.returnPath(), controladorVista, this, areaRecomendaciones, productos); // Pasar los parámetros adicionales
        });


        // Layout principal
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setSpacing(10);
        layoutPrincipal.getChildren().addAll(hboxBusqueda, scrollPane);

        // Escena inicial
        Scene escena = new Scene(layoutPrincipal, 900, 600);
        escena.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm()); // Ruta al archivo CSS

        // Configuración de la ventana
        primaryStage.setTitle("ShopSation");
        primaryStage.setScene(escena);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Método para actualizar recomendaciones
    private void actualizarRecomendaciones(FlowPane areaRecomendaciones, String categoriaSeleccionada,
                                           List<Nodo> productos) {
        areaRecomendaciones.getChildren().clear();

        List<Nodo> productosRecomendados = new ArrayList<>();

        // Filtrar productos por categoría
        for (Nodo nodo : productos) {
            if (categoriaSeleccionada.equals("Todo") || nodo.getCategoria().equals(categoriaSeleccionada)) {
                productosRecomendados.add(nodo);
            }
        }

// Si no se encontraron productos
        if (productosRecomendados.isEmpty()) {
            areaRecomendaciones.getChildren().add(new Label(" "));
        } else {
            // Mostrar los productos recomendados
            for (Nodo producto : productosRecomendados) {
                // Crear un Label para mostrar el nombre del producto
                Label productoCard = new Label(producto.getNombre() + " - " + producto.getPrecio());
                productoCard.getStyleClass().add("producto-card");  // Para el CSS

                // Agregar evento al hacer click en el card
                productoCard.setOnMouseClicked(e -> {
                    DetallesDeProducto ventanaDetalles = new DetallesDeProducto();
                    primaryStage.hide(); // Ocultar la ventana principal
                    String precio = String.valueOf(producto.getPrecio());
                    ventanaDetalles.show(producto.getNombre(), precio,
                            "Descripción del producto " + producto.getNombre(), primaryStage, controladorVista);
                });

                // Agregar el card al área de recomendaciones
                areaRecomendaciones.getChildren().add(productoCard);
            }
        }
    }

    public void actualizarProductosYVista(FlowPane areaRecomendaciones, List<Nodo> productos) {
        productos.clear(); productos.addAll(controladorVista.listadoNodos());
        actualizarRecomendaciones(areaRecomendaciones, "Todo", productos);
    }




}