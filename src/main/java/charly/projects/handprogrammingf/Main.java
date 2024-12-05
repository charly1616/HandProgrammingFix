package charly.projects.handprogrammingf;

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
public class  Main extends Application {
    
    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear una escena con StackPane para superponer el fondo
        StackPane rootPane = new StackPane();
        Scene loadingScene = new Scene(rootPane);

        System.out.println(EvaluadorExpresiones.EvMatSum("5____-____5"));

        if (true) return;
        // Configurar el tamaño de la ventana de carga para que ocupe toda la pantalla
        Screen pantalla = Screen.getPrimary();
        javafx.geometry.Rectangle2D coordenadas = pantalla.getVisualBounds();
        primaryStage.setX(coordenadas.getMinX());
        primaryStage.setY(coordenadas.getMinY());
        primaryStage.setWidth(coordenadas.getWidth());
        primaryStage.setHeight(coordenadas.getHeight());

        primaryStage.setScene(loadingScene);

        // Cargar la imagen de fondo
        System.out.println(getClass().getResource("/Fxml/VentanasFx/ventana.fxml") + "IMAGEN");
        Image backgroundImage = new Image("/Images/2.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        rootPane.getChildren().add(backgroundImageView);
        
        primaryStage.show();

        // Simular la carga gradual con una animación
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    try {
                        Parent mainRoot = FXMLLoader.load(getClass().getResource("/Fxml/VentanasFx/ventana.fxml"));
                        Scene mainScene = new Scene(mainRoot);
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
