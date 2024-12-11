
package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.BloqueEjecutable;
import charly.projects.handprogrammingf.Bloques.BloqueInicio;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Bloque extends Pane{



    public static int IDBloqueMax = 0;
    public int IDBloque;

    public BloqueEjecutable ejecutador;
    
    public Conector conectado = null;
    public Conector chorizontal;
    
    public Conector conectadov = null;
    public Conector cvertical;
    
    //Componentes
    public Rectangle SidePart;
    public Rectangle TopPart;
    
    //Constantes
    public static final int ALTO = 77;
    
    
    //Propiedades
    public double TamBorde;
    public double ancho;
    
    public double x;
    public double y;
    
    
    public static final Color ColorBordeGlobal = Color.BLACK;
    
    public Color ColorBloque;
    public Color ColorBorde;
    
    
    public double mouseAnchorX;
    public double mouseAnchorY;
    public double LastX;
    public double LastY;
    
    public boolean Inconectableh; //No se puede conectar a otros
    public boolean Inconectablev;
    
    //Constructor
    public Bloque(double x, double y, Color ColorBloque) {
        setID(IDBloqueMax);
        this.x = x;
        LastX = x;
        this.y = y;
        LastY = y;
        this.ColorBloque = ColorBloque;
        
        this.ColorBorde = Bloque.ColorBordeGlobal;
        this.TamBorde = 4;
        this.ancho = 50+ Math.random()*120;
        
        
        IniciarComponentes();
        Pintar();
    }
    
    public void setID(int ID){
        this.IDBloque = ID;
        if (ID >= IDBloqueMax){
            IDBloqueMax = ID +1;
        }
    }
    
    public Bloque(double x, double y) {
        this.x = x;
        LastX = x;
        this.y = y;
        LastY = y;
        
        this.ColorBorde = Bloque.ColorBordeGlobal;
        this.TamBorde = 4;
    }
    
    
    
    public void IniciarComponentes(){
        chorizontal = new Conector(this, "h");
        cvertical = new Conector(this, "v");
        
        
        SidePart = new Rectangle();
        TopPart = new Rectangle();
        
        //Redondeado
        SidePart.setArcHeight(30);
        SidePart.setArcWidth(30);
        TopPart.setArcHeight(30);
        TopPart.setArcWidth(30);
        
        //Liso
        SidePart.setSmooth(true);
        TopPart.setSmooth(true);
        
        //Posicion de los recs
        TopPart.setLayoutX(7);
        TopPart.setLayoutY(7);
        TopPart.setHeight(56);
        
        SidePart.setLayoutX(7);
        SidePart.setLayoutY(27);
        SidePart.setHeight(50);
        
        //Se añaden los rectangulos
        getChildren().add(SidePart);
        getChildren().add(TopPart);
        
        setHeight(77);
        
        
         
    }
    
    
    public void DesactivarVertical() {if (cvertical != null) cvertical.Desactivar();}
    public void ActivarVertical() {if (cvertical != null) cvertical.Activar();}
    public void DesactivarHorizontal() {if (chorizontal != null) chorizontal.Desactivar();}
    public void ActivarHorizontal() {if (chorizontal != null) chorizontal.Activar();}
    
    /*Hace: Permite arrastrar el bloque con el maus
      Se pone el borde un poco mas grande y el color cambia a azul
    */
    public void Agarrado(){
        AlFrente();
        
        this.AgarrarBien(this);
        
        this.chorizontal.ocultarPreBloque();
        this.cvertical.ocultarPreBloque();
        if (this.cvertical.multiconectador != null) this.cvertical.multiconectador.ocultarPreBloque();
        
        
        if (conectado != null){
            conectado.Desconectar();
            conectado = null;
        }
        if (conectadov != null){
            conectadov.Desconectar();
            conectadov = null;
        } 
    }
    
    
    //Hace: Permite llevar el bloque al frente haciendolo visible
    public void AlFrente(){
        toFront();
        if (chorizontal != null && chorizontal.conexion != null){
            chorizontal.conexion.AlFrente();
        }
        if (cvertical != null && cvertical.conexion != null){
            cvertical.conexion.AlFrente();
        }
    }
    
    
    //Cuando se suelta se pone el bloque normal (tamaño y color de borde(negro))
    public void Soltado(){
        this.SoltarBien(this);
    }
    
    
    
    /*Calcula y devuelve las coordenadas de los vértices de un rectángulo 
      en función de la posición actual del objeto.
    */
    public double[] getRecVertices(){
        double [] c = {9+getX(), 8+getY(),ancho+7+getX(),this.getHeight()+getY()-18};
        return c;
    }
            
    
    //Recibe las posiciones actuales del bloque y permite cambiar estas mismas
    public void setPosicion(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        this.x = x;
        this.y = y;
        if (chorizontal != null) chorizontal.fixPosicion();
        if (cvertical != null) cvertical.fixPosicion();
    }
    
    public double getX(){
        return getLayoutX();
    }
    
    public double getY(){
        return getLayoutY();
    }
    
    
    /*Recibe: Color que se desea para el bloque
      Hace: Le pone el color a el bloque y un tono mas oscuro para la "base"
    */
    public void setColorBloque(Color c){
        ColorBloque=c;
        
        TopPart.setFill(c);
        SidePart.setFill(c.darker().darker()); //La parte de abajo es un color mas oscuro
    }
    
    
    /*Recibe: Color que se desea para el borde del bloque
      Hace: Le cambia el color a el borde del bloque
    */
    public void setColorBorde(Color s){
        if (TopPart!=null && SidePart!=null) {
        TopPart.setStroke(s);
        SidePart.setStroke(s);
        }
    }
    
    
    /*Recibe: Tamaño del borde que se desea
      Hace: Si existe el bloque le cambia el tamaño que se puso.
    */
    public void setTamBorde(double b){
         if (TopPart!=null && SidePart!=null) {
        TopPart.setStrokeWidth(b);
        SidePart.setStrokeWidth(b);
         }
    }
    
    /*Permite Extender el bloque asegurarse de que sus componentes visuales 
    se actualicen en consecuencia para reflejar el nuevo tamaño
    */
    public void setAncho(double x){
        this.ancho = x;
        TopPart.setWidth(this.ancho);
        SidePart.setWidth(this.ancho);
        setPrefWidth(this.ancho + 15);
        
        if (chorizontal != null){
            chorizontal.fixPosicion();
        }
        if (cvertical != null){
            cvertical.fixPosicion();
        }
    }
    
    
    //Largo de la columna de bloques no identados
    public int LargoConexion() {
        int contador = 0;
        Bloque bloqueActual = this;

        while (bloqueActual != null && bloqueActual.cvertical != null && bloqueActual.cvertical.conexion != null) {
            contador++;
            bloqueActual = bloqueActual.cvertical.conexion;
        }
        return contador;
    }

    
    
    /* 
    Funciona como LargoConexion pero "mejor"
    Diferencia: Este tiene en cuenta los bloques que estan "dentro de otros" 
     y tambien los espacios que van despues del ultimo bloque de forma vertical 
    */
    public int LargoConexionMultiple() {
        int contador = 0;  //Propaga la deteccion del largo de las conexiones a los identados
        if (cvertical.inner != null && cvertical.inner.conexion != null){
            contador += cvertical.inner.conexion.LargoConexionMultiple()+1;
        } else if (cvertical.inner != null){
            contador++;
        }

        //Coloca un largo adicional para los vacios o continua contando los siguientes
        
        if (cvertical.conexion != null) contador += cvertical.conexion.LargoConexionMultiple();
        contador++;

        return contador;
    }
 
    
    //Conecta las propiedades con el bloque
    public void Pintar(){
        
        setStyle("-fx-background-color: rgba(0, 100, 100, 0); -fx-background-radius: 10;");
        
        //Aplicacion del color
        Color c = this.ColorBloque;
        TopPart.setFill(c);
        SidePart.setFill(c.darker().darker()); //La parte de abajo es un color mas oscuro
        
        Color s = this.ColorBorde;
        setColorBorde(s);
        
        //Colocar el tamaño de borde
        setTamBorde(this.TamBorde);
        
        
        //ancho de los rectangulos
        TopPart.setWidth(this.ancho);
        SidePart.setWidth(this.ancho);
        setPrefWidth(this.ancho + 15);
        
        //la posicion del bloque
        setLayoutX(this.x);
        setLayoutY(this.y);
        
//        double[] d = this.getRecVertices();
//        Circle t = new Circle(d[0],d[1],6);
//        getChildren().add(t);
//        t = new Circle(d[0],d[3],6);
//        getChildren().add(t);
//        t = new Circle(d[2],d[1],6);
//        getChildren().add(t);
//        t = new Circle(d[2],d[3],6);
//        getChildren().add(t);
        
    }
    
    
    //No se hace nada aqui porque se va a sobreescribir
    public void Hacer(){
        hacerSiguiente();
    }
    
    
    public void hacerSiguiente(){
        this.setColorBorde(Color.GREENYELLOW);
        this.setTamBorde(8);
        
        final Bloque u = this;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                u.setColorBorde(Bloque.ColorBordeGlobal);
                u.setTamBorde(4);
                
                
                Bloque b = SiguienteLinea();
                if (b == null){
                    ejecutador.vaciarVariables();
                    return;
                }
                b.ejecutador = ejecutador;
                b.Hacer();
                
            }
        }, 600);
    }
    
    
    
    
    //Hace: Pasa al siguiente bloque de la misma linea de manera horizontal
    public Bloque Siguiente(){
        return chorizontal.conexion;
        
    }
      
    
    //Hace: Pasa a la siguiente linea de bloque de manera vertical.
    public Bloque SiguienteLinea(){
        return cvertical.conexion;
    }
 
    
    
    //Recibe un booleano, si esta malo pone el bloque en rojo, sino vuelve a su color de borde natural.
    public void setError(boolean error) {
        if (error) {
            setColorBorde(Color.RED); // Cambiar el color del borde a rojo
            setTamBorde(6); // Cambiar el grosor del borde a 6
        } else {
            setColorBorde(Bloque.ColorBordeGlobal);
            setTamBorde(4);
        }
    }
   
    
    /*
    Comienza desde el bloque actual y luego explora todos los bloques 
    conectados horizontalmente y verticalmente
    */
    public boolean Debug() {
        boolean deb = true;
        if (cvertical.conexion!=null) deb = deb && this.cvertical.conexion.Debug();
        if (cvertical.multiconectador != null && cvertical.multiconectador.getConexion() != null) deb = deb && cvertical.multiconectador.getConexion().Debug();
        return deb;
    }

    
    /*Recibe un bloque, si existe le cambia el tamaño del borde 
     y el color cambia a rojo */
    public void ponerRojo(Bloque bloque) {
        if (bloque == null) {
            return;
        }
        bloque.setColorBorde(Color.RED);
        bloque.setTamBorde(8);

        if (bloque.chorizontal.conexion != null) {
            ponerRojo(bloque.chorizontal.conexion);
        }
    }
    
    
    public void AgarrarBien(Bloque bloque){
        if (bloque == null) {
            return;
        }
        bloque.setColorBorde(Color.DODGERBLUE);
        bloque.setTamBorde(6);
        
        
        
        if (bloque.chorizontal.conexion != null) {
            AgarrarBien(bloque.chorizontal.conexion);
        }
        if (bloque.cvertical.conexion != null) {
            AgarrarBien(bloque.cvertical.conexion);
        }
    }
    
    
    public void SoltarBien(Bloque bloque){
        if (bloque == null) {
            return;
        }
        bloque.setColorBorde(Bloque.ColorBordeGlobal);
        bloque.setTamBorde(4);
        
        bloque.chorizontal.ocultarPreBloque();
        
        
        if (bloque.chorizontal.conexion != null) {
            SoltarBien(bloque.chorizontal.conexion);
        }
        if (bloque.cvertical.conexion != null) {
            SoltarBien(bloque.cvertical.conexion);
        }
    }
    
    
    public String getValor(){
        return "";
    }
    
     public void setEjecutador(BloqueEjecutable b){
        this.ejecutador = b;
    }
    
     /*
        Recibe nada
        Devuelve nada
     Hace: establece el ejecutador de todos los bloques siguientes a el ejecutador de aquí
     */
    public void LineaEjecutador() {

        Bloque bloqueActual = Siguiente();

        // Recorre todos los bloques en la misma línea horizontal
        for (; bloqueActual != null; bloqueActual = bloqueActual.Siguiente()) {
            bloqueActual.setEjecutador(this.ejecutador);
        }
    }

    
    
    public void aparecer(){
        ScaleTransition scaleTransitionSide = createScaleTransition(SidePart);
        ScaleTransition scaleTransitionTop = createScaleTransition(TopPart);
        ScaleTransition s1 = createScaleTransition(chorizontal.linea);
        ScaleTransition s2 = createScaleTransition(cvertical.linea);
        
        
        s1.play();
        s2.play();
        scaleTransitionSide.play();
        scaleTransitionTop.play();
    }

    public ScaleTransition createScaleTransition(Node rectangle) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), rectangle);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1.0);    
        scaleTransition.setToY(1.0);    

        return scaleTransition;
    }
    
     
    
}
