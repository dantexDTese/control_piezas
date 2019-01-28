package Model.RequisicionesModel;

import Model.AlmacenModel.AlmacenMateriaPrima;
import Model.Conexion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;



public class AsignarMaterialRequisicionModel {

    public ArrayList<AlmacenMateriaPrima> listaLotesMaterial(String descMaterial) {
        ArrayList<AlmacenMateriaPrima> listaLotes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT vem.desc_"
                + "lote,cantidad_total - (SELECT SUM(me.cantidad) "
                + "FROM entradas_materiales AS em INNER JOIN materiales_entregados AS me ON " +
                "em.id_entrada_material = me.id_entrada_material WHERE em.desc_lote = "
                + "vem.desc_lote) AS cantidad,fecha_registro FROM ver_entradas_materiales AS vem " +
                "WHERE desc_material = '"+descMaterial+"' AND cantidad > 0;";
        
        if(c != null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {
                        AlmacenMateriaPrima material = new AlmacenMateriaPrima();
                        material.setDescLote(rs.getString(1));
                        material.setCantidadTotal(rs.getInt(2));
                        material.setFechaRegistro(rs.getString(3));
                        listaLotes.add(material);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, class:AsignarMaterialRequisicionModel, metodo: listaLotesMateriales "+e.getMessage());
            }     
        return listaLotes;
    }

    
    
    public void asignarMaterial(int noOrdenProduccion, String descLote, int cantidadGuardar) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                System.err.println(noOrdenProduccion+" "+descLote+" "+cantidadGuardar);
                String query = "{CALL asignar_material_requisicion(?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noOrdenProduccion);
                cs.setString(2, descLote);
                cs.setInt(3, cantidadGuardar);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(4));
                cs.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, "
                        + "class:AsignarMaterialRequisicionModel, metodo: asignarMaterial "+e.getMessage());
            }
        
        
    }
    
    
    public ArrayList<MaterialEntregado> lotesMaterialesAsignados(int noOrdenPruduccion){
        ArrayList<MaterialEntregado> listaLotes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = " SELECT em.desc_lote,me.cantidad,me.fecha FROM " +
                " (SELECT cantidad,fecha,id_entrada_material FROM materiales_entregados AS me INNER JOIN materiales_orden  " +
                " AS mo ON me.id_material_orden = mo.id_material_orden WHERE mo.id_orden_produccion = " + noOrdenPruduccion +")"+ 
                " AS me INNER JOIN entradas_materiales AS em ON me.id_entrada_material = em.id_entrada_material;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        MaterialEntregado material = new MaterialEntregado();
                        material.setDescLote(rs.getString(1));
                        material.setCantidadAsignada(rs.getInt(2));
                        material.setFecha(rs.getString(3));
                        listaLotes.add(material);
                    } while (rs.next());
                
            } catch (SQLException e) {
                 System.err.println("error: paquete:RequisicionesModel, "
                        + "class:AsignarMaterialRequisicionModel, metodo: lotesMaterialesAsignados "+e.getMessage());
            }
    
        return listaLotes;
    }

    public int obtenerFaltantes(int noOrdenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String sql = "SELECT obtener_barras_faltantes_asignar("+noOrdenProduccion+");";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs.first())
                    return rs.getInt(1);
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, "
                        + "class:AsignarMaterialRequisicionModel, metodo: obtenerFaltantes "+e.getMessage());
            }
        
        return 0;
    }
    
    public class MaterialEntregado extends EntradaMaterial{
        
        int cantidadAsignada;
        String fecha;

        public MaterialEntregado() {
        }

        public int getCantidadAsignada() {
            return cantidadAsignada;
        }

        public void setCantidadAsignada(int cantidadAsignada) {
            this.cantidadAsignada = cantidadAsignada;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
        
    }
    
}
