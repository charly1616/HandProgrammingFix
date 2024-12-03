
package charly.projects.handprogrammingf.Main;

import Model.Bloque;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable {

    
    @FXML
    public BorderPane ventana;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crearCuadricula();
    }

    public void crearCuadricula() {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("gridpane.fxml"));
            Pane p = f.load();
            //GridController Bcon = f.getController();
            ventana.setCenter(p);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}