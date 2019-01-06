package Model.RequisicionesModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class RegistroEntradaMaterialesModel {

    
    public ArrayList<EntradaMaterial> listaRegistroEntradaMateriales(){
        ArrayList<EntradaMaterial> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM registro_entrada_materiales;";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        lista.add(new EntradaMaterial(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                                rs.getInt(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                                rs.getString(12),rs.getString(13)));
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: RequisicionModel, class:RegistroEntradaMaterialesModel, metodo:listaRegistroEntradaMateriales "+e.getMessage());
            }
        
        return lista;
    }
    
    
}
