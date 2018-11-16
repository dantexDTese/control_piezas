
package Controller.RequisicionesController;

import Model.Estructuras;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.RequisicionesModel.AgregarMaterialREquisicionModel;
import Model.RequisicionesModel.AgregarRequisicionesModel;
import Model.RequisicionesModel.MaterialesRequisicion;
import Model.RequisicionesModel.ParcialidadMaterial;
import Model.RequisicionesModel.Proveedores;
import Model.RequisicionesModel.Requisicion;
import View.Principal;
import View.Requisiciones.AgregarMaterialRequisicion;
import View.Requisiciones.AgregarRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class AgregarRequisicinesController {

    /**
     * ATRIBUTOS
     */
    private final AgregarRequisiciones view;
    private final AgregarRequisicionesModel model;
    private ArrayList<Proveedores> listProveedores;
    private final ArrayList<ParcialidadMaterial> listaParcialidad;
    private Integer noOrdenSeleccionada;
    private final Principal principal;
    private float subTotalRequisicion;
    private Proveedores proveedorSeleccionado;
    /**
     * CONSTRUCTOR
     * @param view
     * @param model
     * @param principal
     */
    public AgregarRequisicinesController(AgregarRequisiciones view,
            AgregarRequisicionesModel model, Principal principal) {
        this.listaParcialidad = new ArrayList<>();
        this.noOrdenSeleccionada = null;   
        this.view = view;
        this.model = model;
        this.principal = principal;
        this.listProveedores = model.listaProveedores();
        llenarListaPendientes();
        llenarListaProveedores();
        this.view.getJtbPendientes().addMouseListener(listenerOrdenesPendientes);
        this.view.getCbxNoProveedor().addActionListener(listenerAccion);
        this.view.getJtbMaterialesRequeridos().addMouseListener(listenerMaterialesOrden);
        this.view.getBtnEnviar().addActionListener(listenerAccion);
    }
    
    /**
     * METODOS
     */
    private void llenarListaProveedores(){    
        view.getCbxNoProveedor().removeAllItems();
        for(int i = 0;i<listProveedores.size();i++)
            view.getCbxNoProveedor().addItem(listProveedores.get(i).getNoProveedor()+"");
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
    
    private void limpiar(){
        listaParcialidad.clear();
        view.getTxtComentarios().setText("");
        view.getTxtDescProveedor().setText("");
        view.getTxtDireccion().setText("");
        view.getTxtLugarEntrega().setText("");
        view.getTxtSolicitante().setText("");
        view.getTxtTerminoCompra().setText("");
        view.getLbIVA().setText("");
        view.getLbSubTotal().setText("");
        view.getLbTotal().setText("");
        Estructuras.limpiarTabla((DefaultTableModel) view.getJtbListaMateriales().getModel());
        Estructuras.limpiarTabla((DefaultTableModel) view.getJtbProductos().getModel());
        Estructuras.limpiarTabla((DefaultTableModel) view.getJtbMaterialesRequeridos().getModel());
        llenarListaPendientes();
    }
    
    /**
     * EVENTOS
     */
    
    
    
    private final ActionListener listenerAccion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == view.getCbxNoProveedor()){
                if(view.getCbxNoProveedor().getItemCount()>0){
                    proveedorSeleccionado = listProveedores.get(Integer.parseInt(view.getCbxNoProveedor().getSelectedItem().toString()) - 1 );                
                    view.getTxtDescProveedor().setText(proveedorSeleccionado.getDescProveedor());
                    view.getTxtDireccion().setText(proveedorSeleccionado.getDireccion());   
                }   
            }else if(e.getSource() == view.getBtnEnviar()){
                if(listaParcialidad.size()>0)
                    enviarSolicitud();
                else
                    JOptionPane.showMessageDialog(null,"no ha agregado materiales a la lista");
            }
        }
        
        
        private void enviarSolicitud(){
            if(!"".equals(view.getTxtLugarEntrega().getText()) && !"".equals(view.getTxtTerminoCompra().getText()) 
                    &&!"".equals(view.getTxtSolicitante().getText()) && !"".equals(view.getLbIVA().getText())){
                
                Requisicion requisicion = new Requisicion(
                        view.getTxtDescProveedor().getText(),noOrdenSeleccionada,view.getTxtSolicitante().getText(),view.getTxtTerminoCompra().getText(),
                        view.getTxtLugarEntrega().getText(),view.getTxtComentarios().getText(),Float.parseFloat(view.getLbSubTotal().getText())
                        ,Float.parseFloat(view.getLbIVA().getText()),Float.parseFloat(view.getLbTotal().getText()));
                
                
                agregarListaMatereriales(model.agregarRequisicion(requisicion));               
                
            }
        }
        
        private void agregarListaMatereriales(boolean respuesta){
            if(respuesta){
                for(int i = 0;i<listaParcialidad.size();i++){
                    ParcialidadMaterial material = listaParcialidad.get(i);
                    model.agregarMaterialRequisicion(material);
                }
                JOptionPane.showMessageDialog(null,"La requisicion fue guardada y se enviara para su aprovacion");
                limpiar();      
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
                    productosPendientes.get(i).getMaterial(),
                    productosPendientes.get(i).getFechaInicio()
                });
        }
       
       private void llenarListaMateriales(int noOrdenTrabajo){
           ArrayList<MaterialesRequisicion> materialesRequeridos = model.listaMaterialesRequeridos(noOrdenTrabajo);
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
                                
                if(proveedorSeleccionado != null)
                    agregarParcialidad(iniciarParcialidad(e));
                else
                    JOptionPane.showMessageDialog(null, "por favor seleccione un proveedor");
                
            }
            
        }
        
        private ParcialidadMaterial iniciarParcialidad(MouseEvent e){
            int fila = view.getJtbMaterialesRequeridos().rowAtPoint(e.getPoint());
                    String material =view.getJtbMaterialesRequeridos().getValueAt(fila, 0).toString(); 
                    int nParcialidad = model.obtenerParcialidad(material,noOrdenSeleccionada);
            return new ParcialidadMaterial(listaParcialidad.size()+1, nParcialidad+1, material,proveedorSeleccionado.getDescProveedor());
        }
                
        private void agregarParcialidad(ParcialidadMaterial parcialidad){            
                    AgregarMaterialRequisicion viewAgregarMaterial = new AgregarMaterialRequisicion(principal , true);
                    AgregarMaterialRequisicionesController controllerAgregarMaterial = 
                       new AgregarMaterialRequisicionesController(viewAgregarMaterial,new AgregarMaterialREquisicionModel(),parcialidad);           
                    
                    viewAgregarMaterial.addWindowListener(new WindowAdapter() {
                        
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e); 
                            if(controllerAgregarMaterial.isOperacionCompletada())
                                AgregarParcialidadRegistrada(controllerAgregarMaterial.getParcialidad());
                            else System.err.println("no se agrego nada");
                        }

                        private void AgregarParcialidadRegistrada(ParcialidadMaterial parcialidad) {
                            listaParcialidad.add(parcialidad);
                            subTotalRequisicion += parcialidad.getPrecioTotal();
                            float ivaCalculado = subTotalRequisicion*proveedorSeleccionado.getIVA()/100;
                            view.getLbSubTotal().setText(subTotalRequisicion+"");
                            view.getLbIVA().setText(ivaCalculado+"");
                            view.getLbTotal().setText(ivaCalculado+subTotalRequisicion+"");
                            DefaultTableModel modelMaterialesRequeridos = (DefaultTableModel) view.getJtbListaMateriales().getModel();
                            modelMaterialesRequeridos.addRow(new Object[]{
                            parcialidad.getNoPartida(),
                            parcialidad.getMaterial(),
                            parcialidad.getNoParcialidad(),
                            parcialidad.getFechaSolicitadaParcialidadMaterial(),
                            parcialidad.getCuentaCargo(),
                            parcialidad.getUnidad(),
                            parcialidad.getCantidad(),
                            parcialidad.getPrecioUnitario(),
                            parcialidad.getPrecioTotal()
                            });
                        }
                                                                       
                    });
                    
                    viewAgregarMaterial.setVisible(true);                
        }

    };
    
}
