
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.scene.paint.Color;


public class BloqueVariable extends BloqueTexto{
    
    
    public String valorVariable = "";
    
    public BloqueVariable(double x, double y) {
        super(x, y, Color.rgb(255, 165, 0), 250, 15);
    }
    
    
    /*Establece el texto del componente indicador como "Var".
     Configura el texto de marcador de posición del componente valor como "Var".
     */
    @Override
    public void IniciarComponentes(){
        super.IniciarComponentes();
        
        indicador.setText("Var");
        valor.setPromptText("Var");
    }
    
    
    //Este método devuelve el texto contenido en el componente valor
       public String getNombre() {
        return valor.getText();
    }

       
    //Este método devuelve el valor de la variable representada por este bloque, que está almacenado en el campo valorVariable.
    @Override
    public String getValor() {
        if (this.conectado != null && this.valorVariable.isEmpty()){
            if (ejecutador != null) {
                
                return rounded(ejecutador.getValor(this));
                
            }
        }
        return rounded(valorVariable);
    }
    
    
    public String rounded(String i){
        try {
            double numero = Double.parseDouble(i);
            if (numero == (int)numero) {
                return String.valueOf((int)numero);
            } else {
                return String.valueOf(numero);
            }
        } catch (NumberFormatException e) {
            return i;
        }
    }

    /*
    Recibe un parámetro nuevoValor y lo asigna al campo valorVariable.
    Este método permite establecer un nuevo valor para la variable representada por este bloque.
    */
    public void setValor(String nuevoValor) {
        valorVariable = nuevoValor;
        ejecutador.setValor(this, nuevoValor);
    }

    
    /*
    Este método verifica si valorVariable contiene un valor. Si contiene un valor no nulo y no está vacío, devuelve ese valor.
    De lo contrario, devuelve "Non" para indicar que la variable no está definida.
    */
    public String encontrarValor() {
        if (valorVariable != null && !valorVariable.isEmpty()) {
            return valorVariable;
        } else {
            return "Non";
        }
    }

    /*
    Verifica si hay una siguiente línea de código (SiguienteLinea()) y, si es así, llama al método Hacer del siguiente bloque.
    Si no hay una siguiente línea, llama al método vaciarVariables del objeto ejecutador.
    Finalmente, si hay un siguiente bloque, establece el campo ejecutador del siguiente bloque con el mismo valor que el actual.
    */
    @Override
    public void Hacer(){
        this.LineaEjecutador();
        EvaluadorExpresiones.Debug(Siguiente());
        ejecutador.setValor(this, RevisarValor());
        super.Hacer();
    }
    
    // Pasa el siguiente bloque como argumento y devuelve el resultado.
    public String RevisarValor(){
        return EvaluadorExpresiones.Expresion(Siguiente());
    }
    
    /*
    Asigna el texto contenido en el componente valor al campo valorVariable,
    lo que parece indicar que se actualiza el valor de la variable cuando se llama a este método.
    */
    @Override
    public void TypeVariable(){
    }
}
