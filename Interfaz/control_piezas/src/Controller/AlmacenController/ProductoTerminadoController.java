
package Controller.AlmacenController;

import Model.AlmacenModel.ProductoTerminadoModel;
import Model.AlmacenModel.RegistroInventarioModel;
import Model.AlmacenModel.RegistroSalidaModel;
import Model.AlmacenProductoTerminado;
import Model.Constructores;
import Model.Estructuras;
import Model.ProductoCliente;
import Model.RegistroEntradaSalida;
import View.almacenView.ProductoTerminadoView;
import View.almacenView.RegistroInventario;
import View.almacenView.RegistroSalida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        llenarTablaParteClientes(vista.getCbxClientes().getSelectedItem().toString(), vista.getCbxPartes().getSelectedItem().toString());
        vista.setCbxClientes(modelo.llenarCombo(vista.getCbxClientes(),vista.getCbxClientes().getSelectedItem().toString(),modelo.LISTA_CLIENTES));
        vista.setCbxPartes(modelo.llenarCombo(vista.getCbxPartes(), vista.getCbxClientes().getSelectedItem().toString(), modelo.LISTA_PRODUCTOS));
    }

    @Override
    public void asignarEventos() {
        vista.getBtnRegistrarEntrada().addActionListener(listenerRegistarEntrada);
        vista.getBtnRegistrarInventario().addActionListener(listenerRegistarInventario);
        vista.getBtnRegistrarSalida().addActionListener(listenerRegistarSalida);
        vista.getCbxClientes().addActionListener(listenerCombos);
        vista.getCbxPartes().addActionListener(listenerCombos);
    }
    
    private void llenarTablaParteClientes(String cliente, String noParte){
        listaMaterialesClientes = modelo.listaMaterialesClientes(cliente, noParte);
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosClientes().getModel());
        DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbProductosClientes().getModel();
        
        for(int i = 0;i<listaMaterialesClientes.size();i++){
            ProductoCliente materialCliente = listaMaterialesClientes.get(i);
            modelTabla.addRow(new Object[]{materialCliente.getDescCliente(),materialCliente.getClaveProducto()});
        }
            
    } 
    
    private final ActionListener listenerCombos = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista.getCbxClientes() == e.getSource())
                vista.setCbxPartes(modelo.llenarCombo(vista.getCbxPartes(), vista.getCbxClientes().getSelectedItem().toString(), modelo.LISTA_PRODUCTOS));
            
            else if(vista.getCbxPartes().getSelectedItem() != null){
                if(!"cualquiera".equals(vista.getCbxPartes().getSelectedItem().toString()) && !"cualquiera".equals(vista.getCbxClientes().getSelectedItem().toString()) ){
                    productoCliente = obtenerProductoCliente(vista.getCbxClientes().getSelectedItem().toString(),vista.getCbxPartes().getSelectedItem().toString());
                    llenarTablaEntradasSalidas(productoCliente);
                }
            }
            
        }
        
        
    };
    
    private void llenarTablaEntradasSalidas(AlmacenProductoTerminado productoCliente){
        vista.getLbNomCliente().setText(productoCliente.getDescCliente());
        vista.getLbNoParte().setText(productoCliente.getClaveProducto());
        ArrayList<RegistroEntradaSalida> listaEntradasSalidas = modelo.listaRegistrosEntradasSalidas(productoCliente.getNoAlmacenProductoTerminado());
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
    
    private AlmacenProductoTerminado obtenerProductoCliente(String nomCliente,String claveProducto){
            for(int i = 0;i<listaMaterialesClientes.size();i++){
                AlmacenProductoTerminado productoCliente = listaMaterialesClientes.get(i);
                if(productoCliente.getDescCliente().equals(nomCliente) && productoCliente.getClaveProducto().equals(claveProducto))
                        return productoCliente;
            }
            return null;
        }
    
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
        }
    }; 

    
    
    
}
