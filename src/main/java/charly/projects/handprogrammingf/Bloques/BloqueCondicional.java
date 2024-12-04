
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.ConectorMultiple;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class BloqueCondicional extends BloqueEjecutable{
    
    private StackPane stackPane;
    private Label label;
    private String Text;
    
    
    public BloqueCondicional(double x, double y, String Text, Color c) {
        super(x, y);
        if (Text == null || Text.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto del bloque no puede ser nulo o vacío.");
        }
        this.Text = Text;
        this.ColorBloque = c;
        IniciarComponentes();
        Pintar();
    }

    
    /*
    Crear los componentes visuales necesarios para representar un bloque condicional.
    Esto incluye la creación de un StackPane que contiene una etiqueta con el texto 
    del bloque y la inicialización de un conector múltiple (cvertical) que parece estar relacionado con la lógica de conexión del bloque.
    */
    @Override
    public void IniciarComponentes() {
        super.IniciarComponentes();
        
        this.cvertical = new ConectorMultiple(this);
        
        
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_RIGHT);

        label = new Label(Text);

        Font font = new Font("Berlin Sans FB", 35);
        label.setFont(font);

        stackPane.getChildren().add(label);
        getChildren().add(stackPane);
    }

    
    /*
    Calcula el ancho necesario para mostrar un texto con una fuente específica
    Esto puede ser útil para ajustar el tamaño de los elementos que contienen el texto, como el StackPan
    para asegurarse de que el texto encaje adecuadamente sin ser recortado ni desbordado. 
    */
    private double computeTextWidth(javafx.scene.text.Font font, String text) {
        javafx.scene.text.Text textNode = new javafx.scene.text.Text(text);
        textNode.setFont(font);
        return textNode.getBoundsInLocal().getWidth();
    }



    /*
     Se encarga de establecer el tamaño visual (ancho y alto) de un bloque condicional y ajustar su contenido 
    (en este caso, un StackPane que contiene una etiqueta de texto) para que se muestre correctamente en la interfaz gráfica.
    */
    @Override
    public void Pintar() {
        super.Pintar();
        double textWidth = computeTextWidth(label.getFont(), Text);

        stackPane.setPrefWidth(textWidth + 30);
        stackPane.setPrefHeight(65);
        setAncho(textWidth + 62);
        label.setVisible(true);
    }

    public boolean evaluarSiguiente(){
        return EvaluadorExpresiones.EvCondicion(Siguiente());
    }






}
