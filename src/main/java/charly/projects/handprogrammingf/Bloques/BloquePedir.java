package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane; // Importa la clase para mostrar el diálogo de entrada.

public class BloquePedir extends BloqueLabel {


    public BloquePedir(double x, double y) {
        super(x, y, ConstantesDeBloques.ColoresBloques.get(BloquePedir.class)
                , ConstantesDeBloques.NombresBloques.get(BloquePedir.class)[ConstantesDeBloques.IdiomaSeleccionado]);
        setID(IDBloqueMax);
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
    


    private BloqueVariable currentVariable;
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
        

        currentVariable = (BloqueVariable) bloqueVariable;
        Bloque.GlobalController.basicConsole.createInput(this);
    }
    
    
    public void capturarValor(String s) {

        // Verifica si el usuario no canceló el diálogo.
        if (s != null) {
            currentVariable.setValor(s);
        }
        hacerSiguiente();
    }
}
