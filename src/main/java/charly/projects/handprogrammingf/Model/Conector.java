
package charly.projects.handprogrammingf.Model;

import Bloques.BloqueInicio;
import Bloques.BloqueOP;
import Bloques.BloqueValor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;


public class Conector extends Pane{
    
    //Componentes
    public Rectangle SidePart;
    public Rectangle TopPart;
    public Line linea;
    
    public boolean activado;
    
    public String modo;
    
    
    //Propiedades
    public double ancho = 50;
    
    
    
    public static final Color ColorLineaGlobal = Color.color(1,1,1,1);
    public static final Color ColorLineaPre = new Color(1,0.2,1,0.05);
    
    
    public double grosorLinea;
    public Color ColorLinea;
    
    
    public double offX;
    
    public Bloque conectador;
    public Bloque conexion;
    
    public ConectorMultiple multiconectador;
    public Conector inner;
    
    
    public boolean identable; //Los que se conectan no pierden su habilidad de cVertical
    
    
    //Constructores:
    
    //Constructor para el vertical
    public Conector(Bloque conectador, String modo, boolean activado, double offX){
        this.modo = modo;
        this.conectador = conectador;
        this.activado = activado;
        this.offX = offX;
        conexion = null;
        iniciarComponentes();
        pintar();
    }
    
    //Constructor para el Multiple
    public Conector(ConectorMultiple conectador, String modo, boolean activado){
        this.modo = modo;
        this.conectador = null;
        this.multiconectador = conectador;
        this.activado = activado;
        conexion = null;
        this.offX = 0;
        iniciarComponentes();
        pintar();
    }
    
    //Constructor para el Horizontal
    public Conector(Bloque conectador, String modo){
        this.modo = modo;
        this.conectador = conectador;
        this.activado = true;
        this.offX = 0;
        conexion = null;
        iniciarComponentes();
        pintar();
    }
    
    /*
     se encarga de la configuración inicial de la apariencia y posición del conector en la interfaz gráfica
     asegurando que esté listo para ser mostrado y conectado visualmente a otros elementos en el programa
    */
    public void iniciarComponentes(){
        
        if (conectador != null){
            if (modo.equals("h")){
                setHeight(Bloque.ALTO);
                setWidth(ancho);
                setLayoutX(   conectador.getX()+conectador.getWidth()-5+ offX );
                setLayoutY(conectador.getY()+7);
            } else {
                setHeight(Bloque.ALTO);
                setWidth(conectador.ancho);
                setLayoutX(conectador.getX()+7);
                setLayoutY(conectador.getHeight());
            }
        } else { //
            setHeight(Bloque.ALTO);
            setWidth(ancho);
            setLayoutX(   multiconectador.getLayoutX()-5+ offX);
            setLayoutY(multiconectador.getLayoutY()+7    );

        }
        ColorLinea = Conector.ColorLineaGlobal;
        grosorLinea = 8;
        
        
        if (modo.equals("h")){
            if (conectador == null){
                SidePart = new Rectangle(55+7,20-8, 50,50);
                TopPart = new Rectangle(55+7,0-8, 50,56);
            } else {
                SidePart = new Rectangle(7,27, 50,50);
                TopPart = new Rectangle(7,7, 50,56);
            }
            
        }else {
            SidePart = new Rectangle(7,15, 50,50);
            TopPart = new Rectangle(7,-5, 50,56);
        }
        
        SidePart.setFill(Color.color(0, 0, 0, 0));
        TopPart.setFill(Color.color(0, 0, 0, 0));
        
        TopPart.setStroke(Conector.ColorLineaGlobal);
        SidePart.setStroke(Conector.ColorLineaGlobal);
        TopPart.setStrokeLineCap(StrokeLineCap.ROUND);
        SidePart.setStrokeLineCap(StrokeLineCap.ROUND);
        
        SidePart.setStrokeWidth(4);
        TopPart.setStrokeWidth(4);
        
        //Redondeado
        SidePart.setArcHeight(20);
        SidePart.setArcWidth(20);
        TopPart.setArcHeight(20);
        TopPart.setArcWidth(20);
        
        //Liso
        SidePart.setSmooth(true);
        TopPart.setSmooth(true);
        
        
        SidePart.toBack();
        TopPart.toBack();
        
        //Punteado
        SidePart.getStrokeDashArray().addAll(5d, 10d);
        TopPart.getStrokeDashArray().addAll(5d, 10d);
        
        SidePart.setVisible(false);
        TopPart.setVisible(false);
        
        this.setMouseTransparent(true);
        
        getChildren().add(SidePart);
        getChildren().add(TopPart);
        
    }
    
    
    
