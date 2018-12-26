
package Controller.RequisicionesController;

import Model.RequisicionesModel.RegistroEntradaMaterialesModel;
import Model.RequisicionesModel.RegistrarNuevaEntradaMaterialModel;
import View.Requisiciones.RegistrarNuevaEntradaMaterial;
import View.Requisiciones.RegistroEntradaMateriales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegistroEntradaMaterialesController {

    RegistroEntradaMateriales entradaMaterialesView;
    RegistroEntradaMaterialesModel entradaMaterialesModel;
    
    public RegistroEntradaMaterialesController(RegistroEntradaMateriales entradaMaterialesView,
            RegistroEntradaMaterialesModel entradaMaterialesModel) {
        
        this.entradaMaterialesView = entradaMaterialesView;
        this.entradaMaterialesModel = entradaMaterialesModel;
        this.entradaMaterialesView.getBtnRegistrarNuevaEntrada().addActionListener(listenerBotones);
    }
    
    private ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            agregarNuevaEntradaMaterial();
        }
        
        private void agregarNuevaEntradaMaterial(){
            RegistrarNuevaEntradaMaterial viewNuevaEntrada = new RegistrarNuevaEntradaMaterial(entradaMaterialesView.getPrincipal(), true);
            RegistrarNuevaEntradaMaterialController nuevaEntradaController = new RegistrarNuevaEntradaMaterialController(
                    viewNuevaEntrada,new RegistrarNuevaEntradaMaterialModel());
            
            viewNuevaEntrada.setVisible(true);
            
        }
    };
    
    
    
    
    
}
