# Recomendaciones de Seguridad para Almazen-Swing

## 🔐 Guía de Implementación de Mejoras de Seguridad

Este documento proporciona pasos concretos para implementar las mejoras de seguridad identificadas en la revisión del código.

---

## 1. Reemplazo del Sistema de Cifrado de Contraseñas

### 1.1 Problema Actual
El archivo `src/settings/Key.java` implementa un cifrado por sustitución simple que:
- Es reversible y predecible
- No usa salt (sal criptográfica)
- No cumple con estándares modernos
- Es vulnerable a ataques de diccionario

### 1.2 Solución Recomendada: BCrypt

#### Paso 1: Agregar Dependencia
Agregar al archivo `build.xml` o usar Maven:

```xml
<!-- Si migran a Maven, agregar a pom.xml -->
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

O descargar el JAR de BCrypt y agregarlo a `src/z_libraries/`.

#### Paso 2: Crear Nueva Clase de Seguridad

**Crear:** `src/settings/PasswordSecurity.java`

```java
package settings;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordSecurity {
    
    // Número de rondas de hashing (10-12 es recomendado)
    private static final int BCRYPT_ROUNDS = 12;
    
    /**
     * Genera un hash seguro de la contraseña
     * @param plainPassword Contraseña en texto plano
     * @return Hash BCrypt de la contraseña
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    /**
     * Verifica si una contraseña coincide con su hash
     * @param plainPassword Contraseña a verificar
     * @param hashedPassword Hash almacenado en BD
     * @return true si coincide, false en caso contrario
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Hash inválido
            return false;
        }
    }
}
```

#### Paso 3: Migrar Contraseñas Existentes

**Crear script de migración:** `src/settings/MigratePasswords.java`

```java
package settings;

import java.sql.*;

public class MigratePasswords {
    
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/almazen", "root", "");
            
            // Obtener todos los usuarios
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IDusuario, password FROM usuarios");
            
            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE usuarios SET password = ? WHERE IDusuario = ?");
            
            int count = 0;
            while (rs.next()) {
                int userId = rs.getInt("IDusuario");
                String oldPassword = rs.getString("password");
                
                // Aquí necesitarás descifrar la contraseña antigua
                // O pedir a los usuarios que cambien sus contraseñas
                // Por ahora, comentado:
                // String plainPassword = decryptOldPassword(oldPassword);
                // String newHash = PasswordSecurity.hashPassword(plainPassword);
                
                // updateStmt.setString(1, newHash);
                // updateStmt.setInt(2, userId);
                // updateStmt.executeUpdate();
                // count++;
            }
            
            System.out.println("Migradas " + count + " contraseñas");
            
            rs.close();
            stmt.close();
            updateStmt.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

#### Paso 4: Actualizar Login.java

Reemplazar en `src/code/Login.java` líneas ~410-413:

```java
// ANTES:
Key k = new Key();
if(result.getString(1).matches(k.getPassword(txtPassword.getText()))){

// DESPUÉS:
if(PasswordSecurity.verifyPassword(
    new String(txtPassword.getPassword()), 
    result.getString(1))){
```

#### Paso 5: Actualizar Registro de Usuarios

En `src/code/registrarUsuario.java`, al guardar nueva contraseña:

```java
// Antes de INSERT, hashear la contraseña:
String hashedPassword = PasswordSecurity.hashPassword(
    new String(txtPassword.getPassword()));

// Usar hashedPassword en el INSERT
```

---

## 2. Externalización de Credenciales

### 2.1 Crear Archivo de Configuración

**Crear:** `config.properties` (en directorio raíz, NO en src)

```properties
# Configuración de Base de Datos
db.host=localhost
db.port=3306
db.name=almazen
db.user=root
db.password=

# Configuración de Aplicación
app.name=Almazen
app.version=1.0
```

### 2.2 Actualizar .gitignore

Agregar al archivo `.gitignore`:

```
# Archivos de configuración con credenciales
config.properties
*.properties.local

# Logs
*.log
logs/
```

### 2.3 Crear Clase de Configuración

**Crear:** `src/settings/Configuration.java`

```java
package settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Configuration {
    
    private static Properties properties = null;
    private static final String CONFIG_FILE = "config.properties";
    
    /**
     * Carga la configuración desde el archivo
     */
    private static void loadProperties() {
        if (properties != null) {
            return; // Ya cargadas
        }
        
        properties = new Properties();
        
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            // Si no existe el archivo, usar valores por defecto
            System.err.println("Advertencia: No se encontró config.properties, usando valores por defecto");
            setDefaultProperties();
        }
    }
    
    /**
     * Establece valores por defecto
     */
    private static void setDefaultProperties() {
        properties = new Properties();
        properties.setProperty("db.host", "localhost");
        properties.setProperty("db.port", "3306");
        properties.setProperty("db.name", "almazen");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "");
    }
    
    /**
     * Obtiene la URL de conexión a la base de datos
     */
    public static String getDatabaseUrl() {
        loadProperties();
        String host = properties.getProperty("db.host");
        String port = properties.getProperty("db.port");
        String name = properties.getProperty("db.name");
        return "jdbc:mysql://" + host + ":" + port + "/" + name;
    }
    
    /**
     * Obtiene el usuario de la base de datos
     */
    public static String getDatabaseUser() {
        loadProperties();
        return properties.getProperty("db.user");
    }
    
    /**
     * Obtiene la contraseña de la base de datos
     */
    public static String getDatabasePassword() {
        loadProperties();
        return properties.getProperty("db.password");
    }
}
```

### 2.4 Actualizar conexionBD.java

Reemplazar todas las líneas donde se definen las credenciales:

```java
// ANTES:
String user = "root";
String password = "";
String url = "jdbc:mysql://localhost:3306/almazen";

