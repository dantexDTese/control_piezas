
package Controller.PedidosController;

import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.PedidosModel.ParcialidadesPedidosModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.nuevoPedidoClienteModel;
import View.Pedidos.BitacoraPedidosClienteView;
import View.Pedidos.NuevoPedidoCliente;
import View.Pedidos.ParcialidadesPedidos;
import ds.desktop.notify.DesktopNotify;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.CellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 * todo bien
 */

public class BitacoraPedidosClienteController implements ActionListener, MouseListener{

    private BitacoraPedidosClienteView vista;
    private BitacoraPedidosClienteModel model;
    
    public BitacoraPedidosClienteController(BitacoraPedidosClienteView vista, BitacoraPedidosClienteModel model) {
        this.vista = vista;
        this.model = model;       
        tamanoTabla();
        llenarListaPedidos();
        vista.getBtnNuevaOrden().addActionListener(this);
        vista.getTbPedidosClientes().addMouseListener(this);
        
    }
    
    private void tamanoTabla(){
        //pasar a estructuras
        vista.getTbPedidosClientes().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Integer[] listaTamanos = {80,100,130,100,130,100,100,120,120,160};
            
            for(int i = 0;i<listaTamanos.length;i++){
                TableColumn columna = vista.getTbPedidosClientes().getColumnModel().getColumn(i);
                columna.setPreferredWidth(listaTamanos[i]);
            }
        
    }
    
    public void llenarListaPedidos(){
        limpiarTaba();
        ArrayList<Pedido> pedidos = model.listaPedidos();
        if(pedidos.size()>0){
            DefaultTableModel modelTabla = (DefaultTableModel) vista.getTbPedidosClientes().getModel();        
            for(int i = 0;i<pedidos.size();i++){
                Pedido unPedido = pedidos.get(i);
                modelTabla.addRow(new Object[]{unPedido.getNoOrdenTrabajo(),
                unPedido.getNoOrdenCompra(),unPedido.getFechaRecepcion(),
                unPedido.getEstado(),unPedido.getFechaEntrega(),unPedido.getClaveProducto(),
                unPedido.getCantidadCliente(),unPedido.getNombreCliente(),
                unPedido.getDescContacto(),unPedido.getFechaConfirmacionEntrega()});     
            }
        }        
    }
    
    private void limpiarTaba(){
        //pasar a estructuras
        DefaultTableModel modelT = (DefaultTableModel) vista.getTbPedidosClientes().getModel();
        while(modelT.getRowCount()>0)
            modelT.removeRow(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnNuevaOrden())
            agregaNuevaOrden();
    }
    
    private void agregaNuevaOrden(){
        nuevoPedidoClienteModel modelNuevoPedido = new nuevoPedidoClienteModel();
        NuevoPedidoCliente vistaNuevoPedido = new NuevoPedidoCliente(vista.getPrincipal(), true);
        nuevoPedidoClienteController controllerNuevoPedido = new nuevoPedidoClienteController(vistaNuevoPedido,modelNuevoPedido,this);
        vistaNuevoPedido.setVisible(true);       
        vistaNuevoPedido.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); 
                llenarListaPedidos();
            }
            
        });
        
    }
    
        @Override
    public void mouseClicked(MouseEvent e) {
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

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
    
}
