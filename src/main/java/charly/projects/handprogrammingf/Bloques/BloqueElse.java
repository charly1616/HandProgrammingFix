
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import javafx.scene.paint.Color;

/**
 *
 * @author juand
 */
public class BloqueElse extends BloqueCondicional{


    public BloqueElse(double x, double y) {
        super(x, y, ConstantesDeBloques.NombresBloques.get(BloqueElse.class)[ConstantesDeBloques.IdiomaSeleccionado],
                ConstantesDeBloques.ColoresBloques.get(BloqueElse.class));
        setID(IDBloqueMax);
        this.DesactivarHorizontal();
    }
    
    /*
     Se ejecutará si está conectado a un "Elif" o un "IF" en la estructura condicional y su 
    lógica se ejecutará como parte de esa rama condicional. 
    Si no cumple con esa condición, el método no realiza ninguna acción.
    */
    @Override
    public void Hacer() {

        this.LineaEjecutador();

        if (conectadov.conectador instanceof BloqueElif || conectadov.conectador instanceof BloqueIF) {
            super.Hacer();
        } else {
            this.ponerRojo(this);
            return;
        }

        Bloque siguiente = SiguienteLinea();
        if (siguiente != null && siguiente != this) {
            siguiente.ejecutador = ejecutador;
            siguiente.Hacer();
        } else {
            System.out.println("El siguiente bloque es nulo o igual al bloque actual.");
        }
    }



}
