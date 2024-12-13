/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charly.projects.handprogrammingf.Bloques;

import charly.projects.handprogrammingf.Model.ConstantesDeBloques;
import javafx.scene.paint.Color;

/**
 *
 * @author User
 */
public class BloqueLogico extends BloqueOP{
    
    public BloqueLogico(double x, double y, String sign) {
        super(x, y, sign, ConstantesDeBloques.ColoresBloques.get(BloqueLogico.class));
    }
    
    
   // Si signo es igual a "o", se devuelve la cadena "o"; de lo contrario, se devuelve el valor actual de signo
    @Override
    public String getValor(){
        if (signo.equals("o")){
            return "____o____";
        } else if (signo.equals("&")){
            return "____&____";
        }
        return this.signo;
    }
    
    
}
