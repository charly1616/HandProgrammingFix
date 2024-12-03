
package charly.projects.handprogrammingf.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ConectorMultiple extends Conector{
    
    
    
    public double largoConector=0;
    
    
    /* 
        Recibe: (Bloque conectador) que es el bloque al que está conectado
        Devuelve: (void) (Nada)
        Hace: llama al constructor papa y hace que el conectador sea siempre vertical, hace que "conectador" no se pueda conectar horizontalmente
        llama a dos funciones -({setLargoLinea}{setPreBloqueY}) para que se ajusten las posiciones del prebloque y la linea
        
    */
    public ConectorMultiple(Bloque conectador) {
        super(conectador, "v");
        
        setLargoLinea();
        setPreBloqueY();
        conectador.Inconectableh = true;
//        Circle cir = new Circle(inner.getLayoutX(), inner.getLayoutY(),5);
//        cir.setFill(Color.RED);
//        getChildren().add(cir);
    }
    
    
    
    
    /*  Metodo sobreescrito
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: llama al metodo papá y añade al conector "inner", ademas le coloca sus caracteristicas respectivas y ajusta al prebloque 
        
    */
    @Override
    public void iniciarComponentes(){
        super.iniciarComponentes();
        inner = new Conector(this,"h",true);
        inner.multiconectador = this;
        inner.linea.setStartX(45);
        inner.identable = true;
        inner.setPosicion(0, 0);
        inner.fixPosicion();
        setPreBloqueY();
        
    }
    
    
    
    /* 
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: establece el largo de la "linea", la linea es la punteada negra | que se muestra
    */
    public final void setLargoLinea(){
        linea.setEndY(altoLinea());
    }
    
    
    
    /* 
        Recibe: (Nada)
        Devuelve: (double) el largo que se cacula
        Hace: devuelve cual es el largo de la linea
    */
    public double altoLinea(){
        return (Bloque.ALTO-18)*(largoConector)+80;
    }
    
    
    
    /* 
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: establece la posicion Y de los rectangulos que representan al prebloque, esta depende del valor -(Metodo {altoLinea})
        se usa porque la linea | está cambiando de tamaño constantemente y hay que hacer que el prebloque esté al final de la linea
    */
    public final void setPreBloqueY(){
        this.SidePart.setY(-27 + altoLinea());
        this.TopPart.setY( -7 + altoLinea());
    }
    
    
    
    /* Metodo que se sobreescribe porque hay que cambiar tambien la posicion del "inner"
        Recibe: (double x,y) que son las posiciones donde se va a poner el conector
        Devuelve: (void) (Nada)
        Hace: establece la posicion Y de los rectangulos que representan al prebloque, esta depende del valor -(Metodo {altoLinea})
        se usa porque la linea | está cambiando de tamaño constantemente y hay que hacer que el prebloque esté al final de la linea
    */
    @Override
    public void setPosicion(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        
        if (conexion != null) conexion.setPosicion(x, y+altoLinea());
        if (inner != null) inner.setPosicion(x+conectador.ancho/2.0, y);
        fixPosicion();
    }
    
    
    
    /* Metodo que se sobreescribe porque hay que ajustar tambien la posicion del "inner"
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: se coloca al lado de su bloque "conectador" y coloca a "inner" al lado de la linea
        como los bloques se cambian de posicion, hay que mover a sus conectores tambien
    */
    @Override
    public void fixPosicion(){
        setLargoLinea();
        setPreBloqueY();
                
        setLayoutX(conectador.getX() + offX);
        setLayoutY(conectador.getY() + (Bloque.ALTO-15));
        if (conexion != null) {
            conexion.setPosicion(this.getLayoutX(), this.getLayoutY()-27+ altoLinea());
        }
        
        if (inner != null){
            inner.setPosicion(this.getLayoutX()+conectador.ancho/2.0, getLayoutY());
        }
        
        linea.setLayoutX(-8);
        linea.setLayoutY(0);

    }
    
    
    
    /* Metodo que se sobreescribe porque hay que evitar que se pueda ocultar la linea, no es necesario para el conectador Multiple
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: (Nada)
    */
    @Override
    public void ocultarLinea(){
    }
    
    
    
    
    /* Metodo que se sobreescribe porque los vertices de la conexion dependen del largo de la linea |, los vertices van a estár al final de la linea
        Recibe: (Nada)
        Devuelve: (double[]) un array con los puntos de los vertices del conector multiple
        Hace: calcula los puntos de los vertices del conector multiple dependiendo de la altura y lo devuelve
    */
    @Override
    public double[] getRectVertices(){
        double [] d = {getX()+7, getY()+altoLinea(), getX()+conectador.ancho/2.0, getY()+(Bloque.ALTO-15)/2.0+altoLinea()};
        return d;
    }
    
}
