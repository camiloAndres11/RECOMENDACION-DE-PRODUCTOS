package uptc.edu.co.vista;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main2 extends Application {
    private Stage primaryStage;

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
        comboCategoria.getItems().addAll("Todo", "Ropa", "Alimentos", "Electrodomesticos", "Juguetes");
        comboCategoria.setValue("Todo");

        // Botón para Buscar
        Button buscarButton = new Button("Buscar");
        buscarButton.setId("botonBuscar");

        // HBox para la parte de búsqueda (campo de texto, ComboBox y botón)
        HBox hboxBusqueda = new HBox(10);
        hboxBusqueda.setAlignment(Pos.CENTER);
        hboxBusqueda.getChildren().addAll(titulo, campoBusqueda, comboCategoria, buscarButton);
        hboxBusqueda.setId("barraSuperior");

        // Listas de productos como ejemplo
        List<String> productosRopa = new ArrayList<>();
        productosRopa.add("Camisas");
        productosRopa.add("Pantalones");
        productosRopa.add("Zapatos");

        List<String> productosAlimentos = new ArrayList<>();
        productosAlimentos.add("Carnes");
        productosAlimentos.add("Panes");
        productosAlimentos.add("Lacteos");

        List<String> productosElectrodomesticos = new ArrayList<>();
        productosElectrodomesticos.add("Televisores");
        productosElectrodomesticos.add("Lavadoras");
        productosElectrodomesticos.add("Microondas");

        List<String> productosJuguetes = new ArrayList<>();
        productosJuguetes.add("Muñecas");
        productosJuguetes.add("Pistas de autos");
        productosJuguetes.add("Construcciones Lego");

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
        actualizarRecomendaciones(areaRecomendaciones, comboCategoria.getValue(), productosRopa, productosAlimentos, productosElectrodomesticos, productosJuguetes);

        // Acción del botón de búsqueda que aparece al presionarlo
        buscarButton.setOnAction(e -> {
            String busqueda = campoBusqueda.getText().toLowerCase();
            String categoriaSeleccionada = comboCategoria.getValue();

            // Filtrar productos por búsqueda y categoría
            List<String> productosFiltrados = new ArrayList<>();
            if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Ropa")) {
                productosFiltrados.addAll(productosRopa.stream().filter(p -> p.toLowerCase().contains(busqueda)).collect(Collectors.toList()));
            }
            if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Alimentos")) {
                productosFiltrados.addAll(productosAlimentos.stream().filter(p -> p.toLowerCase().contains(busqueda)).collect(Collectors.toList()));
            }
            if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Electrodomesticos")) {
                productosFiltrados.addAll(productosElectrodomesticos.stream().filter(p -> p.toLowerCase().contains(busqueda)).collect(Collectors.toList()));
            }
            if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Juguetes")) {
                productosFiltrados.addAll(productosJuguetes.stream().filter(p -> p.toLowerCase().contains(busqueda)).collect(Collectors.toList()));
            }

            // Actualizar área de productos
            areaRecomendaciones.getChildren().clear();
            if (productosFiltrados.isEmpty()) {
                areaRecomendaciones.getChildren().add(new Label("No se encontraron productos."));
            } else {
                for (String producto : productosFiltrados) {
                    areaRecomendaciones.getChildren().add(new Label(producto));
                }
            }
        });

        // Actualizar recomendaciones según categoría
        comboCategoria.setOnAction(e -> {
            actualizarRecomendaciones(areaRecomendaciones, comboCategoria.getValue(), productosRopa, productosAlimentos, productosElectrodomesticos, productosJuguetes);
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
                                           List<String> productosRopa, List<String> productosAlimentos,
                                           List<String> productosElectrodomesticos, List<String> productosJuguetes) {
        areaRecomendaciones.getChildren().clear();

        List<String> productosRecomendados = new ArrayList<>();
        if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Ropa")) {
            productosRecomendados.addAll(productosRopa);
        }
        if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Alimentos")) {
            productosRecomendados.addAll(productosAlimentos);
        }
        if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Electrodomesticos")) {
            productosRecomendados.addAll(productosElectrodomesticos);
        }
        if (categoriaSeleccionada.equals("Todo") || categoriaSeleccionada.equals("Juguetes")) {
            productosRecomendados.addAll(productosJuguetes);
        }

        for (String producto : productosRecomendados) {
            Label productoCard = new Label(producto);

            productoCard.getStyleClass().add("producto-card");  // Para el CSS

            // Agregar evento al hacer click en el card
            productoCard.setOnMouseClicked(e -> {
                DetallesDeProducto ventanaDetalles = new DetallesDeProducto();
                primaryStage.hide(); // Ocultar la ventana principal
                ventanaDetalles.show(producto, "$100.00", "Descripción del producto " + producto, primaryStage);
            });

            areaRecomendaciones.getChildren().add(productoCard);
        }
    }
}