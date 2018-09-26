/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.EntregarMaterialController;

import Model.EntregasMaterialModel.controlEntregasModel;
import View.EntregasMaterialView.ControlEntregasView;

/**
 *
 * @author cesar
 */
public class controlEntregasController {

    
    private ControlEntregasView entregasView;
    private controlEntregasModel entregasModel;
    public controlEntregasController(ControlEntregasView entragasView, controlEntregasModel entregasModel) {
        this.entregasView = entragasView;
        this.entregasModel = entregasModel;
        
    }

    
    
}
