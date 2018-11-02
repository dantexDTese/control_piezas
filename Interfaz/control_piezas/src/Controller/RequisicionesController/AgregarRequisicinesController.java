
package Controller.RequisicionesController;

import Model.Estructuras;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.RequisicionesModel.AgregarRequisicionesModel;
import Model.RequisicionesModel.Proveedores;
import View.Requisiciones.AgregarRequisiciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;



public class AgregarRequisicinesController {

    private final AgregarRequisiciones view;
    private final AgregarRequisicionesModel model;
    
    public AgregarRequisicinesController(AgregarRequisiciones view,
                        AgregarRequisicionesModel model) {
   
        this.view = view;
        this.model = model;
        llenarListaPendientes();
        view.getJtbPendientes().addMouseListener(listenerOrdenesPendientes);
        
    }
    ArrayList<Proveedores> listProveedores;
    
    private void llenarListaProveedores(){
        listProveedores = model.listaProveedores();
        view.getCbxNoProveedor().removeAllItems();
        
    }
    
    
    
    private void llenarListaPendientes(){
        ArrayList<Pedido> listaPendientes = model.listaOrdenesPendientes();
        Estructuras.limpiarTabla((DefaultTableModel) view.getJtbPendientes().getModel());
        DefaultTableModel model = (DefaultTableModel) view.getJtbPendientes().getModel();
        for(int i = 0;i<listaPendientes.size();i++)
        model.addRow(new Object[]{
            listaPendientes.get(i).getNoOrdenTrabajo(),
            listaPendientes.get(i).getNoOrdenCompra()
        });
    }
    
    
    private final MouseAdapter listenerOrdenesPendientes = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = view.getJtbPendientes().rowAtPoint(e.getPoint());
            llenarListaProductos((int) view.getJtbPendientes().getValueAt(fila,0));                        
            llenarListaMateriales((int) view.getJtbPendientes().getValueAt(fila,0));
        }
        
       private void llenarListaProductos(int noOrdenTrabajo){
            ArrayList<ProductosPendientes> productosPendientes = model.listaProductosPendientes(noOrdenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) view.getJtbProductos().getModel());
            DefaultTableModel model = (DefaultTableModel) view.getJtbProductos().getModel();
            for(int i = 0;i<productosPendientes.size();i++)
                model.addRow(new Object[]{
                    productosPendientes.get(i).getClaveProducto(),
                    productosPendientes.get(i).getQty(),
                    productosPendientes.get(i).getMaterial()
                });
        }
       
       private void llenarListaMateriales(int noOrdenTrabajo){
           ArrayList<AgregarRequisicionesModel.MaterialesRequisicion> materialesRequeridos = model.listaMaterialesRequeridos(noOrdenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) view.getJtbMaterialesRequeridos().getModel());
            DefaultTableModel model = (DefaultTableModel) view.getJtbMaterialesRequeridos().getModel();
            for(int i = 0;i<materialesRequeridos.size();i++)
                model.addRow(new Object[]{
                    materialesRequeridos.get(i).getMaterial(),
                    materialesRequeridos.get(i).getBarrasNecesarias(),
                    0
                });
       }
        
    };
    
    
}
