
package Model.PedidosModel;

import Model.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;


public class CalendarioModel {

    
    public OrdenPlaneada obtenerOrdenPlaneada(String fecha,String maquina){
        OrdenPlaneada orden = null;
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call obtener_orden_planeada(?,?,?,?)}";
        
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1,fecha);
                cs.setString(2,maquina);
                cs.registerOutParameter(3,Types.INTEGER);
                cs.registerOutParameter(4,Types.INTEGER);
                cs.execute();
                orden = new OrdenPlaneada(cs.getInt(3),cs.getInt(4));
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: CalendarioModel method:obtenerOrdenPlaneada "+e.getMessage());
            }
        
        return orden;
    }
    
    
    public class OrdenPlaneada{
    
        private final int noOrden;
        private final int cantidad;

        public OrdenPlaneada(int noOrden, int cantidad) {
            this.noOrden = noOrden;
            this.cantidad = cantidad;
        }

        public int getNoOrden() {
            return noOrden;
        }

        public int getCantidad() {
            return cantidad;
        }
        
        
    }
    
}
 