
package Model.CatalogosModel;

import Model.Conexion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class CatalogoProveedoresModel {

    public ArrayList<Proveedor> obtenerListaProveedores() {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        if(c!=null)
            try {
                String query = "select id_proveedor,desc_proveedor,direccion from proveedores;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Proveedor p = new Proveedor();
                        p.setNoProveedor(rs.getInt(1));
                        p.setDescProveedor(rs.getString(2));
                        p.setDirProveedor(rs.getString(3));
                        proveedores.add(p);
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoProveedoresModel, metodo:obtenerListaProveedores "+e.getMessage());
            }
        return proveedores;      
    }

    

    public void guardarModificarProveedor(String descProveedor, String dirProveedor) {
         Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_modificar_proveedor(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, descProveedor);
                cs.setString(2, dirProveedor);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3),"CONFIRMACION",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | SQLException e) {
                 System.err.println("error: paquete:CatalogosModel, class:CatalogoProveedoresModel, metodo:guardarModificarProveedor "+e.getMessage());
            }
               
    }
    
}
