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
public class BloqueLMat extends BloqueOP{

    public BloqueLMat(double x, double y, String sign) {
        super(x, y, sign, ConstantesDeBloques.ColoresBloques.get(BloqueLMat.class));
    }

}
