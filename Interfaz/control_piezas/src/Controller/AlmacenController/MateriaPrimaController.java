/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.AlmacenController;

import Model.AlmacenModel.MateriaPrimaModel;
import View.almacenView.MateriaPrimaView;

/**
 *
 * @author cesar
 */
public class MateriaPrimaController {

    
    private MateriaPrimaView mPrimaView;
    private MateriaPrimaModel mPrimaModel;
    
    public MateriaPrimaController(MateriaPrimaView mPrimaView, MateriaPrimaModel mPrimaModel) {
        this.mPrimaView = mPrimaView;
        this.mPrimaModel = mPrimaModel;
    }
    
    
    
}
