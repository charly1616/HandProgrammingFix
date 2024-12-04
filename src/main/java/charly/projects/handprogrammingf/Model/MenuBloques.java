
package charly.projects.handprogrammingf.Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class MenuBloques extends VBox{
    
    private CreadorDeBloques creadorb;
    
    
    
    public MenuBloques(CreadorDeBloques creadorb) {
        this.creadorb = creadorb;
        inicializar();
    }
    
    
    
    
    
    public void inicializar(){
        setAlignment(javafx.geometry.Pos.TOP_CENTER);
        setPrefHeight(342.0);
        setPrefWidth(66.0);
        setSpacing(12.0);
        this.setLayoutX(20);
        this.setLayoutY(300);
        
        setStyle("-fx-background-color: #E7E7E7;");

        // Botón 1
        Button button1 = createButton(Objects.requireNonNull(getClass().getResource("/Images/Buttons/Box.png")).getPath(), "#FFA917");
        getChildren().add(button1);

        // Botón 2
        Button button2 = createButton(Objects.requireNonNull(getClass().getResource("/Images/Math/Multiply.png")).getPath(), "#817590");
        getChildren().add(button2);

        // Separador
        Separator separator1 = new Separator();
        separator1.setPrefWidth(200.0);
        getChildren().add(separator1);


        // Botón 3
        Button button3 = createButton(getClass().getResource("/Images/Ejecutable/Repeat_1.png").getPath(), "#A77CE0");
        getChildren().add(button3);

        // Botón 4
        Button button4 = createButton(getClass().getResource("/Images/Ejecutable/Share_1.png").getPath(), "#88DBFF");
        getChildren().add(button4);

        // Separador
        Separator separator3 = new Separator();
        separator3.setPrefWidth(200.0);
        getChildren().add(separator3);

        // Botón 5
        Button button5 = createButton(getClass().getResource("/Images/Buttons/Export.png").getPath(), "#FF6D87");
        getChildren().add(button5);
        button5.setOnAction(e -> {
            crearMostrar();
        });

        // Botón 6
        Button button6 = createButton(getClass().getResource("/Images/Buttons/Input.png").getPath(), "#7EAA00");
        getChildren().add(button6);
        button6.setOnAction(e -> {
            System.out.println("Pediurrrrr");
            crearPedir();
        });
        
        
        
        // Padding
        setPadding(new Insets(20.0));

    }
    
    
    
    
    private void crearMostrar(){
        creadorb.BloqueMostrar(400 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }
    
    
    
    private void crearPedir(){
        creadorb.BloquePedir(400 - (int)creadorb.cuadricula.Grid.getTranslateX(), 400 - (int)creadorb.cuadricula.Grid.getTranslateY());
    }
    
    
    
    private Button createButton(String imageUrl, String backgroundColor) {
        Button button = new Button();
        try {
            ImageView i = new ImageView(new Image(imageUrl));
            i.setFitHeight(25);
            i.setFitWidth(25);
            button.setGraphic(i);
        } catch (Exception e) {
            System.out.println("Error al cargar imagen");
        }
        
        button.setGraphicTextGap(2.0);
        button.setMinHeight(Button.USE_PREF_SIZE);
        button.setMinWidth(Button.USE_PREF_SIZE);
        button.setMnemonicParsing(false);
        button.setPrefHeight(35.0);
        button.setPrefWidth(35.0);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 25;");

        return button;
    }
    
}
