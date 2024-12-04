package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BloqueLabel extends Bloque {

    private StackPane stackPane;
    private Label label;
    private String Text;

    public BloqueLabel(double x, double y, Color ColorBloque, String Text) {
        super(x, y);
        this.Text = Text;
        this.ColorBloque = ColorBloque;
        IniciarComponentes();
        Pintar();
    }

    /*
    se encarga de configurar y agregar al bloque BloqueLabel un Label que muestra el texto y se coloca en un StackPane
    para ajustar su alineación. Esto establece la estructura visual del bloque BloqueLabel.
    Añadir el Label al stackPane: El Label se agrega como un hijo del stackPane
    Añadir el stackPane al bloque: El stackPane, que ahora contiene el Label
    */
    @Override
    public void IniciarComponentes() {
        super.IniciarComponentes();

        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_RIGHT);

        label = new Label(Text);

        Font font = new Font("Berlin Sans FB", 35);
        label.setFont(font);

        stackPane.getChildren().add(label);
        getChildren().add(stackPane);
    }

    
    
    /*
    Recibe: javafx.scene.text.Text llamado textNode y se le pasa el texto como argumento en su constructor.
     Esto crea un nodo de texto que se utiliza para calcular el ancho del texto.
    Hace:es una función de utilidad que se utiliza para calcular el ancho de un texto
    Devuelve: Ancho calculado como resultado de la función.
    */
    private double computeTextWidth(Font font, String text) {
        javafx.scene.text.Text textNode = new javafx.scene.text.Text(text);
        textNode.setFont(font);
        return textNode.getBoundsInLocal().getWidth();
    }

    
    /*
     se encarga de ajustar el tamaño y la apariencia visual del bloque BloqueLabel para que el texto que contiene se muestre
    correctamente. Esto implica calcular el ancho del texto, ajustar el tamaño del StackPane que contiene el Label y hacerlo visible 
    */
    @Override
    public void Pintar() {
        super.Pintar();
        
        double textWidth = computeTextWidth(label.getFont(), Text);
        /*calcular el ancho del texto que se mostrará en el Label. 
         Esto es importante para ajustar el tamaño del bloque según el contenido del texto.
        */

        stackPane.setPrefWidth(textWidth + 30);
        stackPane.setPrefHeight(65);
        setAncho(textWidth + 62);
        label.setVisible(true);
    }
}
