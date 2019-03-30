package Model.RequisicionesModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class RegistroEntradaMaterialesModel {

    
    public ArrayList<EntradaMaterial> listaRegistroEntradaMateriales(int anio,int mes){
        ArrayList<EntradaMaterial> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = " SELECT id_entrada_material,fecha_registro,CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) AS descMaterial, " +
                        " desc_proveedor,cantidad,codigo,certificado,orden_compra,inspector,es.desc_estado,comentarios,factura,desc_lote " +
                        " FROM entradas_materiales AS em INNER JOIN ver_materiales AS mt ON em.id_material = mt.id_material " +
                        " INNER JOIN proveedores AS pr ON em.id_proveedor = pr.id_proveedor " +
                        " INNER JOIN estados AS es ON em.id_estado = es.id_estado "
                        + "WHERE MONTH(fecha_registro) = "+mes+"  AND YEAR(fecha_registro) = "+anio+"; ";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{                        
                        EntradaMaterial entrada = new EntradaMaterial();
                        entrada.setNoEntradaMaterial(rs.getInt(1));
                        entrada.setFechaRegistro(rs.getString(2));
                        entrada.setDescMaterial(rs.getString(3));
                        entrada.setDescProveedor(rs.getString(4));
                        entrada.setCantidad(rs.getInt(5));
                        entrada.setCodigo(rs.getString(6));
                        entrada.setCertificado(rs.getString(7));
                        entrada.setOrdenCompra(rs.getString(8));
                        entrada.setInspector(rs.getString(9));
                        entrada.setDescEstado(rs.getString(10));
                        entrada.setComentarios(rs.getString(11));
                        entrada.setFactura(rs.getString(12));
                        entrada.setDescLote(rs.getString(13));
                        lista.add(entrada);   
                    }while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: RequisicionModel, class:RegistroEntradaMaterialesModel, metodo:listaRegistroEntradaMateriales "+e.getMessage());
            }
        
        return lista;
    }
    
    
}
