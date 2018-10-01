/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ProduccionController;

import Model.Estructuras;
import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import Model.ProduccionModel.RegistroOrdenTrabajo;
import View.Produccion.BitacoraOrdenesTrabajoView;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class BitacoraOrdenesTrabajoController {

    private BitacoraOrdenesTrabajoView bitacoraTrabajosView;
    private BitacoraOrdenesTrabajoModel bitacoraTrabajosModel;
    
    public BitacoraOrdenesTrabajoController(BitacoraOrdenesTrabajoView bitacoraTrabajosView, BitacoraOrdenesTrabajoModel bitacoratrabajosModel) {
        this.bitacoraTrabajosView = bitacoraTrabajosView;
        this.bitacoraTrabajosModel = bitacoratrabajosModel;
    }
    
    private void llenarTablaOrdenesTrabajo(){
        ArrayList<RegistroOrdenTrabajo> ordenesTrabajo = bitacoraTrabajosModel.listaOrdenesTrabajo();
        DefaultTableModel modelOrdenesTrabajo = (DefaultTableModel) bitacoraTrabajosView.getTbOrdenesTrabajo().getModel();
        
        Estructuras.limpiarTabla(modelOrdenesTrabajo);
        
        if(ordenesTrabajo.size()>0)
            for(int i = 0;i<ordenesTrabajo.size();i++){
                RegistroOrdenTrabajo ordenTrabajo = ordenesTrabajo.get(i);
                
            }
    }
    
    
    
    
    
    
}
