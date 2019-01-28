
package Controller.AlmacenController;

import Model.AlmacenModel.AlmacenMateriaPrima;
import Model.AlmacenModel.MateriaPrimaModel;
import Model.Constructores;
import Model.Estructuras;
import View.almacenView.MateriaPrimaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */

public final class MateriaPrimaController implements Constructores{

    
    private final MateriaPrimaView vista;
    private final MateriaPrimaModel modelo;
    private String materialSeleccionado=null;
    private AlmacenMateriaPrima almacenLoteSeleccionado;
    public MateriaPrimaController(MateriaPrimaView mPrimaView, MateriaPrimaModel mPrimaModel) {
        
        this.vista = mPrimaView;
        this.modelo = mPrimaModel;
        llenarComponentes();
        asignarEventos();
    
    }

    @Override
    public void llenarComponentes() {
        llenarTablaMateriales();
    }

    @Override
    public void asignarEventos() {
        vista.getJtbMateriales().addMouseListener(listenerTablaMateriales);
        PropertyChangeListener listenerFechas = (PropertyChangeEvent evt) -> {        
            llenarTablaLotes(materialSeleccionado);
        
        };
        vista.getJdcAnio().addPropertyChangeListener(listenerFechas);
        vista.getJdcMes().addPropertyChangeListener(listenerFechas);
        vista.getJtbLotes().addMouseListener(listenerSeleccionarLote);
        vista.getBtnRegistrar().addActionListener(listenerBotonGuardar);
    }
    
    private void llenarTablaMateriales(){
        ArrayList<String> listaMateriales = modelo.listaMateriasPrimas();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbMateriales().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaMateriales.size();i++)
            modeloTabla.addRow(new Object[]{i+1,listaMateriales.get(i)});
    }
    
    ArrayList<AlmacenMateriaPrima> listaMateriasPrimas;
    
    private void llenarTablaLotes(String descMaterial){
         if(descMaterial != null){
             listaMateriasPrimas = modelo.listaAlmacenMateriaPrima(descMaterial, vista.getJdcAnio().getValue(), vista.getJdcMes().getMonth()+1);
             
             DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbLotes().getModel();
             Estructuras.limpiarTabla(modeloTabla);
             
             for(int i = 0;i<listaMateriasPrimas.size();i++){
                 AlmacenMateriaPrima material = listaMateriasPrimas.get(i);
                 modeloTabla.addRow(new Object[]{i+1,material.getCantidad(),(material.getCantidadTotal())
                         ,material.getFechaRegistro(),material.getDescLote()});
             }    
         }
         
    }
    
    private final MouseListener listenerTablaMateriales = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = vista.getJtbMateriales().rowAtPoint(e.getPoint());
            materialSeleccionado = vista.getJtbMateriales().getValueAt(fila,1).toString();
            llenarTablaLotes(materialSeleccionado);
        }   
    };
    
    
    private final MouseListener listenerSeleccionarLote = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                
                int respuesta = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE QUIERE REALIZAR UNA SALIDA DE ESTE LOTE?","CONFIRMACION",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                
                if(respuesta == JOptionPane.YES_OPTION){
                
                    int fila = vista.getJtbLotes().rowAtPoint(e.getPoint());
                    almacenLoteSeleccionado = seleccionarLote(vista.getJtbLotes().getValueAt(fila, 4).toString());



                    if(almacenLoteSeleccionado != null){
                        if(almacenLoteSeleccionado.getCantidadTotal() > 0){
                            vista.getLbMaterialSeleccionado().setText(materialSeleccionado);
                            vista.getLbLote().setText(almacenLoteSeleccionado.getDescLote());
                            vista.getSprCantidad().setValue(almacenLoteSeleccionado.getCantidadTotal());
                        }else
                            JOptionPane.showMessageDialog(null, "YA NO HAY MAS MATERIALES DE ESTE LOTE, POR FAVOR SELECCIONE OTRO","SIN MATERIAL",
                                    JOptionPane.WARNING_MESSAGE);

                    }
                
                }
            }
            
        }

        private AlmacenMateriaPrima seleccionarLote(String descLote) {
            for(int i = 0;i<listaMateriasPrimas.size();i++)
                if(listaMateriasPrimas.get(i).getDescLote().equals(descLote))
                    return listaMateriasPrimas.get(i);
            
            return null;
                    
        }

        
        
        
    
    };
    
    private final ActionListener listenerBotonGuardar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(almacenLoteSeleccionado != null && 
                    Integer.parseInt(vista.getSprCantidad().getValue().toString()) > 0
                    && Integer.parseInt(vista.getSprCantidad().getValue().toString()) <= almacenLoteSeleccionado.getCantidadTotal()){
                modelo.registrarSalida(almacenLoteSeleccionado,Integer.parseInt(vista.getSprCantidad().getValue().toString()));
                limpiar();
                llenarTablaLotes(materialSeleccionado);
            }else
                JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO UN LOTE O LA CANTIDAD QUE ESTA PIDIENDO NO ES CORRECTA","ERROR",JOptionPane.ERROR_MESSAGE);
        }

        private void limpiar() {
            vista.getLbLote().setText("");
            vista.getLbMaterialSeleccionado().setText("");
            vista.getSprCantidad().setValue(0);
            almacenLoteSeleccionado = null;       
        }
    };
    
    
    
}