    /*
     Se encarga de establecer la apariencia visual de un conector incluyendo la línea
     de conexión que se muestra entre el conector y otro elemento cuando está activado.
    */
    public void pintar(){
        this.toBack();
        setStyle("-fx-background-color: rgba(0, 100, 100, 0); -fx-background-radius: 10;");

        if (modo.equals("h")) {
            if (conectador == null) {
                linea = new Line(0, Bloque.ALTO / 2 - 7, 70, Bloque.ALTO / 2 - 7);
            } else {
                linea = new Line(0, Bloque.ALTO / 2, 40, Bloque.ALTO / 2);
            }
        } else { // linea cuando el conector es inner
            linea = new Line(47, 0, 47, 40);

        }
        linea.setStrokeWidth(grosorLinea);
        linea.setStroke(ColorLinea);
        linea.getStrokeDashArray().addAll(5d, 10d);
        linea.setStrokeLineCap(StrokeLineCap.ROUND);

        if (!activado) {
            linea.setVisible(false);
        }
        
        
        
        
        getChildren().add(linea);
        fixPosicion();

//        double[] d = this.getRectVertices();
//        Circle t = new Circle(d[0] - getLayoutX(), d[1] - getLayoutY(), 6);
//        getChildren().add(t);
//        t = new Circle(d[0] - getLayoutX(), d[3] - getLayoutY(), 6);
//        getChildren().add(t);
//        t = new Circle(d[2] - getLayoutX(), d[1] - getLayoutY(), 6);
//        getChildren().add(t);
//        t = new Circle(d[2] - getLayoutX(), d[3] - getLayoutY(), 6);
//        getChildren().add(t);
    }

    
    /* 
    Cuando un conector está desactivado, su línea de conexión no es visible 
    y no se pueden realizar nuevas conexiones con él 
     */
    public void Desactivar() {
        this.activado = false;
        this.ocultarLinea();
    }

    //Cuando se activa, la línea de conexión se vuelve visible y el conector está listo para aceptar nuevas conexiones
    public void Activar() {
        this.activado = true;
        this.mostrarLinea();
    }

    //Cambia el color del borde al que se desea
    public void setColorBorde(Color c) {
        linea.setStroke(c);
        ColorLinea = c;
    }
 
    //Cambia el grosor de linea al que se desea
    public void setGrosorLinea(double d) {
        linea.setStrokeWidth(d);
        grosorLinea = d;
    }
    
    //Permite actualizar la posicion cuando se mueve un bloque
    public void setPosicion(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        
        fixPosicion();
    }
    
    public Bloque getConexion(){
        return this.conexion;
    }
    
    /*
    Hace: asegurar que el conector esté ubicado correctamente
    en relación con los elementos a los que está conectado y que su línea de conexión esté en la posición adecuada
     */
    public void fixPosicion(){
        if (conectador != null){
            if (modo.equals("h")){
                setLayoutX(conectador.getX() + conectador.ancho+offX   );
                setLayoutY(conectador.getY());
                if (conexion != null) {
                    conexion.setPosicion(this.getLayoutX(), this.getLayoutY());
                }
            } else {
                setLayoutX(conectador.getX() + offX   );
                setLayoutY(conectador.getY()+Bloque.ALTO);
                if (conexion != null) {
                    conexion.setPosicion(this.getLayoutX(), this.getLayoutY()-20);
                }
            }


            if (modo.equals("h")){
                linea.setLayoutX(0);
                linea.setLayoutY(0);
            } else {
                linea.setLayoutX(0);
                linea.setLayoutY(0);
            }
        } else {
            
            setLayoutX( multiconectador.getLayoutX()-5+ offX   );
            setLayoutY(   multiconectador.getLayoutY()+15);
            if (conexion != null) {
                conexion.setPosicion(this.getLayoutX()+ offX + 55, this.getLayoutY()-15);
            }


            linea.setLayoutX(0);
            linea.setLayoutY(0);
            
        }
        
        
    }

    
    
    public void NecesitaSiguiente(){
        
        TopPart.setStrokeWidth(8);
        SidePart.setStrokeWidth(8);
        
        TopPart.setWidth(100);
        SidePart.setWidth(100);
        
        this.SidePart.setStroke(Color.RED);
        this.TopPart.setStroke(Color.RED);
        
        this.SidePart.setFill(Color.color(0, 0, 0, 0));
        this.TopPart.setFill(Color.color(0, 0, 0, 0));
        
        this.SidePart.setVisible(true);
        this.TopPart.setVisible(true);
    }
    
    
    
    
    
    /*Recibe: Recibe un bloque para validar
    Hace: Valida si se puede conectar un bloque a otro de todas las formas
     */
    public boolean puedeConectarse(Bloque b) {
        if (b.Inconectableh && modo.equals("h") && multiconectador == null) {
            return false;
        }
        
        if (b.Inconectablev && multiconectador != null){
            return false;
        }
        
        if (b.Inconectablev && modo.equals("v")) {
            return false;
        }
        return true;
    }


