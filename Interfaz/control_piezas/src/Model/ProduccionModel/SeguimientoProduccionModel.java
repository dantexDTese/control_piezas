
package Model.ProduccionModel;

import Model.Conexion;
import Model.Estructuras;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;



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
        
        String query = " SELECT id_lote_produccion,desc_lote,cantidad_operador,"
                       + " cantidad_administrador,scrap_operador,scrap_administrador,merma,"
                        + " tiempo_muerto,cantidad_registrada,rechazo FROM lotes_produccion AS lpr " +
                        " INNER JOIN  (SELECT lp.id_lote_planeado FROM " +
                        " todos_lotes_planeados AS lp WHERE lp.id_orden_produccion = " + noOrdenProduccion +
                        " AND lp.desc_tipo_proceso = '"+procesoSeleccionado+"' AND lp.desc_estado = 'CERRADO') " +
                        " AS lp ON lp.id_lote_planeado = lpr.id_lote_planeado;";
        
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        
                        LoteProduccion lote = new LoteProduccion();
                        lote.setNoLote(rs.getInt(1));
                        lote.setDescLote(rs.getString(2));
                        lote.setCantidadOperados(rs.getInt(3));
                        lote.setCantidadAdmin(rs.getInt(4));
                        lote.setScrapOperador(rs.getInt(5));
                        lote.setScrapAdmin(rs.getInt(6));
                        lote.setMerma(rs.getFloat(7));
                        lote.setTiempoMuerto(rs.getString(8));
                        lote.setRechazo(rs.getInt(10));
                        listaLotes.add(lote);
                        
                    }while(rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, class:SeguimientoProduccionModel. method:listaLotesProduccion "+e.getMessage());
            }

        return listaLotes;
    }    
    
    public LoteProduccion obtenerOrdenPlaneada(int ordenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        String fechaActual = Estructuras.convertirFechaGuardar(new Date());
        
        String query = "SELECT id_lote_planeado,cantidad_planeada,desc_estado "
                + "FROM todos_lotes_planeados WHERE fecha_planeada = '"+fechaActual+"'"
                + " AND id_orden_produccion = "+ordenProduccion+" AND desc_estado = 'ABIERTO';";
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

    public boolean guardarProduccion(LoteProduccion loteProduccion) {
        
        Connection c = Conexion.getInstance().getConexion();
        
        
        if(c!=null)
            try {
                String query = "{CALL terminar_lote_produccion(?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, loteProduccion.getNoLotePlaneado());
                cs.setString(2, loteProduccion.getDescLote());
                cs.setString(3, loteProduccion.getTiempoMuertoR().toString());
                cs.setString(4, loteProduccion.getDescTurno());
                cs.setInt(5, (int) loteProduccion.getCantidadProducidaR());
                cs.setString(6, loteProduccion.getCodOperador());
                cs.registerOutParameter(7,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(7));
                return true;
            } catch (HeadlessException | SQLException e) {
                System.err.println("error paquete:ProduccionModel , Class:SeguimientoProduccionModel, metodo:"
                        + "guardarProduccion " + e.getMessage());
                
            }
        
        
        return false;
    }

    
    
}
