
package Controller.RequisicionesController;

import Model.RequisicionesModel.AgregarNuevaRequisicionModel;
import Model.RequisicionesModel.MaterialesRequisicion;
import Model.RequisicionesModel.controlEntregasModel;
import View.Requisiciones.ControlEntregasView;
import View.Requisiciones.agregarNuevasRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author cesar
 */
public class controlEntregasController {
    
    /**
     * ATRIBUTOS
     */
    private ControlEntregasView entregasView;
    private final controlEntregasModel entregasModel;
    
    /**
     * CONSTRUCTOR
     * @param entragasView
     * @param entregasModel
     */
    public controlEntregasController(ControlEntregasView entragasView, controlEntregasModel entregasModel) {
        this.entregasView = entragasView;
        this.entregasModel = entregasModel;
        this.entregasView.getBtnAgregarRequisiciones().addActionListener(listenerBotones);
        llenarTablaRequisiciones();
    }
    
    /**
     *METODOS 
     */
    public void llenarTablaRequisiciones(){
        ArrayList<MaterialesRequisicion> listaMaterialesRequisicion = entregasModel.obtenerRequisiciones();
        DefaultTableModel modelRequisiciones = (DefaultTableModel) entregasView.getJtEntregas().getModel();

        for(int i = 0;i<listaMaterialesRequisicion.size();i++){
            MaterialesRequisicion requisicion = listaMaterialesRequisicion.get(i);
            modelRequisiciones.addRow(new Object[]{
                requisicion.getNoRequisicion(),
                requisicion.getMaterial(),
                requisicion.getBarrasNecesarias(),
                requisicion.getDescEstado()
            });
        }
            
    }
    
    /**
     * EVENTOS
     */
        private final ActionListener listenerBotones   =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == entregasView.getBtnAgregarRequisiciones())
                    agregarNuevaRequisicion();
        }
        
        private void agregarNuevaRequisicion(){
            agregarNuevasRequisiciones vista = new agregarNuevasRequisiciones(entregasView.getPrincipal(), true);
            AgregarNuevaRequisicionController controller = new AgregarNuevaRequisicionController(vista,
            new AgregarNuevaRequisicionModel());
            vista.setVisible(true);
            vista.addWindowStateListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    llenarTablaRequisiciones();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    llenarTablaRequisiciones();
                }
                
                
            });
        }
    };
    
    
    
    
    
}
