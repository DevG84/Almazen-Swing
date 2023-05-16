package settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            JOptionPane.showMessageDialog(null, "Estableciendo conexión a la base de datos.");
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
            JOptionPane.showMessageDialog(null, "La base de datos no existe, creando base de datos...");
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
            JOptionPane.showMessageDialog(null, "Se creó la base de datos.");
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

            String archivoSQL = "/settings/almazenScriptBuildDB.sql";
            File file = new File(getClass().getResource(archivoSQL).toURI());
            String rutaArchivo = file.getAbsolutePath();

            try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {

                // Lectura del script SQL línea por línea
                String[] comandos = getComandosSQL(rutaArchivo);

                // Ejecución de cada comando SQL
                for (String comando : comandos) {
                    statement.execute(comando);
                }

                JOptionPane.showMessageDialog(null, "Se cargaron elementos necesarios en la base de datos.");

            } catch (Exception e) {
                if (e.getMessage().equals("Query was empty")){
                    JOptionPane.showMessageDialog(null, "Se cargaron elementos necesarios en la base de datos.");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al crear las tablas en la base de datos, consulte su manual de usuario acerca de la instalación de WampServer.");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Método para leer el script SQL y obtener los comandos separados por punto y coma
    private static String[] getComandosSQL(String scriptSQL) throws IOException {
        Path path = Paths.get(scriptSQL);
        String contenido = new String(Files.readAllBytes(path));

        // Separar los comandos por punto y coma
        return contenido.split(";");
    }


    
}
