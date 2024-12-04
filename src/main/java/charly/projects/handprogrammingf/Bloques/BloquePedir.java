package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane; // Importa la clase para mostrar el diálogo de entrada.

public class BloquePedir extends BloqueLabel {

    public BloquePedir(double x, double y) {
        super(x, y, Color.rgb(158, 160, 40),"Pedir");
        this.Inconectableh = true;
        IniciarComponentes();
        Pintar();
    }

    @Override
    public void IniciarComponentes() {
        super.IniciarComponentes();
    }

    @Override
    public void Pintar() {
        super.Pintar();
    }
    
    
    @Override
    public void Hacer(){
        this.LineaEjecutador();
        Bloque bloqueVariable =  Siguiente();
        // Validación para asegurarse de que se proporciona un BloqueVariable.
        if (bloqueVariable == null) {
            this.chorizontal.NecesitaSiguiente();
            super.Hacer();
            return;
        }
        if (!(bloqueVariable instanceof BloqueVariable)){
            this.ponerRojo(bloqueVariable);
            super.Hacer();
            return;
        }
        
        //Si hay mas bloques innecesarios se colocan en rojo
        if (bloqueVariable.Siguiente() != null) this.ponerRojo(bloqueVariable.Siguiente());
        
        
        capturarValor((BloqueVariable) bloqueVariable);
        super.Hacer();
    }
    
    
    public void capturarValor(BloqueVariable bloqueVariable) {
        // Utiliza JOptionPane para mostrar un cuadro de diálogo de entrada y capturar un valor.
        String valor = JOptionPane.showInputDialog("Ingrese un valor:");
        
        // Verifica si el usuario no canceló el diálogo.
        if (valor != null) {
            bloqueVariable.setValor(valor);
        }
    }
}
