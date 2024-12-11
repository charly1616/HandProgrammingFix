package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        FunctionsBloques.put(Prefix[10], (coord,value) -> creadorDeBloques.BloqueLogico(coord[0],coord[1],value));
        FunctionsBloques.put(Prefix[10], (coord,value) -> creadorDeBloques.BloqueLMat(coord[0],coord[1],value));
    }

    public FileSaver(GridController father){
        this.father = father;
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

    public static String getSaveFilePath() {
        JFileChooser fileChooser = new JFileChooser();

        // Establecer el filtro de archivo para solo mostrar archivos .block
        fileChooser.setDialogTitle("Guardar archivo .block");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos Block (*.block)", "block"));

        // Mostrar el diálogo de guardar archivo
        int result = fileChooser.showSaveDialog(null);

        // Si el usuario selecciona "Guardar"
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File selectedFile = fileChooser.getSelectedFile();

            // Asegurarse de que el archivo tenga la extensión .block
            if (!selectedFile.getName().endsWith(".block")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".block");
            }

            // Devolver la ruta del archivo como un String
            return selectedFile.getAbsolutePath();
        }

        return "";
    }



    public void SaveFile(){
        String filePath = getSaveFilePath();



        String file = "";
        for (Bloque b : father.bloques){
            int BlockConector = ConectionOf(b);
            String conectionType = ConectionType(b);

            String pfx = prefixBloques.get(b.getClass());

            int id = b.IDBloque;
            String x = b.getX() + "";
            String y = b.getY() + "";
            String val = b.getValor();

            file += String.format("%s%s(%d,%s,%s,%s)\n",
                    conectionType.isEmpty() ? "" : BlockConector + conectionType,
                    pfx, id, x, y, val);
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(file);  // Writing your custom data to the file
            System.out.println("Data written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


