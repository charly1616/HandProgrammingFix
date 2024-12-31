package charly.projects.handprogrammingf.Bloques;


import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author juand
 */
public class BloqueMostrar extends BloqueLabel {

    public BloqueMostrar(double x, double y) {
        super(x, y, ConstantesDeBloques.ColoresBloques.get(BloqueMostrar.class),
                ConstantesDeBloques.NombresBloques.get(BloqueMostrar.class)[ConstantesDeBloques.IdiomaSeleccionado]);
        this.Inconectableh = true;
    }

    /*
    Si hay una siguiente línea conectada (Siguiente() != null), se ejecuta el siguiente código.
    Se utiliza para evaluar una expresión, mostrar su resultado en una ventana personalizada.
    */
    @Override
    public void Hacer() {
        if (!EvaluadorExpresiones.Debug(Siguiente())) {
            System.out.println("PROBLEMA EN EVALUAR EXPRESIÓN");
            return;
        }

        if (Siguiente() == null) {
            this.chorizontal.NecesitaSiguiente();
        } else {
            LineaEjecutador();
            mostrarVentanaResultado(EvaluadorExpresiones.Expresion(Siguiente()));
        }

        // Ejecutar la siguiente línea
        super.Hacer();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void mostrarVentanaResultado(String resultado) {
        //esto se corre en la consola
        BloqueMostrar.GlobalController.basicConsole.printLine(resultado);


//        // Esto generaba la ventana pero cambiamos por una consola :) mira que corta es
//        Platform.runLater(() -> {
//            Stage stage = new Stage();
//            stage.initStyle(StageStyle.UNDECORATED); // Elimina la barra de título del sistema
//
//            // Barra de título personalizada
//            Label titulo = new Label("MOSTRAR");
//            titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
//            titulo.setTextFill(Color.WHITE);
//
//            // Botón de cierre
//            Button btnCerrar = new Button("X");
//            btnCerrar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//            btnCerrar.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5px;");
//            btnCerrar.setOnAction(e -> stage.close());
//
//            // Contenedor de la barra de título
//            HBox barraTitulo = new HBox(titulo, btnCerrar);
//            barraTitulo.setStyle("-fx-background-color: #3b5998; -fx-padding: 5px;");
//            HBox.setHgrow(titulo, javafx.scene.layout.Priority.ALWAYS); // El título ocupa el espacio restante
//            barraTitulo.setSpacing(10);
//
//            // Permitir mover la ventana arrastrando la barra personalizada
//            barraTitulo.setOnMousePressed((MouseEvent event) -> {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
//            });
//            barraTitulo.setOnMouseDragged((MouseEvent event) -> {
//                stage.setX(event.getScreenX() - xOffset);
//                stage.setY(event.getScreenY() - yOffset);
//            });
//
//            //Contenido principal
//            Label contenido = new Label(resultado);
//            contenido.setFont(Font.font("Arial", 14));
//            contenido.setStyle("-fx-padding: 20px; -fx-text-alignment: center;");
//            contenido.setWrapText(true); // Permite que el texto con espacios se ajuste correctamente.
//
//
//            // Diseño principal
//            BorderPane root = new BorderPane();
//            root.setTop(barraTitulo);
//            root.setCenter(contenido);
//            root.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #3b5998; -fx-border-width: 2px;");
//
//            // Escena
//            Scene scene = new Scene(root, 400, 200);
//            stage.setScene(scene);
//
//            stage.showAndWait();
//        });
    }
}