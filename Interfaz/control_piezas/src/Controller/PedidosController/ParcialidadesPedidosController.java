/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PedidosController;

import Model.Parcialidad;
import Model.PedidosModel.ParcialidadesPedidosModel;
import View.Pedidos.ParcialidadesPedidos;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class ParcialidadesPedidosController {

    ParcialidadesPedidos vistaParcialidades;
    ParcialidadesPedidosModel parcialidadesPedidosModel;    
    
    ParcialidadesPedidosController(ParcialidadesPedidos vistaParcialidades, ParcialidadesPedidosModel parcialidadesPedidosModel) {
        this.vistaParcialidades = vistaParcialidades;
        this.parcialidadesPedidosModel = parcialidadesPedidosModel;
        this.vistaParcialidades.getLbClaveOrdenCompra().setText(this.vistaParcialidades.getNoOrden());
        this.vistaParcialidades.getLbProducto().setText(vistaParcialidades.getProducto());
        this.vistaParcialidades.getLbCantidadTotal().setText(""+vistaParcialidades.getCantidad());
        llenarTabla();
        obtenerValorRestante();
    }
    
    
    
    private void obtenerValorRestante(){
        
        DefaultTableModel modelTabla = (DefaultTableModel) this.vistaParcialidades.getJtParcialidadesPedidos().getModel();
        int suma=0;
        
        
        for(int i = 0;i<modelTabla.getRowCount();i++)
          suma+= Integer.parseInt(modelTabla.getValueAt(i,1).toString());
        
        vistaParcialidades.getLbCantidadRestante().setText(""+(vistaParcialidades.getCantidad()-suma));
        
    }
    
    private void llenarTabla(){
        ArrayList<Parcialidad> parcialidades = parcialidadesPedidosModel.listaParcialidades(vistaParcialidades.getNoOrden(),vistaParcialidades.getProducto());
            
        DefaultTableModel model = (DefaultTableModel) vistaParcialidades.getJtParcialidadesPedidos().getModel();
        
        if(parcialidades.size()>0){
            
            for(int i = 0;i<parcialidades.size();i++){
                model.addRow(new Object[]{parcialidades.get(i).getFechaEntrega(),parcialidades.get(i).getCantidadEntregada()});
            }
            
        }
          
    }
    
    
    
    
}
