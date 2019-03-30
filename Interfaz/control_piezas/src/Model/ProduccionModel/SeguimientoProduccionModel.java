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

    public final int APROBAR_LOTE = 1;
    
    public final int RECHAZAR_LOTE = 2;
    
    public ArrayList<String> obtenerProcesosProduccion(int noOrdenProduccion) {
        ArrayList<String> listaProcesosProduccion = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = " SELECT tp.desc_tipo_proceso FROM tipos_proceso AS tp " +
                       " INNER JOIN lotes_planeados AS lp ON lp.id_tipo_proceso = tp.id_tipo_proceso " +
                       " WHERE lp.id_orden_produccion = 1 GROUP BY tp.id_tipo_proceso; ";
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
                        + " tiempo_muerto,cantidad_registrada,rechazo,fecha_trabajo,es.desc_estado "
                        + " FROM lotes_produccion AS lpr " +
                        " INNER JOIN  (SELECT lp.id_lote_planeado FROM " +
                        " todos_lotes_planeados AS lp WHERE lp.id_orden_produccion = " + noOrdenProduccion +
                        " AND lp.desc_tipo_proceso = '"+procesoSeleccionado+"') " +
                        " AS lp ON lp.id_lote_planeado = lpr.id_lote_planeado "
                        + " INNER JOIN estados AS es ON es.id_estado = lpr.id_estado;";
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
                        lote.setFechaTrabajo(rs.getString(11));
                        lote.setDescEstado(rs.getString(12));
                        listaLotes.add(lote);
                    }while(rs.next());
            } catch (SQLException e){
                System.err.println("error: paquete:ProduccionModel,"
                        + " class:SeguimientoProduccionModel, method:listaLotesProduccion "+e.getMessage());
            }
        return listaLotes;
    }
    
    public LoteProduccion obtenerOrdenPlaneada(int ordenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        String fechaActual = Estructuras.convertirFechaGuardar(new Date());
        
        String query = " SELECT id_lote_planeado,cantidad_planeada,desc_estado "
                + " FROM todos_lotes_planeados WHERE fecha_planeada = '2019-03-18' "
                + " AND id_orden_produccion = "+ordenProduccion+" AND desc_estado = 'ABIERTO'; ";
        
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

    public LoteProduccion obtenerLoteProducido(String descLote) {
        Connection c = Conexion.getInstance().getConexion();
        LoteProduccion loteProducido=null;
        if(c!=null)
            try {
                String query = "SELECT lpr.rechazo,lpr.desc_lote,op.clave_producto,lpr.barras_utilizadas,"
                        + "lpr.merma,lpr.cantidad_administrador,lpr.scrap_ajustable,lpr.scrap_administrador,"
                        + "lp.desc_tipo_proceso,cantidad_rechazo_liberado,cantidad_operador,scrap_operador "
                        + ",lp.cantidad_planeada,lpr.cantidad_registrada,lpr.tiempo_muerto,es.desc_estado  "
                        + " FROM todos_lotes_planeados AS lp " +
                          " INNER JOIN lotes_produccion AS lpr ON lp.id_lote_planeado = lpr.id_lote_planeado " +
                          " INNER JOIN todas_ordenes_produccion AS op ON op.id_orden_produccion = lp.id_orden_produccion"
                        + " INNER JOIN estados AS es ON es.id_estado = lpr.id_estado " +
                          " WHERE lpr.desc_lote = '"+descLote+"';";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first()){                        
                        loteProducido = new LoteProduccion();
                        loteProducido.setRechazo(rs.getInt(1));
                        loteProducido.setDescLote(rs.getString(2));
                        loteProducido.setCodProducto(rs.getString(3));
                        loteProducido.setBarrasUtilizadas(rs.getFloat(4));
                        loteProducido.setMerma(rs.getFloat(5));
                        loteProducido.setCantidadAdmin(rs.getInt(6));
                        loteProducido.setScrapAjustable(rs.getInt(7));
                        loteProducido.setScrapAdmin(rs.getInt(8));
                        loteProducido.setTipoProceso(rs.getString(9));
                        loteProducido.setCantidadRechazoLiberado(rs.getInt(10));
                        loteProducido.setCantidadOperados(rs.getInt(11));
                        loteProducido.setScrapOperador(rs.getInt(12));
                        loteProducido.setCantidadPlaneada(rs.getInt(13));
                        loteProducido.setCantidadProducidaR(rs.getInt(14));
                        loteProducido.setTiempoMuerto(rs.getString(15));
                        loteProducido.setDescEstado(rs.getString(16));
                    }
            } catch (SQLException e) {
                System.err.println("error: Paquete:ProduccionModel, Class:"
                        + "SeguimientoProduccionModel, metodo:obtenerLotePlaneado "+e.getMessage());
            }
        return loteProducido;
    }

    public void terminarLoteProduccion(String descLote, int estado){
        Connection c = Conexion.getInstance().getConexion();
        String es = (estado == APROBAR_LOTE)? "APROBADA":"RECHAZADA";
        
        if(c!=null)
            try {
                String query = "{CALL evaluacion_lote(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, descLote);
                cs.setString(2, es);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null,cs.getString(3));
            } catch (SQLException e) {
                System.err.println("error: paquete:produccionModel, "
                        + "Class:SeguimientoProduccionModel, Metodo:terminarLoteProduccion "+
                        e.getMessage());
            }
        
    }
    
    
}
