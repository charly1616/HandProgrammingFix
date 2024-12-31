package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Accordion;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MenuBloques extends VBox {

    private CreadorDeBloques creadorb;

    public MenuBloques(CreadorDeBloques creadorb) {
        this.creadorb = creadorb;
        inicializar();
    }

    public void inicializar() {
        setAlignment(javafx.geometry.Pos.TOP_CENTER);
        setPrefHeight(400.0);
        setPrefWidth(100.0);
        setSpacing(12.0);
        this.setLayoutX(20);
        this.setLayoutY(200);
        setStyle("-fx-background-color: #E7E7E7;");

        Accordion menuAccordion = new Accordion();

        Function<Class<? extends Bloque>,String> TextoBloque = (cla) -> ConstantesDeBloques.NombresBloques.get(cla)[ConstantesDeBloques.IdiomaSeleccionado];


        // Submenú: Bloques básicos
        VBox bloquesBasicos = new VBox(10);
        bloquesBasicos.getChildren().addAll(
                createButton("/Images/Buttons/Box.png", BloqueVariable.class,
                        TextoBloque.apply(BloqueVariable.class)),
                createButton("/Images/Buttons/Tags_3.png", BloqueValor.class,
                        TextoBloque.apply(BloqueValor.class)),
                createButton("/Images/Buttons/Repeat.png", BloqueWhile.class,
                        TextoBloque.apply(BloqueWhile.class)),
                createButton("/Images/Buttons/if.png", BloqueIF.class,
                        TextoBloque.apply(BloqueIF.class)),
                createButton("/Images/Buttons/Else.png", BloqueElse.class,
                        TextoBloque.apply(BloqueElse.class)),
                createButton("/Images/Buttons/Multicast.png", BloqueElif.class,
                        TextoBloque.apply(BloqueElif.class)),
                createButton("/Images/Buttons/For.png", BloqueFor.class,
                        TextoBloque.apply(BloqueFor.class)));
        TitledPane paneBasicos = new TitledPane(TextoBloque.apply(Bloque.class), bloquesBasicos);
        // Submenú: Operadores Matemáticos
        VBox operadoresMatematicos = new VBox(10);
        operadoresMatematicos.getChildren().addAll(
                createButton("/Images/Math/Plus.png", BloqueMat.class,"+"),
                createButton("/Images/Math/Subtract.png", BloqueMat.class,"-"),
                createButton("/Images/Math/Multiply.png", BloqueMat.class,"x"),
                createButton("/Images/Math/Potencia.png", BloqueMat.class,"^"),
                createButton("/Images/Math/Divide.png", BloqueMat.class,"/"),
                createButton("/Images/Math/Percentage.png", BloqueMat.class,"%")
        );
        TitledPane paneMatematicos = new TitledPane(TextoBloque.apply(BloqueMat.class), operadoresMatematicos);

        // Submenú: Operadores Lógicos
        VBox operadoresLogicos = new VBox(10);
        operadoresLogicos.getChildren().addAll(
                createButton("/Images/Compare/Equals.png", BloqueLMat.class,"="),
                createButton("/Images/Compare/Unequal.png", BloqueLMat.class,"!="),
                createButton("/Images/Compare/Greater Than_1.png", BloqueLMat.class,">"),
                createButton("/Images/Compare/Less Than_1.png", BloqueLMat.class,"<"),
                createButton("/Images/Compare/Greater_1.png", BloqueLMat.class,">="),
                createButton("/Images/Compare/Less or Equal.png", BloqueLMat.class,"<="),
                createButton("/Images/Compare/Ampersand.png", BloqueLogico.class,"&"),
                createButton("/Images/Compare/O.png", BloqueLogico.class,"o")
        );
        TitledPane paneLogicos = new TitledPane(TextoBloque.apply(BloqueLMat.class)+"\n"+ TextoBloque.apply(BloqueLogico.class), operadoresLogicos);

        // Submenú: Entrada/Salida
        VBox entradaSalida = new VBox(10);
        entradaSalida.getChildren().addAll(

                createButton("/Images/Buttons/Export.png", BloqueMostrar.class, TextoBloque.apply(BloqueMostrar.class)),
                createButton("/Images/Buttons/Input.png", BloquePedir.class, TextoBloque.apply(BloquePedir.class))
        );
        TitledPane paneEntradaSalida = new TitledPane(TextoBloque.apply(null), entradaSalida);

        // Agregar todos los submenús al acordeón
        menuAccordion.getPanes().addAll(paneBasicos, paneMatematicos, paneLogicos, paneEntradaSalida);
        menuAccordion.setPrefWidth(60.0);

        getChildren().add(menuAccordion);
        setPadding(new Insets(10.0));
    }



    private Button createButton(String imageUrl, Class<? extends Bloque> cla, String signo) {
        // Color del bloque
        Function<Color, String> Color2Hex = (co) -> String.format("#%02X%02X%02X", (int) (co.getRed() * 255), (int) (co.getGreen() * 255), (int) (co.getBlue() * 255));
        Function<Class<? extends Bloque>, String> ColorDeBloque = (c) -> Color2Hex.apply(ConstantesDeBloques.ColoresBloques.get(c));
        String backgroundColor = ColorDeBloque.apply(cla);

        // Función que se pasa al cursor
        BiFunction<Double[], String, Bloque> action1 = FileSaver.FunctionsBloques.get(FileSaver.prefixBloques.get(cla));
        BiFunction<Double, Double, Bloque> action = (du, du2) -> action1.apply(new Double[]{du, du2}, (cla != BloqueValor.class && cla != BloqueVariable.class) ? signo : "");


        Button button = new Button();
        HBox content = new HBox();
        content.setSpacing(18);
        content.setAlignment(javafx.geometry.Pos.CENTER_LEFT); // Alineación a la izquierda

        try {
            ImageView i = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm()));
            i.setFitHeight(20);
            i.setFitWidth(20);
            content.getChildren().add(i);
        } catch (Exception e) {
            System.out.println("Error al cargar imagen: " + imageUrl);
        }

        Label label = new Label(signo);
        label.setStyle("-fx-text-fill: black; -fx-font-size: 12; -fx-font-weight: bold;");
        content.getChildren().add(label);

        button.setGraphic(content);
        button.setGraphicTextGap(2.0);
        button.setMinHeight(Button.USE_PREF_SIZE);
        button.setMinWidth(Button.USE_PREF_SIZE);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25.0);
        button.setPrefWidth(100.0);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 25;");
        button.setOnAction(e -> {
            GridController.pCursorColor = ConstantesDeBloques.ColoresBloques.get(cla);
            creadorb.cuadricula.functionCreadora = action;
        });
        button.setOnMouseDragged(e -> {
            GridController.pCursorColor = ConstantesDeBloques.ColoresBloques.get(cla);
            creadorb.cuadricula.functionCreadora = action;
        });

        return button;
    }

}
