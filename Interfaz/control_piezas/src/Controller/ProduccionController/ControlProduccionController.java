
package Controller.ProduccionController;

import Model.ProduccionModel.ControlProduccionModel;
import View.Produccion.ControlProduccionDialogView;

import javax.swing.JTable;
import javax.swing.table.TableColumn;



public class ControlProduccionController {

    private final ControlProduccionDialogView controlProduccionView;
    private final ControlProduccionModel controlProduccionModel;
    
    public ControlProduccionController(ControlProduccionDialogView controlProduccionView, ControlProduccionModel controlProduccionModel) {
        this.controlProduccionView = controlProduccionView;
        this.controlProduccionModel = controlProduccionModel;
        tamanoTabla();
    }
    
     private void tamanoTabla(){
        controlProduccionView.getJtbConntrolProduccion().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
        for(int i = 0;i<50;i++){
            TableColumn columna = controlProduccionView.getJtbConntrolProduccion().getColumnModel().getColumn(i);
            columna.setPreferredWidth(130);
        }        
    }
    
}
