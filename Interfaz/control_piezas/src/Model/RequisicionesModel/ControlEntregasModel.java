
package Model.RequisicionesModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ControlEntregasModel {


    public ArrayList<MaterialesRequisicion> obtenerRequisiciones(){
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM bitacora_requisiciones";
        
        ArrayList<MaterialesRequisicion> listaMaterialesRequisicion = new ArrayList<>();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        listaMaterialesRequisicion.add(new MaterialesRequisicion(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getString(4)));
                    } while (rs.next());
                        
            } catch (SQLException e) {
                System.err.println("error paquete:RequisicionesModel class:controlEntregasModel method:obtenerRequisiciones "+e.getMessage());
            }
        
        return listaMaterialesRequisicion;             
    }
    
    public ArrayList<ParcialidadesRequisicion> listaParcialiades(int numRequisicion,String descMaterial){
        ArrayList<ParcialidadesRequisicion> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT ms.parcialidad,mor.cantidad,ms.fecha_solicitud,ms.fecha_entrega,mo.id_orden_produccion FROM materiales_orden_requisicion AS mor " +
                       "INNER JOIN materiales_orden AS mo ON mo.id_material_orden = mor.id_material_orden " +
                       "INNER JOIN (SELECT * FROM materiales_solicitados WHERE id_material = (SELECT id_material " + 
                       "FROM materiales WHERE desc_material = '"+descMaterial+"') AND id_requisicion = "+numRequisicion+") AS "+
                       "ms ON ms.id_material_solicitado = mor.id_material_solicitado;";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        lista.add(new ParcialidadesRequisicion(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4), rs.getInt(5)));
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionesModel, class ControlEntregasModel, metodo:listaParcialidades "+e.getMessage());
            }
        
        return lista;
    }
    
    
    public class ParcialidadesRequisicion{
        
        private final int parcialidad;
        private final int cantidad;
        private final String fechaSolicitud;
        private final String fechaEntrega;
        private final int noOrdenProduccion;

        public ParcialidadesRequisicion(int parcialidad, int cantidad, String fechaSolicitud, String fechaEntrega, int noOrdenProduccion) {
            this.parcialidad = parcialidad;
            this.cantidad = cantidad;
            this.fechaSolicitud = fechaSolicitud;
            this.fechaEntrega = fechaEntrega;
            this.noOrdenProduccion = noOrdenProduccion;
        }

        public int getParcialidad() {
            return parcialidad;
        }

        public int getCantidad() {
            return cantidad;
        }

        public String getFechaSolicitud() {
            return fechaSolicitud;
        }

        public String getFechaEntrega() {
            return fechaEntrega;
        }

        public int getNoOrdenProduccion() {
            return noOrdenProduccion;
        }
        
        
    }
}
