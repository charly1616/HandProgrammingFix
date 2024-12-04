
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.Bloque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.scene.paint.Color;


public class BloqueEjecutable extends Bloque{
    public HashMap<String,String> variables = new HashMap<>();
    
    
    public BloqueEjecutable(double x, double y) {
        super(x, y);

        Inconectableh = true;
    }

    /*
     Verifica si una variable con un nombre específico existe en la lista de variables 
    del objeto BloqueEjecutable y devuelve true si existe y false si no existe. 
    */
    public boolean esVariable(String nombre) {
        for (String variable : variables.keySet()) {
            if (variable.equals(nombre)) {
                return true;
            }
        }
        return false;
    }

 
    //permite obtener el valor de una variable específica buscándola por nombre en la lista de variables del objeto BloqueEjecutable
    public String getValor(BloqueVariable b) {
        if (esVariable(b.getNombre())){
            return variables.get(b.getNombre());
        }
//        for (BloqueVariable variable : variables) {
//            if (variable.getNombre().equals(b.getNombre())) {
//                return variable.getValor();
//            }
//        }
        
        return "Non";
    }
    
    
    /*
    permite establecer o modificar el valor de una variable específica en la lista de variables del objeto BloqueEjecutable.
    Si la variable con el mismo nombre ya existe en la lista
    Actualiza su valor; de lo contrario, agrega una nueva variable a la lista con el nombre y valor especificados
    */
    public void setValor(BloqueVariable b, String valor) {
        if (b == null || b.getNombre() == null || valor == null) {
            throw new IllegalArgumentException("BloqueVariable y su valor no pueden ser nulos.");
        }
        variables.put(b.getNombre(), valor);
        System.out.println(variables.toString());
//        for (BloqueVariable variable : variables) {
//            if (variable.getNombre().equals(b.getNombre())) {
//                variable.setValor(valor);
//                hacambiado = true;
//            } 
//        }
        if (ejecutador != null){
            if (ejecutador.esVariable(b.getNombre())){
                ejecutador.setValor(b, valor);
            }
        }

    }
    
    
    public void deleteValor(BloqueVariable b){
        variables.remove(b.getNombre());
    }
    
    
    
    //Hasta ahora elimina todas las variables almacenadas en la lista variables de un objeto BloqueEjecutable
    public void vaciarVariables() {
        variables.clear();
        limpiarEjecutadores();
        
        //Ejecutar la siguiente linea
        if (!(this instanceof BloqueInicio)){
            Bloque sig = SiguienteLinea();
            while (sig != null || sig instanceof BloqueElif || sig instanceof BloqueElse){
                sig = sig.SiguienteLinea();
            }
            if (sig != null) {
                sig.ejecutador = this.ejecutador;
                sig.Hacer();
            }
        }
    }
    
    public void limpiarEjecutadores(){
    }
    
    
    
    
    /*
    realiza acciones relacionadas con la manipulación de variables y la ejecución de bloques condicionales. 
    El comportamiento exacto dependerá de la implementación de las clases y
    objetos específicos que utilizan esta función en el código completo.
    */
    @Override
    public void Hacer(){
        this.LineaEjecutador();
        if (ejecutador.variables !=  null) variables.putAll(ejecutador.variables);
        if (this instanceof BloqueCondicional && !(this instanceof BloqueFor)) EjecutarHijos();
    }
    
    /*
     se utiliza para ejecutar los bloques hijos de un bloque condicional en ciertas condiciones.
    Verifica si existe una conexión (conexion) con un bloque hijo y, si es el caso,
    configura el objeto actual como el ejecutador del bloque hijo y ejecuta dicho bloque. 
    */
    public void EjecutarHijos(){
        if (this.cvertical.inner.conexion == null) return;
        this.cvertical.inner.conexion.ejecutador = this;
        this.cvertical.inner.conexion.Hacer();
    }
    
    
    
}
