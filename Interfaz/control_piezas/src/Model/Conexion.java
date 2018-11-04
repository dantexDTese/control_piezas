package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    
    private Connection nuevaConexion;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String host = "69.175.92.67:3306/";
    private static String user = "tlpnowlq_gmcmmt";
    private static String password = ",2004conyla";
    private static String dataBaseName = "tlpnowlq_control_piezas";
    private static String url="jdbc:mysql://"+host+dataBaseName;
 
    private Conexion() {
        nuevaConexion = null;
        try {
            Class.forName(driver);
            nuevaConexion = DriverManager.getConnection(url+"?useSSL=false&noAccessToProcedureBodies=true",user,password);
        } catch (Exception e) {
            System.err.println("error:"+e.getMessage());
        }
    }
    
    public static Conexion getInstance(){
        Conexion conexion = new Conexion();
        return conexion;
    }
    
    public Connection getConexion(){
        return nuevaConexion;
    }
    
    public void desconectar(){
        try {
            nuevaConexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
