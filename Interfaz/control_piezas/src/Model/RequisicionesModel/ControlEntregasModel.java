
package Model.RequisicionesModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class controlEntregasModel {



    public ArrayList<MaterialesRequisicion> obtenerRequisiciones(){
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM bitacora_requisiciones";
        ArrayList<MaterialesRequisicion> listaMaterialesRequisicion = new ArrayList<>();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        listaMaterialesRequisicion.add(new MaterialesRequisicion(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getString(4)));
                    } while (rs.next());
                        
            } catch (SQLException e) {
                System.err.println("error paquete:RequisicionesModel class:controlEntregasModel method:obtenerRequisiciones "+e.getMessage());
            }
        
        return listaMaterialesRequisicion;             
    }
}
