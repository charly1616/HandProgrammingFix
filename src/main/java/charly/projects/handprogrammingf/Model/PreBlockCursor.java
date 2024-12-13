package charly.projects.handprogrammingf.Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PreBlockCursor extends Pane {

    public Rectangle SidePart;
    public Rectangle TopPart;


    public double x;
    public double y;

    public double ancho = 100;
    public Color ColorBloque = Color.WHITE;
    public Color ColorBorde = Color.BLACK;
    public double TamBorde = 4;
    public double mouseAnchorX;
    public double mouseAnchorY;

    public PreBlockCursor(double x, double y){
        this.x = x;
        this.y = y;

        InitializaComponents();
        Pintar();
    }


    public void MostrarFuturoBloque(Color c, double ancho) {
        TopPart.setStrokeWidth(4);
        SidePart.setStrokeWidth(4);

        TopPart.setWidth(ancho);
        SidePart.setWidth(ancho);
        this.SidePart.setStroke(Conector.ColorLineaGlobal);
        this.TopPart.setStroke(Conector.ColorLineaGlobal);


        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.2);

        TopPart.setFill(c);
        SidePart.setFill(c);

        TopPart.setVisible(true);
        SidePart.setVisible(true);
    }

    public void OcultarFuturoBloque(){
        TopPart.setVisible(false);
        SidePart.setVisible(false);
    }


    public void setPosition(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        this.x = x;
        this.y = y;
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


    public void InitializaComponents(){
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
    }


}
