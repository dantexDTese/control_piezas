package Model;

import Controller.PedidosController.CalendarioController;
import Model.PedidosModel.CalendarioModel;
import Model.ProduccionModel.LoteProduccion;
import View.Pedidos.Calendario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author cesar
 */
public class Estructuras {

    public static String obtenerFechaActual(){
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        return sdf.format(fecha);
    }
    
    
    
    public static JComboBox llenaCombo(JComboBox selector,String query){
        Connection conexion = Conexion.getInstance().getConexion();       
        if(conexion != null){
            try {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);               
                
                if(rs.first()){
                    do{
                        selector.addItem(rs.getString(1));
                    }while(rs.next());
                }
                    
                conexion.close();
            } catch (SQLException e) {
                System.err.println("error:"+e.getMessage());
            }
        }
        return selector;
    }
    
    public static void modificarAnchoTabla(JTable tablaModificar,Integer[] listaTamanos){
        //pasar a estructuras
        tablaModificar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i = 0;i<listaTamanos.length;i++){
                TableColumn columna = tablaModificar.getColumnModel().getColumn(i);
                columna.setPreferredWidth(listaTamanos[i]);
            }
    }
    
    public static void modificarAltoTabla(JTable tablaModificar,int alto){
        
        //pasar a estructuras
        
        tablaModificar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Integer[] listaTamanos = {80,100,130,100,130,100,100,120,120,160};
            
                tablaModificar.setRowHeight(alto);

    }
       
    public static void obtenerCalendario(JPanel contenedor,String descMaquina){    
        Calendario calendarioView = new Calendario();
        CalendarioController calendarioController = new CalendarioController(calendarioView, new CalendarioModel(),descMaquina);        
         calendarioView.setSize(contenedor.getWidth(),contenedor.getHeight());
         calendarioView.setLocation(0,0);
         contenedor.removeAll();
         contenedor.add(calendarioView);
         contenedor.revalidate();
         contenedor.repaint();         
    }
    
    public static ArrayList obtenerlistaDatos(String query){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList lista=new ArrayList();
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        lista.add(rs.getString(1));
                    }while(rs.next());
                c.close();
            } catch (Exception e) {
                System.err.println("error clase ordenProduccionModel,metodo listaProductos:"+e.getMessage());
            }
        return lista;
    }
    
    public static boolean existeEntidad(String query){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    return true;
            } catch (Exception e) {
                System.err.println("class:Estructuras,"
                                + "method: existeEntidad,error:"+e.getMessage());
            }
        else{
            System.err.println("no fue posible establecer una conexion");
            return false;
        }
        return true;
    }
    
    public static DefaultTableModel limpiarTabla(DefaultTableModel model){
        
        while(model.getRowCount()>0)
            model.removeRow(0);
    
         return model;
    }
    
    public static String convertirFechaGuardar(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        return sdf.format(fecha);
    }
    
    public static boolean validarFecha(Date fecha){
        if(fecha != null){
            if(new Date().after(fecha)){
                JOptionPane.showMessageDialog(null, "La fecha no es valida","fecha invalida",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    public static Date formateFecha(String fecha){
        if(fecha != null && !"".equals(fecha)){
            fecha = fecha.replace('-', '/');
            return new Date(fecha);
        }
        return null;
    }
    
    public static String sumarHoras(String tiempo1,String tiempo2){
        StringTokenizer tkTiempo1 = new StringTokenizer(tiempo1,":");
        StringTokenizer tkTiempo2 = new StringTokenizer(tiempo2,":");
        
        long horasR,minutosR;
        String resultado = "";
        
        long segundos = ((Integer.parseInt(tkTiempo2.nextToken())*3600)
                +(Integer.parseInt(tkTiempo2.nextToken())*60)
                +(Integer.parseInt(tkTiempo2.nextToken())))
                +
                ((Integer.parseInt(tkTiempo1.nextToken())*3600)
                +(Integer.parseInt(tkTiempo1.nextToken())*60)
                +(Integer.parseInt(tkTiempo1.nextToken())));
        
        horasR = segundos/3600;
        
        segundos -= horasR*3600;
        
        minutosR = segundos/60;
        
        segundos -= minutosR*60;
        
        
        resultado= (horasR < 10)? "0"+horasR+":":horasR+":";
        resultado+=(minutosR < 10)? "0"+minutosR+":":minutosR+":";
        resultado+=(segundos < 10)?"0"+segundos:segundos;
        
        return resultado;
    }
}
