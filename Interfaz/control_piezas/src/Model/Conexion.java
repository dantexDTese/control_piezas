package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    
    private Connection nuevaConexion;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String host = "localhost:3306/";
    private static String user = "root";
    private static String password = "sasa";
    private static String dataBaseName = "control_piezas_2";
    private static String url="jdbc:mysql://"+host+dataBaseName;
 
    private Conexion() {
        nuevaConexion = null;
        try {
            Class.forName(driver);
            nuevaConexion = DriverManager.getConnection(url+"?useSSL=false",user,password);
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
