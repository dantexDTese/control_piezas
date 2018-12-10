
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


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
        Estructuras.modificarAnchoTabla(vista.getTbPedidosClientes(), new Integer[]{80,100,130,100,130,100,100,120,120,160});
        llenarListaPedidos();
        this.vista.getBtnNuevaOrden().addActionListener((ActionEvent e) -> {
            if(e.getSource() == this.vista.getBtnNuevaOrden())
                agregaNuevaOrden();
        });
        
        this.vista.getTbPedidosClientes().addMouseListener(verParcialiadesEntregadas);
        
        
    }
    
    
    public void llenarListaPedidos(){
        Estructuras.limpiarTabla((DefaultTableModel) vista.getTbPedidosClientes().getModel());
        ArrayList<Pedido> pedidos = model.listaPedidos();
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
                llenarListaPedidos();
            }
        });
    }
    
    
        private final MouseAdapter verParcialiadesEntregadas = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            if(e.getClickCount() == 2){
                
                int filaSeleccionada = vista.getTbPedidosClientes().rowAtPoint(e.getPoint());
                ParcialidadesPedidos vistaParcialidades = new ParcialidadesPedidos(vista.getPrincipal()
                , true, vista.getTbPedidosClientes().getValueAt(filaSeleccionada,1).toString(),
                vista.getTbPedidosClientes().getValueAt(filaSeleccionada,5).toString(),
                Integer.parseInt(vista.getTbPedidosClientes().getValueAt(filaSeleccionada,6).toString()));

                ParcialidadesPedidosController controllerParcialiades =
                        new ParcialidadesPedidosController(vistaParcialidades,new ParcialidadesPedidosModel());

                vistaParcialidades.setVisible(true);
            }
        }
        
    };

}