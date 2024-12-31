
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import charly.projects.handprogrammingf.Model.EvaluadorExpresiones;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;


public class BloqueFor extends BloqueCondicional{


    public BloqueFor(double x, double y) {
        super(x, y, ConstantesDeBloques.NombresBloques.get(BloqueFor.class)[ConstantesDeBloques.IdiomaSeleccionado], ConstantesDeBloques.ColoresBloques.get(BloqueFor.class));
    }

    public boolean isIncreaseSet = false;
    //u ->{ return (u < 5);}
    public double TheGeneralCounter = 0.0;
    public BloqueVariable CounterVariable = null;
    private double TargetValue = 5;
    public double Change = 1;


    @Override
    public boolean evaluarSiguiente(){
        int changeSign = (Change<0)?-1:1;
        if (CounterVariable == null){
            System.out.println("CONDICION FOR: " + (TheGeneralCounter* changeSign<TargetValue*changeSign));
            return TheGeneralCounter* changeSign<TargetValue*changeSign;
        } else {
            System.out.println("CONDICION FOR: " + (NumberOf(CounterVariable)* changeSign<TargetValue*changeSign));
            return NumberOf(CounterVariable)* changeSign<TargetValue*changeSign;
        }
    }


    public void counterIncrease(){
        if (CounterVariable == null){
            TheGeneralCounter+=Change;
        } else {
            ejecutador.setValor(CounterVariable,NumberOf(CounterVariable)+Change+"");
        }
    }

    @Override
    public void Hacer(){
        this.LineaEjecutador();
        if (Siguiente() == null) this.chorizontal.NecesitaSiguiente();

        //Coloca todas las variables del padre aquí
        if (ejecutador.variables !=  null) variables.putAll(ejecutador.variables);

        //Incrementa si ya está inicializado
        if (isIncreaseSet) counterIncrease();


        System.out.println(TheGeneralCounter+" : " + TargetValue + "ESTÁ IMPLEMENTADA ? " + isIncreaseSet);

        //Cuando necesita inicializar
        if (!isIncreaseSet){
            isIncreaseSet = true;

            //Primer caso solo un numero
            if (EvaluadorExpresiones.InstBloqueValor(sig(1)) && BloqueValor.GetType(sig(1).getValor()).equals("Num") && sig(2) == null){
                TheGeneralCounter = 0;
                TargetValue = NumberOf(sig(1));
                Change = 1;
                CounterVariable = null;
                Hacer();
            }

            //Segundo caso
            else if (sig(1) instanceof BloqueVariable && EvaluadorExpresiones.InstBloqueValor(sig(2))
                    && BloqueValor.GetType(sig(2).getValor()).equals("Num")
                    && sig(3) == null){
                TheGeneralCounter = 0;
                TargetValue = NumberOf(sig(2));
                ejecutador.setValor((BloqueVariable)sig(1), "0");

                int cambio = (int) NumberOf(sig(2));
                cambio = Integer.signum(cambio);

                Change = cambio;

                CounterVariable = ((BloqueVariable)sig(1));
                Hacer();
            }


            //Tercer caso
            else if (sig(1) instanceof BloqueVariable && EvaluadorExpresiones.InstBloqueValor(sig(2)) && EvaluadorExpresiones.InstBloqueValor(sig(3))
                    && BloqueValor.GetType(sig(2).getValor()).equals("Num") && BloqueValor.GetType(sig(3).getValor()).equals("Num")
                    && sig(4) == null){

                TheGeneralCounter = 0;
                TargetValue = NumberOf(sig(3));
                ejecutador.setValor((BloqueVariable)sig(1), sig(2).getValor());

                int cambio = (int)(NumberOf(sig(3)) - NumberOf(sig(2)));
                cambio = Integer.signum(cambio);

                Change = cambio;

                CounterVariable = ((BloqueVariable)sig(1));
                Hacer();
            }


            //cuarto caso
            else if (sig(1) instanceof BloqueVariable && EvaluadorExpresiones.InstBloqueValor(sig(2)) && EvaluadorExpresiones.InstBloqueValor(sig(3)) && EvaluadorExpresiones.InstBloqueValor(sig(4))
                    && BloqueValor.GetType(sig(2).getValor()).equals("Num") && BloqueValor.GetType(sig(3).getValor()).equals("Num") && BloqueValor.GetType(sig(4).getValor()).equals("Num")){

                TheGeneralCounter = 0;
                TargetValue = NumberOf(sig(3));
                ejecutador.setValor((BloqueVariable)sig(1), sig(2).getValor());

                double cambio = (NumberOf(sig(3)) - NumberOf(sig(2)));
                cambio = (cambio>0)? 1 : -1;
                cambio *= Math.abs(NumberOf(sig(4)));

                Change = cambio;

                CounterVariable = ((BloqueVariable)sig(1));
                Hacer();
            }
            //Sino está mal
            else{
                this.ponerRojo(sig(1));
                isIncreaseSet=false;
            }

        }

        // Cuando si está inicializado
        else if (evaluarSiguiente()){
            super.Hacer();
        }

        //  Cuando ya terminó la vuelta
        else {
            if (CounterVariable != null) ejecutador.deleteValor(CounterVariable);
            isIncreaseSet=false;
            CounterVariable=null;
            super.vaciarVariables();

            //Ejecutar lo siguiente
            hacerSiguiente();
        }
    }
    
    @Override
    public void vaciarVariables() {
        variables.clear();
        limpiarEjecutadores();
        
        //Ejecutar la siguiente linea
        Hacer();
    }
    
    
    
    public Bloque sig(int n){
        Bloque ac = this;
        for (int i = 0; i < n; i++) {
            ac = ac.Siguiente();
        }
        return ac;
    }
    
    public double NumberOf(Bloque n){
        return Double.parseDouble(n.getValor());
    }
    
    
    
}
