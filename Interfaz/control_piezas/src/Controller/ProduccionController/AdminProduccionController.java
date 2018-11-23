
package Controller.ProduccionController;

import Model.Estructuras;
import Model.ProduccionModel.AdminProduccionModel;
import Model.ProduccionModel.OrdenProduccionGuardada;
import View.Produccion.AdminProduccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AdminProduccionController {
    
    /**
     * ATRIBUTOS
     */
    private final AdminProduccion vista;
    private final AdminProduccionModel model;
    
    
    /**
     * CONSTRUCTOR
     * @param vista
     * @param model
     */
    public AdminProduccionController(AdminProduccion vista, AdminProduccionModel model) {
      
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
        
    }
    
    
    /**
     * METODOS
     */
    
    private void llenarTablaOrdenesTrabajo(){
        
        ArrayList<AdminProduccionModel.OrdenTrabajo> listaOrdenesTrabajo =  model.listaOrdenesTrabajo();
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
     * FUNCIONES
     */
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

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
    };
    
    private void llenarOrdenProduccion(Integer noOrdenProduccion){
            OrdenProduccionGuardada orden = model.obtenerOrdenProduccion(noOrdenProduccion);
            if(orden!= null){
                vista.getLbNoOP().setText(orden.getOrdenProduccion()+"");
                vista.getLbProducto().setText(orden.getClaveProducto());
                vista.getLbCliente().setText(orden.getNombreCliente());
                vista.getLbOrdenCompra().setText(orden.getNoOrdenCompra());
                vista.getLbCantidad().setText(orden.getCantidadCliente()+"");
                vista.getLbPiezasPorTurno().setText(orden.getPiezasPorTurno()+"");
                vista.getLbMaterial().setText(orden.getDescMaterial());
                vista.getLbMaquina().setText(orden.getDescMaquina());
                vista.getLbCantidadProduccir().setText(orden.getCantidadTotal()+"");
                vista.getLbBarrasNecesarias().setText(orden.getBarrasNecesarias()+"");
                vista.getLbFechaMontajeMolde().setText(orden.getFechaMontaje());
                vista.getLbFechaArranqueProceso().setText(orden.getFechaInicioOP());
                vista.getLbInicioProduccion().setText(orden.getFechaInicioOP());
                vista.getLbFechaEntrega().setText(orden.getFechaEntregaPedido());
                
                if(orden.getFechaDesmontaje() != null && !"".equals(orden.getFechaDesmontaje()))
                    vista.getJdcDesmontajeMolde().setDate(new Date(orden.getFechaDesmontaje()));
                
            }
            else
                JOptionPane.showMessageDialog(null,"error");
                
        }
    
}
