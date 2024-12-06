package uptc.edu.co.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetallesDeProducto {

    public void show(String nombreProducto, String precio, String descripcion, Stage primaryStage) {

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

        // Botón de comprar
        Button comprarButton = new Button("Comprar ahora");
        comprarButton.setId("botonComprar");
        comprarButton.setOnAction(e -> {
            System.out.println("Producto comprado: " + nombreProducto);
            // No cerrar la ventana por ahora
        });

        // Layout para el boton en la parte derecha
        VBox derechaLayout = new VBox(10);
        derechaLayout.getChildren().add(comprarButton);
        derechaLayout.setPadding(new Insets(10));
        derechaLayout.setAlignment(Pos.CENTER_RIGHT);

        // Botón "Volver"
        Button volverButton = new Button("Volver");
        volverButton.setId("botonVolver");
        volverButton.setOnAction(e -> {
            stage.close(); // Cierra esta ventana
            primaryStage.show(); // Muestra la ventana principal
        });

        // Footer para el botón "Volver"
        HBox footer = new HBox();
        footer.getChildren().add(volverButton);
        footer.setAlignment(Pos.BOTTOM_LEFT);
        footer.setPadding(new Insets(10));

        // Layout principal
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(izquierdaLayout);
        layoutPrincipal.setRight(derechaLayout);
        layoutPrincipal.setBottom(footer);
        layoutPrincipal.getStyleClass().add("window-background");

        // Crear la escena
        Scene escena = new Scene(layoutPrincipal, 500, 300);
        stage.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        // Mostrar la ventana
        stage.show();
    }
}
