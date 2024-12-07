package uptc.edu.co.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uptc.edu.co.controlador.Grafo;
import uptc.edu.co.modelo.Nodo;
import uptc.edu.co.persistencia.Persistencia;

import java.io.IOException;
import java.util.List;

public class VentanaAgregarProducto {

    public void show(Grafo grafo, Stage primaryStage, String filePath) {

        Stage ventanaAgregarProducto = new Stage();
        ventanaAgregarProducto.setTitle("Añadir Nuevo Producto");

        // ventana independiente
        ventanaAgregarProducto.initModality(Modality.WINDOW_MODAL);
        ventanaAgregarProducto.initOwner(primaryStage);

        // etiquetas y campos para atributos
        Label lblNombre = new Label("Nombre del producto:");
        TextField campoNombre = new TextField();

        Label lblNumeroCompras = new Label("Número de compras:");
        TextField campoNumeroCompras = new TextField();

        Label lblDescripcion = new Label("Descripción del producto:");
        TextField campoDescripcion = new TextField();

        Label lblPrecio = new Label("Precio del producto:");
        TextField campoPrecio = new TextField();

        Label lblId = new Label("ID del producto:");
        TextField campoId = new TextField();

        Label lblCategorias = new Label("Categorías (separadas por comas):");
        TextField campoCategorias = new TextField();

        // Botón para agregar el producto
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setId("btnAgregar"); // Asignar ID para el estilo

        // Botón para cancelar
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setId("btnCancelar"); // Asignar ID para el estilo
        // Acción del botón Cancelar
        btnCancelar.setOnAction(event -> {
            primaryStage.show(); // Mostrar la ventana principal nuevamente
            ventanaAgregarProducto.close(); // Cerrar la ventana secundaria
        });

        // Manejo del evento al hacer clic en el botón de agregar
        btnAgregar.setOnAction(event -> {
            try {
                // Validar y recoger los datos ingresados
                String nombre = campoNombre.getText();
                int numeroCompras = Integer.parseInt(campoNumeroCompras.getText());
                String descripcion = campoDescripcion.getText();
                double precio = Double.parseDouble(campoPrecio.getText());
                int id = Integer.parseInt(campoId.getText());
                List<String> categorias = List.of(campoCategorias.getText().split("\\s*,\\s*")); // Limpia espacios alrededor de las comas

                // Crear el nuevo nodo
                Nodo nuevoNodo = new Nodo(nombre, numeroCompras, descripcion, categorias, id, precio);

                // Agregar el nodo al grafo
                grafo.agregarNodo(nuevoNodo);

                // Guardar el grafo actualizado en el archivo JSON
                Persistencia.guardarGrafo(filePath, grafo);

                // Mostrar mensaje de éxito y cerrar la ventana
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Producto Agregado");
                alerta.setHeaderText("El producto ha sido añadido exitosamente");
                alerta.setContentText("Nombre: " + nombre);
                alerta.showAndWait();
                ventanaAgregarProducto.close();

            } catch (NumberFormatException e) {
                // Mostrar alerta en caso de error en los campos numéricos
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Datos inválidos");
                alerta.setContentText("Asegúrate de ingresar valores numéricos correctos en Número de Compras, Precio e ID.");
                alerta.showAndWait();
            } catch (IOException e) {
                // Mostrar alerta en caso de error al guardar el grafo
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Error al guardar el grafo");
                alerta.setContentText("Ocurrió un error al intentar guardar el grafo: " + e.getMessage());
                alerta.showAndWait();
            } catch (Exception e) {
                // Manejo general de errores
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Error inesperado");
                alerta.setContentText("Ocurrió un error: " + e.getMessage());
                alerta.showAndWait();
            }
        });

        // Crear el GridPane para organizar los elementos
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        // Agregar las etiquetas y campos al GridPane
        gridPane.add(lblNombre, 0, 0);
        gridPane.add(campoNombre, 1, 0);

        gridPane.add(lblNumeroCompras, 0, 1);
        gridPane.add(campoNumeroCompras, 1, 1);

        gridPane.add(lblDescripcion, 0, 2);
        gridPane.add(campoDescripcion, 1, 2);

        gridPane.add(lblPrecio, 0, 3);
        gridPane.add(campoPrecio, 1, 3);

        gridPane.add(lblId, 0, 4);
        gridPane.add(campoId, 1, 4);

        gridPane.add(lblCategorias, 0, 5);
        gridPane.add(campoCategorias, 1, 5);

        // Contenedor para los botones
        HBox botonesLayout = new HBox(10);
        botonesLayout.getChildren().addAll(btnAgregar, btnCancelar);
        botonesLayout.setAlignment(Pos.CENTER);

        // Agregar los botones al GridPane
        gridPane.add(botonesLayout, 0, 6, 2, 1); // Ocupa dos columnas
        gridPane.setId("ventanaAgregarProducto");

        // Configuración de la escena
        Scene escena = new Scene(gridPane, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        ventanaAgregarProducto.setScene(escena);

        // Mostrar la ventana
        ventanaAgregarProducto.show();
    }
}