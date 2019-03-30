
package Model.EtiquetasModel;

import Model.Conexion;
import Model.Pedido;
import Model.ordenProduccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SeguimientoLotesModel {
    
    public ordenProduccion pedidoLote(String codigoEtiqueta){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = " SELECT pd.id_pedido,pd.desc_contacto,pd.fecha_recepcion,pd.no_orden_compra,"
                        + " pd.fecha_confirmacion_entrega,pd.nombre_cliente,clave_producto,desc_producto " +
                          " FROM todos_pedidos AS pd" +
                          " INNER JOIN (SELECT id_orden_trabajo,id_orden_produccion,clave_producto,desc_producto FROM todas_ordenes_produccion) "
                        + " AS op ON pd.id_orden_trabajo = op.id_orden_trabajo " +
                          " INNER JOIN (SELECT id_lote_planeado,id_orden_produccion FROM lotes_planeados) AS lp "
                        + " ON lp.id_orden_produccion = op.id_orden_produccion " +
                          " INNER JOIN (SELECT id_lote_produccion,id_lote_planeado,id_etiqueta "
                        + " FROM lotes_produccion) AS lpr ON lpr.id_lote_planeado = lp.id_lote_planeado " +
                          " INNER JOIN (SELECT id_etiqueta FROM etiquetas WHERE codigo_etiqueta = '"+codigoEtiqueta+"') "
                        + " AS et ON et.id_etiqueta = lpr.id_etiqueta;";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first()){   
                    
                        ordenProduccion op = new ordenProduccion();
                        op.setNoPedido(rs.getInt(1));
                        op.setDescContacto(rs.getString(2));
                        op.setFechaRecepcion(rs.getString(3));
                        op.setNoOrdenCompra(rs.getString(4));
                        op.setFechaConfirmacionEntrega(rs.getString(5));
                        op.setDescCliente(rs.getString(6));
                        op.setCodProducto(rs.getString(7));
                        op.setDescProducto(rs.getString(8));
                        
                        return op;
                }
                
            } catch (SQLException e) {
                 System.err.println("error: paquete:EtiquetasModel, Class:SeguimientoLotesModel, metodo:pedidoLote "+e.getMessage());
            }
        
        return null;
    }
    
    public Etiqueta etiquetaLote(String codigoEtiqueta){
        
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                
                String query = "select id_etiqueta,codigo_etiqueta,fecha,folio,piezas_por_bolsa,"
                        + "piezas_totales from etiquetas WHERE codigo_etiqueta = '"+codigoEtiqueta+"';";
                
                Statement st = c.createStatement();
                
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first()){
                    Etiqueta etiqueta = new Etiqueta(rs.getInt(1));
                    etiqueta.setCodigoEtiqueta(rs.getString(2));
                    etiqueta.setFecha(rs.getString(3));
                    etiqueta.setFolio(rs.getString(4));
                    etiqueta.setPiezasPorBolsa(rs.getInt(5));
                    etiqueta.setPiezasTotales(rs.getInt(6));
                    return etiqueta;
                }
                
            } catch (SQLException e) {
                System.err.println("error: paquete:EtiquetasModel, "
                        + "class:SeguimientoLotesModel, metodo:etiquetaLote "+e.getMessage());
            }
    
        return null;
    }
    
    
    public ArrayList<EtiquetaLote> listaEtiquetasLote(String codigoEtiqueta){
        ArrayList<EtiquetaLote> listaEtiquetas = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        EtiquetaLote etLote;
        if(c != null)
            try {
                String query = " SELECT cantidad FROM etiquetas_lotes AS etl " +
                               " INNER JOIN etiquetas AS et ON et.id_etiqueta = etl.id_etiqueta " +
                               " WHERE et.codigo_etiqueta = '"+codigoEtiqueta+"'; ";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                     etLote = new EtiquetaLote();
                     etLote.setCantidad(rs.getInt(1));
                     listaEtiquetas.add(etLote);
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println( "error: paquete:EtiquetasModel, Class:SeguimientoLotesModel, "
                        + " metodo:listaEtiquetasLote " + e.getMessage() );
            }
        
        return listaEtiquetas;
    }
}
