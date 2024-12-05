package charly.projects.handprogrammingf.Bloques;
import charly.projects.handprogrammingf.Model.Bloque;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Hands Programming
 */
public class BloqueOP extends Bloque {   
    
    public Label operaciones;
    public String signo;
    
    public BloqueOP(double x, double y, String sign, Color c) {
        super(x, y, c);
        this.Inconectablev = true;
        this.DesactivarVertical();
        operaciones.setText(sign);
        signo = sign;
    }
    
    
    //Comentado por partes en el codigo:
    @Override
    public void IniciarComponentes(){
        super.IniciarComponentes();
        
        //Creando el Label
        operaciones = new Label("Op");
        
        //alineamiento del indicador
        operaciones.setAlignment(Pos.CENTER_LEFT);
        
        //Posicion del label
        operaciones.setLayoutX(30);
        operaciones.setLayoutY(15);
        
        //tamaño del label
        operaciones.setPrefHeight(39); //altura
        operaciones.setPrefWidth(26); //ancho
        
        //Se coloca la fuente
        Font font = Font.font("Berlin Sans FB", FontWeight.NORMAL, 32);
        operaciones.setFont(font);
   
        
        //Se muestra en el bloque
        getChildren().add(operaciones);
        
        // para configurar el ancho del bloque Esto probablemente ajusta el tamaño del bloque para que se ajuste al contenido 
        setAncho(70);
    }
    
    /*
    No recibe nada
    Determina si es posible conectar dos bloques a un bloque BloqueOP de una manera que sea coherente
    Verifica que los bloques relacionados pertenezcan a un bloqueValor o Variable
    Devuelve un booleano 
    */
    public boolean PosibleConex() {
        Bloque bloqueIzquierdo = conectado != null ? conectado.conectador : null;
        Bloque bloqueDerecho = chorizontal != null && chorizontal.conexion != null ? chorizontal.conexion : null;
        
        if (bloqueIzquierdo instanceof BloqueValor || bloqueIzquierdo instanceof BloqueVariable) {
            if (bloqueDerecho instanceof BloqueValor || bloqueDerecho instanceof BloqueVariable) {
                return true;
            }
        }
      return false;
    }

    public static boolean isMathOperation(Bloque b){
        return b.getValor().matches("(?<=_{4})(\\+|\\-|\\*|\\/|%|<=|>=|<|>)(?=_{4})");
    }

    public boolean isMathOperation(){
        return getValor().matches("(?<=_{4})(\\+|\\-|\\*|\\/|%|<=|>=|<|>)(?=_{4})");
    }

    //Devuelve el valor contenido en el atributo signo.
    @Override
    public String getValor(){
        return "____"+this.signo+"____";
    }
    
}
