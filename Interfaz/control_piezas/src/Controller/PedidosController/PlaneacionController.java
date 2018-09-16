/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PedidosController;

import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.PlaneacionModel;
import View.Pedidos.AsignarMaquinaAPedido;
import View.Pedidos.PlaneacionView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author cesar
 */
public class PlaneacionController  {

    private PlaneacionView vista;
    private PlaneacionModel model;
    
    private MouseAdapter moueAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(e.getClickCount() >= 2){
                int fila = vista.getTbListaPedidosPendientes().rowAtPoint(e.getPoint());
                AsignarMaquinaAPedido vistaSelecMaquina = new AsignarMaquinaAPedido(vista.getPrincpial(), true);
                AsignacionMaquinaAPedidoController controller = new AsignacionMaquinaAPedidoController(vistaSelecMaquina,
                new AsignacionMaquinaAPedidoModel(),
                vista.getTbListaPedidosPendientes().getValueAt(fila, 0).toString());
                vistaSelecMaquina.setVisible(true);
            }
            
        }
        
            
     };
    
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
        
        this.vista = vista;
        this.model = model;
        tamanoTabla();
        llenarListaMaquinas();
        llenarListaPedidosPendientes();
        this.vista.getTbListaPedidosPendientes().addMouseListener(moueAdapter);
    }
    
    private void tamanoTabla(){
        
        vista.getTbLIstaPedidosMaquina().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Integer[] listaTamanos = {140,140,140,130,130,110,120,140};
            
            for(int i = 0;i<listaTamanos.length;i++){
                TableColumn columna = vista.getTbLIstaPedidosMaquina().getColumnModel().getColumn(i);
                columna.setPreferredWidth(listaTamanos[i]);
            }
        
    }
    
    
    private void llenarListaMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        
        if(maquinas.size()>0)
            for(int i = 0;i<maquinas.size();i++)
                vista.getCbxListaMaquinas().addItem(maquinas.get(i));
                     
        
        
    }
    
    private void llenarListaPedidosPendientes(){
     ArrayList<Pedido> pedidos = model.listaPedidosPendientes();
     
     if(pedidos.size()>0){
     
         DefaultTableModel tableModel = (DefaultTableModel) vista.getTbListaPedidosPendientes().getModel();
         for(int i = 0;i<pedidos.size();i++)
             tableModel.addRow(new Object[]{pedidos.get(i).getNo_orden_compra(),pedidos.get(i).getFecha_recepcion()});
         
     }
     
     
         
     
    }
}
