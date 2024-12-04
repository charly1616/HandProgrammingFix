
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import javafx.scene.paint.Color;


public class BloqueElif extends BloqueCondicional{
    
    public BloqueElif(double x, double y) {
        super(x, y, "Sino Si", Color.rgb(3, 156, 110));
    }
    
    
    /*
    No recibe nada y devuelve null
    Primero controla que este conectado a un bloque if o un bloque elif
    Si no se cumple la condición anterior, se verifica si existe un bloque siguiente (Siguiente())
    y se evalúa si la condición (evaluarSiguiente()) asociada a ese bloque es verdadera
    Si la condición del paso 2 es falsa, se verifica si el bloque siguiente (SiguienteLinea()) es un bloque BloqueElse/BloqueElif.
    Si es alguno de estos dos casos, se llama al método Hacer() de ese bloque
    */
    @Override
    public void Hacer(){
        this.LineaEjecutador();
        if (this.conectadov.conectador instanceof BloqueIF || this.conectadov.conectador instanceof BloqueElif);
        else {
            if (SiguienteLinea() != null && SiguienteLinea().SiguienteLinea()!= null) SiguienteLinea().SiguienteLinea().Hacer();
            this.ponerRojo(this);
            return;
        }
        
        if (Siguiente() == null){
            this.chorizontal.NecesitaSiguiente();
            if (SiguienteLinea() != null ){
                SiguienteLinea().ejecutador = ejecutador;
                SiguienteLinea().Hacer();
            }
            
        }else if (evaluarSiguiente()){
            super.Hacer();
            
            Bloque sig = SiguienteLinea();
            while (sig instanceof BloqueElif || sig instanceof BloqueElse){
                sig = sig.SiguienteLinea();
            }
            if (sig != null) {
                sig.ejecutador = this.ejecutador;
                sig.Hacer();
            }
            
        }else {
            if (SiguienteLinea() != null ){
                SiguienteLinea().ejecutador = ejecutador;
                SiguienteLinea().Hacer();
            }
        }
        
    }
    
}
