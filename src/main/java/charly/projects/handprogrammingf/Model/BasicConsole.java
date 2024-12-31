package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.BloquePedir;
import charly.projects.handprogrammingf.Controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.Timer;
import java.util.TimerTask;

public class BasicConsole extends Pane {
    private VBox textVBox = new VBox();
    private Label lastLabel = null;

    private BloquePedir bloqueQuePidio = null;
    private ConsoleInput consoleInput = null;


    public Controller controll;
    public BasicConsole(){
        VBox bb = new VBox();
        Label titleLabel = new Label("Charly's console");
        titleLabel.setPrefWidth(this.getPrefWidth());
        titleLabel.setTextFill(Color.rgb(31, 220, 255));
        titleLabel.setFont(new Font(20));
        bb.getChildren().addAll(titleLabel, new Label("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        bb.getChildren().add(textVBox);
        bb.setSpacing(6);
        bb.setPadding(new Insets(30,50,50,30));
        this.getChildren().add(bb);
        textVBox.setPadding(new Insets(10,10,10,10));
    }


    public void printLine(String s){
        if (consoleInput != null) return;
        Label last = createLabel(s);
        lastLabel = last;
        //esto es para poder editar el UI afuera del hilo del FX
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    textVBox.getChildren().add(last);
                });
            }
        }, 1);

    }

    public Label createLabel(String s){
        Label lab = new Label();
        String textHex = "#d3d3d3";
        lab.setStyle("-fx-text-fill: "+textHex+";");
        lab.setText(s);
        return lab;
    }


    public void createInput(BloquePedir bp){
        bloqueQuePidio = bp;
        consoleInput = new ConsoleInput("", this);
        //esto es para poder editar el UI afuera del hilo del FX
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    textVBox.getChildren().add(consoleInput);
                });
            }
        }, 1);
    }


    public void submitInput(){
        if (consoleInput == null) return;
        String submitted = consoleInput.getTf().getText();
        String leftText = consoleInput.getLb().getText() + submitted;
        textVBox.getChildren().remove(consoleInput);
        bloqueQuePidio.capturarValor(submitted);

        bloqueQuePidio = null;
        consoleInput = null;

        printLine(leftText);
    }


    public void clearConsole(){
        //esto es para poder editar el UI afuera del hilo del FX
        Timer timer = new Timer();
        for (Node no : textVBox.getChildren()){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                            textVBox.getChildren().remove(no);
                    });
                }
            }, 1);
        }
    }


}
