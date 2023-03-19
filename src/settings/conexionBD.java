package settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexionBD {
    boolean modo=false;
    public Connection conectar=null;
    
    public conexionBD(){
        if(modo==false){
            try{
                String user="root";
                String password="";
                String url="jdbc:mysql://localhost:3306/almazen";
                conectar=DriverManager.getConnection(url,user,password);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "La base de datos no está disponible.");
            }
        }else{
            //Conexion remota
            
        }
    }
}
