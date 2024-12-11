
package charly.projects.handprogrammingf.Bloques;

import javafx.animation.TranslateTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import charly.projects.handprogrammingf.Model.Bloque;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class BloqueInicio extends BloqueEjecutable{
    
    public Label label;
    public Button boton;
    public Polyline tri;
    
    
    
    
    public BloqueInicio(double x, double y) {
        super(x,y);
        setID(IDBloqueMax);
        this.ColorBloque = Color.rgb(86, 255, 114);
        this.ancho = 160;
        
        IniciarComponentes();
        Pintar();
        chorizontal.Desactivar();
        cvertical.offX = 60;
        Inconectablev = true;
    }
    
    
    /* Es la estrucuta del bloque inicio, se inician todos sus componentes visuales
    se encarga de configurar y agregar al Pane del bloque un Label (etiqueta de texto), un Button (botón) y un Polyline (triángulo)
    y de configurar sus respectivas propiedades visuales, como posición, tamaño, color y fuente.
    Se agrega un manejador de evento al botón. Cuando se hace clic en el botón, se llama al método Undir().
    */
    @Override
    public void IniciarComponentes(){
        super.IniciarComponentes();
        
        //Creando el Label
        label = new Label("Inicio");
        
        //Posicion del label
        label.setLayoutX(62.0);
        label.setLayoutY(15.0);
        
        //tamaño del label
        label.setPrefHeight(40.0); //altura
        label.setPrefWidth(94.0); //ancho
        
        //Se coloca la fuente
        Font font = new Font("Berlin Sans FB",35);
        
        label.setFont(font);
        
        
        //Se muestra en el bloque
        getChildren().add(label);
        
        // Creando el botón
        boton = new Button ();
        
        //Posicion del botón
        boton.setLayoutX(25.0);
        boton.setLayoutY(15.0);
        
         //tamaño del botón 
        boton.setPrefHeight(40.0); //altura
        boton.setPrefWidth(36.0); //ancho
        
        // transparencia 
        boton.setMnemonicParsing(false);
        
        //
        boton.setOpacity(0.0);
        
        getChildren().add(boton);
        
        // creando el triangulo
        tri = new Polyline();
        
         //Posicion del triangulo
        tri.setLayoutX(49.0);
        tri.setLayoutY(34.0);
        
        //
        tri.setFill(Color.LIME);
        //tri.points();
      
        //Vertices del triangulo
        tri.getPoints().addAll(-18.0, 16.0, -18.0, -13.0, 6.0, 2.0, -18.0, 16.0);
        tri.setStroke(Color.valueOf("#2c2c2c") );
        tri.setStrokeType(StrokeType.INSIDE);
        tri.setStrokeWidth(4.0);
        
        tri.setMouseTransparent(true);
        
        getChildren().add(tri);

        boton.setOnAction(e -> {
            Undir();
        });
        
    }
    
    
    /*
    Undir() anima visualmente el bloque BloqueInicio haciendo que se hunda.El comportamiento exacto del método dependerá
    de la implementación específica de la clase BloqueInicio y cómo se relaciona con otros bloques en el programa.
    */
    public void Undir(){
        
        //Animación de hundimiento visual:
        TranslateTransition transicion = Transicion(TopPart);
        final TranslateTransition transicion1 = Transicion(label);
        TranslateTransition transicion2 = Transicion(tri);
        
        //Se configuran estas animaciones para que duren 0.06 segundos y se repitan una vez sin auto inversión.
        transicion.setDelay(Duration.ZERO);
        transicion1.setDelay(Duration.ZERO);
        transicion2.setDelay(Duration.ZERO);
        
        //Inicio de las animaciones:
        transicion.setByY(9);
        transicion1.setByY(9);
        transicion2.setByY(9);
        
        transicion.play();
        transicion1.play();
        transicion2.play();
        
        /*
            Se verifica si hay un bloque adyacente (SiguienteLinea()) al bloque BloqueInicio.
            Si existe un segundo bloque adyacente, se establece el ejecutador del bloque adyacente al BloqueInicio.
            llama al método Hacer(), lo que puede llevar a la ejecución de cierto código.
         */
        if (SiguienteLinea() != null) {
            for (int i = 0; i < 20; i++) {
                System.out.println("");
            }
            SiguienteLinea().ejecutador = this;
            SiguienteLinea().Debug();
            SiguienteLinea().Hacer();
        }
        transicion1.play();


        
       
       
    }
    
    
    
    
    
    public TranslateTransition Transicion(Node n){
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.06), n);
        t.setCycleCount(1);
        t.setAutoReverse(true);
        TranslateTransition r = new TranslateTransition(Duration.seconds(0.06), n);
        r.setToY(0);
        t.setOnFinished(event -> {
            r.play();
        });

        return t;
    }
}
