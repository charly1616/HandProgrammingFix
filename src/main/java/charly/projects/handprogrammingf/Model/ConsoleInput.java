package charly.projects.handprogrammingf.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class ConsoleInput extends Pane {

    private final Label lb = new Label("Pedir:");
    private final TextField tf = new TextField();

    public ConsoleInput(String s, BasicConsole bc){
        String bgCol = "#0d0d0d";
        tf.setStyle("-fx-background-color: "+bgCol+"; -fx-border-color: "+bgCol+"; -fx-text-fill: #fff;");

        HBox hb = new HBox();
        this.getChildren().add(hb);
        lb.setText(s);
        Button b = new Button("Enviar");
        String btCol = "#58b04d";
        b.setStyle("-fx-background-color: "+btCol+"; -fx-text-fill: white;");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bc.submitInput();
            }
        });
        hb.setSpacing(10);
        hb.getChildren().addAll(lb,tf,b);
    }

    public TextField getTf() {
        return tf;
    }

    public Label getLb(){
        return lb;
    }
}
