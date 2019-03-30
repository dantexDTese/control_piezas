
package Controller.PedidosController;

import Model.Constructores;
import Model.Estructuras;
import Model.LotePlaneado;
import Model.PedidosModel.AjustesPlaneacionModel;
import View.Pedidos.AjustePlaneacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public final class AjustesPlaneacionController implements Constructores{

    private final AjustePlaneacion vista;
    private final AjustesPlaneacionModel model;
    private final LotePlaneado loteSeleccionado;
    private LotePlaneado loteModificar;
    private ArrayList<LotePlaneado> lotesPlaneados;
    private int cantidadTotal;
    private int cantidadModificable;
    private int cantidadModificableCopia;
    
    AjustesPlaneacionController(AjustePlaneacion vista,
            AjustesPlaneacionModel model, LotePlaneado loteSeleccionado){
        
            this.vista = vista;
            this.model = model;
            this.loteSeleccionado = loteSeleccionado;
            llenarComponentes();
            asignarEventos();
            
    }

    @Override
    public void llenarComponentes(){
        lotesPlaneados = model.listaLotesPlaneados(loteSeleccionado.getOrdenTrabajo(), loteSeleccionado.getNoOrdenProduccion());
        cantidadTotal = model.obtenerCantidadTotal(loteSeleccionado.getNoOrdenProduccion());
        vista.getLbCantidadTotal().setText(cantidadTotal+"");
        cantidadModificable = cantidadTotal-sumarCantidadLotes();
        cantidadModificableCopia = cantidadModificable;
        vista.getLbRestante().setText(cantidadModificable+"");
        llenarLIstaProcesos();
    }

    @Override
    public void asignarEventos(){
        vista.getCbsTipoProceso().addActionListener(listenerSeleccionarProceso);
        vista.getJtbFechasPlaneadas().addMouseListener(listenerSeleccionarLote);
        vista.getSprCantidad().addChangeListener(listenerCambiarValor);
        vista.getBtnGuardar().addActionListener(listenerModificarEliminar);
        vista.getBtnEliminar().addActionListener(listenerModificarEliminar);
    }
  
    private void llenarLIstaProcesos(){
        if(vista.getCbsTipoProceso().getItemCount()>0)
            vista.getCbsTipoProceso().removeAllItems();
            
        for(int i = 0;i<lotesPlaneados.size();i++)
            if(!existeProcesoCombo(lotesPlaneados.get(i).getTipoProceso()))
                vista.getCbsTipoProceso().addItem(lotesPlaneados.get(i).getTipoProceso());
    }
    
    private boolean existeProcesoCombo(String tipoProceso){
        for(int i = 0;i<vista.getCbsTipoProceso().getItemCount();i++)
            if(tipoProceso.equals(vista.getCbsTipoProceso().getItemAt(i)))
                return true;
        return false;
    }
    
    private final ActionListener listenerSeleccionarProceso = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(vista.getCbsTipoProceso().getItemCount() > 0)
                llenarTablaLotesPlaneados(vista.getCbsTipoProceso().getSelectedItem().toString());
           
        }

        private void llenarTablaLotesPlaneados(String tipoProceso) {
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbFechasPlaneadas().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            
            for( int i = 0; i < lotesPlaneados.size() ; i++ )
                if(lotesPlaneados.get(i).getTipoProceso().endsWith(tipoProceso)){
                    modeloTabla.addRow(new Object[]{
                        i+1,lotesPlaneados.get(i).getFechaPlaneada(),lotesPlaneados.get(i).getCantidadPlaneada()
                    });
                }
                        
        }
        
    };
    
    private final MouseListener listenerSeleccionarLote = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
             
            super.mousePressed(e);
            
            if(e.getClickCount() == 2){    
                if(JOptionPane.showConfirmDialog(null,"¿MODIFICAR LOTE?","VALIDACION",JOptionPane.YES_NO_OPTION,
                         JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

                    int fila = vista.getJtbFechasPlaneadas().rowAtPoint(e.getPoint());

                    loteModificar = obtenerLotePlaneado(vista.getJtbFechasPlaneadas().getValueAt(fila, 1).toString());
                    
                    if(loteModificar != null){
                        vista.getLbLote().setText(vista.getJtbFechasPlaneadas().getValueAt(fila, 0).toString());
                        vista.getSprCantidad().setValue(loteModificar.getCantidadPlaneada());   
                    }   
                }
            }
        
        }

        private LotePlaneado obtenerLotePlaneado( String fechaPlaneada){
            
            for(int i = 0; i<lotesPlaneados.size() ; i++)
                if(fechaPlaneada.equals(lotesPlaneados.get(i).getFechaPlaneada()))
                    return lotesPlaneados.get(i);    
            
            return null;
        }
        
    };
    
    private final ChangeListener listenerCambiarValor = new ChangeListener() {
     
        @Override
        public void stateChanged(ChangeEvent e) {
            if(loteModificar != null){   
                
                if(Integer.parseInt(vista.getSprCantidad().getValue().toString()) < 0){
                    
                    JOptionPane.showMessageDialog(null, " LA CANTIDAD ES MINIMA NO SE PUEDE REDUCIR AUN MAS ",
                            "VALIDACION",JOptionPane.WARNING_MESSAGE);
                    vista.getSprCantidad().setValue(0);
                    
                }else if(loteModificar.getCantidadPlaneada() > 
                            Integer.parseInt(vista.getSprCantidad().getValue().toString())){
                          
                    cantidadModificableCopia = cantidadModificable + (loteModificar.getCantidadPlaneada() -
                            Integer.parseInt(vista.getSprCantidad().getValue().toString()));
                    vista.getLbRestante().setText(cantidadModificableCopia+"");    
                    
                }else if(loteModificar.getCantidadPlaneada() <
                            Integer.parseInt(vista.getSprCantidad().getValue().toString()) && 
                        cantidadModificableCopia > 0){
                    
                    cantidadModificableCopia = cantidadModificable +(loteModificar.getCantidadPlaneada() -
                            Integer.parseInt(vista.getSprCantidad().getValue().toString()));
                    vista.getLbRestante().setText(cantidadModificableCopia+"");
                    
                }else{
                    
                    vista.getLbRestante().setText(cantidadModificable+"");
                    cantidadModificableCopia = cantidadModificable;
                    vista.getSprCantidad().setValue(loteModificar.getCantidadPlaneada());
                    
                }   
                    
            } else vista.getSprCantidad().setValue(0);
            
        }
        
    };
    
    private final ActionListener listenerModificarEliminar = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnGuardar()){
               if(JOptionPane.showConfirmDialog(null, "¿SEGURO QUE QUIERES MODIFICAR ESTE LOTE?",
                       "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                       JOptionPane.YES_OPTION){
                    if( loteSeleccionado != null && (cantidadModificable != cantidadModificableCopia) ){
                        loteModificar.setCantidadPlaneada(Integer.parseInt(vista.getSprCantidad().getValue().toString()));
                        model.modificarLote(loteModificar);
                        vista.dispose();
                    }
               }
            }else if(e.getSource() == vista.getBtnEliminar()){
                  if(JOptionPane.showConfirmDialog(null, "¿SEGURO QUE QUIERES ELIMINAR ESTE LOTE?",
                       "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                       JOptionPane.YES_OPTION){
                    if(loteSeleccionado != null){                    
                        model.eliminarLote(loteModificar);
                        vista.dispose();
                  
                    }
                  
                  }
            }
            
        }    
        
        
       
        
    };
    
    
    
    private int sumarCantidadLotes(){
        int suma=0;
        for(int i = 0;i<lotesPlaneados.size();i++)
            suma+=lotesPlaneados.get(i).getCantidadPlaneada();
        
        return suma;
    }
    
}
