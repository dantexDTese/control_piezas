package Controller.PedidosController;


import Model.Constructores;
import Model.Estructuras;
import Model.PedidosModel.nuevoPedidoClienteModel;
import Model.ordenProduccion;
import View.Pedidos.NuevoPedidoCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public final class nuevoPedidoClienteController implements ActionListener,Constructores{

    private final NuevoPedidoCliente vistaNuevoPedido;
    private final nuevoPedidoClienteModel modelNuevoPedido;
    
    
    nuevoPedidoClienteController(NuevoPedidoCliente vistaNuevoPedido,nuevoPedidoClienteModel modelNuevoPedido) {
        //INICIALIZAR
        this.vistaNuevoPedido = vistaNuevoPedido;
        this.modelNuevoPedido = modelNuevoPedido;
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        llenarListaClientes();
        llenarListaProductos();
        Estructuras.modificarAnchoTabla(vistaNuevoPedido.getTbListaProductos(),new Integer[]{90,155});
    }

    @Override
    public void asignarEventos() {
       //ASIGNAR EVENTOS
        this.vistaNuevoPedido.getBtnGuardar().addActionListener(this);
        this.vistaNuevoPedido.getTbListaProductos().addMouseListener(listenerAgregarProducto);
        this.vistaNuevoPedido.getDcFechaEntrega().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if(!Estructuras.validarFecha(vistaNuevoPedido.getDcFechaEntrega().getDate()))
                vistaNuevoPedido.getDcFechaEntrega().setCalendar(null);
        });
        this.vistaNuevoPedido.getTbListaPedido().addMouseListener(listenerQuitar);  
        this.vistaNuevoPedido.getCbxDescCliente().addActionListener(listenerSeleccionarCliente);
        
    }
    
    private void llenarListaClientes(){
        ArrayList listaClientes = modelNuevoPedido.listaClientes();
        
        if(this.vistaNuevoPedido.getCbxDescCliente().getItemCount() > 0)
        this.vistaNuevoPedido.getCbxDescCliente().removeAllItems();
        
        for(int i = 0;i<listaClientes.size();i++)
            this.vistaNuevoPedido.getCbxDescCliente().addItem(listaClientes.get(i).toString());
    }
    
    private void llenarListaProductos(){
        DefaultTableModel  model = (DefaultTableModel) vistaNuevoPedido.getTbListaProductos().getModel();
        ArrayList<String> productos = this.modelNuevoPedido.listaProductos();
        
        for(int i = 0;i<productos.size();i++)
            model.addRow(new Object[]{i+1,productos.get(i)});
        
        
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == this.vistaNuevoPedido.getBtnGuardar()){
            int res = JOptionPane.showConfirmDialog(null, "¿Esta seguro de guardar esta orden?",
                    "Guardar orden",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(res == JOptionPane.YES_OPTION)
                guardarListaProductos();
        }
            
    }
    
    private void guardarListaProductos(){
        
        
        ArrayList<ordenProduccion> productos = obtenerListaProductos();
        
        if(productos.size()>0){           
            int idPedidoAgregado = this.modelNuevoPedido.agregarPedido(
                    this.vistaNuevoPedido.getTxtNoOrdenCompra().getText()
                    ,this.vistaNuevoPedido.getCbxDescCliente().getSelectedItem().toString()
                    ,this.vistaNuevoPedido.getCbxContacto().getSelectedItem().toString()
                    ,Estructuras.convertirFechaGuardar(vistaNuevoPedido.getDcFechaEntrega().getDate()));

            if(idPedidoAgregado > 0){
                for(int i = 0;i<productos.size();i++){
                    ordenProduccion producto = productos.get(i);
                modelNuevoPedido.agregarOrdenProduccion(idPedidoAgregado,
                        producto.getCodProducto(), producto.getCantidadCliente());
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
            ordenProduccion producto = new ordenProduccion(tableModel.getValueAt(i, 0).toString()
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
                    && !"".equals(vistaNuevoPedido.getCbxContacto().getSelectedItem().toString())){
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
    
    private final ActionListener listenerSeleccionarCliente = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(vistaNuevoPedido.getCbxDescCliente().getItemCount() > 0){
                
                ArrayList<String> listaContactos = modelNuevoPedido.listaContactosCliente(
                        vistaNuevoPedido.getCbxDescCliente().getSelectedItem().toString());  
                
                if(vistaNuevoPedido.getCbxContacto().getItemCount() > 0)
                    vistaNuevoPedido.getCbxContacto().removeAllItems();
                
                for(int i = 0;i<listaContactos.size();i++)
                        vistaNuevoPedido.getCbxContacto().addItem(listaContactos.get(i));
                        
            }
        }
        
    };
    
}
