package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.scene.paint.Color;

import java.util.concurrent.CompletionService;

public class BloqueIF extends BloqueCondicional {


    public BloqueIF(double x, double y) {
        super(x, y, ConstantesDeBloques.NombresBloques.get(BloqueIF.class)[ConstantesDeBloques.IdiomaSeleccionado],
                ConstantesDeBloques.ColoresBloques.get(BloqueIF.class));
    }
    /*
    Verifica si el bloque actual (BloqueIF) tiene un bloque siguiente (Siguiente()) y si la condición asociada a ese bloque 
    siguiente (evaluarSiguiente()) es verdadera. Si la condición se cumple se llama al método super.Hacer(). Esto implica que
    el bloque "IF" se considera verdadero y su lógica se ejecuta.
    Sino se verifica si la siguiente línea (SiguienteLinea()) es un bloque BloqueElse o un bloque BloqueElif.
    Si es alguno de estos dos casos, se establece el ejecutador de la siguiente línea (SiguienteLinea().ejecutador) para que sea
    el mismo que el ejecutador del bloque actual (ejecutador). Luego, se llama al método Hacer() de la siguiente línea. Esto corresponde
    al comportamiento de un bloque "IF" cuando la condición es falsa y hay una rama "Else" o "Elif" que debe ejecutarse en su lugar.
     */

    @Override
    public void Hacer() {
        this.LineaEjecutador();

        if (Siguiente() == null) {
            this.chorizontal.NecesitaSiguiente();
            if (SiguienteLinea() != null) {
                SiguienteLinea().ejecutador = ejecutador;
                SiguienteLinea().Hacer();
            }
            return;
        }

        if (evaluarSiguiente()) {
            // Ejecutar la lógica del bloque IF
            System.out.println("EJECUTANDO SUPER");
            super.Hacer();

            if (SiguienteLinea() != null) {
                SiguienteLinea().ejecutador = ejecutador;
                SiguienteLinea().Hacer();
            }
        } else if (SiguienteLinea() != null) {
            this.hacerSiguiente();
            SiguienteLinea().Hacer();
        }
    }


}
