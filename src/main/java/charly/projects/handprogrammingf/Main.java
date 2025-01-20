package charly.projects.handprogrammingf;

import charly.projects.handprogrammingf.Controller.Controller;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main extends Application {

    boolean ModoDePruebaDeEvaluadorDeExpresiones2000 = false;
    private static Process pythonProcess; // Variable compartida para el proceso de Python

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear una escena con StackPane para superponer el fondo
        StackPane rootPane = new StackPane();
        Scene loadingScene = new Scene(rootPane);

        if (ModoDePruebaDeEvaluadorDeExpresiones2000) {
            JTextArea textArea = new JTextArea(15, 60);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showConfirmDialog(null, scrollPane,
                    "Ingresar las pruebas a realizar en diferentes líneas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            String[] evs = textArea.getText().split("\\n");
            StringBuilder finalRes = new StringBuilder();
            for (String ev : evs) {
                String res = EvaluadorExpresiones.EvOR(ev);
                finalRes.append(res).append("\n");
            }
            JOptionPane.showMessageDialog(null, finalRes.toString(), "Respuestas", JOptionPane.PLAIN_MESSAGE);
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

        // Configurar el evento de cierre para detener el proceso de Python
        primaryStage.setOnCloseRequest(event -> {
            if (pythonProcess != null && pythonProcess.isAlive()) {
                System.out.println("Cerrando proceso de Python...");
                pythonProcess.destroy(); // Detener el proceso de Python
            }
        });
    }

    public static void main(String[] args) {
        // Ejecutar el script de Python en un hilo en segundo plano
        Thread pythonThread = new Thread(() -> {
            try {
                // Ruta completa al script de Python
                String scriptPath = "C:/Users/juand/IdeaProjects/HandProgrammingFix/Py/PythonCod.py"; // Ruta al archivo Python

                // Ruta al intérprete de Python (asegúrate de que sea válida)
                String[] command = {"C:/Users/juand/AppData/Local/Programs/Python/Python310/python.exe", scriptPath};

                // Crear el proceso
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                pythonProcess = processBuilder.start(); // Guardar referencia al proceso

                // Leer la salida estándar del script de Python
                BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
                String line;
                System.out.println("Salida del script de Python:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Leer la salida de error del script de Python (si existe)
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(pythonProcess.getErrorStream()));
                System.out.println("Errores del script de Python (si los hay):");
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

                // Esperar a que el proceso termine
                int exitCode = pythonProcess.waitFor();
                System.out.println("El script de Python ha terminado con código de salida: " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        pythonThread.setDaemon(true);  // Deja que este hilo se cierre cuando la aplicación JavaFX termine
        pythonThread.start();  // Inicia el hilo en segundo plano

        // Lanzar la aplicación JavaFX
        launch(args);
    }
}
