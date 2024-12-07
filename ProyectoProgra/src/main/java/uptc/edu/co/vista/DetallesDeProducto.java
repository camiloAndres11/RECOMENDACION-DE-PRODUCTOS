package uptc.edu.co.vista;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uptc.edu.co.controlador.ControladorVista;
import uptc.edu.co.modelo.Nodo;

import java.util.List;

public class DetallesDeProducto {

    public void show(String nombreProducto, String precio, String descripcion, Stage primaryStage, ControladorVista controladorVista) {

        // Crear una nueva ventana
        Stage stage = new Stage();
        stage.setTitle("Detalles del Producto");

        // Etiquetas de información del producto
        Label nombreLabel = new Label(nombreProducto);
        nombreLabel.setId("nombreProducto");

        Label precioLabel = new Label("Precio: " + precio);
        precioLabel.setId("precioProducto");

        Label descripcionLabel = new Label("Descripción: " + descripcion);
        descripcionLabel.setId("descripcionProducto");

        // Layout para la información en la parte izquierda
        VBox izquierdaLayout = new VBox(10);
        izquierdaLayout.getChildren().addAll(nombreLabel, precioLabel, descripcionLabel);
        izquierdaLayout.setPadding(new Insets(10));
        izquierdaLayout.setAlignment(Pos.CENTER_LEFT);

        // Obtener recomendaciones
        List<Nodo> recomendaciones = controladorVista.recomendaciones(nombreProducto);
        ObservableList<Nodo> datosRecomendaciones = FXCollections.observableArrayList(recomendaciones);

        // Crear la tabla de recomendaciones
        TableView<Nodo> tablaRecomendaciones = new TableView<>(datosRecomendaciones);
        tablaRecomendaciones.setPrefWidth(500); // Ajustar el ancho de la tabla

        TableColumn<Nodo, String> columnaNombre = new TableColumn<>("Nombre del Producto");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(200); // Ajustar el ancho de la columna

        TableColumn<Nodo, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaPrecio.setPrefWidth(150); // Ajustar el ancho de la columna

        TableColumn<Nodo, Button> columnaAccion = new TableColumn<>("Acción");
        columnaAccion.setCellValueFactory(param -> {
            Button verProductoButton = new Button("Ver Producto");
            verProductoButton.setOnAction(e -> {
                Nodo productoSeleccionado = param.getValue();
                stage.close(); // Cerrar la ventana actual
                show(productoSeleccionado.getNombre(), String.valueOf(productoSeleccionado.getPrecio()), productoSeleccionado.getDescripcion(), primaryStage, controladorVista);
            });
            return new SimpleObjectProperty<>(verProductoButton);
        });
        columnaAccion.setPrefWidth(150); // Ajustar el ancho de la columna

        tablaRecomendaciones.getColumns().addAll(columnaNombre, columnaPrecio, columnaAccion);

        // Añadir la tabla de recomendaciones al layout de la izquierda
        izquierdaLayout.getChildren().add(tablaRecomendaciones);

        // Botón "Volver"
        Button volverButton = new Button("Volver");
        volverButton.setId("botonVolver");
        volverButton.setOnAction(e -> {
            stage.close(); // Cierra esta ventana
            primaryStage.show(); // Muestra la ventana principal
        });

        // Botón de comprar
        Button comprarButton = new Button("Comprar ahora");
        comprarButton.setId("botonComprar");
        comprarButton.setOnAction(e -> {
            System.out.println("Producto comprado: " + nombreProducto);
            // No cerrar la ventana por ahora
        });

        // Footer para los botones "Volver" y "Comprar ahora"
        HBox footer = new HBox(10);
        footer.getChildren().addAll(volverButton, comprarButton);
        footer.setAlignment(Pos.BOTTOM_CENTER); // Centrar ambos botones horizontalmente
        footer.setPadding(new Insets(10));
        HBox.setMargin(volverButton, new Insets(0, 0, 0, 20));
        HBox.setMargin(comprarButton, new Insets(0, 20, 0, 0));
        footer.setPrefWidth(580);

        // Layout principal
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(izquierdaLayout);
        layoutPrincipal.setBottom(footer); // Añadir el footer a la parte inferior
        layoutPrincipal.getStyleClass().add("window-background");

        // Crear la escena
        Scene escena = new Scene(layoutPrincipal, 600, 400);
        stage.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        // Mostrar la ventana
        stage.show();
    }
}
