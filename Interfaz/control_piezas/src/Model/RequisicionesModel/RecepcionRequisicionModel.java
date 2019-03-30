
package Model.RequisicionesModel;

import Model.Conexion;
import Model.Estructuras;
import Model.Requisicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class RecepcionRequisicionModel {
    
    public ArrayList<Requisicion> listaRequisicionesPendientes(){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Requisicion> listaRequisiciones = new ArrayList<>();
        if(c!=null)
            try {
                String query = " SELECT rq.id_requisicion,fecha_creacion FROM requisiciones AS rq " +
                            " INNER JOIN (SELECT id_requisicion FROM materiales_solicitados " +
                            " WHERE id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'ABIERTO')) AS ms " +
                            " ON ms.id_requisicion = rq.id_requisicion GROUP BY rq.id_requisicion;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Requisicion requisicionPendiente = new Requisicion();
                        requisicionPendiente.setNoRequisicion(rs.getInt(1));
                        requisicionPendiente.setFechaCreacion(rs.getString(2));
                        listaRequisiciones.add(requisicionPendiente);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionModel, "
                        + "class:RecepcionRequisicionModel, metodo:listaRequisicionesPendientes ");
            }
        return listaRequisiciones;
    }

    public ArrayList<ParcialidadMaterial> listaMaterialesRequisicion(int noRequisicion) {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<ParcialidadMaterial> lista = new ArrayList<>();
        if(c!=null)
            try {
                String query = " SELECT no_partida,"
                                + "CONCAT(vm.desc_tipo_material,' ',vm.desc_dimencion,' ',vm.clave_forma) AS material," +
                                " parcialidad,cantidad,(cantidad_restante_compras(ms.id_material_solicitado,cantidad))"
                                + " AS restantes " +
                                " ,ms.fecha_solicitud,ms.id_material_solicitado FROM materiales_solicitados AS ms " +
                                " INNER JOIN ver_materiales AS vm ON vm.id_material = ms.id_material "
                                + " WHERE ms.id_requisicion = "+noRequisicion+" "
                                + "AND cantidad_restante_compras(ms.id_material_solicitado,cantidad) > 0"
                                + " ORDER BY no_partida;";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                      ParcialidadMaterial pMaterial = new ParcialidadMaterial();
                      pMaterial.setNoPartida(rs.getInt(1));
                      pMaterial.setDescMaterial(rs.getString(2));
                      pMaterial.setNoParcialidad(rs.getInt(3));
                      pMaterial.setCantidad(rs.getInt(4));
                      pMaterial.setCantidadRestante(rs.getInt(5));
                      pMaterial.setFechaSolicitadaParcialidadMaterial(rs.getString(6));
                      pMaterial.setNoRequisicion(noRequisicion);
                      pMaterial.setNoMaterialSolicitado(rs.getInt(7));
                      lista.add(pMaterial);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionModel, "
                        + "class:RecepcionRequisicionModel, metodo:listaMaterialesRequisicion "+e.getMessage());
            }
        
        
        return lista;
    }

    public int registrarValidacionMateriales() {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String fecha = Estructuras.convertirFechaGuardar(new Date());
                String query = "INSERT INTO materiales_solicitados_compras(fecha_registro) VALUES('"+fecha+"')";
                PreparedStatement ps = c.prepareStatement(query);
                
                ps.execute();
                
                query = "SELECT MAX(id_material_solicitado_compras) FROM materiales_solicitados_compras;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                return (rs.first())? rs.getInt(1):0;
                
            } catch (SQLException e) {
                System.err.println("error: paquete:RequisicionModel, "
                        + "class:RecepcionRequisicionModel, metodo:registrarValidacionMateriales "+e.getMessage());
            }
        
        return 0;
    }

    public void guardarMateriales(int materialSolicitado,int materialSolicitadoCompras,int cantidad) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "INSERT INTO materiales_solicitud_compras"
                        + "(id_material_solicitado_compras,id_material_solicitado,cantidad)"
                        + " VALUES("+materialSolicitadoCompras+","+materialSolicitado+","+cantidad+");";
                PreparedStatement ps = c.prepareStatement(query);
                ps.execute();
                
            } catch (Exception e) {
                System.err.println("error: paquete:RequisicionModel, "
                        + "class:RecepcionRequisicionModel, metodo:guardarMateriales "+e.getMessage());
            }
    }
    
    
    
}
