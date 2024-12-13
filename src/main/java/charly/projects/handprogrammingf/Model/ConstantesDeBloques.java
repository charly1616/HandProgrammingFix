package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;
import javafx.scene.paint.Color;
import java.util.HashMap;

public class ConstantesDeBloques {

    public static int IdiomaSeleccionado = 2;


    public static HashMap<Class<? extends Bloque>, Color> ColoresBloques = new HashMap<>();
    static {
        ColoresBloques.put(BloqueInicio.class, Color.rgb(86, 255, 114));
        ColoresBloques.put(BloqueFor.class, Color.rgb(226, 100, 59));
        ColoresBloques.put(BloqueWhile.class, Color.rgb(147, 120, 201));
        ColoresBloques.put(BloqueValor.class, Color.rgb(247, 218, 63));
        ColoresBloques.put(BloqueVariable.class, Color.rgb(255, 165, 0));
        ColoresBloques.put(BloqueIF.class, Color.rgb(135, 206, 235));
        ColoresBloques.put(BloqueElif.class, Color.rgb(3, 156, 110));
        ColoresBloques.put(BloqueElse.class, Color.rgb(50, 229, 205));
        ColoresBloques.put(BloqueMostrar.class, Color.rgb(255, 102, 128));
        ColoresBloques.put(BloquePedir.class, Color.rgb(158, 160, 40));
        ColoresBloques.put(BloqueMat.class, Color.rgb(192, 81, 247));
        ColoresBloques.put(BloqueLogico.class, Color.rgb(255, 106, 194));
        ColoresBloques.put(BloqueLMat.class, Color.rgb(123, 62, 221));
    }

    public static String[] Idiomas = {"Español","English","Français"};

    public static HashMap<Class<? extends Bloque>, String[] > NombresBloques = new HashMap<>();
    static {
        NombresBloques.put(BloqueInicio.class, new String[]{"Inicio", "Main", "Debut"});
        NombresBloques.put(BloqueFor.class, new String[]{"Para", "For", "Pour"});
        NombresBloques.put(BloqueWhile.class, new String[]{"Mientras", "While", "Pendant"});
        NombresBloques.put(BloqueValor.class, new String[]{"Dato", "Valu", "Valr"});
        NombresBloques.put(BloqueVariable.class, new String[]{"Variable", "Variable", "Variable"});
        NombresBloques.put(BloqueIF.class, new String[]{"Si", "If", "Si"});
        NombresBloques.put(BloqueElif.class, new String[]{"PeroSi", "ElseIf", "MaisSi"});
        NombresBloques.put(BloqueElse.class, new String[]{"Sino", "Else", "Sinon"});
        NombresBloques.put(BloqueMostrar.class, new String[]{"Mostrar", "Show", "Montrer"});
        NombresBloques.put(BloquePedir.class, new String[]{"Pedir", "Input", "Demander"});
        NombresBloques.put(Bloque.class, new String[]{"Bloques Básicos", "Basic Blocks", "Blocs de base"});
        NombresBloques.put(BloqueMat.class, new String[]{"Operadores", "Operators", "Opérateurs"});
        NombresBloques.put(BloqueLMat.class, new String[]{"Comparadores", "Comparators", "Comparateurs"});
        NombresBloques.put(BloqueLogico.class, new String[]{"Logica", "Logic", "Logique"});
        NombresBloques.put(null, new String[]{"Entrada/Salida", "Input/Output", "Entrée/Sortie"});
    }




}