    /*
     Recibe un bloque y se encarga de establecer una conexión visual entre el conector actual y un bloque b en la interfaz gráfica
    Asegura que la conexión se realice correctamente y que se realicen los ajustes necesarios en la posición.
    */
    public void setConexion(Bloque b) {
        if (!puedeConectarse(b)) return;
        
        
        this.conexion = b;
        fixLargoLineaIdentada();
        if (modo.equals("h") && !identable) {
            b.DesactivarVertical();
        }
        if (!modo.equals("h")){
            b.conectadov = this;
        }
        if (b != null && modo.equals("h")) {
            b.conectado = this;
        }
        
        fixPosicion();
        ocultarPreBloque();
        ocultarLinea();
    }
    
    
    /*
    Garantiza que la línea de conexión visual entre el conector
    actual y otros elementos se ajuste adecuadamente en longitud cuando sea necesario
    dependiendo de cuantos hijos tenga el inner.
    */
    public void fixLargoLineaIdentada(){
        
        //Propagar al identador
        if (conectador == null && conexion != null){
            
            //actualiza el largo del conector 
            multiconectador.largoConector = conexion.LargoConexionMultiple();
            multiconectador.fixPosicion();
            multiconectador.fixLargoLineaIdentada();
        } else if (conectador == null ){
            //actualiza el largo del conector 
            multiconectador.largoConector = 0;
            multiconectador.fixPosicion();
            multiconectador.fixLargoLineaIdentada();
        }
        
        //propagar al de arriba
        if (conectador != null && conectador.conectado != null){
            conectador.conectado.fixPosicion();
            conectador.conectado.fixLargoLineaIdentada();
        }
        
    }
    
    
    //se encarga de eliminar una conexión existente entre el conector actual y un bloque.
    public void Desconectar(){
        this.conexion.conectado = null;
        this.conexion.conectadov = null;
        if (modo.equals("h")) this.conexion.ActivarVertical();
        this.conexion = null;
        if (conectador == null){
            multiconectador.largoConector = 0;
            multiconectador.fixPosicion();
        }
        fixLargoLineaIdentada();
        mostrarLinea();
    }
    
    //Obtiene la posicion x
    public double getX(){
//        if (conectador == null) return multiconectador.getX()+getLayoutX();
        return getLayoutX();
    }
    
    //Obtiene la posicion y
    public double getY(){
//        if (conectador == null) return multiconectador.getY()+getLayoutY();
        return getLayoutY();
    }
    
    
    /*
    Hace: Obtiene las coordenadas de los vértices de un rectángulo que representa visualmente el conector en la interfaz gráfica
    Estas coordenadas se devuelven en forma de un arreglo de números (double array)
    que representan las coordenadas X e Y de los cuatro vértices del rectángulo. 
    */
    public double[] getRectVertices(){
        if (modo.equals("v")){
            double [] d = {getX()+7, getY()+7, getX()+52, getY()+Bloque.ALTO/2.0};
            return d;
        } else {
            
            if (conectador != null) {
                double [] d = {getX()+7, getY()+7, getX()+conectador.ancho/2.0, getY()+Bloque.ALTO/2.0};
                return d;
            } else {
                double [] d = {getX()+7+55, getY()+7, getX()+multiconectador.conectador.ancho/2.0+55, getY()+Bloque.ALTO/2.0};
                return d;
            }
        }
    }
    
    
    //Se utiliza para verificar si existe una colisión entre el conector actual y un bloque recibido(Bloque b).
    public boolean detectarColision(Bloque b){
        if (conexion != null || !activado) return false;
        if (modo.equals("h") && (b.cvertical.conexion != null && !identable)) return false;
        
        double [] p1 = b.getRecVertices();
        double[] p2 = getRectVertices();
        return (!(p1[0]+50 < p2[0] || p2[2] < p1[0] || p1[3] < p2[1] || p2[3] < p1[1]));
    }
    
    
    /*
    Hace visible un prebloque
     Se utiliza para mostrar una representación gráfica preliminar del bloque al que se puede conectar el conector.
    Esto ayuda al usuario a identificar visualmente las áreas de conexión
    válidas y a entender que es posible realizar una conexión en ese lugar
    */
    public void mostrarPreBloque(Bloque b) {
    if (!activado || !puedeConectarse(b)) {
            return;
        }
    
        TopPart.setStrokeWidth(4);
        SidePart.setStrokeWidth(4);
        
        TopPart.setWidth(b.ancho);
        SidePart.setWidth(b.ancho);
        this.SidePart.setStroke(Conector.ColorLineaGlobal);
        this.TopPart.setStroke(Conector.ColorLineaGlobal);

        Color c = b.ColorBloque;
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.2);

        TopPart.setFill(c);
        SidePart.setFill(c);

        TopPart.setVisible(true);
        SidePart.setVisible(true);
    }

    
    public void ocultarLinea(){
        linea.setVisible(false);
    }
    
    public void ocultarPreBloque(){
        TopPart.setVisible(false);
        SidePart.setVisible(false);
    }
    
    /*
    Este método es útil para controlar cuándo se muestra y se oculta visualmente 
    la línea de conexión entre el conector y el elemento al que está conectado.
    */
    public void mostrarLinea(){
        if (activado && !((conectador instanceof BloqueOP || conectador instanceof BloqueValor) && modo.equals("v"))) linea.setVisible(true);
    }

}
