
package Controller.RequisicionesController;

import Model.Estructuras;
import Model.RequisicionesModel.AgregarMaterialREquisicionModel;
import Model.RequisicionesModel.ParcialidadMaterial;
import View.Requisiciones.AgregarMaterialRequisicion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class AgregarMaterialRequisicionesController {

    /**
     * ATRIBUTOS
     */
    private final AgregarMaterialRequisicion viewAgregarMaterial;
    private final AgregarMaterialREquisicionModel agregarMaterialREquisicionModel;
    private ParcialidadMaterial parcialidad;
    private boolean operacionCompletada = false;
    
    
    /**
     * CONSTRUCTOR
     */
    
    AgregarMaterialRequisicionesController(AgregarMaterialRequisicion viewAgregarMaterial,
            AgregarMaterialREquisicionModel agregarMaterialREquisicionModel, ParcialidadMaterial parcialidad){   
        
        this.viewAgregarMaterial = viewAgregarMaterial;
        this.agregarMaterialREquisicionModel = agregarMaterialREquisicionModel;
        this.parcialidad = parcialidad;
        this.viewAgregarMaterial.getLbNoPartida().setText(parcialidad.getNoPartida()+1+"");
        this.viewAgregarMaterial.getLbParcialidad().setText(parcialidad.getNoParcialidad()+1+"");
        this.viewAgregarMaterial.getLbMaterial().setText(parcialidad.getMaterial());
        obtenerPrecionUnitario();
        
        this.viewAgregarMaterial.getSprCantidad().addChangeListener(changeListenerPrecioTotal);
        this.viewAgregarMaterial.getBtnGuardar().addActionListener(actionListenerBotones);
    }
    
    /**
     * EVENTOS
     */
    private final ChangeListener changeListenerPrecioTotal = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            parcialidad.setCantidad(Integer.parseInt(viewAgregarMaterial.getSprCantidad().getValue().toString()));
            
            if(Integer.parseInt(viewAgregarMaterial.getSprCantidad().getValue().toString())>0){
                parcialidad.setPrecioTotal((float)parcialidad.getCantidad()*parcialidad.getPrecioUnitario());
                viewAgregarMaterial.getLbPrecioTotal().setText(parcialidad.getPrecioTotal()+"");
            }else
                viewAgregarMaterial.getSprCantidad().setValue(0);
                
        }
    };

    private final ActionListener actionListenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == viewAgregarMaterial.getBtnGuardar())
                guardarParcialidad();
        }

        private void guardarParcialidad(){
            agregarCamposFaltantes();
            if(validaParcialidad(parcialidad)){                
                JOptionPane.showMessageDialog(null,"el material se ha agregara a la lista de solicitud");
                operacionCompletada=true;
                viewAgregarMaterial.dispose();
            }else
                JOptionPane.showMessageDialog(null,"complete los campos por favor");
        }


        private boolean validaParcialidad(ParcialidadMaterial parcialidad) {
            if(parcialidad.getCantidad()>0 && parcialidad.getPrecioTotal()>0
                    && !"".equals(parcialidad.getFechaSolicitadaParcialidadMaterial()) &&
                    !"".equals(parcialidad.getCuentaCargo()) && !"".equals(parcialidad.getUnidad()))
                return true;
            else return false;
        }

        private void agregarCamposFaltantes() {
            if(viewAgregarMaterial.getJdcFechaSolicitada().getDate()!=null)
                parcialidad.setFechaSolicitadaParcialidadMaterial(Estructuras.convertirFecha(viewAgregarMaterial.getJdcFechaSolicitada().getDate()));
            
            parcialidad.setCuentaCargo(viewAgregarMaterial.getTxtCuentaCargo().getText());
            parcialidad.setUnidad(viewAgregarMaterial.getTxtUnidad().getText());
        }
     };
    
    /**
     * METODOS
     */
    
    private void obtenerPrecionUnitario() {
        
        parcialidad.setPrecioUnitario(
                agregarMaterialREquisicionModel.obtenerPrecionUnitario(
                parcialidad.getMaterial(),parcialidad.getProveedor()));        
        
        viewAgregarMaterial.getLbPrecioUnitario().setText(parcialidad.getPrecioUnitario()+"");
        
    }
    
    
    /**
     * 
     * PROPIEDADES
     * @return 
     */
    public ParcialidadMaterial getParcialidad() {
        return parcialidad;
    }

    public boolean isOperacionCompletada() {
        return operacionCompletada;
    }
    
}
