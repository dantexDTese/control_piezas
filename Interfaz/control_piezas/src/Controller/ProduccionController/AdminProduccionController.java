
package Controller.ProduccionController;

import Model.Estructuras;
import Model.ProduccionModel.AdminProduccionModel;
import Model.ProduccionModel.OrdenProduccionGuardada;
import View.Produccion.AdminProduccion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
            
        }
                
        private void llenarOrdenProduccion(Integer noOrdenProduccion){
            OrdenProduccionGuardada orden = model.obtenerOrdenProduccion(noOrdenProduccion);
            if(orden!= null){
                vista.getLbNoOP().setText(orden.getOrdenProduccion()+"");
                vista.getLbProducto().setText(orden.getClaveProducto());
                vista.getLbCliente().setText(orden.getNombreCliente());
                
                
            }
            else
                JOptionPane.showMessageDialog(null,"error");
                
        }
        
    };
    
}
