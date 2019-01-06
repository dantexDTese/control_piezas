
package Model.RequisicionesModel;

import Model.Conexion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;


public class PlanInspeccionMaterialesModel {

    /**
     * 
     * @param materialSeleccionado
     * 
     */
    
    public void actualizarInformacion(EntradaMaterial materialSeleccionado) {
          
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL actualizar_entrada_material(?,?,?,?,?,?)}";
        try {
            
            CallableStatement cs = c.prepareCall(query);
            
            cs.setInt(1, materialSeleccionado.getNoEntradaMaterial());
            cs.setString(2, materialSeleccionado.getNoParte());
            cs.setString(3, materialSeleccionado.getComentarios());
            cs.setString(4, materialSeleccionado.getDescEstado());
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            JOptionPane.showMessageDialog(null, cs.getString(5));
            
        } catch (HeadlessException | SQLException e) {
            System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:ActualizarInformacion " + e.getMessage());
        }
          
    }
    
}
