
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.PedidosModel.ParcialidadesPedidosModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.nuevoPedidoClienteModel;
import View.Pedidos.BitacoraPedidosClienteView;
import View.Pedidos.NuevoPedidoCliente;
import View.Pedidos.ParcialidadesPedidos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public final class BitacoraPedidosClienteController{

    
    /**
     * Atributos
     */
    private final BitacoraPedidosClienteView vista;
    private final BitacoraPedidosClienteModel model;
    
    /**
     * Constructor
     * @param model
     * @param vista
     */   
    public BitacoraPedidosClienteController(BitacoraPedidosClienteView vista, BitacoraPedidosClienteModel model) {
        this.vista = vista;
        this.model = model;       
        Estructuras.modificarAnchoTabla(vista.getTbPedidosClientes(), new Integer[]{80,90,120,100,95,135,160,120,120,140});
        llenarListaPedidos(this.vista.getTxtOrdenCompra().getText(),this.vista.getJycAnio().getValue(),this.vista.getJmcMes().getMonth()+1);
        this.vista.getLbAnio().setText(this.vista.getJycAnio().getValue()+"");
        
        
        this.vista.getBtnNuevaOrden().addActionListener((ActionEvent e) -> {
            if(e.getSource() == this.vista.getBtnNuevaOrden())
                agregaNuevaOrden();
        });
        
        
        this.vista.getTbPedidosClientes().addMouseListener(verParcialiadesEntregadas); 
        this.vista.getJycAnio().addPropertyChangeListener(listenerAnioMes);
        this.vista.getJmcMes().addPropertyChangeListener(listenerAnioMes);
        this.vista.getBtnBuscarOrden().addActionListener(listenerBotones);
        this.vista.getBtnVertodo().addActionListener(listenerBotones);
        
    }
      
    public void llenarListaPedidos(String noOrdenCompra,Integer anio,Integer mes){
        Estructuras.limpiarTabla((DefaultTableModel) vista.getTbPedidosClientes().getModel());
        ArrayList<Pedido> pedidos = model.listaPedidos(noOrdenCompra,anio,mes);
        if(pedidos.size()>0){
            DefaultTableModel modelTabla = (DefaultTableModel) vista.getTbPedidosClientes().getModel();        
            for(int i = 0;i<pedidos.size();i++){
                Pedido unPedido = pedidos.get(i);
                modelTabla.addRow(new Object[]{
                    unPedido.getEstado(),
                    unPedido.getNoOrdenTrabajo(),
                    unPedido.getNoOrdenCompra(),
                    unPedido.getClaveProducto(),
                    unPedido.getCantidadCliente(),
                    unPedido.getFechaEntrega(),
                    unPedido.getFechaConfirmacionEntrega(),
                    unPedido.getFechaRecepcion(),
                    unPedido.getDescContacto(),
                    unPedido.getNombreCliente()
                });     
            }
        }        
    }
    
    
    private void agregaNuevaOrden(){
        nuevoPedidoClienteModel modelNuevoPedido = new nuevoPedidoClienteModel();
        NuevoPedidoCliente vistaNuevoPedido = new NuevoPedidoCliente(vista.getPrincipal(), true);
        nuevoPedidoClienteController controllerNuevoPedido = new nuevoPedidoClienteController(vistaNuevoPedido,modelNuevoPedido);
        vistaNuevoPedido.setVisible(true);       
        vistaNuevoPedido.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); 
                llenarListaPedidos("",vista.getJycAnio().getValue(),vista.getJmcMes().getMonth()+1);
                vista.getLbAnio().setText(vista.getJycAnio().getValue()+"");
            }
        });
    }
    
    private final MouseAdapter verParcialiadesEntregadas = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            if(e.getClickCount() == 2){
                int filaSeleccionada = vista.getTbPedidosClientes().rowAtPoint(e.getPoint());
                
                ParcialidadesPedidos vistaParcialidades = new ParcialidadesPedidos(
                vista.getPrincipal(),true,vista.getTbPedidosClientes().getValueAt(filaSeleccionada,2).toString(),
                vista.getTbPedidosClientes().getValueAt(filaSeleccionada,3).toString(),
                Integer.parseInt(vista.getTbPedidosClientes().getValueAt(filaSeleccionada,4).toString()));

                ParcialidadesPedidosController controllerParcialiades = 
                        new ParcialidadesPedidosController(vistaParcialidades,new ParcialidadesPedidosModel());

                vistaParcialidades.setVisible(true);
            }
        }
        
    };
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnBuscarOrden()){
                if(!"".equals(vista.getTxtOrdenCompra().getText()))
                    llenarListaPedidos(vista.getTxtOrdenCompra().getText(),vista.getJycAnio().getValue(),vista.getJmcMes().getMonth()+1);
            }else if(e.getSource() == vista.getBtnVertodo()){
                    vista.getTxtOrdenCompra().setText("");
                    llenarListaPedidos(vista.getTxtOrdenCompra().getText(),vista.getJycAnio().getValue(),vista.getJmcMes().getMonth()+1);
            }
            vista.getLbAnio().setText(vista.getJycAnio().getValue()+"");            
        }
    };
    
    private final PropertyChangeListener listenerAnioMes = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            llenarListaPedidos(vista.getTxtOrdenCompra().getText(),vista.getJycAnio().getValue(),vista.getJmcMes().getMonth()+1);
            vista.getLbAnio().setText(vista.getJycAnio().getValue()+"");            
        }
    };
}