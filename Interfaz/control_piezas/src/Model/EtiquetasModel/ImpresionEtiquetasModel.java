
package Model.EtiquetasModel;

import Model.Conexion;
import Model.Estructuras;
import Model.Pedido;
import Model.ProduccionModel.LoteProduccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class ImpresionEtiquetasModel {
    
    public ArrayList<Pedido> pedidosPendientes(){
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                String query = "SELECT id_pedido,no_orden_compra,nombre_cliente FROM todos_pedidos WHERE desc_estado = 'ABIERTO';";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Pedido ped = new Pedido();
                        ped.setNoPedido(rs.getInt(1));
                        ped.setNoOrdenCompra(rs.getString(2));
                        ped.setDescCliente(rs.getString(3));
                        listaPedidos.add(ped);
                    } while (rs.next());
                    
                
            } catch (SQLException e) {
                System.err.println("error: paquete:EtiquetasModel, class:ImpresionEtiquetasModel, metodo:pedidosPendientes "+e.getMessage());
            }
        
        
        return listaPedidos;
    }
    
    public ArrayList<LoteProduccion> listaLotesProduccion(String noOrdenCompra,
            String claveProducto){
        ArrayList<LoteProduccion> listaLotes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                String query =  " SELECT lp.desc_lote, " +
                        " (SELECT SUM(cantidad_administrador) FROM lotes_produccion " +
                        " WHERE id_lote_planeado = tlp.id_lote_planeado) AS cantidad_producida " +
                        " ,tlp.desc_maquina,op.desc_producto,pd.nombre_cliente,op.clave_producto,pd.no_orden_compra " +
                        " FROM lotes_produccion AS lp " +
                        " INNER JOIN todos_lotes_planeados AS tlp ON tlp.id_lote_planeado = lp.id_lote_planeado " +
                        " INNER JOIN todas_ordenes_produccion AS op ON op.id_orden_produccion = tlp.id_orden_produccion " +
                        " INNER JOIN todos_pedidos AS pd ON pd.id_orden_trabajo = op.id_orden_trabajo " +
                        " WHERE tlp.desc_tipo_proceso = 'Maquinado' AND tlp.desc_estado = 'CERRADO'" +
                        " AND pd.no_orden_compra = '"+noOrdenCompra+"' AND op.clave_producto = '"+claveProducto+"' "+ 
                        " AND cantidad_administrador IS NOT NULL " +
                        " GROUP BY tlp.id_lote_planeado; ";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                int numLote = 1;
                if(rs.first())
                    do {
                        LoteProduccion lote = new LoteProduccion();
                        lote.setNumLote(numLote);
                        lote.setDescLote(rs.getString(1));
                        lote.setCantidadAdmin(rs.getInt(2));
                        lote.setDescMaquina(rs.getString(3));
                        lote.setDescProducto(rs.getString(4));
                        lote.setDescCliente(rs.getString(5));
                        lote.setCodProducto(rs.getString(6));
                        lote.setNoOrdenCompra(noOrdenCompra);
                        listaLotes.add(lote);
                        numLote++;
                    } while (rs.next());
                        
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, Class:ImpresionEtiquetasModel,"
                        + " metodo:listaLotesProduccion "+e.getMessage());
            }
        
        
        
        return listaLotes;
    }
    
    public JComboBox listaProductos(JComboBox combo,String codOrdenCompra){
        if(combo.getItemCount() > 0 )
            combo.removeAllItems();
        
        return Estructuras.llenaCombo(combo, "SELECT clave_producto FROM todos_pedidos AS pd " +
                                        "INNER JOIN todas_ordenes_produccion AS op ON " +
                                        "pd.id_orden_trabajo = op.id_orden_trabajo "
                                      + "WHERE no_orden_compra = '"+codOrdenCompra+"';");
    }

    public int guardarEtiqueta(String folio, int piezasTotal, int piezasBolsa, String codigoEtiqueta) {
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                String query = "{CALL guardar_etiqueta(?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, codigoEtiqueta);
                cs.setString(2, folio);
                cs.setInt(3, piezasBolsa);
                cs.setInt(4, piezasTotal);
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();
                return cs.getInt(5);
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, Class:ImpresionEtiquetasModel,"
                        + " metodo:guardarEtiqueta "+e.getMessage());
            }
        
        return 0;
    }
    
    public void agregarEtiquetaLoteProduccion(int noEtiqueta,String descLote){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
               
                String query = "{CALL agregar_etiqueta_lote_produccion(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, descLote);
                cs.setInt(2, noEtiqueta);
                cs.execute(); 
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, Class:ImpresionEtiquetasModel,"
                        + " metodo:agregarEtiquetaLoteProduccion "+e.getMessage());
            }
        
    }
    
    public void registrarEtiquetaLote(int noEtiqueta,int numEtiqueta,int cantidad){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL registrar_etiqueta_lote(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noEtiqueta);
                cs.setInt(2, numEtiqueta);
                cs.setInt(3, cantidad); 
                cs.execute();
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, Class:ImpresionEtiquetasModel,"
                        + " metodo:registrarEtiquetaLote "+e.getMessage());
            }
    }
    
}
