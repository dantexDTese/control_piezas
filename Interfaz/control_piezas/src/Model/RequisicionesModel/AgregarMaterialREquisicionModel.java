
package Model.RequisicionesModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AgregarMaterialREquisicionModel {

    
    public float obtenerPrecionUnitario(String material, String proveedor) {
        float precioUnitario = 0;
        
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT precio_unitario FROM materiales_proveedor AS mp " +
                        "WHERE mp.id_material = (SELECT id_material FROM materiales WHERE desc_material = '"+material+"') " +
                        "AND mp.id_proveedor = (SELECT id_proveedor FROM proveedores AS pr WHERE pr.desc_proveedor = '"+proveedor+"');";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    precioUnitario = rs.getFloat(1);
            } catch (SQLException e) {
                System.err.println("error: Class:AgregarMaterialRequisicionModel method:obtenerPrecioUnitario "+e.getMessage());
            }
        
        return precioUnitario;
    }

    
}
