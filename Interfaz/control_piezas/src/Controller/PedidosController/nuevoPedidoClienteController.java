
package Controller.PedidosController;

import Model.Estructuras;
import Model.OrdenProductoActivo;
import Model.PedidosModel.nuevoPedidoClienteModel;
import Model.ordenProducto;
import Model.productoModel;
import View.Pedidos.NuevoPedidoCliente;
import ds.desktop.notify.DesktopNotify;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class nuevoPedidoClienteController implements ActionListener,MouseListener{

    private NuevoPedidoCliente vistaNuevoPedido;
    private nuevoPedidoClienteModel modelNuevoPedido;
    
    
    nuevoPedidoClienteController(NuevoPedidoCliente vistaNuevoPedido,
            nuevoPedidoClienteModel modelNuevoPedido) {
        this.vistaNuevoPedido = vistaNuevoPedido;
        this.modelNuevoPedido = modelNuevoPedido;
        llenarListaClientes();
        llenarListaProductos();
        this.vistaNuevoPedido.getCbxDescCliente().addActionListener(this);
        this.vistaNuevoPedido.getTbListaProductos().addMouseListener(this);
       this.vistaNuevoPedido.getBtnGuardar().addActionListener(this);
    }
    
    private void llenarListaClientes(){
        ArrayList listaClientes = modelNuevoPedido.listaClientes();
        
        if(this.vistaNuevoPedido.getCbxDescCliente().getItemCount() > 0)
        this.vistaNuevoPedido.getCbxDescCliente().removeAllItems();
        
        for(int i = 0;i<listaClientes.size();i++)
            this.vistaNuevoPedido.getCbxDescCliente().addItem(listaClientes.get(i).toString());
    }
    
    private void llenarListaContactos(String nombreCliente){
        ArrayList listaContactos = modelNuevoPedido.listaContacto(nombreCliente);
        
        if(this.vistaNuevoPedido.getCbxContactoCliente().getItemCount() > 0)
        this.vistaNuevoPedido.getCbxContactoCliente().removeAllItems();
        
        for(int i = 0;i<listaContactos.size();i++)
            this.vistaNuevoPedido.getCbxContactoCliente().addItem(listaContactos.get(i).toString());
        
    }
    
    private void llenarListaProductos(){
        DefaultTableModel  model = (DefaultTableModel) vistaNuevoPedido.getTbListaProductos().getModel();
        ArrayList<productoModel> productos = this.modelNuevoPedido.listaProductos();
        
        for(int i = 0;i<productos.size();i++)
            model.addRow(new Object[]{i+1,productos.get(i).getClaveProducto()});
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.vistaNuevoPedido.getCbxDescCliente())
            llenarListaContactos(this.vistaNuevoPedido.getCbxDescCliente().getSelectedItem().toString());
    
        
        else if(e.getSource() == this.vistaNuevoPedido.getBtnGuardar())
            guardarListaProductos();
    }
    
    
    private void guardarListaProductos(){
        ArrayList<ordenProducto> productos = obtenerListaProductos();
        
        int idPedidoAgregado = this.modelNuevoPedido.agregarPedido(
                this.vistaNuevoPedido.getTxtNoOrdenCompra().getText()
                ,this.vistaNuevoPedido.getCbxDescCliente().getSelectedItem().toString()
                ,this.vistaNuevoPedido.getCbxContactoCliente().getSelectedItem().toString()
                ,"");
        
        this.vistaNuevoPedido.dispose();
    }
    
    private ArrayList obtenerListaProductos(){
        ArrayList lista = new ArrayList();
        
        DefaultTableModel tableModel = (DefaultTableModel) this.vistaNuevoPedido.getTbListaPedido().getModel();
        
        for(int i = 0;i<tableModel.getRowCount();i++){
            ordenProducto producto = new ordenProducto(tableModel.getValueAt(i, 0).toString()
                    ,Integer.parseInt( tableModel.getValueAt(i, 1).toString()));
            lista.add(producto);
        }
        
        return lista;
    }
    
    
    
    
        
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int seleccionado = this.vistaNuevoPedido.getTbListaProductos().rowAtPoint(e.getPoint());
        
         
        String cantidad = JOptionPane.showInputDialog(new JFrame(), "INCRESA LA CANTIDAD NECESARIA");
        
        if(cantidad!=null && !cantidad.equals("")){
            try {
            agregarProductoPedido(this.vistaNuevoPedido.getTbListaProductos()
                    .getValueAt(seleccionado, 1).toString(),
                    Integer.parseInt(cantidad));    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "El valor no es correcto");
            }
            
        }
        
    }
    
    private void agregarProductoPedido(String claveProducto,int cantidad){
        DefaultTableModel model = (DefaultTableModel) this.vistaNuevoPedido.getTbListaPedido().getModel();
        
        model.addRow(new Object[]{claveProducto,cantidad});
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
