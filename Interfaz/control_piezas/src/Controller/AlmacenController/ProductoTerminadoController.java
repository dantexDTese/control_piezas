
package Controller.AlmacenController;

import Model.AlmacenModel.ProductoInventario;
import Model.AlmacenModel.ProductoTerminadoModel;
import Model.AlmacenModel.RegistroInventarioModel;
import Model.AlmacenModel.RegistroSalidaModel;
import Model.AlmacenProductoTerminado;
import Model.Constructores;
import Model.Estructuras;
import Model.RegistroEntradaSalida;
import View.almacenView.ProductoTerminadoView;
import View.almacenView.RegistroInventario;
import View.almacenView.RegistroSalida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class ProductoTerminadoController implements Constructores{

    
    ProductoTerminadoView vista;
    ProductoTerminadoModel modelo;
    ArrayList<AlmacenProductoTerminado> listaMaterialesClientes;
    AlmacenProductoTerminado productoCliente;
    public ProductoTerminadoController(ProductoTerminadoView vista, ProductoTerminadoModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
    
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaParteClientes();
        llenarTablaInventarios();
        
    }

    @Override
    public void asignarEventos() {
        vista.getBtnRegistrarEntrada().addActionListener(listenerRegistarEntrada);
        vista.getBtnRegistrarInventario().addActionListener(listenerRegistarInventario);
        vista.getBtnRegistrarSalida().addActionListener(listenerRegistarSalida);
        vista.getJtbProductosClientes().addMouseListener(lisntenerTablaProductos);
        vista.getJtbRegistroInventario().addMouseListener(listenerMostrarProductosInventario);
        PropertyChangeListener listenerFecha = (PropertyChangeEvent evt) -> {llenarTablaEntradasSalidas(productoCliente);};
        vista.getJdcAnio().addPropertyChangeListener(listenerFecha);
        vista.getJdcMes().addPropertyChangeListener(listenerFecha);
        PropertyChangeListener listenerFechasInventario = (PropertyChangeEvent evt) -> {llenarTablaInventarios();};
        vista.getJdcMesInventario().addPropertyChangeListener(listenerFechasInventario);
        vista.getJdcAnioInventario().addPropertyChangeListener(listenerFechasInventario);
    }
    
    private void llenarTablaParteClientes(){
        listaMaterialesClientes = modelo.listaMaterialesClientes();
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosClientes().getModel());
        DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbProductosClientes().getModel();
        
        for(int i = 0;i<listaMaterialesClientes.size();i++){
            AlmacenProductoTerminado materialCliente = listaMaterialesClientes.get(i);
            modelTabla.addRow(new Object[]{i+1,materialCliente.getCodProducto()});
        }
            
    } 
    
    private void llenarTablaEntradasSalidas(AlmacenProductoTerminado productoCliente){
        if(productoCliente != null){
            vista.getLbNoParte().setText(productoCliente.getCodProducto());
            ArrayList<RegistroEntradaSalida> listaEntradasSalidas = modelo.listaRegistrosEntradasSalidas(productoCliente.getNoAlmacenProductoTerminado(),
                    vista.getJdcAnio().getValue(),vista.getJdcMes().getMonth()+1);
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbRegistroEntradaSalida().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            for(int i = 0;i<listaEntradasSalidas.size();i++){
                RegistroEntradaSalida registro = listaEntradasSalidas.get(i);
                modeloTabla.addRow(new Object[]{
                    registro.getFechaRegistro(),
                    ("ENTRADA".equals(registro.getDescTipoOperacionAlmacen()))?  registro.getCantidad():null,
                    ("SALIDA".equals(registro.getDescTipoOperacionAlmacen()))?  registro.getCantidad():null,
                    registro.getTotalRegistrado()
                });
            }
        }
    }
    
    private void llenarTablaInventarios(){
       ArrayList<String> listaInventarios = modelo.listaInventarios(vista.getJdcAnioInventario().getValue(), vista.getJdcMesInventario().getMonth()+1);
       DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbRegistroInventario().getModel();
       Estructuras.limpiarTabla(modeloTabla);
       for(int i = 0;i<listaInventarios.size();i++)
           modeloTabla.addRow(new Object[]{i+1,listaInventarios.get(i)});
    }
    
    private final MouseListener lisntenerTablaProductos = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila = vista.getJtbProductosClientes().rowAtPoint(e.getPoint());
            String codProducto = vista.getJtbProductosClientes().getValueAt(fila, 1).toString();
            for(int i = 0;i<listaMaterialesClientes.size();i++)
                if(codProducto.equals(listaMaterialesClientes.get(i).getCodProducto()))
                    productoCliente = listaMaterialesClientes.get(i);
            
            llenarTablaEntradasSalidas(productoCliente);
        }
        
        
        
    };
    
    private final ActionListener listenerRegistarInventario = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegistroInventario viewInventario = new RegistroInventario(vista.getPrincipal(), true);
            RegistroInventarioController controllerInventario = new RegistroInventarioController(viewInventario,new RegistroInventarioModel());
            viewInventario.setVisible(true);
        }
    }; 
    
    private final ActionListener listenerRegistarEntrada = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(productoCliente != null){
                String respuesta = JOptionPane.showInputDialog("INGRESA LA CANTIDAD");
                if(respuesta != null)
                    try {
                    
                        int cantidadRegistrar = Integer.parseInt(respuesta);   
                        modelo.registrarEntrada(cantidadRegistrar,productoCliente.getNoAlmacenProductoTerminado());
                        llenarTablaEntradasSalidas(productoCliente);
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,"VALOR INCORRECTO, INTENTE DE NUEVO","VALIDACION",JOptionPane.ERROR_MESSAGE);
                    }
            }else
                JOptionPane.showMessageDialog(null, "PRIMERO SELECCIONE UN PRODUCTO","VALIDACION",JOptionPane.INFORMATION_MESSAGE);
                
        }
    }; 
    
    private final ActionListener listenerRegistarSalida = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegistroSalida vistaSalida = new RegistroSalida(vista.getPrincipal(), true);
            RegistroSalidaController controllerSalida = new RegistroSalidaController(vistaSalida,new RegistroSalidaModel());
            vistaSalida.setVisible(true);
            
            vistaSalida.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    llenarTablaEntradasSalidas(productoCliente);
                }
                
            });
        }
    }; 

    private final MouseListener listenerMostrarProductosInventario = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = vista.getJtbRegistroInventario().rowAtPoint(e.getPoint());
            
            ArrayList<ProductoInventario> listaInventario = modelo.obtenerProductosInventario(
                Integer.parseInt(vista.getJtbRegistroInventario().getValueAt(fila,0).toString()),
                vista.getJtbRegistroInventario().getValueAt(fila,1).toString());
            
            
            
            RegistroInventario viewInventario = new RegistroInventario(vista.getPrincipal(), true);
            RegistroInventarioController controllerInventario = 
                    new RegistroInventarioController(viewInventario,new RegistroInventarioModel(),
                    listaInventario,vista.getJtbRegistroInventario().getValueAt(fila,1).toString());
            viewInventario.setVisible(true);
            
        }
        
        
        
    
    };
    
    
}
