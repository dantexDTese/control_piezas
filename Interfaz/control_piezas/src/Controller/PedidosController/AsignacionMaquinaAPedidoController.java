package Controller.PedidosController;
import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import View.Pedidos.AsignarMaquinaAPedido;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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

    private void limpiar(){
    
        vista.getLbNoOrdenProduccion().setText("");
        vista.getLbCantidadCliente().setText("");
        vista.getSprCantidadProducir().setValue(0);
        vista.getSprPiecesByShift().setValue(0);
        vista.getJdcFechaMontajeMolde().setDate(null);
        vista.getDcrFechaInicioProduccion().setDate(null);
    
    }
    /**
     * FIN
     */
    
    /**
     * EVENTOS
     */
    private final ActionListener listenerCbxProducto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {            
            if(pendientes != null){
                
                limpiar();
                
                if((pendiente = obtenerAgregado(vista.getCbxProducto().getSelectedItem().toString())) == null)
                    pendiente = obtenerPendiente(pendientes,vista.getCbxProducto().getSelectedItem().toString());

                 else{
                    JOptionPane.showConfirmDialog(null,"Modificar producto");
                    vista.getSprCantidadProducir().setValue(pendiente.getQty());
                    if(pendiente.getWorker()==1)
                        vista.getCbxWorker().setSelectedIndex(1);
                    vista.getSprPiecesByShift().setValue(pendiente.getPiecesByShift());
                    vista.getCbxMateriales().setSelectedIndex(obtenerIndex(vista.getCbxMateriales(),pendiente.getMaterial()));
                    vista.getJdcFechaMontajeMolde().setDate(new Date(pendiente.getFechaMontaje()));
                    vista.getDcrFechaInicioProduccion().setDate(new Date(pendiente.getFechaInicio()));
                    vista.getCbxMaquina().setSelectedIndex(obtenerIndex(vista.getCbxMaquina(),pendiente.getMaquina()));
                }
                
                vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
                vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
                vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
                    
            }
        }
        
        private ProductosPendientes obtenerPendiente(ArrayList<ProductosPendientes> lista,String claveProductos){
        for(int i = 0;i< lista.size();i++)
            if(claveProductos.equals( lista.get(i).getClaveProducto()))
                return lista.get(i);
        return null;
    }
    
    private ProductosPendientes obtenerAgregado(String claveProducto) {
        ProductosPendientes pendiente=null;        
        for(int i = 0;i<agregados.size();i++)
            if(agregados.get(i).getClaveProducto().equals(claveProducto))
                pendiente = agregados.get(i);
        
        return pendiente;
    }

        private int obtenerIndex(JComboBox lista, String material){
            for(int i=0;i<lista.getItemCount();i++)
                if(lista.getItemAt(i).equals(material))
                    return i;
            
            return 0;
        }
    };
    
    private final ActionListener listenerGuardarCambiosOrden = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AgregarPendiente();
            limpiar();            
        }
        
        private void AgregarPendiente(){        
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");            
            Integer index;
            
            pendiente.setQty(Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()));
            pendiente.setPiecesByShift(Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()));
            pendiente.setMaquina(vista.getCbxMaquina().getSelectedItem().toString());
            pendiente.setMaterial(vista.getCbxMateriales().getSelectedItem().toString());
            pendiente.setWorker(Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()));
            pendiente.setFechaMontaje(sdf.format(vista.getJdcFechaMontajeMolde().getDate()));
            pendiente.setFechaInicio(sdf.format(vista.getDcrFechaInicioProduccion().getDate()));        
                                    
            if((index = obtenerIndexAgregado(pendiente.getClaveProducto())) == null)
                agregados.add(pendiente);
            else 
                agregados.set(index, pendiente);
            
            llenarTablaAgregados(agregados);            
        }
        
        
        private Integer obtenerIndexAgregado(String claveProducto){
            Integer index = null;

            for(int i = 0 ;i<agregados.size();i++)
                if(agregados.get(i).getClaveProducto().equals(claveProducto))
                    index = i;

            return index;
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
            
            private void seleccionarOrdenTrabajo(Point pFila){
                int fila = vista.getJtOrdenesPendientes().rowAtPoint(pFila);
                ordenTrabajo = vista.getJtOrdenesPendientes().getValueAt(fila,1)+"";
                vista.getLbOrdenCompra().setText(vista.getJtOrdenesPendientes().getValueAt(fila,0).toString());
                pendientes = model.listaProductosPendientes(ordenTrabajo);
            }
    };
    
    private final ActionListener listenerTerminarPendientes = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendientes != null){
                for(int i = 0;i<pendientes.size();i++)
                    if(model.agregarProductoPendiente(pendientes.get(i))==null)
                        break;
                
              llenarTablaPedidosPendientes();  
              Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosAgregados().getModel());
              
                
                DesktopNotify.showDesktopMessage("---PLANEACION COMPLETA----",
                        "La orden se ha agregado correctamente a la bitacora de planeacion\t\t\n",
                        DesktopNotify.SUCCESS,null,null, 9000);
            }
                    
            else
                JOptionPane.showMessageDialog(null,"la tabla de productos pendientes esta vacia");                       
        }
    };
    
    /**
     * FIN
     */
    
    
}
