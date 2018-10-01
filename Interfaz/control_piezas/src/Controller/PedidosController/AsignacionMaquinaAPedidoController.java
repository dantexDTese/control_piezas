
package Controller.PedidosController;

import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.ProductosPendientes;
import Model.PedidosModel.ordenPlaneada;
import Model.PedidosModel.procedimientoTotal;
import View.Pedidos.AsignarMaquinaAPedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


public class AsignacionMaquinaAPedidoController {

    private AsignarMaquinaAPedido vista;
    private AsignacionMaquinaAPedidoModel model;
    private ArrayList<ProductosPendientes> pendientes;
    private ProductosPendientes pendiente;
    private final ActionListener productosListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pendiente = obtenerPendiente(vista.getCbxProducto().getSelectedItem().toString());
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
        }
    };
    
    private final ActionListener guardarCambiosOrden = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista!=null){
                model.agregarOrdenMaquina(new ordenPlaneada(
                Integer.parseInt(vista.getLbNoOrdenProduccion().getText()),
                vista.getCbxProducto().getSelectedItem().toString(),
                Float.parseFloat(vista.getTxtWorker().getText()),
                Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()),
                vista.getCbxMaquina().getSelectedItem().toString(),
                vista.getCbxMateriales().getSelectedItem().toString()
                ));
                
                try {
                    vista.dispose();
                } catch (Throwable ex) {
                    Logger.getLogger(AsignacionMaquinaAPedidoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        llenarMateriales();
        this.vista.getBtnGuardar().addActionListener(guardarCambiosOrden);
        
    }
    
        
    private void llenarMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        for(int i = 0;i<maquinas.size();i++)
            vista.getCbxMaquina().addItem(maquinas.get(i).toString());
    }
    
    private void llenarMateriales(){
        ArrayList<String> materiales = model.listaMateriales();
        for(int i = 0;i<materiales.size();i++)
            vista.getCbxMateriales().addItem(materiales.get(i));
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
