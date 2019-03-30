
package control_piezas;

import Model.ProcesosProduccion;
import View.Principal;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;


public class Control_piezas {


    public static void main(String[] args) {          
        
        Principal pantallaPrincipal = new Principal();
        pantallaPrincipal.setVisible(true);
        
        pantallaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(pantallaPrincipal.getSesionIniciada().getDescUsuario() != null){
                    if(pantallaPrincipal.getSesionIniciada().getDescUsuario().equals("CALIDAD") 
                            || pantallaPrincipal.getSesionIniciada().getDescUsuario().equals("PRODUCCION") ){
                        if(ProcesosProduccion.listaProcesando.isEmpty()){
                            cerrarAplicacion(pantallaPrincipal);
                        }else JOptionPane.showMessageDialog(null, " AUN HAY ALGUNOS PROCESOS EN EJECUCION, TERMINE LOS PROCESOS "
                                    + " PARA PODER CERRAR LA APLICACION ");
                           }
                    else cerrarAplicacion(pantallaPrincipal);
                }
                else cerrarAplicacion(pantallaPrincipal);
            }
        });
    }
        
    private static void cerrarAplicacion(Principal pantallaPrincipal){
        if(JOptionPane.showConfirmDialog(null, " ¿SEGURO QUE QUIERES SALIR? ","VALIDACION",JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            pantallaPrincipal.dispose();
            System.exit(0);
        }
    }
}
