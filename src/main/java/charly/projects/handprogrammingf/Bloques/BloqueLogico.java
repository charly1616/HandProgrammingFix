/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charly.projects.handprogrammingf.Bloques;

import javafx.scene.paint.Color;

/**
 *
 * @author User
 */
public class BloqueLogico extends BloqueOP{
    
    public BloqueLogico(double x, double y, String sign) {
        super(x, y, sign, Color.rgb(255, 106, 194));
    }
    
    
   // Si signo es igual a "o", se devuelve la cadena "o"; de lo contrario, se devuelve el valor actual de signo
    @Override
    public String getValor(){
        if (signo.equals("o")){
            return "___o___";
        }
        return this.signo;
    }
    
    
}
