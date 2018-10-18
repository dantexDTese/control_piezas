package Controller.PedidosController;
import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.CalendarioModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.PedidosModel.ordenPlaneada;
import Model.PedidosModel.procedimientoTotal;
import View.Pedidos.AsignarMaquinaAPedido;
import View.Pedidos.Calendario;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


public class AsignacionMaquinaAPedidoController {

    private AsignarMaquinaAPedido vista;
    private final AsignacionMaquinaAPedidoModel model;
    private ArrayList<ProductosPendientes> pendientes;
    private final ArrayList<ProductosPendientes> agregados;
    private ProductosPendientes pendiente=null;                
    private String ordenTrabajo;
    
    public AsignacionMaquinaAPedidoController(AsignarMaquinaAPedido vista,AsignacionMaquinaAPedidoModel model){     
        this.vista = vista;
        this.model = model;                
        llenarListaMaquinas();
        llenarListaMateriales();        
        this.vista.getJtOrdenesPendientes().addMouseListener(listenerSeleccionarPendiente);        
        this.vista.getBtnGuardar().addActionListener(listenerGuardarCambiosOrden);   
        llenarTablaPedidosPendientes();        
        agregados = new ArrayList<>();
        Estructuras.obtenerCalendario(this.vista.getJpCalendario());
        this.vista.getBtnTerminar().addActionListener(listenerTerminarPendientes);
    }
    
    
    private void limpiar(){
    
        vista.getLbNoOrdenProduccion().setText("");
        vista.getLbCantidadCliente().setText("");
        vista.getSprCantidadProducir().setValue(0);
        vista.getSprPiecesByShift().setValue(0);
        vista.getJdcFechaMontajeMolde().setDate(null);
        vista.getDcrFechaInicioProduccion().setDate(null);
    
    }
    
    
    /**
     * LLENAR LISTAS Y TABLAS
     */
    private void llenarTablaPedidosPendientes(){        
        ArrayList<Pedido> listaPendientes = model.listaPedidosPendientes();
        DefaultTableModel modelT = (DefaultTableModel) vista.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            Pedido ped = listaPendientes.get(i);
            modelT.addRow(new Object[]{ped.getNoOrdenCompra(),
                ped.getNoOrdenTrabajo(),ped.getFechaRecepcion()});
        }
    }
    
    private void llenarListaMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        for(int i = 0;i<maquinas.size();i++)
            vista.getCbxMaquina().addItem(maquinas.get(i));
    }
    
    private void llenarListaMateriales(){
        ArrayList<String> materiales = model.listaMateriales();
        for(int i = 0;i<materiales.size();i++)
            vista.getCbxMateriales().addItem(materiales.get(i));
    }
    
    private void llenarListaProductos(){
        for(int i = 0;i<pendientes.size();i++)
            vista.getCbxProducto().addItem(pendientes.get(i).getClaveProducto());  
        vista.getCbxProducto().addActionListener(listenerCbxProducto);        
    }

    
    /**
     * LLENAR LISTAS Y TABLAS
     */
    
    /**
     * EVENTOS
     */
    
    private final ActionListener listenerCbxProducto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendientes != null){
                pendiente = obtenerPendiente(pendientes,vista.getCbxProducto().getSelectedItem().toString());
                vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
                vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
                vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
            }
        }
    };
    
    private final ActionListener listenerGuardarCambiosOrden = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista!=null){
                      if(AgregarPendiente()){
                        limpiar();
                      }
                try {
                    //vista.dispose();
                } catch (Throwable ex) {
                    Logger.getLogger(AsignacionMaquinaAPedidoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    
    private final MouseListener listenerSeleccionarPendiente = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); 
                if(pendientes == null){
                    seleccionarOrdenTrabajo(e.getPoint());
                    llenarListaProductos();
                }else
                    JOptionPane.showMessageDialog(null, "debe completar la planeacion para la actual orden de trabajo");
            }
    };
    
    private final ActionListener listenerTerminarPendientes = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendientes != null)
                for(int i = 0;i<pendientes.size();i++)
                    if(model.agregarProductoPendiente(pendientes.get(i))==null)
                        break;
            else
                JOptionPane.showMessageDialog(null,"la tabla de productos pendientes esta vacia");                       
        }
    };
    
    /**EVENTOS*/
    
    /**
     * FUNCIONES QUE UTILIZAN LOS EVENTOS
     */
    
    private boolean AgregarPendiente(){
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        
            
            
            
            pendiente.setQty(Integer.parseInt(this.vista.getSprCantidadProducir().getValue().toString()));
            pendiente.setPiecesByShift(Integer.parseInt(this.vista.getSprPiecesByShift().getValue().toString()));
            pendiente.setMaquina(this.vista.getCbxMaquina().getSelectedItem().toString());
            pendiente.setMaterial(this.vista.getCbxMateriales().getSelectedItem().toString());
            pendiente.setWorker(Float.parseFloat(this.vista.getCbxWorker().getSelectedItem().toString()));
            pendiente.setFechaMontaje(sdf.format(this.vista.getJdcFechaMontajeMolde().getDate()));
            pendiente.setFechaInicio(sdf.format(this.vista.getDcrFechaInicioProduccion().getDate()));        

            agregados.add(pendiente);
            llenarTablaAgregados(agregados);
        return true;
    }
    
    private void llenarTablaAgregados(ArrayList<ProductosPendientes> pendientes){
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosAgregados().getModel());
        DefaultTableModel model;
        model = (DefaultTableModel) vista.getJtbProductosAgregados().getModel();
        
        for(int i = 0;i<pendientes.size();i++){
            ProductosPendientes pendiente = pendientes.get(i);
            model.addRow(new Object[]{pendiente.getClaveProducto(),pendiente.getFechaMontaje(),
            pendiente.getFechaInicio(),pendiente.getQty(),pendiente.getMaquina(),pendiente.getPiecesByShift(),
            pendiente.getMaterial(),pendiente.getWorker()});
        }
        
    }
    
    
    
    private void seleccionarOrdenTrabajo(Point pFila){
        int fila = vista.getJtOrdenesPendientes().rowAtPoint(pFila);
        ordenTrabajo = vista.getJtOrdenesPendientes().getValueAt(fila,1)+"";
        this.vista.getLbOrdenCompra().setText(vista.getJtOrdenesPendientes().getValueAt(fila,0).toString());
        pendientes = this.model.listaProductosPendientes(ordenTrabajo);
    }
    
    private ProductosPendientes obtenerPendiente(ArrayList<ProductosPendientes> lista,String claveProductos){
        for(int i = 0;i< lista.size();i++)
            if(claveProductos.equals( lista.get(i).getClaveProducto()))
                return lista.get(i);
        return null;
    }
    
    
    /**
     * FUNCIONES QUE UTILIZAN LOS EVENTOS
     */
    
    
    
}
