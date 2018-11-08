
package Controller.RequisicionesController;

import Model.Estructuras;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.RequisicionesModel.AgregarRequisicionesModel;
import Model.RequisicionesModel.Proveedores;
import View.Requisiciones.AgregarRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class AgregarRequisicinesController {

    private final AgregarRequisiciones view;
    private final AgregarRequisicionesModel model;
    private ArrayList<Proveedores> listProveedores;
    private ArrayList<AgregarRequisicionesModel.ParcialidadMaterial> listaParcialidad;
    public AgregarRequisicinesController(AgregarRequisiciones view,
                        AgregarRequisicionesModel model) {
        
        this.listaParcialidad = new ArrayList<>();
        this.noOrdenSeleccionada = null;   
        this.view = view;
        this.model = model;
        this.listProveedores = model.listaProveedores();
        llenarListaPendientes();
        llenarListaProveedores();
        this.view.getJtbPendientes().addMouseListener(listenerOrdenesPendientes);
        this.view.getCbxNoProveedor().addActionListener(listenerProveedorSeleccionado);
        this.view.getJtbMaterialesRequeridos().addMouseListener(listenerMaterialesOrden);
    }
    
    
    private void llenarListaProveedores(){    
        view.getCbxNoProveedor().removeAllItems();
        for(int i = 0;i<listProveedores.size();i++)
            view.getCbxNoProveedor().addItem(listProveedores.get(i).getNoProveedor()+"");
    }
    
    private Integer noOrdenSeleccionada;
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
    
    private final ActionListener listenerProveedorSeleccionado = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(view.getCbxNoProveedor().getItemCount()>0){
                Proveedores proveedor = listProveedores.get(
                        Integer.parseInt(view.getCbxNoProveedor().getSelectedItem().toString()) - 1 );                
                view.getTxtDescProveedor().setText(proveedor.getDescProveedor());
                view.getTxtDireccion().setText(proveedor.getDireccion());   
            }   
        }
    };
            
    private final MouseAdapter listenerOrdenesPendientes = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = view.getJtbPendientes().rowAtPoint(e.getPoint());
            noOrdenSeleccionada = (int) view.getJtbPendientes().getValueAt(fila,0);
            llenarListaProductos(noOrdenSeleccionada);                        
            llenarListaMateriales(noOrdenSeleccionada);
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
    
    
    
    
    private final MouseAdapter listenerMaterialesOrden = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                JOptionPane.showConfirmDialog(null,"¿Segur@ que desea comenzar la requisicion?");
                int fila = view.getJtbMaterialesRequeridos().rowAtPoint(e.getPoint());
                inicializarTabla(view.getJtbMaterialesRequeridos().getValueAt(fila, 0).toString());
            }
            
        }
        
        private void inicializarTabla(String material){
            Estructuras.limpiarTabla((DefaultTableModel) view.getJtbListaMateriales().getModel());
            DefaultTableModel modelTabla = (DefaultTableModel) view.getJtbListaMateriales().getModel();
            int noPartida = modelTabla.getRowCount()+1;
            modelTabla.addRow(new Object[]{noPartida,material});            
        }
    
    };
    
    
    
    
}
