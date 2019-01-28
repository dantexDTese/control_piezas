
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
        String query = String.format("SELECT op.id_orden_produccion,lpn.cantidad_planeada,DAY(fecha_planeada) AS dia "
                                    + " ,lpn.fecha_planeada,lpn.desc_maquina,lpn.desc_tipo_proceso " +
                                    "FROM todos_lotes_planeados AS lpn " +
                                    "JOIN ordenes_produccion AS op ON op.id_orden_produccion = lpn.id_orden_produccion " +
                                    "WHERE (fecha_planeada BETWEEN '%s' AND ADDDATE('%s',%d)) " +
                                    "AND desc_maquina = '%s';",fecha,fecha,dias-1,maquina);
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        listOrdenesMes.add(new OrdenPlaneada(rs.getInt(3),rs.getInt(1),rs.getInt(2),rs.getString(6)));
                    }while(rs.next());
                    
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: CalendarioModel method:obtenerOrdenesPlaneadas "+e.getMessage());
            }
        
        return listOrdenesMes;
    }
    
    
    public class OrdenPlaneada{
        
        private final int dia;
        private final int noOrden;
        private final int cantidad;
        private final String descTipoProceso;

        public OrdenPlaneada(int dia,int noOrden, int cantidad,String descTipoProceso) {
            this.noOrden = noOrden;
            this.cantidad = cantidad;
            this.dia = dia;
            this.descTipoProceso = descTipoProceso;
        }

        public String getDescTipoProceso() {
            return descTipoProceso;
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
 