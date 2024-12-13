package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Accordion;
import java.util.Objects;

public class MenuBloques extends VBox {

    private CreadorDeBloques creadorb;

    public MenuBloques(CreadorDeBloques creadorb) {
        this.creadorb = creadorb;
        inicializar();
    }

    public void inicializar() {
        setAlignment(javafx.geometry.Pos.TOP_CENTER);
        setPrefHeight(400.0);
        setPrefWidth(70.0);
        setSpacing(12.0);
        this.setLayoutX(20);
        this.setLayoutY(200);
        setStyle("-fx-background-color: #E7E7E7;");

        Accordion menuAccordion = new Accordion();

        // Submenú: Bloques básicos
        VBox bloquesBasicos = new VBox(10);
        bloquesBasicos.getChildren().addAll(
                createButton("/Images/Buttons/Box.png", "#FFA917",() -> crearbloque(BloqueVariable.class,0.0,0.0,""),"Var"),
                createButton("/Images/Math/Multiply.png", "#FFFF00", () ->crearbloque(BloqueValor.class,0.0,0.0,""),"Dato"),
                createButton("/Images/Ejecutable/Repeat_1.png", "#A77CE0",() -> crearbloque(BloqueWhile.class,0.0,0.0,""),"Mientras"),
                createButton("/Images/Ejecutable/Share_1.png", "#88DBFF", () ->crearbloque(BloqueIF.class,0.0,0.0,""),"Si"),
                createButton("/Images/Ejecutable/Else_1.png", "#8FCFD1",() -> crearbloque(BloqueElse.class,0.0,0.0,""),"Sino"),
                createButton("/Images/Ejecutable/ElseIf_1.png", "#006400", () ->crearbloque(BloqueElif.class,0.0,0.0,""),"Sinosi"),
                createButton("/Images/Ejecutable/For_1.png", "#FF7F50",() -> crearbloque(BloqueFor.class,0.0,0.0,""),"Para"));
        TitledPane paneBasicos = new TitledPane("Bloques Básicos", bloquesBasicos);

        // Submenú: Operadores Matemáticos
        VBox operadoresMatematicos = new VBox(10);
        operadoresMatematicos.getChildren().addAll(
                createButton("/Images/Operators/Plus.png", "#C051F7FF", () -> crearbloque(BloqueMat.class,0.0,0.0,"+"),"+"),
                createButton("/Images/Operators/Minus.png", "#C051F7FF", () -> crearbloque(BloqueMat.class,0.0,0.0,"-"),"-"),
                createButton("/Images/Operators/Multiply.png", "#C051F7FF",  () -> crearbloque(BloqueMat.class,0.0,0.0,"x"),"x"),
                createButton("/Images/Operators/Power.png", "#C051F7FF",  () -> crearbloque(BloqueMat.class,0.0,0.0,"^"),"^"),
                createButton("/Images/Operators/Divide.png", "#C051F7FF",  () -> crearbloque(BloqueMat.class,0.0,0.0,"/"),"/"),
                createButton("/Images/Operators/Modulo.png", "#C051F7FF",  () -> crearbloque(BloqueMat.class,0.0,0.0,"%"),"%")
        );
        TitledPane paneMatematicos = new TitledPane("Operadores Matemáticos", operadoresMatematicos);

        // Submenú: Operadores Lógicos
        VBox operadoresLogicos = new VBox(10);
        operadoresLogicos.getChildren().addAll(
                createButton("/Images/Operators/Equal.png", "#8E22BB", () -> crearbloque(BloqueLMat.class,0.0,0.0,"="),"="),
                createButton("/Images/Operators/NotEqual.png", "#8E22BB", () -> crearbloque(BloqueLMat.class,0.0,0.0,"!="),"!="),
                createButton("/Images/Operators/Greater.png", "#8E22BB", () ->  crearbloque(BloqueLMat.class,0.0,0.0,">"),">"),
                createButton("/Images/Operators/Less.png", "#8E22BB", () ->  crearbloque(BloqueLMat.class,0.0,0.0,"<"),"<"),
                createButton("/Images/Operators/GreaterEqual.png", "#8E22BB", () ->  crearbloque(BloqueLMat.class,0.0,0.0,">="),">="),
                createButton("/Images/Operators/LessEqual.png", "#8E22BB", () ->  crearbloque(BloqueLMat.class,0.0,0.0,"<="),"<="),
                createButton("/Images/Operators/And.png", "FF6AC2FF", () ->  crearbloque(BloqueLogico.class,0.0,0.0,"&"),"&"),
                createButton("/Images/Operators/Or.png", "FF6AC2FF", () ->  crearbloque(BloqueLogico.class,0.0,0.0,"o"),"o")
        );
        TitledPane paneLogicos = new TitledPane("Operadores Lógicos\n LógicosMatemáticos", operadoresLogicos);

        // Submenú: Entrada/Salida
        VBox entradaSalida = new VBox(10);
        entradaSalida.getChildren().addAll(

                createButton("/Images/Buttons/Export.png", "#FF6D87", () -> crearbloque(BloqueMostrar.class,0.0,0.0,""),"Mostrar"),
                createButton("/Images/Buttons/Input.png", "#7EAA00", () ->crearbloque(BloquePedir.class,0.0,0.0,""),"Pedir")
        );
        TitledPane paneEntradaSalida = new TitledPane("Entrada/Salida", entradaSalida);

        // Agregar todos los submenús al acordeón
        menuAccordion.getPanes().addAll(paneBasicos, paneMatematicos, paneLogicos, paneEntradaSalida);
        menuAccordion.setPrefWidth(60.0);

        getChildren().add(menuAccordion);
        setPadding(new Insets(10.0));
    }


    private void crearbloque(Class<?extends Bloque>a, Double x, Double y, String string){
        String pre=FileSaver.prefixBloques.get(a);
        x=400.0 - (int) creadorb.cuadricula.Grid.getTranslateX(); y=400.0 - (int) creadorb.cuadricula.Grid.getTranslateY();
        Double [] array  = {x,y};
        FileSaver.FunctionsBloques.get(pre).apply(array, string);
    }

    private Button createButton(String imageUrl, String backgroundColor, Runnable action, String signo) {
        Button button = new Button();
        VBox content = new VBox();
        content.setSpacing(5); // Espaciado entre imagen y signo
        content.setAlignment(javafx.geometry.Pos.CENTER);

        try {
            ImageView i = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm()));
            i.setFitHeight(25);
            i.setFitWidth(25);
            content.getChildren().add(i);
        } catch (Exception e) {
            System.out.println("Error al cargar imagen: " + imageUrl);
        }

        javafx.scene.control.Label label = new javafx.scene.control.Label(signo);
        label.setStyle("-fx-text-fill: black; -fx-font-size: 14; -fx-font-weight: bold;");
        content.getChildren().add(label);

        button.setGraphic(content);
        button.setGraphicTextGap(2.0);
        button.setMinHeight(Button.USE_PREF_SIZE);
        button.setMinWidth(Button.USE_PREF_SIZE);
        button.setMnemonicParsing(false);
        button.setPrefHeight(45.0);
        button.setPrefWidth(76.0);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 25;");
        button.setOnAction(e -> action.run());

        return button;
    }
}
