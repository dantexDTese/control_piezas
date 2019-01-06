
package Model.AlmacenModel;

import Model.Conexion;
import Model.Estructuras;
import Model.OrdenTrabajo;
import Model.ordenProduccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;


public class RegistroSalidaModel {
     
    public ArrayList<OrdenTrabajo> listaPedidosPendientes(){
        ArrayList<OrdenTrabajo> listaPedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT id_pedido,id_orden_trabajo,no_orden_compra,nombre_cliente FROM todos_pedidos WHERE desc_estado = 'ABIERTO';";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        listaPedidos.add(new OrdenTrabajo(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4)));
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, class:RegistroSalidaModel, metodo:llistaPedidosPendientes "+e.getMessage());
            }
             
        return listaPedidos;
    }
    
    public ArrayList<OrdeneProduccionParcialidad> listaProductosPendientes(int noOrdenTrabajo){
        ArrayList<OrdeneProduccionParcialidad> listaProductos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT id_orden_produccion,clave_producto,cantidad_cliente, (cantidad_cliente - cantidad_restante_parcialidad(id_orden_produccion)) AS restante " +
                        "FROM todas_ordenes_produccion WHERE id_orden_trabajo = "+noOrdenTrabajo+" AND desc_estado = 'ABIERTO';";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        listaProductos.add(new OrdeneProduccionParcialidad(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, class:RegistroSalidaModel, metodo:llistaProductosPendientes "+e.getMessage());

                
            }
        
        return listaProductos;
    }
    
    public int obtenerCantidadAlmacenada(String claveProducto){
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT total FROM productos_clientes WHERE clave_producto = '"+claveProducto+"';";
        int resultado=0;
        try {
            
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.first())
                resultado = rs.getInt(1);
            
        } catch (SQLException e) {
            
        }
        return resultado;
    }

    public int registrarParcialidadPedido(int ordenTrabajo,Date fechaEntrega) {
        int respuesta = 0;
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null){
            String query = "{ CALL agregar_parcialidad(?,?,?)}";
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, ordenTrabajo);
                cs.setString(2, Estructuras.convertirFechaGuardar(fechaEntrega));
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                respuesta = cs.getInt(3);
                
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, class:RegistroSalidaModel, metodo:registrarParcialidadPedido "+e.getMessage());
            }
            
        }
        return respuesta;
    }

    public int registrarSalidaProducto(String claveProducto, String descCliente, int cantidadRegistrar) {
        int noRegistroSalidaProducto = 0;
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL registrar_salida_producto(?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, claveProducto);
                cs.setString(2, descCliente);
                cs.setInt(3, cantidadRegistrar);
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                noRegistroSalidaProducto = cs.getInt(4);
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, class:RegistroSalidaModel, metodo:registrarSalidaProducto "+e.getMessage());                
            }
        return noRegistroSalidaProducto;
    }

    public void registrarParcialidadEntrega(int noOrdenProduccion, int noRegistroSalida, int noParcialidadPedido) {

        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL registrar_parcialidad_entrega(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noOrdenProduccion);
                cs.setInt(2, noRegistroSalida);
                cs.setInt(3, noParcialidadPedido);
                cs.execute();                
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, class:RegistroSalidaModel, metodo:RegistrarParcialidadEntrega "+e.getMessage());                
            }
        
    }
    
    public class OrdeneProduccionParcialidad extends ordenProduccion{
        
        private final int cantidadRestante;
        
        
        private OrdeneProduccionParcialidad(int noOrdenProduccio, String claveProducto, int cantidadCliente, int cantidadRestante) {
            super(noOrdenProduccio,claveProducto,cantidadCliente);
            this.cantidadRestante = cantidadRestante;
        }

        public int getCantidadRestante() {
            return cantidadRestante;
        } 
    }
    
}
