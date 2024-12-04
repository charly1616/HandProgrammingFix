
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import javafx.scene.paint.Color;

/**
 *
 * @author juand
 */
public class BloqueWhile extends BloqueCondicional{
    
    public BloqueWhile(double x, double y) {
        super(x, y, "Mientras", Color.rgb(147, 120, 201));
    }
    
    
    //Hasta ahora elimina todas las variables almacenadas en la lista variables de un objeto BloqueEjecutable
    @Override
    public void vaciarVariables() {
        variables.clear();
        limpiarEjecutadores();
        
        //Ejecutar la siguiente linea
        Hacer();
    }
    
    
    
    
      /*
     Implementa un bucle "while" que ejecuta su contenido repetidamente mientras una cierta condición sea verdadera
     Luego continúa con la ejecución del código después del bucle cuando la condición se vuelve falsa.
    */
    @Override
    public void Hacer(){
        this.LineaEjecutador();
        if (Siguiente() == null) this.chorizontal.NecesitaSiguiente();
        
        if (evaluarSiguiente()){
            super.Hacer();
        } else {
            Bloque b = SiguienteLinea();
            if (b == null){
                ejecutador.vaciarVariables();
                return;
            }
            b.ejecutador = ejecutador;
            b.Hacer();
        }
    }
    
}
