package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSaver {

    public static final String[] Prefix = {"Mai","For","Whi","Val","Var","Ifi","Eli","Els","Out","Inp","Mat","Log","Com","Fun"};

    public static HashMap<Class<? extends Bloque>, String> prefixBloques = new HashMap<>();
    static {
        prefixBloques.put(BloqueInicio.class,Prefix[0]);
        prefixBloques.put(BloqueFor.class,Prefix[1]);
        prefixBloques.put(BloqueWhile.class,Prefix[2]);
        prefixBloques.put(BloqueValor.class,Prefix[3]);
        prefixBloques.put(BloqueVariable.class,Prefix[4]);
        prefixBloques.put(BloqueIF.class,Prefix[5]);
        prefixBloques.put(BloqueElif.class,Prefix[6]);
        prefixBloques.put(BloqueElse.class,Prefix[7]);
        prefixBloques.put(BloqueMostrar.class,Prefix[8]);
        prefixBloques.put(BloquePedir.class,Prefix[9]);
        prefixBloques.put(BloqueMat.class,Prefix[10]);
        prefixBloques.put(BloqueLogico.class,Prefix[11]);
        prefixBloques.put(BloqueLMat.class,Prefix[12]);
    }

    public final CreadorDeBloques creadorDeBloques;
    public final GridController father;
    private final Stage primaryStage;

    public static final HashMap<String, BiFunction<Double[],String,Bloque>> FunctionsBloques = new HashMap<>();
    private void InitializeFunctionsBloques(){
        FunctionsBloques.put(Prefix[0], (coord,value) -> creadorDeBloques.BloqueInicio(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[1], (coord,value) -> creadorDeBloques.BloqueFor(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[2], (coord,value) -> creadorDeBloques.BloqueWhile(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[3], (coord,value) -> creadorDeBloques.BloqueValor(coord[0],coord[1],value));
        FunctionsBloques.put(Prefix[4], (coord,value) -> creadorDeBloques.BloqueVariable(coord[0],coord[1],value));
        FunctionsBloques.put(Prefix[5], (coord,value) -> creadorDeBloques.BloqueIF(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[6], (coord,value) -> creadorDeBloques.BloqueElif(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[7], (coord,value) -> creadorDeBloques.BloqueElse(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[8], (coord,value) -> creadorDeBloques.BloqueMostrar(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[9], (coord,value) -> creadorDeBloques.BloquePedir(coord[0],coord[1]));
        FunctionsBloques.put(Prefix[10], (coord,value) -> creadorDeBloques.BloqueOPMAT(coord[0],coord[1],value));
        FunctionsBloques.put(Prefix[11], (coord,value) -> creadorDeBloques.BloqueLogico(coord[0],coord[1],value));
        FunctionsBloques.put(Prefix[12], (coord,value) -> creadorDeBloques.BloqueLMat(coord[0],coord[1],value));
    }

    public FileSaver(GridController father){
        this.father = father;
        primaryStage = father.stage;
        creadorDeBloques = father.creadorb;
        InitializeFunctionsBloques();
    }


    public int ConectionOf(Bloque b){
        if (b.conectadov != null && b.conectadov.conectador != null){
            return b.conectadov.conectador.IDBloque;
        } else if (b.conectado != null && b.conectado.conectador != null) {
            return b.conectado.conectador.IDBloque;
        }
        return 0;
    }

    public String ConectionType(Bloque b){
        if (b.conectado instanceof ConectorMultiple){
            return "=";
        } else if (b.conectado != null){
            return "-";
        } else if (b.conectadov != null){
            return "/";
        }
        return "";
    }


    /**
     * Obtiene la direccion para guardar un archivo de tipo .block
     * @return String - Ruta del archivo o cadena vacía si no se seleccionó
     */
    public String getSaveFilePath() {
        FileChooser fileChooser = new FileChooser();

        // Establecer el filtro de archivo para solo mostrar archivos .block
        fileChooser.setTitle("Guardar archivo .block");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Block (*.block)", "*.block"));

        // Mostrar el cuadro de diálogo de guardar archivo
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        if (selectedFile != null) {
            // Asegurarse de que el archivo tenga la extensión .block
            if (!selectedFile.getName().endsWith(".block")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".block");
            }

            return selectedFile.getAbsolutePath();
        }

        return "";
    }

    /**
     * Funcion que pide la direccion del archivo con una UI de JavaFX
     * @return String - Ruta del archivo o cadena vacía si no se encontró
     */
    public  String getExistingBlockFilePath() {
        FileChooser fileChooser = new FileChooser();

        // Establecer el filtro de archivo para solo mostrar archivos .block
        fileChooser.setTitle("Seleccionar archivo .block");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Block (*.block)", "*.block"));

        // Mostrar el cuadro de diálogo para abrir archivo
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            // Verificar si el archivo tiene la extensión .block y existe
            if (selectedFile.getName().endsWith(".block") && selectedFile.exists()) {
                return selectedFile.getAbsolutePath();
            } else {
                // Mostrar mensaje de error si el archivo no cumple con los requisitos
                System.out.println("El archivo seleccionado no es un archivo válido .block o no existe.");
            }
        }

        return "";
    }



    public void SaveFile(){
        String filePath = getSaveFilePath();
        if (filePath.isEmpty()) return;


        String file = "";
        for (Bloque b : father.bloques){
            int BlockConector = ConectionOf(b);
            String conectionType = ConectionType(b);

            String pfx = prefixBloques.get(b.getClass());

            int id = b.IDBloque;
            String x = b.getX() + "";
            String y = b.getY() + "";
            String val = b.getSaveValue();

            file += String.format("%s%s(%d,%s,%s,%s)\n",
                    conectionType.isEmpty() ? "" : BlockConector + conectionType,
                    pfx, id, x, y, val);
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(file);  // Writing your custom data to the file
            System.out.println("Data written to " + filePath);
        } catch (IOException e) {
            System.out.println("Archivo no guardado");
        }
    }


    private final HashMap<Double, Bloque> bloquesPorID = new HashMap<Double, Bloque>();
    private final Queue<Pair<Bloque,Double>> bloquesAConectar = new ArrayDeque<>();
    private final Queue<String> bloquesCTypes = new ArrayDeque<>();

    //Carga el archivo y crea los bloques de cada linea
    public void LoadFile(){
        String filePath = getExistingBlockFilePath();
        if (filePath.isEmpty()) return;


        //Obtener el contenido del archivo
        String loadText = "";
        try {
            loadText = Files.readString(Paths.get(filePath));
        } catch (Exception e){ System.out.println("Archivo no encontrado"); }


        String [] Lines = loadText.split("\\R");
        for (String line : Lines){
            LoadBlockLine(line);
        }

        clearPool();
    }

    /*
    * Para cada bloque esperando ser conectado, dependiendo de su conexion se hace la correspondiente
    * Se vacia la fila de los bloques
    * */
    public void clearPool(){
        while(!bloquesAConectar.isEmpty() && !bloquesCTypes.isEmpty()){
            String type = bloquesCTypes.poll();
            Pair<Bloque,Double> bid = bloquesAConectar.poll();
            assert bid != null;
            System.out.println(bid.getValue());
            Bloque p = bloquesPorID.get(bid.getValue());
            if (p == null) continue;

            //System.out.println("Conecting : " + bid.getKey().IDBloque + " With : " + bid.getValue());

            switch (type) {
                case "/" -> {
                    if (p.cvertical != null) p.cvertical.setConexion(bid.getKey());
                }
                case "-" -> {
                    if (p.chorizontal != null) p.chorizontal.setConexion(bid.getKey());
                }
                case "=" -> {
                    if (p.cvertical != null && p.cvertical.inner != null)
                        p.cvertical.inner.setConexion(bid.getKey());
                }
            }
        }
    }


    /*
    * Recuerda que cada linea en el archivo.block es de la forma TipoBloque(ID,X,Y,String) y aveces tienen al principio un Numero(Simbolo)...
    * Lo que hace esta funcion es separar la linea en sus componentes, crear el bloque y dejar listo las conexiones que se tienen que hacer
    * */
    public void LoadBlockLine(String line){
        //Separate the general parts
        String regex = "(\\d+)?\\s*([=/\\-\\[])?\\s*(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        // Variables para los valores extraídos
        int number = -1; // Valor predeterminado
        String symbol = ""; // Cadena vacía por defecto
        String text = line; // Texto cualquiera



        //Declares the symbol and the number if found
        if (matcher.matches()) {
            if (matcher.group(1) != null) number = Integer.parseInt(matcher.group(1));
            if (matcher.group(2) != null) symbol = matcher.group(2);
            if (matcher.group(3) != null) text = matcher.group(3).trim();
        }


        //TypeOfBlock
        String BlockType = text.substring(0,3);
        if (!Arrays.asList(Prefix).contains(BlockType)) return; //Irse si es incompatible
        //Separate the parameters
        //(int,double,double,String)
        String[] Params = text.substring(4,text.length()-1).split(",");
        Double[] doubles = {Double.parseDouble(Params[1]),Double.parseDouble(Params[2])};
        String value = (Params.length == 4) ? Params[3] : "";

        //Create the Block
        Bloque b = FunctionsBloques.get(BlockType).apply(doubles,value);
        b.setID((int) Double.parseDouble(Params[0]));
        //System.out.println(Arrays.asList(Params).toString());


        //Add the block to the ID pool
        bloquesPorID.put(b.IDBloque+0.0,b);
        //If needed, add the block to the Conection pool
        if (!symbol.isEmpty()){
            bloquesAConectar.add(new Pair<>(b,number+0.0));
            bloquesCTypes.add(symbol);
        }
    }
}


