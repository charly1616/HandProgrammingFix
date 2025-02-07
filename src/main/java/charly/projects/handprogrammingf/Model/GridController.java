package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.BloqueInicio;
import java.net.URL;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class GridController implements Initializable {
    
    
    public Process camara;
    
    
    @FXML
    public Pane GridView;
    public double scale = 1;
    public Scene escena;

    @FXML
    public Pane Grid;

    private Bloque bloqueSeleccionado;
    public CreadorDeBloques creadorb = new CreadorDeBloques(this);

    // Lo que guarda los componentes
    public ArrayList<Bloque> bloques = new ArrayList<Bloque>();
    public ArrayList<Circle> puntos = new ArrayList<Circle>();
    public Pane cirs = new Pane();

    public Stage stage = null;

    private final List<Bloque> bloquesSeleccionados = new ArrayList<>();
    public static Color pCursorColor = Color.WHITE;


    public BiFunction<Double,Double,Bloque> functionCreadora = null;
    public final PreBlockCursor pcursor = new PreBlockCursor(0,0);



    //Movimiento del fondo
    public double mouseAnchorX;
    public double mouseAnchorY;

    private double lastMouseX, lastMouseY;
    private FileSaver fileSaver;
    /* 
        Recibe: (URL, ResourceBundle) cosas que son necesarias para JavaFX
        Devuelve: (void) (Nada)
        Hace: añade la pantalla, establece las caracteristicas como el tamaño, coloca los eventos -(Metodos {hacerNavegable}{hacerZoomeable})
        crea el bloque de inicio y los bloques iniciales -(Metodos {hacerBloqueMovible}{añadirBloque}), aparte de los puntos -(Metodo {crearPuntos})
        
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileSaver = new FileSaver(this);

        Screen pantalla = Screen.getPrimary();
        javafx.geometry.Rectangle2D coordenadas = pantalla.getVisualBounds();

        GridView.setMaxWidth((coordenadas.getMaxX()));
        GridView.setMaxHeight((coordenadas.getMaxY()));
        Grid.setMaxWidth(GridView.getWidth());
        Grid.setMaxHeight(GridView.getHeight());

        cirs.setScaleX(Grid.getScaleX());
        cirs.setScaleY(Grid.getScaleY());
        crearPuntos();
        Grid.getChildren().add(cirs);

        Grid.setBackground(Background.EMPTY);
        GridView.setBackground(Background.fill(Color.WHITE));
        Grid.getChildren().add(pcursor);
       

        GridView.setOnMouseMoved(event -> {
            if (functionCreadora != null) {
                pcursor.mouseAnchorX = -Grid.getTranslateX() / scale;
                pcursor.mouseAnchorY = -Grid.getTranslateY() / scale;

                double adjustedX = (event.getX() / scale) + pcursor.mouseAnchorX;
                double adjustedY = (event.getY() / scale) + pcursor.mouseAnchorY;

                pcursor.MostrarFuturoBloque(pCursorColor,pcursor.ancho);
                pcursor.setPosition(adjustedX, adjustedY);
                event.consume();
            } else {
                pcursor.OcultarFuturoBloque();
            }
        });


        BorderPane layout = new BorderPane();
        layout.setLayoutX(0);
        layout.setLayoutY(15);
        MenuBar menuBar = new MenuBar();


        GridView.getChildren().add(layout);
        Menu file = new Menu("File");
        MenuItem saveFile = new MenuItem("Save file");
        MenuItem loadFile = new MenuItem("Load file");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileSaver.SaveFile();
            }
        });
        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileSaver.LoadFile();
                organizarBloques();
            }
        });

        file.getItems().addAll(saveFile, loadFile);
        Menu about = new Menu("About");
        layout.setTop(menuBar);

        menuBar.setUseSystemMenuBar(true);

        menuBar.getMenus().addAll(file, about);

        MenuItem item1 = new MenuItem("Open");
        MenuItem item2 = new MenuItem("Save");
        MenuItem item3 = new MenuItem("Exit");

        // this RadioMenuItem
        RadioMenuItem r1 = new RadioMenuItem("True");
        RadioMenuItem r2 = new RadioMenuItem("False");
        ToggleGroup rGroup = new ToggleGroup();
        rGroup.getToggles().addAll(r1, r2);

        // This one is CheckMenuItem
        CheckMenuItem c1 = new CheckMenuItem("Stroke for Circle");
        CheckMenuItem c2 = new CheckMenuItem("Stroke for Rectangle");

        // This one submenu item
        Menu submenu = new Menu("Save As");
        submenu.getItems().addAll(r1, r2);

        // Custom Menu Item
        CustomMenuItem customItem = new CustomMenuItem();
        Button btn = new Button("Kensoft PH");
        TextField text = new TextField();
        customItem.setHideOnClick(false);
        customItem.setContent(text);

        // Separator Menuitem
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();

        hacerNavegable();
        GridView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Verificar si se hizo clic en un bloque o un punto
                boolean clicEnBloqueOPunto = false;

                for (Bloque bloque : bloques) {
                    if (bloque.getBoundsInParent().contains(event.getX(), event.getY())) {
                        clicEnBloqueOPunto = true;
                        break;
                    }
                }

                for (Circle punto : puntos) {
                    if (punto.getBoundsInParent().contains(event.getX(), event.getY())) {
                        clicEnBloqueOPunto = true;
                        break;
                    }
                }

                if (!clicEnBloqueOPunto) {
                    // Si no se hizo clic en un bloque ni un punto, deseleccionar todos los bloques
                    if (functionCreadora != null){
                        functionCreadora.apply(pcursor.getLayoutX(),pcursor.getLayoutY());
                        functionCreadora = null;

                    }
                    deseleccionarBloques();
                }
            }
        });



        Grid.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DELETE) { // Cambiado de KeyCode.BACK_SPACE a KeyCode.DELETE
                // Verificar si hay bloques seleccionados para eliminar
                if (!bloquesSeleccionados.isEmpty()) {
                    for (Bloque b : bloquesSeleccionados) {
                        if (b.conectado != null) b.conectado.Desconectar();
                        if (b.conectadov != null) b.conectadov.Desconectar();
                        eliminarBloque(b);
                    }
                    bloquesSeleccionados.clear();
                }
            }
        });

        creadorb.BloqueInicio(0.0,0.0);

        MenuBloques u = new MenuBloques(creadorb);
        GridView.getChildren().add(u);
        
        
        hacerZoomeable();

    }

    
    private void deseleccionarBloques() {
        for (Bloque bloque : bloques) {
            bloque.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;");
        }
        bloquesSeleccionados.clear();
    }

    /* 
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: establece el evento que cuando se ruede se haga zoom, aplica el zoom a todos los componentes
        
     */
    public void hacerZoomeable() {
        Grid.getParent().setOnScroll((ScrollEvent event) -> {
            double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 0.9;
            double newScale = scale * scaleFactor;

            // Esto establece los límites máximos y mínimos de escala
            double maxScale = 3.0;
            double minScale = 0.3;

            if (newScale <= maxScale && newScale >= minScale) {
                scale = newScale;
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

                // Calcular el desplazamiento del punto de enfoque
                double offsetX = (mouseX) * (1 - scaleFactor);
                double offsetY = (mouseY) * (1 - scaleFactor);

                Grid.setScaleX(scale);
                Grid.setScaleY(scale);

                // Ajustar la posición para mantener el punto de enfoque
                Grid.setTranslateX((Grid.getTranslateX() + offsetX) * scaleFactor);
                Grid.setTranslateY((Grid.getTranslateY() + offsetY) * scaleFactor);
            }

            event.consume();
        });
    }

    private void cargarBloques(){
        for (int i = 0; i < 10; i++) { creadorb.BloqueMostrar(1500.0, 360.0); }

        for (int i = 0; i < 10; i++) { creadorb.BloqueWhile(1720.0, 720.0); }

        for (int i = 0; i < 10; i++) { creadorb.BloqueFor(1500.0, 720.0); }

        for (int i = 0; i < 10; i++) { creadorb.BloqueIF(1500.0, 580.0); }

        for (int i = 0; i < 10; i++) { creadorb.BloqueElif(1720.0, 580.0); }

        for (int i = 0; i < 10; i++) { creadorb.BloqueElse(1720.0, 220.0); }


        for (int i = 0; i < 10; i++) { creadorb.BloqueVariable(1500.0, 120.0,""); }

        for (int i = 0; i < 10; i++) { creadorb.BloquePedir(1720.0, 360.0); }

        String[] signos = {"+", "-", "x", "^", "/", "%"};

        for (int j = 0; j < signos.length; j++) {
            for (int i = 0; i < 10; i++) { creadorb.BloqueOPMAT(100.0 * j, 1000.0, signos[j]); }
        }

        String[] signos2 = {"=", "!=", ">", "<", "<=", ">="};

        for (int j = 0; j < signos2.length; j++) {
            for (int i = 0; i < 10; i++) { creadorb.BloqueLMat(100.0 * j, 1100.0, signos2[j]); }
        }

        String[] signos3 = {"&", "o"};

        for (int j = 0; j < signos3.length; j++) {
            for (int i = 0; i < 10; i++) { creadorb.BloqueLogico(100.0 * j, 1200.0, signos3[j]); }
        }
    }



    /* 
        Recibe: (Bloque b)
        Devuelve: (void) (Nada)
        Hace: le coloca distintos eventos al bloque "b"
        hace que "b" se pueda agarrar y arrastrar, conecta la funcion {agarrado} y {soltado}
        establece las conexiones -(funcion {detectarColision}{b.setConexion}) cuando se mueve el bloque
        hace que cuando se de click dos veces, se elimine
        
     */
    public void hacerBloqueMovible(Bloque b) {
        b.setOnMousePressed(event -> {
            // Calcular la posición anclada inicial del objeto con respecto al mouse
            b.mouseAnchorX = event.getSceneX() / scale - b.getX();
            b.mouseAnchorY = event.getSceneY() / scale - b.getY();
            event.consume();
        });

        b.setOnMouseDragged(event -> {
            // Calcular la nueva posición del objeto ajustando por el anclaje y el zoom
            double adjustedX = (event.getSceneX() / scale) - b.mouseAnchorX;
            double adjustedY = (event.getSceneY() / scale) - b.mouseAnchorY;

            b.setPosicion(adjustedX, adjustedY);
            b.toFront(); // Llevar el objeto al frente
            pintarPreBloque(b); // Lógica adicional según tu aplicación
            event.consume();
        });

        b.setOnDragDetected(event -> {
            b.Agarrado();
            if (b.conectado != null) {
                b.conectado.Desconectar();
            }
            event.consume();
        });



        b.setOnMouseReleased(event -> {
            b.Soltado();
            OcultarPreBloques();

            Conector c = pintarPreBloque(b);
            if (c != null) {
                c.setConexion(b);
            } else if (detectarColision(b)) {
                b.setPosicion(b.LastX * scale, b.LastY * scale);
            } else {
                b.LastX = b.getX();
                b.LastY = b.getY();
            }

            organizarBloques();
            event.consume();
        });

        b.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                seleccionarBloque(b);
            }
        });
        b.setOnMouseClicked(event -> {
            boolean agregarSeleccion = event.isControlDown();
            seleccionarBloquer(b, agregarSeleccion);
        });

    }

    /* 
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: hace que el fondo "Grid" se pueda mover y que todos los bloques y puntos se muevan con él
        
     */
    public void hacerNavegable() {
        Grid.getParent().setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
            event.consume();
        });

        Grid.getParent().setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;
            Grid.setTranslateX(Grid.getTranslateX() + deltaX);
            Grid.setTranslateY(Grid.getTranslateY() + deltaY);
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });

    }

    /* 
        Recibe: (Bloque b) 
        Devuelve: (Conector) devuelve el primer conector que está colisionando con "b"
        Hace: busca en todos los bloques, en todos sus conectores si el conector está colisionando, cuando es así, muestra el prebloque de ese conector y devuelve al conector
        cuando no encuentra ninguno devuelve null
        
     */
    public Conector pintarPreBloque(Bloque b) {
        for (Bloque p : bloques) {

            if (p == b) {
                continue;
            }
            OcultarPreBloques();
            if (p.chorizontal.detectarColision(b)) {
                p.chorizontal.mostrarPreBloque(b);
                return p.chorizontal;
            }

            if (p.cvertical.inner != null && p.cvertical.inner.detectarColision(b)) {
                p.cvertical.inner.mostrarPreBloque(b);
                return p.cvertical.inner;
            }
            if (p.cvertical.detectarColision(b)) {
                p.cvertical.mostrarPreBloque(b);
                return p.cvertical;
            }

        }
        return null;
    }

    /* 
        Recibe: (Nada)
        Devuelve: (void) (Nada)
        Hace: oculta todos los prebloques de todos los conectores de todos los bloques en la lista "bloques"
        
     */
    public void OcultarPreBloques() {
        for (Bloque p : bloques) {
            p.chorizontal.ocultarPreBloque();
            p.cvertical.ocultarPreBloque();
            if (p.cvertical.inner != null) {
                p.cvertical.inner.ocultarPreBloque();
            }
        }
    }

    /* 
        Recibe: (Bloque b)
        Devuelve: (void) (Nada)
        Hace: establece las conexiones de "b" con el primer conector que esté cerca de "b" sea conector vertical u horizontal
        despues oculta todos los prebloques
        
     */
    public void ConectarBloque(Bloque b) {
        for (Bloque p : bloques) {
            if (p == b) {
                continue;
            }

            if (p.chorizontal.detectarColision(b)) {
                p.chorizontal.setConexion(b);
            } else if (p.cvertical.detectarColision(b)) {
                p.cvertical.setConexion(b);
            }
        }
        OcultarPreBloques();
    }

    /* 
        Recibe: (Bloque b)
        Devuelve: (boolean) que significa que el bloque "b" esta colisionando con otro o no
        Hace: verifica si "b" está colisionando con alguno de los otros bloques en la lista "bloques" (usa los vertices para ver si están encima de otro)
        
     */
    public boolean detectarColision(Bloque b) {
        double[] p2 = b.getRecVertices();
        for (Bloque p : bloques) {
            if (p == b) {
                continue;
            }

            double[] p1 = p.getRecVertices();

            if (!(p1[2] < p2[0] || p2[2] < p1[0] || p1[3] < p2[1] || p2[3] < p1[1])) {
                return true;
            }
        }
        return false;
    }

    /* 
        Recibe: (Nada)
        Devuelve: (Void)(Nada)
        Hace: crea puntos "cir" en una cuadricula y los añade al panel y a una lista de puntos llamada "puntos"
     */
    public void crearPuntos() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Circle cir = new Circle();

                cir.setCenterX((i - 100) * 60);
                cir.setCenterY((j - 100) * 60);

                cir.setRadius(1);
                cir.setStrokeWidth(0);
                cir.setFill(Color.GREY.darker().darker());
                puntos.add(cir);
                Grid.getChildren().add(cir);
            }
        }
    }

    /* 
        Recibe: (Nada)
        Devuelve: (Void)(Nada)
        Hace: organiza los bloques de mayor altura a menor altura
        Hace que los que estén mas alto se vean detrás
     */
    public void organizarBloques() {
        ArrayList<Bloque> blq = new ArrayList<>(bloques);

        // Ordenar los bloques por su posición en el eje Y de manera descendente
        blq.sort((bloque1, bloque2) -> Double.compare(bloque1.getLayoutY(), bloque2.getLayoutY()));

        // Mantener las posiciones originales de los bloques al organizarlos
        for (int i = 0; i < blq.size(); i++) {
            Bloque bloque = blq.get(i);
            bloque.toFront();
        }
    }

    /* 
        Recibe: (Bloque p) que es el bloque que se va a añadir
        Devuelve: (Void)(Nada)
        Hace: añade al bloque "p" y todos sus componentes (conectores) al panel e ingresa a "p" a la lista "bloques" 
     */
    public void añadirBloque(Bloque p) {
        if (p.chorizontal != null) {
            Grid.getChildren().add(p.chorizontal);
            p.chorizontal.fixPosicion();
        }
        if (p.cvertical != null) {
            Grid.getChildren().add(p.cvertical);
            p.cvertical.fixPosicion();
        }
        if (p.cvertical != null && p.cvertical.inner != null) {
            Grid.getChildren().add(p.cvertical.inner);
            p.cvertical.inner.fixPosicion();
        }
        Grid.getChildren().add(p);
        bloques.add(p);
    }

    public void seleccionarBloque(Bloque bloque) {
        if (bloqueSeleccionado != null) {
            bloqueSeleccionado.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;");
        }

        if (bloque != null) {
            bloque.setStyle("-fx-border-color: darkblue; -fx-border-width: 2px;");
            bloque.requestFocus();
        }

        bloqueSeleccionado = bloque;
    }

    public void seleccionarBloquer(Bloque bloque, boolean agregarSeleccion) {
        if (agregarSeleccion) {
            if (bloquesSeleccionados.contains(bloque)) {
                // Deseleccionar el bloque si ya está seleccionado y se presiona Control nuevamente
                bloque.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;");
                bloquesSeleccionados.remove(bloque);
            } else {
                // Seleccionar el bloque si se presiona Control y aún no está seleccionado
                bloque.setStyle("-fx-border-color: darkblue; -fx-border-width: 2px;");
                bloquesSeleccionados.add(bloque);
            }
        } else {
            // Limpiar la selección actual si no se presiona Control
            for (Bloque bloqueSeleccionado : bloquesSeleccionados) {
                bloqueSeleccionado.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;");
            }

            bloquesSeleccionados.clear();

            // Seleccionar el bloque actual
            bloque.setStyle("-fx-border-color: darkblue; -fx-border-width: 2px;");
            bloquesSeleccionados.add(bloque);
        }

        bloque.requestFocus();
    }

    /* 
        Recibe: (Bloque) que es el bloque que se va a eliminar
        Devuelve: (Void)(Nada)
        Hace: Elimina el bloque y sus conexiones del panel y del arrayList
     */
    public void eliminarBloque(Bloque bloque) {
        if (bloque.chorizontal != null) {
            // Elimina el conector horizontal y lo quita de la lista
            Grid.getChildren().remove(bloque.chorizontal);

        }
        if (bloque.cvertical != null) {
            // Elimina el conector vertical y lo quita de la lista
            Grid.getChildren().remove(bloque.cvertical);
        }

        if (bloque.cvertical != null && bloque.cvertical.inner != null) {
            // Elimina el conector interno y lo quita de la lista
            Grid.getChildren().remove(bloque.cvertical.inner);
        }

        // Elimina el bloque
        Grid.getChildren().remove(bloque);
        bloques.remove(bloque);
    }


    public void limpiarBloques(){
        for (Bloque b: bloques){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        eliminarBloque(b);
                    });
                }
            }, 1);
        }
        bloques.clear();
    }
    
    
    
//    private void ActivarCamara(){
//        try {
//            String scriptPath = "src/Main/PythonCode.py";
//
//            //String[] command = {"C:/Users/juand/AppData/Local/Microsoft/WindowsApps/python.exe", scriptPath};   //Juanda
//            String[] command = {"C:/Users/User/AppData/Local/Programs/Python/Python311/python.exe", scriptPath};   //Charly
//
//
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            camara = processBuilder.start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
//    private void DesactivarCamara(){
//        try {
//            camara.destroy();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//

}
