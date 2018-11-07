
package Model.PedidosModel;

import Model.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;


public class CalendarioModel {

    
    public ArrayList<OrdenPlaneada> obtenerOrdenesPlaneadas(int dias,String fecha,String maquina){
        
        ArrayList<OrdenPlaneada>  listOrdenesMes = new ArrayList<>();
        
        Connection c = Conexion.getInstance().getConexion();
        String query = String.format("SELECT dia,id_orden_produccion,cantidad_planeada "
                + "FROM fechas_planeadas WHERE (fecha_planeada BETWEEN '%s' AND ADDDATE('%s',%d)) " +
                        " AND desc_maquina = '%s';",fecha,fecha,dias-1,maquina);
        if(c!=null)
            try {
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        listOrdenesMes.add(new OrdenPlaneada(rs.getInt(1),rs.getInt(2),rs.getInt(3)));
                    }while(rs.next());
                    
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: CalendarioModel method:obtenerOrdenPlaneada "+e.getMessage());
            }
        
        return listOrdenesMes;
    }
    
    
    public class OrdenPlaneada{
        
        private final int dia;
        private final int noOrden;
        private final int cantidad;

        public OrdenPlaneada(int dia,int noOrden, int cantidad) {
            this.noOrden = noOrden;
            this.cantidad = cantidad;
            this.dia = dia;
        }

        public int getDia(){
            return dia;
        }
        
        public int getNoOrden() {
            return noOrden;
        }

        public int getCantidad() {
            return cantidad;
        }
        
        
    }
    
}
 