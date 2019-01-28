
package Model.RequisicionesModel;

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


public class ControlEntregasModel {


    public ArrayList<MaterialesRequisicion> obtenerRequisiciones(int anio,int mes){
        Connection c = Conexion.getInstance().getConexion();
        String query =  "SELECT ms.id_requisicion,mt.desc_tipo_material,mt.desc_dimencion,mt.clave_forma,"
                        + "SUM(ms.cantidad),desc_estado " +
                        "FROM materiales_solicitados AS ms JOIN ver_materiales AS mt ON ms.id_material = mt.id_material "
                        + "INNER JOIN requisiciones AS rq ON ms.id_requisicion = rq.id_requisicion " +
                        "JOIN estados AS es ON es.id_estado = ms.id_estado "
                        + "WHERE MONTH(rq.fecha_creacion) = "+mes+"  AND YEAR(rq.fecha_creacion) = "+anio+" "
                        + "GROUP BY id_requisicion,desc_tipo_material,desc_dimencion,clave_forma " +
                          "ORDER BY id_requisicion;";
        
        ArrayList<MaterialesRequisicion> listaMaterialesRequisicion = new ArrayList<>();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        MaterialesRequisicion materialRequisicion = new MaterialesRequisicion();
                        materialRequisicion.setNoRequisicion(rs.getInt(1));
                        materialRequisicion.setDescTipoMaterial(rs.getString(2));
                        materialRequisicion.setDescDimencion(rs.getString(3));
                        materialRequisicion.setClaveForma(rs.getString(4));
                        materialRequisicion.setBarrasNecesarias(rs.getInt(5));
                        materialRequisicion.setDescEstado(rs.getString(6));
                        listaMaterialesRequisicion.add(materialRequisicion);
                    } while (rs.next());
                        
            } catch (SQLException e) {
                System.err.println("error paquete:RequisicionesModel class:controlEntregasModel method:obtenerRequisiciones "+e.getMessage());
            }
        
        return listaMaterialesRequisicion;             
    }
    
    public ArrayList<ParcialidadesRequisicion> listaParcialiades(int numRequisicion,String descMaterial){
        ArrayList<ParcialidadesRequisicion> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT ms.parcialidad,mor.cantidad,ms.fecha_solicitud,ms.fecha_entrega,mo.id_orden_produccion " +
                        "FROM materiales_orden_requisicion AS mor " +
                        "INNER JOIN materiales_orden AS mo ON mo.id_material_orden = mor.id_material_orden " +
                        "INNER JOIN (SELECT * FROM materiales_solicitados WHERE id_material = (SELECT id_material " +
                        "FROM ver_materiales WHERE CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) = '"+descMaterial+"')"
                        + " AND id_requisicion = "+numRequisicion+") AS " +
                        "ms ON ms.id_material_solicitado = mor.id_material_solicitado;";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        ParcialidadesRequisicion parcialidad = new ParcialidadesRequisicion(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4), rs.getInt(5));
                        parcialidad.setNoRequisicion(numRequisicion);
                        parcialidad.setDescMaterial(descMaterial);
                        lista.add(parcialidad);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, class ControlEntregasModel, metodo:listaParcialidades "+e.getMessage());
            }
        
        return lista;
    }
    
    public String obtenerObservaciones(int noRequisicion) {
        Connection c = Conexion.getInstance().getConexion();
        String comentarios = "";
        if(c!=null)
            try {
                String query = "select comentarios from requisiciones WHERE id_requisicion = '"+noRequisicion+"';";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    comentarios = rs.getString(1);
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, class ControlEntregasModel, metodo:obtenerObservaciones "+e.getMessage());
            }
        return comentarios;
    }
    
    public void agregarComentarios(int noRequisicion,String comentarios){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_comentario_requisicion(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noRequisicion);
                cs.setString(2, comentarios);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
                cs.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, class ControlEntregasModel, metodo:agregarComentarios "+e.getMessage());
            }
    }

    public void cerrarParteRequisicion(int noRequisicion, String descMaterial) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL cerrar_parte_requisicion(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noRequisicion);
                cs.setString(2, descMaterial);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
                cs.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:RequisicionesModel,"
                        + " class ControlEntregasModel, metodo:cerrarParteRequisicion "+e.getMessage());
            }
    }
}
