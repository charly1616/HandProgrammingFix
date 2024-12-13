package charly.projects.handprogrammingf.Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Accordion;
import java.util.Objects;

public class MenuBloques extends VBox{

    private CreadorDeBloques creadorb;

    public MenuBloques(CreadorDeBloques creadorb) {
        this.creadorb = creadorb;
        inicializar();
    }

    public void inicializar(){
        setAlignment(javafx.geometry.Pos.TOP_CENTER);
        setPrefHeight(400.0);
        setPrefWidth(70.0);
        setSpacing(12.0);
        this.setLayoutX(20);
        this.setLayoutY(300);
        setStyle("-fx-background-color: #E7E7E7;");

        Accordion menuAccordion = new Accordion();

        // Submenú: Bloques básicos
        VBox bloquesBasicos = new VBox(10);
        bloquesBasicos.getChildren().addAll(
                createButton("/Images/Buttons/Box.png", "#FFA917", this::crearVar),
                createButton("/Images/Math/Multiply.png", "#FFFF00", this::creardato),
                createButton("/Images/Ejecutable/Repeat_1.png", "#A77CE0", this::crearMientras),
                createButton("/Images/Ejecutable/Share_1.png", "#88DBFF", this::crearIf),
                createButton("/Images/Ejecutable/Else_1.png", "#8FCFD1", this::crearSino),
                createButton("/Images/Ejecutable/ElseIf_1.png", "#006400", this::crearSinoSi),
                createButton("/Images/Ejecutable/For_1.png", "#FF7F50", this::crearPara)
        );
        TitledPane paneBasicos = new TitledPane("Bloques Básicos", bloquesBasicos);

        // Submenú: Operadores Matemáticos y Lógicos
        HBox operadores = new HBox(10);

        VBox operadoresMatematicos = new VBox(10);
        operadoresMatematicos.getChildren().addAll(
                createButton("/Images/Operators/Plus.png", "#CFB5DC", () -> crearOperador("+")),
                createButton("/Images/Operators/Minus.png", "#CFB5DC", () -> crearOperador("-")),
                createButton("/Images/Operators/Multiply.png", "#CFB5DC", () -> crearOperador("*")),
                createButton("/Images/Operators/Power.png", "#CFB5DC", () -> crearOperador("^")),
                createButton("/Images/Operators/Divide.png", "#CFB5DC", () -> crearOperador("/")),
                createButton("/Images/Operators/Modulo.png", "#CFB5DC", () -> crearOperador("%")


        ));

        VBox operadoresLogicos = new VBox(10);
        operadoresLogicos.getChildren().addAll(
                createButton("/Images/Operators/Equal.png", "#FFB7A5", () -> crearOperador("==")),
                createButton("/Images/Operators/NotEqual.png", "#FFB7A5", () -> crearOperador("!=")),
                createButton("/Images/Operators/Greater.png", "#FFB7A5", () -> crearOperador(">")),
                createButton("/Images/Operators/Less.png", "#FFB7A5", () -> crearOperador("<")),
                createButton("/Images/Operators/GreaterEqual.png", "#FFB7A5", () -> crearOperador(">=")),
                createButton("/Images/Operators/LessEqual.png", "#FFB7A5", () -> crearOperador("<=")),
                createButton("/Images/Operators/And.png", "#FFB7A5", () -> crearOperador("&&")),
                createButton("/Images/Operators/Or.png", "#FFB7A5", () -> crearOperador("||"))
        );

        operadores.getChildren().addAll(operadoresMatematicos, operadoresLogicos);
        TitledPane paneOperadores = new TitledPane("Operadores", operadores);

        // Submenú: Entrada/Salida
        VBox entradaSalida = new VBox(10);
        entradaSalida.getChildren().addAll(
                createButton("/Images/Buttons/Export.png", "#FF6D87", this::crearMostrar),
                createButton("/Images/Buttons/Input.png", "#7EAA00", this::crearPedir)
        );
        TitledPane paneEntradaSalida = new TitledPane("Entrada/Salida", entradaSalida);

        // Agregar todos los submenús al acordeón
        menuAccordion.getPanes().addAll(paneBasicos, paneOperadores, paneEntradaSalida);
        menuAccordion.setPrefWidth(60.0);

        getChildren().add(menuAccordion);
        setPadding(new Insets(10.0));
    }

    private void crearMientras() {
        creadorb.BloqueWhile(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void creardato() {
        creadorb.BloqueValor(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY(), "");
    }

    private void crearVar() {
        creadorb.BloqueVariable(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY(), "");
    }

    private void crearMostrar() {
        creadorb.BloqueMostrar(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearIf() {
        creadorb.BloqueIF(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearSino() {
        creadorb.BloqueElse(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearSinoSi() {
        creadorb.BloqueElif(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearPara() {
        creadorb.BloqueFor(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearPedir() {
        creadorb.BloquePedir(400.0 - (int)creadorb.cuadricula.Grid.getTranslateY(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }

    private void crearOperador(String operador) {
        creadorb.BloqueLogico(400.0 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400.0 - (int)creadorb.cuadricula.Grid.getTranslateY(), operador);
    }

    private Button createButton(String imageUrl, String backgroundColor, Runnable action) {
        Button button = new Button();
        try {
            ImageView i = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).getPath()));
            i.setFitHeight(25);
            i.setFitWidth(25);
            button.setGraphic(i);
        } catch (Exception e) {
            System.out.println("Error al cargar imagen: " + imageUrl);
        }

        button.setGraphicTextGap(2.0);
        button.setMinHeight(Button.USE_PREF_SIZE);
        button.setMinWidth(Button.USE_PREF_SIZE);
        button.setMnemonicParsing(false);
        button.setPrefHeight(35.0);
        button.setPrefWidth(35.0);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 25;");
        button.setOnAction(e -> action.run());

        return button;
    }
}
