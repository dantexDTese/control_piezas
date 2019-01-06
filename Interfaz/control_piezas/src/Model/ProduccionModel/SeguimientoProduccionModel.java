
package Model.ProduccionModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;



public class SeguimientoProduccionModel {

    
    public ArrayList<String> obtenerProcesosProduccion(int noOrdenProduccion) {
        ArrayList<String> listaProcesosProduccion = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT desc_tipo_proceso FROM tipos_proceso AS pc " +
                        "WHERE pc.id_tipo_proceso = (SELECT id_tipo_proceso FROM lotes_planeados "
                        + "WHERE id_orden_produccion = "+noOrdenProduccion+" GROUP BY id_tipo_proceso);";
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        listaProcesosProduccion.add(rs.getString(1));
                    }while(rs.next());
                 
            } catch (SQLException e) {
                System.err.println("error: paquete:produccionModel, class:SeguimientoProduccionModel, method:obtenerProocesosProduccion "+e.getMessage());
            }
  
        return listaProcesosProduccion;
    }
    
    public ArrayList<LoteProduccion> listaLotesProduccion(int noOrdenProduccion,String procesoSeleccionado){
        ArrayList<LoteProduccion> listaLotes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM lotes_produccion AS lpr "
                + "WHERE lpr.id_lote_planeado = (SELECT id_lote_planeado FROM "
                + "lotes_planeados AS lp WHERE lp.id_orden_produccion = " + noOrdenProduccion +
                " AND lp.id_tipo_proceso = (SELECT id_tipo_proceso "
                + "FROM tipos_proceso WHERE desc_tipo_proceso = '"+procesoSeleccionado+"'));";
        
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        listaLotes.add(new LoteProduccion(rs.getInt(1),rs.getString(2),
                                rs.getInt(3), rs.getInt(4), rs.getFloat(5),rs.getString(6),
                                rs.getInt(7),rs.getInt(8), rs.getInt(9)));
                    }while(rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, class:SeguimientoProduccionModel. method:listaLotesProduccion "+e.getMessage());
            }

        return listaLotes;
    }    
    
    public LoteProduccion obtenerOrdenPlaneada(int ordenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        String fechaActual = Estructuras.convertirFechaGuardar(new Date());
        
        String query = "SELECT id_lote_planeado,cantidad_planeada,desc_estado FROM todos_lotes_planeados WHERE fecha_planeada = '2018-12-31'"
                + " AND id_orden_produccion = "+ordenProduccion+";";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    return new LoteProduccion(rs.getInt(1), rs.getInt(2), rs.getString(3),ordenProduccion);
                
            } catch (SQLException e) {
                System.err.println("error paquete:ProduccionModel , Class:SeguimientoProduccionModel, metodo:obtenerOrdenPlaneada " + e.getMessage());
            }
        return null;
    }
    
}
