package settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import settings.customPane;

public class conexionBD {
    public Connection conectar = null;
    
    public conexionBD() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/almazen";
            conectar = DriverManager.getConnection(url, user, password);
            
            if (!existenTablas()) {
                cargarTablas();
            }
            
        } catch (SQLException e) {
            // Ejemplo de uso
            customPane.showMessageDialogWithTimeout("Estableciendo conexión a la base de datos...", "Cargando...", JOptionPane.INFORMATION_MESSAGE, 1000);
            if (!existeBaseDeDatos()) {
                crearBaseDeDatos();
                cargarTablas();
            }
        }
    }
    
    private boolean existeBaseDeDatos() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/";
            String checkDBQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
            conectar = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conectar.prepareStatement(checkDBQuery);
            stmt.setString(1, "almazen");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true; // La base de datos existe
            }
        } catch (SQLException e) {
            customPane.showMessageDialogWithTimeout("La base de datos no existe, creando base de datos...", "Cargando...", JOptionPane.INFORMATION_MESSAGE, 2000);
        }
        return false; // La base de datos no existe
    }
    
    private void crearBaseDeDatos() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/";
            String createDBQuery = "CREATE DATABASE almazen";
            conectar = DriverManager.getConnection(url, user, password);
            Statement stmt = conectar.createStatement();
            stmt.executeUpdate(createDBQuery);
            customPane.showMessageDialogWithTimeout("Se creó la base de datos. Por favor espere...", "Cargando...", JOptionPane.INFORMATION_MESSAGE, 2000);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear la base de datos, por favor abra WampServer y reinicie Almazen.");
            System.exit(0);
        }
    }
    
    private boolean existenTablas() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/almazen";
            conectar = DriverManager.getConnection(url, user, password);
            
            String[] tablasRequeridas = {"usuarios", "privilegios", "mercancia", "movimiento"};
            String checkTablesQuery = "SHOW TABLES";
            Statement stmt = conectar.createStatement();
            ResultSet rs = stmt.executeQuery(checkTablesQuery);
            
            while (rs.next()) {
                String tableName = rs.getString(1);
                for (String tabla : tablasRequeridas) {
                    if (tableName.equalsIgnoreCase(tabla)) {
                        return true; // Tabla requerida encontrada
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No se encontraron todas las tablas
    }
    
    private void cargarTablas() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/almazen";
            conectar = DriverManager.getConnection(url, user, password);

            String archivoSQL = "/settings/almazenScriptDBDemo.sql";
            InputStream inputStream = getClass().getResourceAsStream(archivoSQL);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 Statement statement = connection.createStatement()) {

                // Lectura del script SQL línea por línea
                StringBuilder scriptBuilder = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    scriptBuilder.append(linea);
                    scriptBuilder.append("\n");
                }
                String scriptSQL = scriptBuilder.toString();

                // Separar los comandos SQL por punto y coma
                String[] comandos = scriptSQL.split(";");

                // Ejecución de cada comando SQL
                for (String comando : comandos) {
                    statement.execute(comando);
                }
            } catch (Exception e) {
                if (e.getMessage().equals("Query was empty")){
                    customPane.showMessageDialogWithTimeout("Se cargaron elementos necesarios en la base de datos.", "Cargando...", JOptionPane.INFORMATION_MESSAGE, 1500);
                } else {
                    if (archivoSQL=="/settings/almazenScriptBuildDB.sql") {
                        JOptionPane.showMessageDialog(null, "Error al crear las tablas en la base de datos, consulte su manual de usuario acerca de la instalación de WampServer.");
                        System.exit(0);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
