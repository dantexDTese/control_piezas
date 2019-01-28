
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import Model.LotePlaneado;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PlaneacionModel {
 
    
    public ArrayList<String> listaMaquinas(){
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas");
    }
    
    public ArrayList<LotePlaneado> listaProcedimientoMaquina(String nombreMaquina,int mes,int anio){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<LotePlaneado> lista = new ArrayList<>();
        
        String query="SELECT op.id_orden_trabajo,op.clave_producto,op.cantidad_total,pd.no_orden_compra,op.id_orden_produccion,op.piezas_turno_registro," +
                    " vm.desc_tipo_material,vm.desc_dimencion,vm.clave_forma,op.worker,lp.desc_tipo_proceso,lp.desc_maquina,op.desc_estado,fecha_registro " +
                    " FROM todas_ordenes_produccion AS op INNER JOIN todos_pedidos AS pd ON op.id_orden_trabajo = pd.id_orden_trabajo " +
                    " INNER JOIN ver_materiales AS vm ON op.id_material = vm.id_material " +
                    " INNER JOIN todos_lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion " +
                    " WHERE lp.desc_maquina = '"+nombreMaquina+"' AND(MONTH(fecha_registro) = "+mes+"  AND YEAR(fecha_registro) = "+anio+") "
                    + " GROUP BY op.id_orden_produccion ORDER BY op.id_orden_trabajo; ";
              
        if(c!=null)
            try {
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.first())
                    do {    
                        
                        LotePlaneado lote = new LotePlaneado();
                        lote.setOrdenTrabajo(rs.getInt(1));
                        lote.setCodProducto(rs.getString(2));
                        lote.setCantidadTotal(rs.getInt(3));
                        lote.setNoOrdenCompra(rs.getString(4));
                        lote.setNoOrdenProduccion(rs.getInt(5));
                        lote.setPiezasPorTurno(rs.getInt(6));
                        lote.setDescTipoMaterial(rs.getString(7));
                        lote.setDescDimencion(rs.getString(8));
                        lote.setClaveForma(rs.getString(9));
                        lote.setWorker(rs.getFloat(10));
                        lote.setTipoProceso(rs.getString(11));
                        lote.setDescMaquina(rs.getString(12));
                        lote.setDescEstadoOrdenProduccion(rs.getString(13));
                        
                        lista.add(lote);
                        
                    } while (rs.next());
               c.close();
            } catch (SQLException e) {
                System.err.println("error: class: PlaneacionModel, method:listaProcedimentosMaquina "+e.getMessage());
            }
        
        return lista;
    }
    
    public ProcesoPrincipal obtenerProcesoPrincipal(String descMaquina){
        ProcesoPrincipal principal = null;
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM procesando_producto WHERE desc_maquina = '"+descMaquina+"'"
                + " GROUP BY id_orden_produccion;";
                
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    principal = new ProcesoPrincipal(rs.getInt(2),rs.getString(3),
                            rs.getString(7),rs.getString(9));
                c.close();
            } catch (Exception e) {
                System.err.println("error: class: PlaneacionModel method: obtenerProcesosPrincipal");
            }
        return principal;
    }
    
    
    
    
    
    
}
