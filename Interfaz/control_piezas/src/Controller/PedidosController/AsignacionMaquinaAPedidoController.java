
package Controller.PedidosController;

import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.ProductosPendientes;
import View.Pedidos.AsignarMaquinaAPedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AsignacionMaquinaAPedidoController {


    
    private AsignarMaquinaAPedido vista;
    private AsignacionMaquinaAPedidoModel model;
    private ArrayList<ProductosPendientes> pendientes;
    private ProductosPendientes pendiente;
    private ActionListener productosListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pendiente = obtenerPendiente(vista.getCbxProducto().getSelectedItem().toString());
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
        }
    };
    
    public AsignacionMaquinaAPedidoController(AsignarMaquinaAPedido vista,AsignacionMaquinaAPedidoModel model,
            String ordenCOmpra){
     
        this.vista = vista;
        this.model = model;
        this.vista.getLbOrdenCompra().setText(ordenCOmpra);
        pendientes = this.model.listaProductosPendientes(ordenCOmpra);
        llenarProductos();
        llenarMaquinas();
    }
    
    private void llenarMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        for(int i = 0;i<maquinas.size();i++)
            vista.getCbxMaquina().addItem(maquinas.get(i).toString());
    }
    
    private void llenarProductos(){
        for(int i = 0;i<pendientes.size();i++)
            vista.getCbxProducto().addItem(pendientes.get(i).getClaveProducto());
        
        vista.getCbxProducto().addActionListener(productosListener);
    }
    
    private ProductosPendientes obtenerPendiente(String claveProductos){
        for(int i = 0;i<pendientes.size();i++)
            if(claveProductos.equals(pendientes.get(i).getClaveProducto()))
                return pendientes.get(i);
        
        return null;
    }
    
    
    
    
    
}
