/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ProduccionController;

import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import View.Produccion.BitacoraOrdenesTrabajoView;

/**
 *
 * @author cesar
 */
public class BitacoraOrdenesTrabajoController {

    private BitacoraOrdenesTrabajoView bitacoraTrabajosView;
    private BitacoraOrdenesTrabajoModel bitacoratrabajosModel;
    
    public BitacoraOrdenesTrabajoController(BitacoraOrdenesTrabajoView bitacoraTrabajosView, BitacoraOrdenesTrabajoModel bitacoratrabajosModel) {
        this.bitacoraTrabajosView = bitacoraTrabajosView;
        this.bitacoratrabajosModel = bitacoratrabajosModel;
    }
    
}
