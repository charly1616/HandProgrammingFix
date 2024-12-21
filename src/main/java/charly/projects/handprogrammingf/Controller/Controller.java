
package charly.projects.handprogrammingf.Controller;

import charly.projects.handprogrammingf.Model.Bloque;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import charly.projects.handprogrammingf.Model.GridController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Controller implements Initializable {

    
    @FXML
    public BorderPane ventana;

    public Stage stage = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crearCuadricula();
    }

    public void crearCuadricula() {
        SplitPane split = new SplitPane();
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/Fxml/VentanasFx/gridpane.fxml"));
            Pane p = f.load();
            GridController g = f.getController();

            split.getItems().add(p);
            g.stage = stage;
            //GridController Bcon = f.getController();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pane pe = new Pane();
        pe.setBackground(Background.fill(Paint.valueOf("#689345")));
        pe.minWidth(200);
        split.getItems().add(pe);
        ventana.setCenter(split);
    }

    
    
}