// DESPUÉS:
String user = Configuration.getDatabaseUser();
String password = Configuration.getDatabasePassword();
String url = Configuration.getDatabaseUrl();
```

### 2.5 Crear config.properties de Ejemplo

**Crear:** `config.properties.example`

```properties
# Ejemplo de Configuración de Almazen
# Copiar este archivo como config.properties y ajustar valores

# Configuración de Base de Datos
db.host=localhost
db.port=3306
db.name=almazen
db.user=root
db.password=

# IMPORTANTE: Cambiar las credenciales por defecto en producción
```

---

## 3. Implementar Try-With-Resources

### 3.1 Patrón Recomendado

**Antes:**
```java
PreparedStatement cmd = null;
ResultSet result = null;
try {
    cmd = conexion.conectar.prepareStatement(consulta);
    result = cmd.executeQuery();
    // Procesar resultado
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    if (result != null) result.close();
    if (cmd != null) cmd.close();
}
```

**Después:**
```java
try (PreparedStatement cmd = conexion.conectar.prepareStatement(consulta);
     ResultSet result = cmd.executeQuery()) {
    // Procesar resultado
} catch (SQLException e) {
    logger.error("Error en consulta", e);
}
```

### 3.2 Ejemplo Completo en Login.java

```java
private boolean validarCredenciales(String nickname, String password) {
    String consulta = "SELECT password, status FROM usuarios " +
                     "NATURAL JOIN privilegios WHERE nickname = ?";
    
    try (PreparedStatement cmd = conexion.conectar.prepareStatement(consulta)) {
        cmd.setString(1, nickname);
        
        try (ResultSet result = cmd.executeQuery()) {
            if (result.next()) {
                String hashedPassword = result.getString("password");
                String status = result.getString("status");
                
                if (!"S".equals(status)) {
                    return false; // Usuario inactivo
                }
                
                return PasswordSecurity.verifyPassword(password, hashedPassword);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al validar credenciales. Por favor intente nuevamente.");
        return false;
    }
    
    return false; // Usuario no encontrado
}
```

---

## 4. Sistema de Logging

### 4.1 Agregar Dependencia Log4j 2

```xml
<!-- En Maven pom.xml -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.20.0</version>
</dependency>
```

### 4.2 Configuración Log4j

**Crear:** `src/log4j2.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        <File name="File" fileName="logs/almazen.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

### 4.3 Usar Logger en Código

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class conexionBD {
    private static final Logger logger = LogManager.getLogger(conexionBD.class);
    
    public conexionBD() {
        try {
            // ...
            logger.info("Conexión a base de datos establecida correctamente");
        } catch (SQLException e) {
            logger.error("Error al conectar a base de datos", e);
            throw new RuntimeException("No se pudo establecer conexión", e);
        }
    }
}
```

---

## 5. Validación de Entrada

### 5.1 Validar Entradas de Usuario

```java
public class InputValidator {
    
    /**
     * Valida que un nickname sea seguro
     */
    public static boolean isValidNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            return false;
        }
        // Solo letras, números y guiones bajos, 3-35 caracteres
        return nickname.matches("^[a-zA-Z0-9_]{3,35}$");
    }
    
    /**
     * Valida que una contraseña sea suficientemente fuerte
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        // Al menos una mayúscula, minúscula, número
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        
        return hasUpper && hasLower && hasDigit;
    }
    
    /**
     * Sanitiza entrada para prevenir XSS en mensajes
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[<>\"']", "");
    }
}
```

---

## 6. Lista de Verificación de Seguridad

Antes de desplegar en producción, verificar:

- [ ] Contraseñas hasheadas con BCrypt/PBKDF2
- [ ] Credenciales externalizadas en config.properties
- [ ] config.properties en .gitignore
- [ ] Try-with-resources implementado
- [ ] Sistema de logging configurado
- [ ] Sin printStackTrace en código de producción
- [ ] Validación de entradas de usuario
- [ ] Actualización de dependencias a últimas versiones
- [ ] Base de datos con usuario específico (no root)
- [ ] Contraseña de BD diferente a cadena vacía
- [ ] Backups de base de datos configurados
- [ ] Conexiones SSL/TLS si es red externa

---

## 7. Recursos Adicionales

- [OWASP Java Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Java_Security_Cheat_Sheet.html)
- [BCrypt Documentation](https://github.com/jeremyh/jBCrypt)
- [Log4j 2 Manual](https://logging.apache.org/log4j/2.x/manual/)
- [Java Secure Coding Guidelines](https://www.oracle.com/java/technologies/javase/seccodeguide.html)

---

**Nota:** Implementar estas mejoras de manera incremental, probando cada cambio antes de proceder al siguiente. Hacer backup de la base de datos antes de migrar contraseñas.
