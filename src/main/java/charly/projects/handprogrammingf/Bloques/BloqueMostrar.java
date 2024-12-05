package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author juand
 */
public class BloqueMostrar extends BloqueLabel {
    
    public BloqueMostrar(double x, double y) {
        super(x, y,  Color.rgb(255, 102, 128), "Mostrar");
        this.Inconectableh = true;
    }
    
    /*
    Si hay una siguiente línea conectada (Siguiente() != null), se ejecuta el siguiente código
    Se utiliza para evaluar una expresión, mostrar su resultado en la consola.
    */
    @Override
    public void Hacer() {
        if (!EvaluadorExpresiones.Debug(Siguiente())){
            System.out.println("PROBLEMA EN EEVALUAR EXPRESION");
            return;
        }

        if (Siguiente() == null){
            this.chorizontal.NecesitaSiguiente();
        }else {
            LineaEjecutador();
            JOptionPane.showMessageDialog(null, EvaluadorExpresiones.Expresion(Siguiente()), "BLOQUE MOSTRAR", JOptionPane.PLAIN_MESSAGE);
    // chorizontal.conexion.setError(true);
        }
        
        //Ejecutar la siguiente linea
        super.Hacer();
    }
}
