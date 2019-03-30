package Controller.RequisicionesController;

import Model.Constructores;
import Model.RequisicionesModel.AsignacionMaterialModel;
import Model.RequisicionesModel.EntradaMaterial;
import View.Requisiciones.AsignacionMaterialView;


public class AsignacionMaterialController implements Constructores{

    private final AsignacionMaterialView vista;
    private final AsignacionMaterialModel modelo;
    private final EntradaMaterial loteSeleccionado;
    
    AsignacionMaterialController(AsignacionMaterialView vista,
            AsignacionMaterialModel modelo, EntradaMaterial loteSeleccionado) {
        this.vista = vista;
        this.modelo = modelo;
        this.loteSeleccionado = loteSeleccionado;
    }

    @Override
    public void llenarComponentes() {
        
    }

    @Override
    public void asignarEventos() {
        
    }
    
   
    
}