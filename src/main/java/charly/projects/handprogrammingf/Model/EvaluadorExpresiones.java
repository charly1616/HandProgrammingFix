
package charly.projects.handprogrammingf.Model;

import charly.projects.handprogrammingf.Bloques.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluadorExpresiones {
    
    
    public static boolean Debug(Bloque inicial){
        if (inicial == null) return false;
        
        boolean deb = true;
        if (!(InstBloqueValor(inicial))){
            inicial.ponerRojo(inicial);
            return false;
        }

        Bloque Actual = inicial;
        Bloque Last = inicial;

        String last = "";


        do {
            if (Actual instanceof BloqueOP && Actual.Siguiente() == null){
                System.out.println("ERROR DE EXPRESION: Operación al final");
                Actual.ponerRojo(Actual);
                return false;
            }


            String value = Actual.getValor();

            if (BloqueOP.isMathOperation(Actual) && !IsNum(last)){
                System.out.println(last);
                System.out.println("ERROR DE EXPRESIÓN: Operación matematica con valor diferente de numero: " + last + " No es un numero");
                Actual.ponerRojo(Actual);
                return false;
            }

            if (InstBloqueValor(Actual)){ last += value; }

            if (Actual instanceof BloqueOP && InstBloqueValor(Last)){
                last = "";
            }


            if (Actual instanceof BloqueOP && Last instanceof BloqueOP){
                System.out.println("ERROR DE EXPRESION: Dos operaciones juntas");
                Actual.ponerRojo(Actual);
                return false;
            }

            if (IsMatExpresion(last) && Actual instanceof BloqueOP){
                System.out.println("ERROR DE EXPRESION: Operación matematica junto valor diferente de numero: " + last + " No es un numero");
                Actual.ponerRojo(Actual);
                return false;
            }


            Last = Actual;
            Actual = Actual.Siguiente();
        } while (Actual != null);

        return deb;
    }
    
    
    
    
    
    //Concatenar
    public static String Expresion(Bloque inicial){
        
        
        if (!(InstBloqueValor(inicial))) return "";
        
        Bloque Actual = inicial;
        Bloque Last = inicial;

        String track = "";
        String last = "";

        
        do {
            if (Actual instanceof BloqueOP && Actual.Siguiente() == null) return "";


            String value = Actual.getValor();

            if (BloqueOP.isMathOperation(Actual) && !IsNum(last)) return "";


            if (InstBloqueValor(Actual)){ last += value; }

            if (Actual instanceof BloqueOP && InstBloqueValor(Last)){
                track += last + Actual.getValor();
                last = "";
            }


            if (Actual instanceof BloqueOP && Last instanceof BloqueOP) return "";



            if (IsMatExpresion(last) && Actual instanceof BloqueOP) return "";


            Last = Actual;
            Actual = Actual.Siguiente();
        } while (Actual != null);


        track += last;
        System.out.println("Texto: " + track);
        System.out.println("Concatenar texto: " + EvOR(track));
        return EvOR(track);
    }
    
    
    /*
        Recibe: un Bloque inicial como argumento y evalúa todos los bloques siguientes para ver si representa una expresión condicional válida
        para poder hacer uso de las los condicionales
        Devuelve: el valor de la condicion evaluada
        Hace: a medida que va recorriendo los bloques en horizontal, convierte a los bloques en un String que es la expresion y ese String se lo manda a la funcion {EvLog}
        dependiendo de ciertas distinciones, la expresion se cancela o se divide o se hace
    */
    public static boolean EvCondicion(Bloque inicial){
        String exA = Expresion(inicial);
        return ValBool(EvOR(exA));
    }





    /* Separa y evalua por separado el valor usando OR */
    public static String EvOR(String ev){
        ev = ev.replaceAll(" ","");
        String [] Sep = ev.split("____o____");
        System.out.println(Arrays.toString(Arrays.stream(Sep).toArray()));
        if (Sep.length == 1) return EvAND(ev);
        String ev1 = EvAND(Sep[0]);
        if (IsNum(ev1)) ev1 = (Double.parseDouble(ev1) == 0) ? "False":"True";
        if (!IsBool(ev1)) return "";
        for (int i = 1; i < Sep.length; i++) {
            String ev2 = EvAND(Sep[i]);
            if (IsNum(ev2)) ev2 = (Double.parseDouble(ev2) == 0) ? "False":"True";
            if (!IsBool(ev2)) return "";
            ev1 = (ValBool(ev1) || ValBool(ev2)) + "";
        }
        return ev1;
    }



    /* Separa y evalua por separado el valor usando AND */
    public static String EvAND(String ev){
        ev = ev.replaceAll(" ","");
        String [] Sep = ev.split("____&____");
        System.out.println(Arrays.toString(Arrays.stream(Sep).toArray()));
        if (Sep.length == 1) return EvLogEquality(ev);
        String ev1 = EvLogEquality(Sep[0]);
        if (IsNum(ev1)) ev1 = (Double.parseDouble(ev1) == 0) ? "False":"True";
        if (!IsBool(ev1)) return "";
        for (int i = 1; i < Sep.length; i++) {
            String ev2 = EvLogEquality(Sep[i]);
            if (IsNum(ev2)) ev2 = (Double.parseDouble(ev2) == 0) ? "False":"True";
            if (!IsBool(ev2)) return "";
            ev1 = (ValBool(ev1) && ValBool(ev2)) + "";
        }
        return ev1;
    }
















    /* Evalua igualdad o desigualdad de los resultados de las subexpresiones*/
    public static String EvLogEquality(String ev){
        String[] sep = splitWithDelimiters(ev, new String[]{"____=____", "____!=____"});
        if (sep.length == 1) return EvLogMat(ev);
        String ev1 = EvLogMat(sep[0]);
        for (int i = 1; i < sep.length; i+=2) {
            String nev = EvLogMat(sep[i+1]);
            if (ev1.isEmpty()) return "";
            switch (sep[i]){
                case "____=____" -> {
                    if (BloqueValor.esNumero(ev1) && BloqueValor.esNumero(nev)) {
                        double num1 = Double.parseDouble(ev1);
                        double num2 = Double.parseDouble(nev);
                        ev1 = (num1 == num2) ? "True" : "False";
                        break;
                    }
                    else ev1 = (ev1.toLowerCase().equals(nev.toLowerCase()))? "True" : "False";
                    break;
                }
                case "____!=____" -> {
                    if (BloqueValor.esNumero(ev1) && BloqueValor.esNumero(nev)) {
                        double num1 = Double.parseDouble(ev1);
                        double num2 = Double.parseDouble(nev);
                        ev1 = (num1 == num2) ? "True" : "False";
                        break;
                    }
                    else ev1 = (ev1.toLowerCase().equals(nev.toLowerCase()))? "True" : "False";
                    break;
                }
            }
        }
        return ev1;
    }















    /*
        Recibe: (String ev) que es un String con la expresion a evaluar
        Devuelve: (String) con la expresion ya evaluada si se pudo dividir
        Hace: Se evaluar expresiones lógicas matematicas (booleanas) compuestas por operadores lógicos matematicos >|<|=|!=|<=|>= , separando el problema a evaluar 
        en dos expresiones usando otro Metodo {EvMatSum} y evaluando su resultado dependiendo del operador que separe a los dos >|<|=|!=|<=|>=.
    */
    public static String EvLogMat(String ev){
        String [] Sep = splitWithDelimiters(ev, new String[]{"____>____", "____<____","____<=____","____>=____"});
        if (Sep.length == 1){ return EvMatSum(Sep[0]); }
        if (Sep.length != 3){ return "";}
        
        try {
            double x = Double.parseDouble(EvMatSum(Sep[0]));
            double x2 = Double.parseDouble(EvMatSum(Sep[2]));
            switch (Sep[1]) {
                case "____>____" -> {return (x > x2) + "";}
                case "____<____" -> {return (x < x2) + "";}
                case "____>=____" -> {return (x >= x2) + "";}
                case "____<=____" -> {return (x <= x2) + "";}
            }
            return x+"";
        }catch (Exception e){ }
        
        
        return "";
    }


















    /*
    * Funcion util
    */
    public static String[] splitWithDelimiters(String input, String[] delimiters) {
        if (input == null || delimiters == null || delimiters.length == 0) {
            return new String[]{input};
        }

        List<String> parts = new ArrayList<>();

        String[] escapedDelimiters = Arrays.stream(delimiters)
                .map(Pattern::quote)
                .toArray(String[]::new);

        String regex = String.join("|", escapedDelimiters);
        Pattern pattern = Pattern.compile("(" + regex + ")");
        Matcher matcher = pattern.matcher(input);

        int lastEnd = 0;
        while (matcher.find()) {
            parts.add(input.substring(lastEnd, matcher.start()));
            parts.add(matcher.group());
            lastEnd = matcher.end();
        }

        if (lastEnd < input.length()) {
            parts.add(input.substring(lastEnd));
        }

        parts.removeIf(String::isEmpty);

        return parts.toArray(new String[0]);
    }



    /*
        Recibe: (String ev) que es un String con la expresion a evaluar
        Devuelve: (String) con la expresion ya evaluada si se pudo dividir
        Hace: Se evaluan las sumas o restas que existan, para ello se separan los terminos que se suman y se evaluan por separado (Metodo {EvMatMult}) 
        y se suman o se restan sus resultados
    */
    public static String EvMatSum(String ev){
        String[] Sep = splitWithDelimiters(ev, new String[]{"____-____", "____+____"});

        if (Sep.length == 1) {
            return EvMatMult(Sep[0]);
        }
        System.out.println(Arrays.toString(Arrays.stream(Sep).toArray()));
        double result = 0.0;
        boolean isPositive = true;

        for (String part : Sep) {
            part = part.trim();
            if (part.isEmpty()) continue;

            if (part.equals("____+____") || part.equals("____-____")) {
                isPositive = part.equals("____+____");
            } else {
                try {
                    double value = Double.parseDouble(EvMatMult(part));
                    result = isPositive ? result + value : result - value;
                } catch (NumberFormatException e) {
                    return "";
                }
            }
        }
        return String.valueOf(result);
    }
    

















    /*
        Recibe: (String ev) que es un String con la expresion a evaluar
        Devuelve: (String) con la expresion ya evaluada si se pudo dividir
        Hace: Se evaluan las multiplicaciones y funciones en su nivel, para ello se separan otros terminos y se hacen, luego se realizan las operaciones respectivas x / %
    */
    public static String EvMatMult(String ev){
        if (ev.isEmpty()) return "";

        String[] Sep = splitWithDelimiters(ev, new String[]{"____x____", "____/____","____%____"});

        if (Sep.length == 1) {
            return EvMatPot(Sep[0]);
        }
        System.out.println("Separation   "+Sep[0]);
        double result = Double.parseDouble(EvMatPot(Sep[0]));

        for (int i = 1; i < Sep.length; i++) {
            String current = Sep[i].trim();
            if (current.isEmpty()) continue;

            double nextValue = Double.parseDouble(EvMatPot(Sep[i + 1]));
            switch (current) {
                case "____x____":
                    result *= nextValue;
                    break;
                case "____/____":
                    if (nextValue == 0) {
                        return "";
                    }
                    result /= nextValue;
                    break;
                case "____%____":
                    result %= nextValue;
                    break;
            }
            i++;
        }

        return String.valueOf(result);
    }
    



















    
    /*
        Recibe: (String ev) que es un String con la expresion a evaluar
        Devuelve: (String) con la expresion ya evaluada si se pudo hacer
        Hace: Se evaluan las potencias, se separan en numeros y se realizan las potencias respectivas, luego se devuelve el resultado
    */
    public static String EvMatPot(String ev){
        String [] Sep = ev.split("____\\^____");
        if (Sep.length == 1){
            return ev;
        }

        try {
            double x = Double.parseDouble(Sep[Sep.length-1]);
            for (int i = Sep.length-2; i >= 0; i--) {
                double x2 = Double.parseDouble(Sep[i]);
                x = Math.pow(x2, x);
            }
            return x+"";
        }catch (Exception e){
            System.out.println("Error Evaluacion de potencia");
        }

        return "";
    }
    




















    /*
        Recibe: (Bloque b, String tipovalor) el bloque operacion y el String que tiene el tipo de valor
        Devuelve: (boolean) devuelve si la operacion se puede hacer dependiendo del tipo de valor que se le envia
        Hace: Verifica que se pueda hacer la operacion con el tipo de dato
    */
    public static boolean OpCorrecta(Bloque b, String tipovalor){
        if (b instanceof BloqueLogico) return true;
        
        if (b instanceof BloqueLMat && b.getValor().equals("=") && tipovalor.equals("str")) return true;
        else if (b instanceof BloqueLMat && !tipovalor.equals("num"))  return false;
        
        if (b instanceof BloqueMat && b.getValor().equals("+") && tipovalor.equals("str")) return true;
        else if (b instanceof BloqueMat && !tipovalor.equals("num"))  return false;
        
        return false;
    }
    









    
    /*
        Recibe: (Bloque b) el bloque a verificar
        Devuelve: (boolean) devuelve si el bloque contiene un valor
        Hace: Verifica si "b" es un bloque que contiene algun tipo de valor
    */
    public static boolean InstBloqueValor(Bloque b){
        return (b instanceof BloqueValor || b instanceof BloqueVariable);
    }
    











    /*
        Recibe: (String s) el bloque a verificar
        Devuelve: (boolean) devuelve el valor booleano del string
        Hace: Verifica si "s" es true, no tiene en cuenta mayusculas y minusculas solo que diga TrUE
    */
    public static boolean ValBool(String s){
        s = s.toLowerCase();
        return s.equals("true");
    }

    public static boolean IsBool(String s){
        s = s.toLowerCase();
        return s.equals("true") || s.equals("false");
    }



    /*
        Recibe: (String s) el string a verificar
        Devuelve: (boolean) devuelve si es un numero
        Hace: Revisa si el string está compuesto de solo caracteres de numeros o punto
    */
    public static boolean IsNum(String s) {
        if (s == null || s.isEmpty()) return false; // Handle null or empty string
        try {
            Double.parseDouble(s); // Attempt to parse as a number
            return true; // It's a valid number
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }
    }

    /*
        Recibe: (String s) el string a verificar
        Devuelve: (boolean) devuelve si es una operación matemática
        Hace: Revisa si el string está compuesto de solo caracteres de operaciones numéricas
    */
    public static boolean IsMatExpresion(String s) {
        if (s == null || s.isEmpty()) return false; // Handle null or empty string
        String validChars = "1234567890+-x/%^. _"; // Define valid characters as a string

        for (int i = 0; i < s.length(); i++) {
            if (validChars.indexOf(s.charAt(i)) == -1) { // Check if character is not in validChars
                return false; // If any character is invalid, it's not a math expression
            }
        }
        return true; // All characters are valid
    }
    
}
