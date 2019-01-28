
package Controller.PedidosController;

import Model.Constructores;
import Model.Estructuras;
import Model.Pedido;
import Model.PedidosModel.AsignarDiasProduccionModel;
import Model.PedidosModel.AsignarDiasProduccionModel.OrdenProduccionNuevaPlaneacion;
import Model.ProductoMaquina;
import View.Pedidos.AsignarDiasProduccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class AsignarDiasProduccionController implements Constructores{

    private final AsignarDiasProduccion vista;
    private final AsignarDiasProduccionModel modelo;
    ArrayList<OrdenProduccionNuevaPlaneacion> listaOrdenesNuevaPlaneacion;
    OrdenProduccionNuevaPlaneacion nuevaPlaneacion;
    String pedidoSeleccionado;
       
    
    AsignarDiasProduccionController(AsignarDiasProduccion vista,AsignarDiasProduccionModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        pedidoSeleccionado = "";
        llenarComponentes();
        asignarEventos();
    }
    @Override
    public void llenarComponentes() {
        llenarTablaPedidosPendientes();
        vista.getCbxMaquina().addActionListener((ActionEvent e) -> {
            if(vista.getCbxMaquina().getSelectedItem() != null)
                Estructuras.obtenerCalendario(vista.getJpCalendario(),vista.getCbxMaquina().getSelectedItem().toString());
        });        
    }
    
    @Override
    public void asignarEventos() {
        vista.getJtOrdenesPendientes().addMouseListener(listenerTablaPedidos);
        vista.getCbxTipoOperacion().addActionListener(listenerCbxTipoOperacion);
        vista.getCbxProducto().addActionListener(listenerCbxProductos);
        vista.getCheckOrdenCompleta().addActionListener(listenerCheckOrdenCompleta);
        vista.getBtnGuardar().addActionListener(listenerGuardar);
    }
    
    private void limpiar(){
        vista.getLbOP().setText("");
        vista.getLbPiezasOperacion().setText("");
        vista.getLbProducto().setText("");
        vista.getLbQTY().setText("");
        vista.getSprDiasTrabajar().setValue(0);
        vista.getSprPiezasProcesar().setValue(0);
        vista.getSprPiezasTurno().setValue(0);
        
        if(vista.getCbxWorker().getItemCount()>0)
            vista.getCbxWorker().removeAllItems();
        if(vista.getCbxMaquina().getItemCount()>0)
            vista.getCbxMaquina().removeAllItems();
    }
    
    private void llenarTablaPedidosPendientes(){        
        ArrayList<Pedido> listaPendientes = modelo.listaPedidosPendientes();
        DefaultTableModel modelT = (DefaultTableModel) vista.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            Pedido ped = listaPendientes.get(i);
            modelT.addRow(new Object[]{ped.getNoPedido(),ped.getNoOrdenCompra()});
        }
    }   

    private void obtenerProductosPendientes(){

            if(!"".equals(pedidoSeleccionado)){
                if(vista.getCbxTipoOperacion().getSelectedIndex() == modelo.SELECCION_FALTAMTES_PROCESAR)
                        listaOrdenesNuevaPlaneacion = modelo.listaOrdenesNuevaPlaneacion(pedidoSeleccionado,modelo.SELECCION_FALTAMTES_PROCESAR);
                    else 
                        listaOrdenesNuevaPlaneacion = modelo.listaOrdenesNuevaPlaneacion(pedidoSeleccionado,modelo.SELECCION_SIGUIENTE_PROCESO);
                }
            
            
                if(vista.getCbxProducto().getItemCount() > 0)
                    vista.getCbxProducto().removeAllItems();
            
                if(listaOrdenesNuevaPlaneacion != null){
                    for(int i = 0;i<listaOrdenesNuevaPlaneacion.size();i++)
                        vista.getCbxProducto().addItem(listaOrdenesNuevaPlaneacion.get(i).getCodProducto());        
                }
    }
    
    private final MouseListener listenerTablaPedidos = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            limpiar();
            int fila = vista.getJtOrdenesPendientes().rowAtPoint(e.getPoint());
            pedidoSeleccionado = vista.getJtOrdenesPendientes().getValueAt(fila, 1).toString();
            obtenerProductosPendientes();
            
        }
        
    };
    
    private final ActionListener listenerCbxTipoOperacion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            limpiar();
            obtenerProductosPendientes();
            if(vista.getCbxTipoOperacion().getSelectedIndex() == modelo.SELECCION_FALTAMTES_PROCESAR){
                if(vista.getCbxProceso().getActionListeners().length>0){
                    vista.getCbxProceso().removeActionListener(listenerCbxProceso);
                    vista.getCbxMaquina().removeActionListener(listenerCbxMaquina);
                }
                vista.getLbMensaje().setText("PIEZAS FALTANTES");
            }
            else{                  
                if(vista.getCbxProceso().getActionListeners().length==0){
                    vista.getCbxProceso().addActionListener(listenerCbxProceso);                
                    vista.getCbxMaquina().addActionListener(listenerCbxMaquina);
                }
                    
                vista.getLbMensaje().setText("PIEZAS PARA PROCESAR");
            }
            
        }
    };
    
    private final ActionListener listenerCbxProductos = new ActionListener() {
     
        @Override
        public void actionPerformed(ActionEvent e) {
            limpiar();
            if(vista.getCbxProducto().getItemCount() >0 ){
                nuevaPlaneacion = obtenerProductoNuevaPlaneacion(vista.getCbxProducto().getSelectedItem().toString());

                if(nuevaPlaneacion != null){
                     vista.getLbProducto().setText(nuevaPlaneacion.getCodProducto());
                    vista.getLbQTY().setText(nuevaPlaneacion.getCantidadTotal()+"");
                    vista.getLbOP().setText(nuevaPlaneacion.getNoOrdenProduccion()+"");
                    
                    if(vista.getCbxTipoOperacion().getSelectedIndex() == modelo.SELECCION_FALTAMTES_PROCESAR){    
                        nuevaPlaneacion.setProductoMaquinaSeleccionado(modelo.obtenerMaquinaUtilizada(nuevaPlaneacion.getCodProducto()));                
                        llenarFaltantesProcesar();
                    }
                    else{ 
                        obtenerProcesoProducto();
                        
                        llenarWorker();
                    }
                }
            }
        }
        
        private void obtenerProcesoProducto(){
            if(vista.getCbxProceso().getItemCount() > 0)
                vista.getCbxProceso().removeAllItems();
            
            vista.setCbxProceso(modelo.llenarCombo(vista.getCbxProceso(),modelo.LISTA_PROCESOS,vista.getCbxProducto().getSelectedItem().toString()));
        }
        
        private void llenarFaltantesProcesar(){
            vista.getLbPiezasOperacion().setText(nuevaPlaneacion.getPiezasParaProcesar()+"");
                        
            if(vista.getCbxMaquina().getItemCount() > 0)
                vista.getCbxMaquina().removeAll();
            
            if(vista.getCbxProceso().getItemCount() > 0)
                vista.getCbxProceso().removeAllItems();
            
           
            vista.getCbxProceso().addItem(nuevaPlaneacion.getProductoMaquinaSeleccionado().getDescTipoProceso());
             vista.getCbxMaquina().addItem(nuevaPlaneacion.getProductoMaquinaSeleccionado().getDescMaquina());
             
            vista.getCbxWorker().removeAllItems();
            vista.getCbxWorker().addItem(nuevaPlaneacion.getWorker()+"");
           
            vista.getSprPiezasProcesar().setValue(nuevaPlaneacion.getPiezasParaProcesar());            
            vista.getSprPiezasTurno().setValue(nuevaPlaneacion.getProductoMaquinaSeleccionado().getPiezasPorTurno());
        }
        
        private void llenarWorker(){
        if(vista.getCbxWorker().getItemCount()>0)
            vista.getCbxWorker().removeAllItems();
        vista.getCbxWorker().addItem("0.5");
        vista.getCbxWorker().addItem("1.0");
        vista.getCbxWorker().addItem("1.5");
        vista.getCbxWorker().addItem("2.0");
    }
    
     
        private OrdenProduccionNuevaPlaneacion obtenerProductoNuevaPlaneacion(String codProducto){
            for(int i = 0;i<listaOrdenesNuevaPlaneacion.size();i++)
                if(listaOrdenesNuevaPlaneacion.get(i).getCodProducto().equals(codProducto))
                    return listaOrdenesNuevaPlaneacion.get(i);

            return null;
        }
        
    };
    
    private final ActionListener listenerCbxProceso = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista.getCbxProducto().getItemCount() > 0 && vista.getCbxProceso().getItemCount() > 0){
                nuevaPlaneacion.setProductosMaquina(modelo.obtenerMaquinasProducto(nuevaPlaneacion.getCodProducto(),vista.getCbxProceso().getSelectedItem().toString()));
                nuevaPlaneacion.setPiezasListas(modelo.obtenerPiezasListas(nuevaPlaneacion.getNoOrdenProduccion(),vista.getCbxProceso().getSelectedItem().toString()));
                vista.getLbPiezasOperacion().setText(nuevaPlaneacion.getPiezasListas()+"");
                vista.getSprPiezasProcesar().setValue(nuevaPlaneacion.getPiezasListas());
                if(vista.getCbxMaquina().getItemCount() > 0)
                    vista.getCbxMaquina().removeAllItems();

                for(int i = 0;i<nuevaPlaneacion.getProductosMaquina().size();i++)
                    vista.getCbxMaquina().addItem(nuevaPlaneacion.getProductosMaquina().get(i).getDescMaquina());
            }
        }
    };
    
    private final ActionListener listenerCbxMaquina = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(vista.getCbxMaquina().getItemCount() > 0){
                String maquinaSeleccionada = vista.getCbxMaquina().getSelectedItem().toString();

                for(int i = 0;i<nuevaPlaneacion.getProductosMaquina().size();i++)
                    if(nuevaPlaneacion.getProductosMaquina().get(i).getDescMaquina().equals(maquinaSeleccionada))
                        nuevaPlaneacion.setProductoMaquinaSeleccionado(nuevaPlaneacion.getProductosMaquina().get(i));

                if(nuevaPlaneacion.getProductoMaquinaSeleccionado() != null){
                    ProductoMaquina productoMaquina = nuevaPlaneacion.getProductoMaquinaSeleccionado();
                    vista.getSprPiezasTurno().setValue(productoMaquina.getPiezasPorTurno());
                }
            }
        }
    };
    
    private final ActionListener listenerCheckOrdenCompleta = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista.getCheckOrdenCompleta().isSelected()){        
                obtenerNumDiasTrabajo();
                vista.getSprDiasTrabajar().setEnabled(false);
                
            }else{
                vista.getSprDiasTrabajar().setEnabled(true);
            }
        }
        
        private void obtenerNumDiasTrabajo(){
        if(vista.getCheckOrdenCompleta().isSelected()){        
            int dias =  (int)Math.ceil(Float.parseFloat(vista.getSprPiezasProcesar().getValue().toString())/
                                    Float.parseFloat(vista.getSprPiezasTurno().getValue().toString()));            
            vista.getSprDiasTrabajar().setValue(dias);
        }    
    }
    
        
    };

    private final ActionListener listenerGuardar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            
            if (Integer.parseInt(vista.getSprPiezasProcesar().getValue().toString()) > 0 && 
            Integer.parseInt(vista.getSprPiezasTurno().getValue().toString()) > 0 &&
            Integer.parseInt(vista.getSprDiasTrabajar().getValue().toString()) > 0 && 
            vista.getJdcFecha().getDate() != null){
                nuevaPlaneacion.setCantidadPlaneada(Integer.parseInt(vista.getSprPiezasProcesar().getValue().toString()));
                nuevaPlaneacion.setPiezasPorTurno(Integer.parseInt(vista.getSprPiezasTurno().getValue().toString()));
                nuevaPlaneacion.setDiasTrabajo(Integer.parseInt(vista.getSprDiasTrabajar().getValue().toString()));
                nuevaPlaneacion.setFechaPlaneada(Estructuras.convertirFechaGuardar(vista.getJdcFecha().getDate()));
                nuevaPlaneacion.setWorker(Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()));
                modelo.registrarNuevaPlaneacion(nuevaPlaneacion);
                vista.dispose();
            }else 
                JOptionPane.showMessageDialog(null, "LOS CAMPOS NO SON VALIDOS, POR FAVOR INTENTE DE NUEVO");
        }
    };
}

