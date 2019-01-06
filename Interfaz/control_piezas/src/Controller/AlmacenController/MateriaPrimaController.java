
package Controller.AlmacenController;

import Model.AlmacenModel.MateriaPrimaModel;
import Model.Constructores;
import View.almacenView.MateriaPrimaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author cesar
 */
public final class MateriaPrimaController implements Constructores{

    
    private MateriaPrimaView vista;
    private MateriaPrimaModel modelo;
    
    public MateriaPrimaController(MateriaPrimaView mPrimaView, MateriaPrimaModel mPrimaModel) {
        
        this.vista = mPrimaView;
        this.modelo = mPrimaModel;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        
    }

    @Override
    public void asignarEventos() {
        
    }
    
    
    
    
    
}
