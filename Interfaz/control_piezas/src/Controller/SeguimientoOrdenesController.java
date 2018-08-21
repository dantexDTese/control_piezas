
package Controller;

import Model.OrdenTrabajoActiva;
import Model.SeguimientoOrdenesModel;
import View.PantillaOrdenesCompras;
import View.SeguimientoOrdenes;
import View.plantillaOpcionLista;
import ds.desktop.notify.DesktopNotify;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SeguimientoOrdenesController implements ActionListener{

    
    private SeguimientoOrdenes vista;
    private SeguimientoOrdenesModel modelo;
    private SeguimientoProductosController productosController;
    
    public SeguimientoOrdenesController(SeguimientoOrdenes vista,
            SeguimientoOrdenesModel modelo,SeguimientoProductosController productosController) {
        
        this.vista = vista;
        this.modelo = modelo;
        this.vista.setListaOrdenesTrabajo(llenaLista(this.modelo.obtenerListaOrdenesActivas()
                ,this.vista.getListaOrdenesTrabajo()));
        this.productosController = productosController;
        
        
    }
       
    
    
     private JPanel llenaLista(ArrayList<OrdenTrabajoActiva> lista,JPanel panelContenedor){
        
        GridLayout gridLayout = new GridLayout(lista.size(), 1);
        panelContenedor.setLayout(gridLayout);               
        for(int i = 0;i<lista.size();i++){
            PantillaOrdenesCompras opcion = new PantillaOrdenesCompras(lista.get(i));
            opcion.getVerOrdenesProduccion().addActionListener(this);
            panelContenedor.add(opcion);
            
        }
        return panelContenedor;
    }

     
    @Override
    public void actionPerformed(ActionEvent e) {
        

    }
    
}
