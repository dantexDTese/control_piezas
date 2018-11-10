
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

    private final AdminProduccion vista;
    private final AdminProduccionModel model;
    
    
    public AdminProduccionController(AdminProduccion vista, AdminProduccionModel model) {
      
        this.vista = vista;
        this.model = model;
        llenarTablaOrdenesTrabajo();
        this.vista.getJtbOrdenesTrabajo().addMouseListener(listenerSeleccionOrdenTrabajo);
        this.vista.getJtbOrdenesProduccion().addMouseListener(listenerSeleccionOrdenProduccion);
        this.vista.getBtnModificar().addActionListener(listenerBotones);
        this.vista.getBtnGuardarModificacion().addActionListener(listenerBotones);
        
    }
    
    private void desActivarModificacion(){
        vista.getTxtBarrasNecesarias().setEditable(false);
        vista.getTxtTurnosNecesarios().setEditable(false);   
        vista.getJdcDesmontajeMolde().setEnabled(false);
    }
    
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
    
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnModificar())
                actiarModificacion();
            else if(e.getSource() == vista.getBtnGuardarModificacion())
                guardarCambios();
        }
      
        private void guardarCambios(){
            try {
                model.modificarBarrasNecesarias(
                        Integer.parseInt(vista.getLbNoOP().getText()),
                        Integer.parseInt(vista.getTxtBarrasNecesarias().getText()));    
            } catch (NumberFormatException e) {
                System.err.println("error al modificar las barras necesarias " +e.getMessage());
            }
            
            llenarOrdenProduccion(Integer.parseInt(vista.getLbNoOP().getText()));
            
        }
        
         private void actiarModificacion(){
                vista.getTxtBarrasNecesarias().setEditable(true);
                vista.getTxtTurnosNecesarios().setEditable(true);   
                vista.getJdcDesmontajeMolde().setEnabled(true);
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
            int fila = vista.getJtbOrdenesProduccion().rowAtPoint(e.getPoint());
            llenarOrdenProduccion((Integer)vista.getJtbOrdenesProduccion().getValueAt(fila,0));
            desActivarModificacion();
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
                vista.getTxtBarrasNecesarias().setText(orden.getBarrasNecesarias()+"");
                vista.getLbFechaMontajeMolde().setText(orden.getFechaMontaje());
                vista.getLbFechaArranqueProceso().setText(orden.getFechaInicioOP());
                if(orden.getFechaDesmontaje() != null && !"".equals(orden.getFechaDesmontaje()))
                    vista.getJdcDesmontajeMolde().setDate(new Date(orden.getFechaDesmontaje()));
                
            }
            else
                JOptionPane.showMessageDialog(null,"error");
                
        }
    
}
