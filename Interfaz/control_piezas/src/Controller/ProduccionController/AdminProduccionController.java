
package Controller.ProduccionController;

import Model.Estructuras;
import Model.PedidosModel.Pedido;
import Model.ProduccionModel.AdminProduccionModel;
import Model.ProduccionModel.ControlProduccionModel;
import Model.ProduccionModel.OrdenProduccionGuardada;
import Model.ProduccionModel.SeguimientoProduccionModel;
import View.Produccion.AdminProduccionView;
import View.Produccion.ControlProduccionDialogView;
import View.Produccion.SeguimientoProduccionDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AdminProduccionController {
    
    /**
     * ATRIBUTOS
     */
    private final AdminProduccionView vista;
    private final AdminProduccionModel model;
    private OrdenProduccionGuardada ordenSeleccionada;
    
    /**
     * CONSTRUCTOR
     * @param vista
     * @param model
     */
    public AdminProduccionController(AdminProduccionView vista, AdminProduccionModel model) {
      
        //INICIALIZACION
        this.vista = vista;
        this.model = model;
        
        //ORDENES DE TRABAJO
        llenarTablaOrdenesTrabajo();
        this.vista.getJtbOrdenesTrabajo().addMouseListener(listenerSeleccionOrdenTrabajo);
        
        //PRDEN DE PRODUCCION DE LA ORDEN DE TRABAJO
        this.vista.getJtbOrdenesProduccion().addMouseListener(listenerSeleccionOrdenProduccion);
        
        //BOTONES MODIFICAR Y GUARDAR MODIFICACION
        this.vista.getBtnModificar().addActionListener(listenerBotones);
        this.vista.getBtnGuardarModificacion().addActionListener(listenerBotones);
        this.vista.getBtnSeguimientoProduccion().addActionListener(listenerBotones);
        this.vista.getBtnControlProduccion().addActionListener(listenerBotones);
    }
    
    
    /**
     * METODOS
     */
    private void llenarTablaOrdenesTrabajo(){
        
        ArrayList<Pedido> listaOrdenesTrabajo =  model.listaOrdenesTrabajo();
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbOrdenesTrabajo().getModel());
        DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbOrdenesTrabajo().getModel();
        
        for(int i = 0;i<listaOrdenesTrabajo.size();i++){
            modelTabla.addRow(new Object[]{
                listaOrdenesTrabajo.get(i).getNoOrdenTrabajo(),
                listaOrdenesTrabajo.get(i).getEstado(),
            });
        }
    }
    
    /**
     * EVENTOS
     */
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnSeguimientoProduccion())
                mostrarSeguimientoProduccion();
            else if(e.getSource() == vista.getBtnControlProduccion())
                mostrarControlProduccion();
        }     
        
        private void mostrarSeguimientoProduccion(){
            SeguimientoProduccionDialogView vistaSeguimiento = new SeguimientoProduccionDialogView(vista.getPrincipal(), true);
            SeguimientoProduccionController controllerSeguimiento = new SeguimientoProduccionController(vistaSeguimiento,new SeguimientoProduccionModel(),ordenSeleccionada);
            vistaSeguimiento.setVisible(true);
            
        }
        
        private void mostrarControlProduccion(){
            ControlProduccionDialogView viewControlProduccion = new ControlProduccionDialogView(vista.getPrincipal(), true);
            ControlProduccionController controllerControlProduccion = new ControlProduccionController(viewControlProduccion
                                        , new ControlProduccionModel());
            viewControlProduccion.setVisible(true);
        }
        
    };
    
    
    
    private final MouseListener listenerSeleccionOrdenTrabajo = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
          
                int fila = vista.getJtbOrdenesTrabajo().rowAtPoint(e.getPoint());
                llenarTablaOrdenesProduccion(Integer.parseInt(vista.getJtbOrdenesTrabajo().getValueAt(fila, 0).toString()));
                
        }
    
        private void llenarTablaOrdenesProduccion(int ordenTrabajo){
            ArrayList<AdminProduccionModel.OrdenProduccion> listaOrdenesProduccion = model.listaOrdenesProduccion(ordenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbOrdenesProduccion().getModel());
            DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbOrdenesProduccion().getModel();
            
            for(int i = 0;i<listaOrdenesProduccion.size();i++)
                modelTabla.addRow(new Object[]{
                    listaOrdenesProduccion.get(i).getNoOrdenProduccion(),
                    listaOrdenesProduccion.get(i).getClaveProducto(),
                });            
        }
    
    };        
        
    private final MouseListener listenerSeleccionOrdenProduccion = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);            
            int filaSeleccionada = vista.getJtbOrdenesProduccion().rowAtPoint(e.getPoint());
            llenarOrdenProduccion((Integer)vista.getJtbOrdenesProduccion().getValueAt(filaSeleccionada,0));
        }
        
        private void llenarOrdenProduccion(Integer noOrdenProduccion){
            ordenSeleccionada = model.obtenerOrdenProduccion(noOrdenProduccion);
            if(ordenSeleccionada!= null){
                vista.getLbNoOP().setText(ordenSeleccionada.getOrdenProduccion()+"");
                vista.getLbProducto().setText(ordenSeleccionada.getClaveProducto());
                vista.getLbCliente().setText(ordenSeleccionada.getNombreCliente());
                vista.getLbOrdenCompra().setText(ordenSeleccionada.getNoOrdenCompra());
                vista.getLbCantidad().setText(ordenSeleccionada.getCantidadCliente()+"");
                vista.getLbPiezasPorTurno().setText(ordenSeleccionada.getPiezasPorTurno()+"");
                vista.getLbMaterial().setText(ordenSeleccionada.getDescMaterial());
                vista.getLbMaquina().setText(ordenSeleccionada.getDescMaquina());
                vista.getLbCantidadProduccir().setText(ordenSeleccionada.getCantidadTotal()+"");
                vista.getLbBarrasNecesarias().setText(ordenSeleccionada.getBarrasNecesarias()+"");
                vista.getLbFechaMontajeMolde().setText(ordenSeleccionada.getFechaMontaje());
                vista.getLbFechaArranqueProceso().setText(ordenSeleccionada.getFechaInicioOP());
                vista.getLbInicioProduccion().setText(ordenSeleccionada.getFechaInicioOP());
                vista.getLbFechaEntrega().setText(ordenSeleccionada.getFechaEntregaPedido());
                
                if(ordenSeleccionada.getFechaDesmontaje() != null && !"".equals(ordenSeleccionada.getFechaDesmontaje()))
                    vista.getJdcDesmontajeMolde().setDate(new Date(ordenSeleccionada.getFechaDesmontaje()));
                
            }
            else
                JOptionPane.showMessageDialog(null,"error");       
        }
    };
    
    
    
}
