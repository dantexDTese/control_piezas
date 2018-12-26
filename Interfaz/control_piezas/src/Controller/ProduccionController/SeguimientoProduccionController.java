package Controller.ProduccionController;

import Model.Estructuras;
import Model.ProduccionModel.OrdenProduccionGuardada;
import Model.ProduccionModel.SeguimientoProduccionModel;
import Model.ProduccionModel.loteProduccion;
import View.Produccion.SeguimientoProduccionDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class SeguimientoProduccionController {

    
    private final SeguimientoProduccionDialogView vistaSeguimiento;
    private final SeguimientoProduccionModel seguimientoProduccionModel;
    private final OrdenProduccionGuardada ordenSeleccionada;     
    
    SeguimientoProduccionController(SeguimientoProduccionDialogView vistaSeguimiento, SeguimientoProduccionModel seguimientoProduccionModel,OrdenProduccionGuardada ordenSeleccionada) {
        
        //INICIALIZAR
        this.vistaSeguimiento = vistaSeguimiento;
        this.seguimientoProduccionModel = seguimientoProduccionModel;
        this.ordenSeleccionada = ordenSeleccionada;
        llenarAtributos(this.ordenSeleccionada);
        llenarListaProcesos(this.ordenSeleccionada.getOrdenProduccion());
        
        //AGREGAR EVENTOS
        this.vistaSeguimiento.getCbxProcesosProduccion().addActionListener(listenerSeleccionProceso);
    }
    
    //METODOS
    
    private void llenarListaProcesos(int noOrdenProduccion){
        ArrayList<String> procesosProduccion = seguimientoProduccionModel.obtenerProcesosProduccion(noOrdenProduccion);
        for(int i = 0;i<procesosProduccion.size();i++)
           vistaSeguimiento.getCbxProcesosProduccion().addItem(procesosProduccion.get(i));
    }
    
    private void llenarAtributos(OrdenProduccionGuardada orden){
        vistaSeguimiento.getLbCliente().setText(orden.getNombreCliente());
        vistaSeguimiento.getLbParte().setText(orden.getClaveProducto());
        vistaSeguimiento.getLbCantidadTotal().setText(orden.getCantidadTotal()+"");
        vistaSeguimiento.getLbMaquina().setText(orden.getDescMaquina());        
    }
    
                
    
    //EVENTOS
    
    private final ActionListener listenerSeleccionProceso = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<loteProduccion> listaLotes = obtenerLotesProcesados(ordenSeleccionada.getOrdenProduccion(),vistaSeguimiento.getCbxProcesosProduccion().getSelectedItem().toString());
            llenarTotalesLotes(listaLotes);
            
        }
        
        private ArrayList<loteProduccion> obtenerLotesProcesados(int noOrdenProduccion,String procesoSeleccionado){
            ArrayList<loteProduccion> listaLotesProduccion = 
                    seguimientoProduccionModel.listaLotesProduccion(noOrdenProduccion, procesoSeleccionado);
            
            DefaultTableModel modelLotesProduccion = (DefaultTableModel) vistaSeguimiento.getJtbLotesProduccion().getModel();
            Estructuras.limpiarTabla((DefaultTableModel) vistaSeguimiento.getJtbLotesProduccion().getModel());
            
            for(int i = 0;i<listaLotesProduccion.size();i++){
                loteProduccion lote = listaLotesProduccion.get(i);
                modelLotesProduccion.addRow(new Object[]{
                    i,lote.getDescLote(),lote.getCantidadOperados(),lote.getScrapOperador(),
                    lote.getMerma(),lote.getTiempoMuerto(),lote.getRechazo(),
                    lote.getCantidadAdmin(),lote.getScrapAdmin()});
            }
            
            return listaLotesProduccion;
        }

        private void llenarTotalesLotes(ArrayList<loteProduccion> listaLotes) {
            loteProduccion p = new loteProduccion();

            for(int i = 0;i<listaLotes.size();i++){
                loteProduccion lote = listaLotes.get(i);
                p.setCantidadOperados( p.getCantidadOperados() + lote.getCantidadOperados());
                p.setScrapOperador(p.getScrapOperador() + lote.getScrapOperador());
                p.setMerma(p.getMerma() + lote.getMerma());   
                p.setTiempoMuerto(sumarTiempo(p.getTiempoMuerto(),lote.getTiempoMuerto()));
                p.setRechazo(p.getRechazo() + lote.getRechazo());
                p.setCantidadAdmin(p.getCantidadAdmin() + lote.getCantidadAdmin());
                p.setScrapAdmin(p.getScrapAdmin() + lote.getScrapAdmin() );
            }

        }
        
        private String sumarTiempo(String tiempo1,String tiempo2){
            String tiempoResultado = new String();
            
                
                
            return tiempoResultado;
        }
    };
    
    
    
}
