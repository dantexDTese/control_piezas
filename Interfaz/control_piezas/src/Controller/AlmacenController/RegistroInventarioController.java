
package Controller.AlmacenController;

import Model.AlmacenModel.RegistroInventarioModel;
import Model.Constructores;
import Model.Estructuras;
import View.almacenView.RegistroInventario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class RegistroInventarioController implements Constructores{

    
    RegistroInventario vista;
    RegistroInventarioModel modelo;
    
    RegistroInventarioController(RegistroInventario vista, RegistroInventarioModel modelo) {
        
        this.vista = vista;
        this.modelo = modelo;
        llenarComponentes();
        asignarEventos();
        
    }

    @Override
    public void llenarComponentes() {
        llenarTablaProductos();
        vista.getLbFecha().setText(Estructuras.obtenerFechaActual());
    }

    @Override
    public void asignarEventos() {
        vista.getBtnGuardar().addActionListener(listenerGuardar);
    }
    
    private void llenarTablaProductos(){
        ArrayList<String> listaProductos = modelo.listaProductos();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbProductosInventario().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaProductos.size();i++)
            modeloTabla.addRow(new Object[]{
                listaProductos.get(i)
            });
    }
    
    private final ActionListener listenerGuardar = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO DE GUARDAR ESTE INVENTARIO?","CONFIRMACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if(respuesta == JOptionPane.YES_OPTION){
                
                DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbProductosInventario().getModel();
                if(validar(modeloTabla)){

                    int noInventario = modelo.iniciarInventario(vista.getTxtEncargado().getText());

                    if( noInventario > 0){
                        for(int i = 0;i<modeloTabla.getRowCount();i++)
                            modelo.agregarProductoInventario(modeloTabla.getValueAt(i, 0).toString(),Integer.parseInt(modeloTabla.getValueAt(i, 1).toString()),noInventario);
                    }

                    JOptionPane.showMessageDialog(null, "EL INVENTARIO SE A GUARDADO CORRECTAMENTE");
                    vista.dispose();

                } else JOptionPane.showMessageDialog(null, "EL INVENTARIO NO ESTA COMPLETO POR FAVOR COMPLETE LOS CAMPOS");
            }
        }
        
        private boolean  validar(DefaultTableModel modeloTabla){
            for(int i = 0;i<modeloTabla.getRowCount();i++)
                if(modeloTabla.getValueAt(i, 1) == null)
                    return false;
            return true;
        }
    };
    
}
