
package Model.RequisicionesModel;

import Model.Conexion;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AgregarRequisicionesModel {
 
    
    public ArrayList<Pedido> listaOrdenesPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select id_orden_trabajo,no_orden_compra "
                + "from requisicion_ordenes WHERE desc_estados = 'ABIERTO' "
                + "GROUP BY id_orden_trabajo;";        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                       pedidos.add(new Pedido(rs.getString(2),rs.getInt(1)));
                    }while(rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error class:AgregarRequisicionesModel method:listaOrdenesPendientes "+e.getMessage());
            }
        return pedidos;       
    }
    
    
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(int noOrdenTrabajo){
        ArrayList<ProductosPendientes> productosPendientes = new ArrayList<>();
        String query = "select clave_producto,cantidad_total,desc_material FROM"
                        + " requisicion_ordenes AS ro WHERE ro.id_orden_trabajo = "+noOrdenTrabajo+";";
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        productosPendientes.add(new ProductosPendientes(rs.getString(1),rs.getInt(2),rs.getString(3))); 
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: AgregarRequisicionesModel method:listaProductosPendientes "+e.getMessage());
                
            }
        return productosPendientes;
    }  
    
    public ArrayList<MaterialesRequisicion> listaMaterialesRequeridos(int noOrdenTrabajo){
        ArrayList<MaterialesRequisicion> materialesRequeridos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select SUM(barras_necesarias) AS barras_necesarias ,desc_material"
                + " FROM requisicion_ordenes AS ro WHERE ro.id_orden_trabajo = "+noOrdenTrabajo+""
                + " GROUP BY desc_material;";
        if(c!=null)
            
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        materialesRequeridos.add(new MaterialesRequisicion(rs.getInt(1),rs.getString(2)));
                    } while (rs.next());

              c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AgregarRequisicionesModel method:listaMaterialesRequeridos "+e.getMessage());            
            }
        
        return materialesRequeridos;
    }
    
    public ArrayList<Proveedores> listaProveedores(){
        ArrayList<Proveedores> proveedores = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM proveedores";
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        proveedores.add(new Proveedores(rs.getInt(1),rs.getString(2),rs.getString(3)));
                    } while (rs.next());
                    c.close();
            } catch (SQLException e) {
                System.err.println("error: class:AgregarRequisicionesModel method: listaProveedores "+e.getMessage());
            }
                
            
            return proveedores;
    }
    
    public final class ParcialidadMaterial extends  MaterialesRequisicion{
        private int noPartida;
        //private final String material;
        private int noParcialidad;
        private String fechaSolicitadaParcialidadMaterial;
        private String cuentaCargo;
        private String unidad;
        private int cantidad;
        private int precioUnitario;
        private int precioTotal;

        public ParcialidadMaterial(int noPartida,int noParcialidad,String material) {
            super(material);
            this.noParcialidad = noParcialidad;
            this.noPartida = noPartida;
        }
        
        public int getNoPartida() {
            return noPartida;
        }

        public int getNoParcialidad() {
            return noParcialidad;
        }

        public String getFechaSolicitadaParcialidadMaterial() {
            return fechaSolicitadaParcialidadMaterial;
        }

        public String getCuentaCargo() {
            return cuentaCargo;
        }

        public String getUnidad() {
            return unidad;
        }

        public int getCantidad() {
            return cantidad;
        }

        public int getPrecioUnitario() {
            return precioUnitario;
        }

        public int getPrecioTotal() {
            return precioTotal;
        }
    }
    
        
    public class MaterialesRequisicion{
        private final String material;
        private int barrasNecesarias;

        public MaterialesRequisicion(int barrasNecesarias,String material) {
            this.material = material;
            this.barrasNecesarias = barrasNecesarias;
        }

        public MaterialesRequisicion(String material) {
            this.material = material;
        }
        
        public String getMaterial() {
            return material;
        }

        public int getBarrasNecesarias() {
            return barrasNecesarias;
        }
              
    }
    
    
}
