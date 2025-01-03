package charly.projects.handprogrammingf;

import charly.projects.handprogrammingf.Controller.Controller;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
//import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

public class  Main extends Application {
    
    
    boolean ModoDePruebaDeEvaluadorDeExpresiones2000 = false;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear una escena con StackPane para superponer el fondo
        StackPane rootPane = new StackPane();
        Scene loadingScene = new Scene(rootPane);


        if (ModoDePruebaDeEvaluadorDeExpresiones2000) {
            JTextArea textArea = new JTextArea(15, 60);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showConfirmDialog(null, scrollPane,
                    "Ingresar las pruebas a realizar en diferentes lineas", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
            String [] evs = textArea.getText().split("\\n");
            StringBuilder finalRes = new StringBuilder();
            for (String ev : evs) {
                String res = EvaluadorExpresiones.EvOR(ev);
                finalRes.append(res).append("\n");
            }
            JOptionPane.showMessageDialog(null,finalRes.toString(),"Respuestas",JOptionPane.PLAIN_MESSAGE);
            return;
        }
        // Configurar el tamaño de la ventana de carga para que ocupe toda la pantalla
        Screen pantalla = Screen.getPrimary();
        javafx.geometry.Rectangle2D coordenadas = pantalla.getVisualBounds();
        primaryStage.setX(coordenadas.getMinX());
        primaryStage.setY(coordenadas.getMinY());
        primaryStage.setWidth(coordenadas.getWidth());
        primaryStage.setHeight(coordenadas.getHeight());

        primaryStage.setScene(loadingScene);

        // Cargar la imagen de fondo
        // System.out.println(getClass().getResource("/Fxml/VentanasFx/ventana.fxml") + "IMAGEN");
        Image backgroundImage = new Image("/Images/welcome!.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        rootPane.getChildren().add(backgroundImageView);
        
        primaryStage.show();

        // Simular la carga gradual con una animación
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    try {
                        FXMLLoader fxm = new FXMLLoader();
                        fxm.setLocation(getClass().getResource("/Fxml/VentanasFx/ventana.fxml"));
                        Parent mainRoot = fxm.load();
                        Scene mainScene = new Scene(mainRoot);
                        Controller c = fxm.getController();
                        c.stage = primaryStage;
                        primaryStage.setScene(mainScene);
                        primaryStage.setTitle("PROYECTO SISTEMAS");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // Ocultar la imagen de fondo y mostrar la ventana principal
                        rootPane.getChildren().remove(backgroundImageView);
                    }
                })
        );
        
        
        timeline.setCycleCount(1); // Ejecutar una vez
        timeline.play();
    }

    public static void main(String[] args) {


        try {
            // Ruta al script de Python
            //String scriptPath = "src/Main/PythonCode.py";

            // Comando para ejecutar el script de Python
            //String[] command = {"C:/Users/juand/AppData/Local/Microsoft/WindowsApps/python.exe", scriptPath};   //Juanda
//            String[] command = {"C:/Users/User/AppData/Local/Programs/Python/Python311/python.exe", scriptPath};   //Charly
            
            // Crear el proceso
            //ProcessBuilder processBuilder = new ProcessBuilder(command);
            //Process process = processBuilder.start();
            

//            // Leer la salida estándar del proceso
//            InputStream inputStream = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            // Leer la salida de error del proceso
//            InputStream errorStream = process.getErrorStream();
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Imprimir la salida de error
//            while ((line = errorReader.readLine()) != null) {
//                System.err.println(line);
//            }

            // Esperar a que el proceso termine
//            int exitCode = process.waitFor();
//            System.out.println("El script de Python ha terminado con código de salida: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        launch(args);
    }
}
