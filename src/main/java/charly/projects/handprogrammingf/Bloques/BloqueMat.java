package charly.projects.handprogrammingf.Bloques;

import static charly.projects.handprogrammingf.Bloques.BloqueValor.GetType;
import static charly.projects.handprogrammingf.Bloques.BloqueValor.esNumero;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import charly.projects.handprogrammingf.Model.Bloque;
import javafx.scene.paint.Color;

public class BloqueMat extends BloqueOP {

    public BloqueMat(double x, double y, String signo) {
        super(x, y, signo, Color.rgb(192, 81, 247));
    }

    @Override
    public void IniciarComponentes() {
        super.IniciarComponentes();
    }

    
    
    
    /*
    Determina si es posible conectar dos bloques a un bloque BloqueMat de una manera que sea coherente
    Si ambos bloques conectados son instancias de BloqueValor o BloqueVariable
    la conexión se considera posible; de lo contrario, se considera inválida.
    */
    @Override
    public boolean PosibleConex() {
        Bloque bloqueIzquierdo = conectado != null ? conectado.conectador : null;
        Bloque bloqueDerecho = chorizontal != null && chorizontal.conexion != null ? chorizontal.conexion : null;

        if ((bloqueIzquierdo instanceof BloqueValor || bloqueIzquierdo instanceof BloqueVariable) &&
         (bloqueDerecho instanceof BloqueValor || bloqueDerecho instanceof BloqueVariable)) {
            return true;
        }
        return false;
    }

    
    
    
    @Override
    public boolean Debug() {
        //Obtención de bloques conectados
        Bloque bloqueIzquierdo = conectado != null ? conectado.conectador : null;
        Bloque bloqueDerecho = chorizontal != null && chorizontal.conexion != null ? chorizontal.conexion : null;

        // Obtén los valores de los bloques izquierdo y derecho
        String valorIzquierdo = (bloqueIzquierdo).getValor();
        String valorDerecho = bloqueDerecho.getValor();

        
        boolean esNumeroIzquierdo = esNumero(valorIzquierdo);
        boolean esNumeroDerecho = esNumero(valorDerecho);

        
        /* Si ambos valores son números, entonces usa GetType
         Si ambos valores son números, se obtienen los tipos de datos de los números. 
        Esto se hace utilizando la función GetType(valor)
        */
        if (esNumeroIzquierdo && esNumeroDerecho) {
            String tipoIzquierdo = GetType(valorIzquierdo);
            String tipoDerecho = GetType(valorDerecho);
            System.out.println("Tipo Bloque Izquierdo: " + tipoIzquierdo);
            System.out.println("Tipo Bloque Derecho: " + tipoDerecho);
       
            
        /*
        Si uno de los valores no es un número (por ejemplo, si esNumeroIzquierdo o esNumeroDerecho es false)
        se establece un indicador de error en el bloque correspondiente (usando setError(true)).
        Si ambos valores son números, se establece que no hay errores (usando setError(false)).
        */
        } else if (!esNumeroDerecho ) {
            chorizontal.conexion.setError(true);
        }else if (!esNumeroIzquierdo) {
            conectado.conectador.setError(true);
        }else {
            chorizontal.conexion.setError(false);
            conectado.conectador.setError(false);
        }
        return true;
    }


}
