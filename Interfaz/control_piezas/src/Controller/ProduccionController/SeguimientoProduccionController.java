package Controller.ProduccionController;

import Model.ProduccionModel.OrdenProduccionGuardada;
import Model.ProduccionModel.SeguimientoProduccionModel;
import View.Produccion.SeguimientoProduccionDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        llenarAtributos(ordenSeleccionada);
        llenarListaProcesos(ordenSeleccionada.getOrdenProduccion());
        
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
            
        }
    };
    
    
    
}
