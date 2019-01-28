
package Controller.AlmacenController;

import Model.AlmacenModel.RegistroSalidaModel;
import Model.Constructores;
import Model.Estructuras;
import Model.OrdenTrabajo;
import Model.LotePlaneado;
import View.almacenView.CantidadParcialidad;
import View.almacenView.RegistroSalida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class RegistroSalidaController implements Constructores{

    RegistroSalida vista;
    RegistroSalidaModel modelo;
    OrdenTrabajo ordenTrabajo;
    
    RegistroSalidaController(RegistroSalida vista, RegistroSalidaModel model) {
        
        this.vista = vista;
        this.modelo = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarListaPedidosPendientes();
    }

    @Override
    public void asignarEventos() {
        vista.getJtbPedidosPendientes().addMouseListener(listenerTablaPedidos);
        vista.getJtbProductosPedido().addMouseListener(listenerTablaProductos);
        vista.getBtnRegistrarSalida().addActionListener(listenerRegistrar);
        
    }
    
    private void llenarListaPedidosPendientes(){
        ArrayList<OrdenTrabajo> listaPedidos = modelo.listaPedidosPendientes();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbPedidosPendientes().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaPedidos.size();i++){
            OrdenTrabajo ordenTrabajo = listaPedidos.get(i);
            modeloTabla.addRow(new Object[]{
                ordenTrabajo.getOrdenTrabajo(),
                ordenTrabajo.getNoOrdenCompra(),
                ordenTrabajo.getDescCliente()
            });
        }
            
        
    }
    
    private final MouseListener listenerTablaPedidos = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            ordenTrabajo = new LotePlaneado();    
            int filaSeleccionada = vista.getJtbPedidosPendientes().rowAtPoint(e.getPoint());
            ordenTrabajo.setOrdenTrabajo(Integer.parseInt(vista.getJtbPedidosPendientes().getValueAt(filaSeleccionada, 0).toString()));
            ordenTrabajo.setDescCliente(vista.getJtbPedidosPendientes().getValueAt(filaSeleccionada, 2).toString());
            
            llenarTablaProductos(ordenTrabajo.getOrdenTrabajo());
        }
        
        private void llenarTablaProductos(int noOrdenTrabajo){
            ArrayList<RegistroSalidaModel.OrdeneProduccionParcialidad> listaProductos = modelo.listaProductosPendientes(noOrdenTrabajo);
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbProductosPedido().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            
            for(int i = 0;i<listaProductos.size();i++){
                RegistroSalidaModel.OrdeneProduccionParcialidad producto = listaProductos.get(i);
                modeloTabla.addRow(new Object[]{
                    producto.getNoOrdenProduccion(),
                    producto.getCodProducto(),
                    producto.getCantidadCliente(),
                    producto.getCantidadRestante()
                });
            }
        }
    
    };
    
   private final MouseListener listenerTablaProductos = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
                int fila = vista.getJtbPedidosPendientes().rowAtPoint(e.getPoint());
                String claveProducto = vista.getJtbProductosPedido().getValueAt(fila, 1).toString();
                
                String resultado = JOptionPane.showInputDialog(new CantidadParcialidad(
                    Integer.parseInt(vista.getJtbProductosPedido().getValueAt(fila, 0).toString()),claveProducto,
                    Integer.parseInt(vista.getJtbProductosPedido().getValueAt(fila, 3).toString()),
                    modelo.obtenerCantidadAlmacenada(claveProducto)));
                
                try {
                    if(resultado != null){
                        int cantidadRegistrar = Integer.parseInt(resultado);
                        aParcialidad(Integer.parseInt(vista.getJtbProductosPedido().getValueAt(fila, 0).toString()),claveProducto,cantidadRegistrar);
                    }   
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "LO QUE HA ESCRITO NO ES VALIDO, VUELTA A INTENTAR");
                }
            
        }
        
        private void aParcialidad(int noOrdenProduccion,String claveProducto,int cantidad){
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbContenidoParcialidad().getModel();
            modeloTabla.addRow(new Object[]{noOrdenProduccion,claveProducto,cantidad});
        }
   
       
   
   };
    
   private final ActionListener listenerRegistrar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbContenidoParcialidad().getModel();
            
            if(modeloTabla.getRowCount() > 0){
            
                int noParcialidadPedido = modelo.registrarParcialidadPedido(ordenTrabajo.getOrdenTrabajo(),vista.getJdcFechaEntrega().getDate());

                if(noParcialidadPedido != 0){

                    for(int i = 0;i<modeloTabla.getRowCount();i++){

                        int noRegistroSalida = modelo.registrarSalidaProducto(modeloTabla.getValueAt(i, 1).toString(),Integer.parseInt(modeloTabla.getValueAt(i, 2).toString()));

                        modelo.registrarParcialidadEntrega(Integer.parseInt(modeloTabla.getValueAt(i, 0).toString()),noRegistroSalida,noParcialidadPedido);

                    }
                }

                JOptionPane.showMessageDialog(null, "REGISTRO CORRECTO");
                vista.dispose();
            } else
                JOptionPane.showMessageDialog(null, "LA LISTA DE PRODUCTOS ESTA VACIA");
        }
    };
}
