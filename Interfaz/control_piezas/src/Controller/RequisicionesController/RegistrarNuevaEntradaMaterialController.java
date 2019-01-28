
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.RegistrarNuevaEntradaMaterialModel;
import Model.RequisicionesModel.EntradaMaterial;
import View.Requisiciones.RegistrarNuevaEntradaMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public final class RegistrarNuevaEntradaMaterialController implements Constructores{

    private final RegistrarNuevaEntradaMaterial vista;
    private final RegistrarNuevaEntradaMaterialModel model;
    
    RegistrarNuevaEntradaMaterialController(RegistrarNuevaEntradaMaterial viewNuevaEntrada,
            RegistrarNuevaEntradaMaterialModel registroNuevaEntradaMaterialModel) {
        this.vista = viewNuevaEntrada;
        this.model = registroNuevaEntradaMaterialModel;
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        vista.setCbxMaterial(model.llenarCombo(vista.getCbxMaterial(),this.model.LISTA_MATERIALES));
        vista.setCbxProveedores(model.llenarCombo(vista.getCbxProveedores(), this.model.LISTA_PROVEEDORES));
        vista.getLbFecha().setText(Estructuras.obtenerFechaActual());   
    }

    @Override
    public void asignarEventos() {
        vista.getBtnGuardar().addActionListener(listenerGuardar);
    }
    
    
    //VARIABLES DE EVENTOS 
    
    private final ActionListener listenerGuardar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO DE REGISTAR ESTE MATERIAL?","REGISTRO MATERIAL",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(respuesta == JOptionPane.YES_OPTION && validarCampos()){
            
                
                EntradaMaterial material = new EntradaMaterial();
                material.setDescMaterial(vista.getCbxMaterial().getSelectedItem().toString());
                material.setDescProveedor(vista.getCbxProveedores().getSelectedItem().toString());
                material.setCantidad(Integer.parseInt(vista.getSprCantidad().getValue().toString()));
                material.setCodigo(vista.getTxtCodigo().getText());
                material.setCertificado(vista.getTxtCertificado().getText());
                material.setOrdenCompra(vista.getTxtOrdenCompra().getText());
                material.setInspector(vista.getTxtInspector().getText());
            
                model.agregarEntradaMaterial(material);
                
                vista.dispose();
            }else
                JOptionPane.showMessageDialog(null, "LLENA LOS CAMPOS CORRECTAMENTE POR FAVOR");
                
        }
        
        private boolean validarCampos(){
            if("".equals(vista.getTxtCertificado().getText()) || "".equals(vista.getTxtCodigo().getText()) || "".equals(vista.getTxtInspector().getText()) || "".equals(vista.getTxtOrdenCompra().getText())
                    || Integer.parseInt(vista.getSprCantidad().getValue().toString()) < 1)
               return false;
            else return true;
        }
        
    };

    

    
    
    
}
