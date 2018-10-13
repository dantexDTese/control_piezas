
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.PedidosModel.ordenPlaneada;
import Model.PedidosModel.procedimientoTotal;
import View.Pedidos.AsignarMaquinaAPedido;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AsignacionMaquinaAPedidoController {

    private AsignarMaquinaAPedido vista;
    private AsignacionMaquinaAPedidoModel model;
    private ArrayList<ProductosPendientes> pendientes;
    private ProductosPendientes pendiente;
    private final ActionListener productosListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //pendiente = obtenerPendiente(vista.getCbxProducto().getSelectedItem().toString());
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
                Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()),
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
        
    private String ordenTrabajo;
    
    public AsignacionMaquinaAPedidoController(AsignarMaquinaAPedido vista,AsignacionMaquinaAPedidoModel model){
     
        this.vista = vista;
        this.model = model;
        //this.vista.getLbOrdenCompra().setText(ordenCOmpra);
       // llenarProductos();
        llenarMaquinas();
        llenarMateriales();
        
        this.vista.getJtOrdenesPendientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); 
                   seleccionarOrdenTrabajo(e.getPoint());
            }
        });
        
        this.vista.getBtnGuardar().addActionListener(guardarCambiosOrden);        

        agregarPedidosPendientes();
    }
    
    private void seleccionarOrdenTrabajo(Point pFila){
        int fila = vista.getJtOrdenesPendientes().rowAtPoint(pFila);
        ordenTrabajo = vista.getJtOrdenesPendientes().getValueAt(fila,0)+"";
        pendientes = this.model.listaProductosPendientes(ordenTrabajo);
        
    }
    
    
    private void agregarPedidosPendientes(){        
        ArrayList<Pedido> listaPendientes = model.listaPedidosPendientes();
        JOptionPane.showMessageDialog(null,listaPendientes.size());
        DefaultTableModel modelT = (DefaultTableModel) vista.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            modelT.addRow(new Object[]{listaPendientes.get(i).getNo_orden_compra(),listaPendientes.get(i).getFecha_recepcion()});
        }
    }
    
    private void llenarMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        for(int i = 0;i<maquinas.size();i++)
            vista.getCbxMaquina().addItem(maquinas.get(i));
    }
    
    private void llenarMateriales(){
        ArrayList<String> materiales = model.listaMateriales();
        for(int i = 0;i<materiales.size();i++)
            vista.getCbxMateriales().addItem(materiales.get(i));
    }
    
    /*
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
    }*/
    
}
