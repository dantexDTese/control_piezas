
package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SeguimientoOrdenesModel {

    
    public SeguimientoOrdenesModel() {
    
    }
    
    
    public ArrayList obtenerListaOrdenesActivas(){
        ArrayList<OrdenTrabajoActiva> listaOrdenesActivas = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM ordenes_trabajo_activas");
                if(rs.first())
                    do{
                        listaOrdenesActivas.add(new OrdenTrabajoActiva(rs.getString(1),rs.getString(2)));
                    }while(rs.next());
            } catch (Exception e) {
                System.err.println("error: class: SeguimientoOrdenesModel, metodo: obtenerLIstaOrdenesActicas:"+e.getMessage());
            }
        
        return listaOrdenesActivas;
    }
  
    
}
