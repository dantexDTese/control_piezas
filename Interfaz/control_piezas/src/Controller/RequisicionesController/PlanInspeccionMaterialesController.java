
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.EntradaMaterial;
import Model.RequisicionesModel.PlanInspeccionMaterialesModel;
import View.Requisiciones.PlanInspeccionMateriales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import javax.swing.JOptionPane;



public final class PlanInspeccionMaterialesController implements Constructores{

    private final PlanInspeccionMateriales vista;
    private final PlanInspeccionMaterialesModel model;
    private final EntradaMaterial materialSeleccionado;
    
    PlanInspeccionMaterialesController(PlanInspeccionMateriales viewInspeccion, PlanInspeccionMaterialesModel planInspeccionMaterialesModel,EntradaMaterial materialSeleccionado) {
       this.vista = viewInspeccion;
       this.model = planInspeccionMaterialesModel;
       this.materialSeleccionado = materialSeleccionado;
       llenarComponentes();
       asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        
        vista.getLbProveedor().setText(materialSeleccionado.getDescProveedor());
        vista.getLbDisponibilidad().setText(materialSeleccionado.getDescEstado());
        vista.getLbAprobacion().setText(materialSeleccionado.getInspector());
        vista.getLbDescripcion().setText(materialSeleccionado.getDescMaterial());
        vista.getLbOrdenCompra().setText(materialSeleccionado.getOrdenCompra());
        vista.getLbFecha().setText(Estructuras.convertirFechaGuardar(new Date()));
        vista.getLbCantidad().setText(materialSeleccionado.getCantidad()+"");
        vista.getJtbInspeccion().setRowHeight(50);
        vista.getTxtComentarios().setText(materialSeleccionado.getComentarios());
        vista.getTxtFactura().setText(materialSeleccionado.getFactura());
        vista.getTxtNoParte().setText(materialSeleccionado.getNoParte());
        
        Estructuras.modificarAnchoTabla(vista.getJtbInspeccion(), new Integer[]{250,50,50,50,50,50,50});
        
        if(materialSeleccionado.getDescEstado().equals("APROBADA") || materialSeleccionado.getDescEstado().equals("RECHAZADA")){
            vista.getBtnGuardar().setEnabled(false);
            vista.getTxtFactura().setEnabled(false);
            vista.getTxtNoParte().setEnabled(false);
        }
        
    }

    @Override
    public void asignarEventos() {
        vista.getBtnGuardar().addActionListener(listenerBotones);
        vista.getJtbInspeccion().addKeyListener(listenerTabla);
    }
    
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!"".equals(vista.getTxtFactura().getText()) && !"".equals(vista.getTxtNoParte().getText()) && 
                    (vista.getRbtAprobar().isSelected() || vista.getRbtRechazar().isSelected())){
                
                completarCampos();
                model.actualizarInformacion(materialSeleccionado);
                
            }else
                JOptionPane.showMessageDialog(null,"POR FAVOR COMPLETA LOS CAMPOS");
        }
        
        
        private void completarCampos(){
            
            materialSeleccionado.setFactura(vista.getTxtFactura().getText());
            materialSeleccionado.setNoParte(vista.getTxtNoParte().getText());
            materialSeleccionado.setComentarios(vista.getTxtComentarios().getText());
            materialSeleccionado.setDescEstado( (vista.getRbtAprobar().isSelected())? "APROBADA":"RECHAZADA" );
            
        }
        
    };
    
    private final KeyListener listenerTabla = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e); 
                
        }
        
        
        
    
    };
            
    
    
    
    
    
    
}
