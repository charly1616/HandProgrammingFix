
package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class CreadorDeBloques {
    
    public GridController cuadricula;
    
    public CreadorDeBloques(GridController panel){
        this.cuadricula = panel;
    }
    
    
    
    //Crea un Bloque General, es una guia
    public void crearBloque(Color c, int x, int y) {
        Bloque p = new Bloque(x, y);
        cuadricula.hacerBloqueMovible(p);
        if (p.chorizontal != null) cuadricula.Grid.getChildren().add(p.chorizontal);
        p.setAncho(150);
        cuadricula.Grid.getChildren().add(p.cvertical);
        if (p.cvertical != null) {
            cuadricula.Grid.getChildren().add(p.cvertical);
        }
        cuadricula.Grid.getChildren().add(p);
        cuadricula.bloques.add(p);
    }

    public Bloque BloqueInicio(Double x, Double y){
        Bloque p = new BloqueInicio(x,y);
        añadir(p);
        return p;
    }


    public Bloque BloqueWhile(Double x, Double y) {
        Bloque p = new BloqueWhile(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueMostrar(Double x, Double y) {
        Bloque p = new BloqueMostrar(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueIF(Double x, Double y) {
        Bloque p = new BloqueIF(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueElif(Double x, Double y) {
        Bloque p = new BloqueElif(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueElse(Double x, Double y) {
        Bloque p = new BloqueElse(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueValor(Double x, Double y, String valor) {
        BloqueValor p = new BloqueValor(x, y,valor);
        añadir(p);
        return p;
    }

    public Bloque BloqueFor(Double x, Double y) {
        Bloque p = new BloqueFor(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueVariable(Double x, Double y, String valor) {
        BloqueVariable p = new BloqueVariable(x, y,valor);
        añadir(p);
        return p;
    }

    public Bloque BloquePedir(Double x, Double y) {
        Bloque p = new BloquePedir(x, y);
        añadir(p);
        return p;
    }

    public Bloque BloqueLogico(Double x, Double y, String signo) {
        Bloque p = new BloqueLogico(x, y, signo);
        añadir(p);
        return p;
    }

    public Bloque BloqueLMat(Double x, Double y, String signo) {
        Bloque p = new BloqueLMat(x, y, signo);
        añadir(p);
        return p;
    }

    public Bloque BloqueOPMAT(Double x, Double y, String signo) {
        Bloque p = new BloqueMat(x, y, signo);
        añadir(p);
        return p;
    }
    
    public void añadir(Bloque p){
        cuadricula.hacerBloqueMovible(p);
        cuadricula.añadirBloque(p);
        p.aparecer();
    }
    
}
