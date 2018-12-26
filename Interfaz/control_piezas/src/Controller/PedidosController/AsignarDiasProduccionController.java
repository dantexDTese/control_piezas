
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.AsignarDiasProduccionModel;
import Model.PedidosModel.Pedido;
import View.Pedidos.AsignarDiasProduccion;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class AsignarDiasProduccionController {

    private final AsignarDiasProduccion diasProduccionView;
    private final AsignarDiasProduccionModel asignarDiasProduccionModel;
    
    AsignarDiasProduccionController(AsignarDiasProduccion diasProduccionView,
            AsignarDiasProduccionModel asignarDiasProduccionModel) {
            
        this.diasProduccionView = diasProduccionView;
        this.asignarDiasProduccionModel = asignarDiasProduccionModel;
        llenarTablaPedidosPendientes();
    }
    
    private void llenarTablaPedidosPendientes(){        
        ArrayList<Pedido> listaPendientes = asignarDiasProduccionModel.listaPedidosPendientes();
        DefaultTableModel modelT = (DefaultTableModel) diasProduccionView.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            Pedido ped = listaPendientes.get(i);
            modelT.addRow(new Object[]{ped.getNoOrdenTrabajo(),ped.getNoOrdenCompra(),ped.getFechaRecepcion()});
        }
    }   
    
}
