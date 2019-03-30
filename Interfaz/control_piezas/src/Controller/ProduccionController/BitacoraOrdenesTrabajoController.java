
package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.ImgTabla;
import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import Model.ProduccionModel.RegistroOrdenTrabajo;
import View.Pedidos.ColorEstado;
import View.Produccion.BitacoraOrdenesTrabajoView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public final class BitacoraOrdenesTrabajoController implements Constructores{

    private final BitacoraOrdenesTrabajoView bitacoraTrabajosView;
    private final BitacoraOrdenesTrabajoModel bitacoraTrabajosModel;
    private ArrayList<RegistroOrdenTrabajo> ordenesTrabajo;
    int noOrden=0;
    
    public BitacoraOrdenesTrabajoController(BitacoraOrdenesTrabajoView bitacoraTrabajosView, BitacoraOrdenesTrabajoModel bitacoratrabajosModel) {
        this.bitacoraTrabajosView = bitacoraTrabajosView;
        this.bitacoraTrabajosModel = bitacoratrabajosModel;
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        bitacoraTrabajosView.getTbOrdenesTrabajo().setDefaultRenderer(Object.class,new ImgTabla());
        llenarTablaOrdenesTrabajo(this.bitacoraTrabajosView.getYcrAnio().getValue());
        this.bitacoraTrabajosView.getLbAnio().setText(bitacoraTrabajosView.getYcrAnio().getValue()+"");
    }

    @Override
    public void asignarEventos() {
        this.bitacoraTrabajosView.getTbOrdenesTrabajo().addMouseListener(clickTabla);
        this.bitacoraTrabajosView.getBtnGuardarObservacion().addActionListener(botonGuardar);
        this.bitacoraTrabajosView.getYcrAnio().addPropertyChangeListener(listenerBuscarAnio);
    }
    
    private final MouseAdapter clickTabla = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila  = bitacoraTrabajosView.getTbOrdenesTrabajo().rowAtPoint(e.getPoint());
            noOrden = (int)bitacoraTrabajosView.getTbOrdenesTrabajo().getValueAt(fila, 0);
            bitacoraTrabajosView.getLbNoOrdenTrabajo().setText(noOrden+"");
            bitacoraTrabajosView.getTxtObservaciones().setText(obtenerObservacion(noOrden));
        }
    };
    
    private final ActionListener botonGuardar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {       
            if(noOrden != 0){
                bitacoraTrabajosModel.guardarObservacion(bitacoraTrabajosView.getTxtObservaciones().getText(),noOrden);
            }
            else
                JOptionPane.showMessageDialog(null,"SELECCIONA UNA ORDEN DE PRODUCCION");
            
            llenarTablaOrdenesTrabajo(bitacoraTrabajosView.getYcrAnio().getValue());
            bitacoraTrabajosView.getTxtObservaciones().setText("");
            noOrden = 0;
            bitacoraTrabajosView.getLbNoOrdenTrabajo().setText("");
        }
        
        
    };
    
    private final PropertyChangeListener listenerBuscarAnio = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
             bitacoraTrabajosView.getLbAnio().setText(bitacoraTrabajosView.getYcrAnio().getValue()+"");
             llenarTablaOrdenesTrabajo(bitacoraTrabajosView.getYcrAnio().getValue());
        }
    };
    
    private String obtenerObservacion(int noOrden){
        String observacion = bitacoraTrabajosModel.obtenerObservacion(noOrden);
        return observacion;
    }
   
    private void llenarTablaOrdenesTrabajo(int anio){
            ordenesTrabajo = bitacoraTrabajosModel.listaOrdenesTrabajo(anio);
            DefaultTableModel modelOrdenesTrabajo = (DefaultTableModel) bitacoraTrabajosView.getTbOrdenesTrabajo().getModel();        
            Estructuras.limpiarTabla(modelOrdenesTrabajo);
            if(ordenesTrabajo.size()>0)
                for(int i = 0;i<ordenesTrabajo.size();i++){
                    RegistroOrdenTrabajo ordenTrabajo = ordenesTrabajo.get(i);
                    
                    ColorEstado estado = Estructuras.obtenerColorEstado(ordenTrabajo.getDescEstados());
                    
                    modelOrdenesTrabajo.addRow(new Object[]{
                        ordenTrabajo.getNoOrdenProduccion(),
                        ordenTrabajo.getFechaRegistro(),
                        ordenTrabajo.getClaveProducto(),
                        ordenTrabajo.getCantidadCliente(),
                        ordenTrabajo.getFechaInicio(),
                        ordenTrabajo.getFechaFin(),
                        ordenTrabajo.getNoPedido(),
                        ordenTrabajo.getFechaEntrega(),
                        estado
                    });
                }
    }   

    
}
