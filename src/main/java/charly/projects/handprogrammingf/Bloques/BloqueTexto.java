
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import java.util.HashSet;

import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public abstract class BloqueTexto extends Bloque{
    public Label indicador;
    public TextField valor;
    
    public int Limite;
    
    public int LetrasMax;
    
    
    public BloqueTexto(double x, double y, Color ColorBloque, int Limite, int LetrasMax) {
        super(x, y, ColorBloque);
        this.Limite = Limite;
        this.LetrasMax = LetrasMax;
    }
    
    
    //Codigo comentado dentro del Metodo
    @Override
    public void IniciarComponentes(){
        super.IniciarComponentes();
        
        //Creando el Label
        indicador = new Label("Str");
        
        //alineamiento del indicador
        indicador.setAlignment(Pos.CENTER_RIGHT);
        
        //Posicion del label
        indicador.setLayoutX(22);
        indicador.setLayoutY(15);
        
        //tamaño del label
        indicador.setPrefHeight(39); //altura
        indicador.setPrefWidth(32); //ancho
        
        //Se coloca la fuente
        Font font = new Font("Berlin Sans FB",14);
        indicador.setFont(font);
        
        valor = new TextField();
        
        valor.setPromptText(ConstantesDeBloques.NombresBloques.get(BloqueValor.class)[ConstantesDeBloques.IdiomaSeleccionado]);
        
        //Posicion del label
        valor.setLayoutX(43);
        valor.setLayoutY(16);
        
        //tamaño del label
        valor.setPrefHeight(22); //altura
        valor.setPrefWidth(77); //ancho
        
        //FUENTE
        font = new Font("Berlin Sans FB",22);
        valor.setFont(font);
        
        
        //Estilo del TextField (valor):
        valor.setStyle("-fx-text-fill: "+toHex(Color.BLACK).toLowerCase()+
                "; -fx-prompt-text-fill: "+toHex(Color.BLACK).toLowerCase()+
                "; -fx-background-color: "+toHex(ColorBloque).toLowerCase()+
                "; -fx-alignment: BOTTOM_CENTER;");
        
        valor.textProperty().addListener((ObservableValue<? extends String> observable, String valorviejo, String valornuevo) -> {
            
            
        /*
        Se verifica la longitud del nuevo valor del TextField (valornuevo) en comparación con LetrasMax.
        Si el nuevo valor supera la longitud máxima permitida,se restaura el valor anterior (valorviejo)
        Esto asegura que no se permitan más caracteres de los que se especifican en LetrasMax.
        */
       if (valornuevo.length()>this.LetrasMax ){
                valor.setText(valorviejo);
        }


       //Ajuste del ancho del TextField y del bloque padre
        double textWidth = computeTextWidth(valor.getFont(), valornuevo);


        //Si el ancho calculado más 30 unidades supera el límite (Limite más 77 unidades), no se realiza ningún ajuste.
        if (textWidth + 30 > Limite+77){
        }
        else if (textWidth + 30 > 77){
                valor.setPrefWidth(textWidth + 30);
                setAncho(textWidth + 75);
        } else {
                valor.setPrefWidth(77);
                setAncho(122);
            }
            TypeVariable();
        });
        
        // Agrega el componente TextField (valor) como un hijo del bloque de interfaz 
        getChildren().add(valor);
        //Se muestra en el bloque
        getChildren().add(indicador);
        
        setAncho(130);
    }

    //Este método es abstracto y debe implementarse en las clases que heredan de BloqueTexto
    public abstract void TypeVariable();
    
    
    //Recibe un color para convertirlo a color en formato RGB (Red, Green, Blue) en una cadena hexadecimal que representa ese color.
    public static String toHex(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("#%02X%02X%02X", r, g, b);
        
        
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
    
     
     
    @Override
    public void aparecer(){
        super.aparecer();
        ScaleTransition s1 = createScaleTransition(indicador);
        ScaleTransition s2 = createScaleTransition(valor);
        
        s1.play();
        s2.play();
        
    }
     
     
     
}
