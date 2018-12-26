package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.nuevoPedidoClienteModel;
import Model.ordenProducto;
import Model.productoModel;
import View.Pedidos.NuevoPedidoCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class nuevoPedidoClienteController implements ActionListener{

    private final NuevoPedidoCliente vistaNuevoPedido;
    private final nuevoPedidoClienteModel modelNuevoPedido;
    
    
    nuevoPedidoClienteController(NuevoPedidoCliente vistaNuevoPedido,nuevoPedidoClienteModel modelNuevoPedido) {
        //INICIALIZAR
        this.vistaNuevoPedido = vistaNuevoPedido;
        this.modelNuevoPedido = modelNuevoPedido;
        llenarListaClientes();
        llenarListaProductos();
        
        //ASIGNAR EVENTOS
        this.vistaNuevoPedido.getCbxDescCliente().addActionListener(this);
        this.vistaNuevoPedido.getTbListaProductos().addMouseListener(listenerAgregarProducto);
        this.vistaNuevoPedido.getBtnGuardar().addActionListener(this);
        this.vistaNuevoPedido.getDcFechaEntrega().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if(!Estructuras.validarFecha(vistaNuevoPedido.getDcFechaEntrega().getDate()))
                vistaNuevoPedido.getDcFechaEntrega().setCalendar(null);
        });
        
        this.vistaNuevoPedido.getTbListaPedido().addMouseListener(listenerQuitar);
        
        
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
    
        
        else if(e.getSource() == this.vistaNuevoPedido.getBtnGuardar()){
            int res = JOptionPane.showConfirmDialog(null, "¿Esta seguro de guardar esta orden?",
                    "Guardar orden",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(res == JOptionPane.YES_OPTION)
                guardarListaProductos();
        }
            
    }
    
    
    private void guardarListaProductos(){
        
        
        ArrayList<ordenProducto> productos = obtenerListaProductos();
        
        if(productos.size()>0){           
            int idPedidoAgregado = this.modelNuevoPedido.agregarPedido(
                    this.vistaNuevoPedido.getTxtNoOrdenCompra().getText()
                    ,this.vistaNuevoPedido.getCbxDescCliente().getSelectedItem().toString()
                    ,this.vistaNuevoPedido.getCbxContactoCliente().getSelectedItem().toString()
                    ,Estructuras.convertirFecha(vistaNuevoPedido.getDcFechaEntrega().getDate()));

            if(idPedidoAgregado > 0){
            
                for(int i = 0;i<productos.size();i++){
                    ordenProducto producto = productos.get(i);
                modelNuevoPedido.agregarOrdenProduccion(idPedidoAgregado,
                        producto.getCodProducto(), producto.getCantidadSolicitada());

                }
               
                this.vistaNuevoPedido.dispose();
            }
        }else 
            JOptionPane.showMessageDialog(null,"Por favor antes de guardar agregue productos a la lista","competar lista",JOptionPane.WARNING_MESSAGE);
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
    
    private final MouseListener listenerQuitar = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
              if(JOptionPane.showConfirmDialog(null,"¿Desea quitar este producto de la lista?","Quitar productos",
                      JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                int fila = vistaNuevoPedido.getTbListaPedido().rowAtPoint(e.getPoint());
                 eliminarFila(fila, vistaNuevoPedido.getTbListaPedido());
              }
            }
        }

        private void eliminarFila(int fila,JTable tabla){
            DefaultTableModel modelTabla = (DefaultTableModel) tabla.getModel();
            modelTabla.removeRow(fila);
        }
    };
    
    private final MouseListener listenerAgregarProducto = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            if(!"".equals(vistaNuevoPedido.getTxtNoOrdenCompra().getText()) && vistaNuevoPedido.getDcFechaEntrega().getDate() != null
                    && vistaNuevoPedido.getCbxContactoCliente().getSelectedItem() != null){
            int seleccionado = vistaNuevoPedido.getTbListaProductos().rowAtPoint(e.getPoint());
                 
            String cantidad = JOptionPane.showInputDialog(new JFrame(), "INCRESA LA CANTIDAD NECESARIA");

            if(cantidad!=null && !cantidad.equals("")){
                try {
                agregarProductoPedido(vistaNuevoPedido.getTbListaProductos()
                        .getValueAt(seleccionado, 1).toString(),
                        Integer.parseInt(cantidad));    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El valor no es correcto");
                }
            }
        }else
            JOptionPane.showMessageDialog(null, "por favor complete los campos que se encuentran en la seccion de arriba","completar campos",JOptionPane.WARNING_MESSAGE);
        }
        
        private void agregarProductoPedido(String claveProducto,int cantidad){
            DefaultTableModel model = (DefaultTableModel) vistaNuevoPedido.getTbListaPedido().getModel();        
            model.addRow(new Object[]{claveProducto,cantidad});
        }
    };
    
    
    
}
