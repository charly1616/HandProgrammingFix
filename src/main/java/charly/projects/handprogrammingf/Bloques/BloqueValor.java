
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.scene.paint.Color;


public class BloqueValor extends BloqueTexto{


    public BloqueValor(double x, double y, String val) {
        super(x, y, Color.rgb(247, 218, 63), 300,250);
        this.DesactivarVertical();
        this.Inconectablev = true;
        this.valor.setText(val);
    }
    
    public BloqueValor(double x, double y) {
        super(x, y, Color.rgb(247, 218, 63), 300,250);
        this.DesactivarVertical();
        this.Inconectablev = true;
    }
    
    
    
    //Establece el texto del componente indicador utilizando el resultado del método 
    @Override
    public void TypeVariable(){
        indicador.setText(GetType(valor.getText()));
    }
    
    //Retorna el texto contenido en el componente valor.
    @Override
    public String getValor(){
        if (valor.getText().equals("\\n")){
            return "\n";
        }
        
        
        try {
            double numero = Double.parseDouble(valor.getText());

            if (numero == (int)numero) {
                return String.valueOf((int)numero);
            } else {
                return String.valueOf(numero);
            }
        } catch (NumberFormatException e) {

        }
        
        return valor.getText();
    }
    
    
    
    /*
    Recibe una cadena de texto valor como argumento y determina el tipo de esa variable.
    Si la cadena está vacía, se establece type como "Non" (para "No definido").
    Si la cadena puede ser convertida a un número (mediante el método esNumero), se establece type como "Num" (para "Número").
    Si la cadena es "true" o "false" (ignorando mayúsculas/minúsculas), se establece type como "Bol" (para "Booleano").
    En otros casos, se establece type como "Str" (para "Cadena de texto").
    Finalmente, se devuelve el tipo determinado.
    */
    public static String GetType(String valor) {
        String type = "";
        if (valor.equals("")) {
            type = "Non";
        } else if (esNumero(valor)) {
            type = "Num";
        } else if (valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false")) {
            type = "Bol";
        } else {
            type = "Str";
        }
        return type;
    }

  
     /*
     Recibe una cadena de texto n como argumento y verifica si esta cadena puede ser convertida a un número(Double)
     Si retorna false significa que "n" no es numerico.
    */
    public static boolean esNumero(String n) {
        try {
            double b = Double.parseDouble(n);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getSaveValue(){return this.valor.getText();}
    
}
