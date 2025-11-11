# Mejores Prácticas de Desarrollo - Almazen-Swing

## 📚 Guía de Buenas Prácticas para el Proyecto

Este documento proporciona recomendaciones para mejorar la calidad, mantenibilidad y profesionalismo del código del proyecto Almazen.

---

## 1. Estructura del Código

### 1.1 Refactorización de Inicio.java

**Problema:** La clase `Inicio.java` tiene 4,556 líneas, lo que dificulta su mantenimiento.

**Solución:** Aplicar el Principio de Responsabilidad Única (SRP)

#### Ejemplo de Separación:

```
src/code/Inicio.java (actual)
↓
src/code/main/
  ├── MainWindow.java (Ventana principal)
  ├── MenuManager.java (Gestión de menús)
  └── ModuleLoader.java (Carga de módulos)
  
src/code/modules/
  ├── SearchModule.java (Módulo de búsqueda)
  ├── InventoryModule.java (Entrada/Salida)
  ├── ReportsModule.java (Consultas)
  └── UserManagementModule.java (Gestión de usuarios)
  
src/code/components/
  ├── TableManager.java (Gestión de tablas)
  ├── FormValidator.java (Validación de formularios)
  └── DialogHelper.java (Diálogos comunes)
```

### 1.2 Separación de Lógica de Negocio

**Patrón MVC Recomendado:**

```
src/
├── code/ (Vista - UI)
│   ├── views/
│   └── dialogs/
├── model/ (Modelo - Datos)
│   ├── entities/
│   │   ├── Usuario.java
│   │   ├── Mercancia.java
│   │   └── Movimiento.java
│   └── dao/
│       ├── UsuarioDAO.java
│       ├── MercanciaDAO.java
│       └── MovimientoDAO.java
├── controller/ (Controlador - Lógica)
│   ├── LoginController.java
│   ├── InventoryController.java
│   └── UserController.java
└── settings/ (Configuración)
```

#### Ejemplo de Entity:

```java
package model.entities;

import java.util.Date;

public class Mercancia {
    private int idMercancia;
    private String codigo;
    private String articulo;
    private String descripcion;
    private String marca;
    private String presentacion;
    private int existencia;
    private String almacen;
    private String anaquel;
    private String repisa;
    
    // Constructor vacío
    public Mercancia() {}
    
    // Constructor completo
    public Mercancia(int idMercancia, String codigo, String articulo, 
                     String descripcion, String marca, String presentacion,
                     int existencia, String almacen, String anaquel, String repisa) {
        this.idMercancia = idMercancia;
        this.codigo = codigo;
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.existencia = existencia;
        this.almacen = almacen;
        this.anaquel = anaquel;
        this.repisa = repisa;
    }
    
    // Getters y Setters
    public int getIdMercancia() { return idMercancia; }
    public void setIdMercancia(int idMercancia) { this.idMercancia = idMercancia; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    // ... resto de getters y setters
    
    @Override
    public String toString() {
        return "Mercancia{" +
               "codigo='" + codigo + '\'' +
               ", articulo='" + articulo + '\'' +
               '}';
    }
}
```

#### Ejemplo de DAO:

```java
package model.dao;

import model.entities.Mercancia;
import settings.conexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MercanciaDAO {
    
    private conexionBD conexion;
    
    public MercanciaDAO() {
        this.conexion = new conexionBD();
    }
    
    /**
     * Busca mercancía por código
     */
    public Mercancia buscarPorCodigo(String codigo) throws SQLException {
        String query = "SELECT * FROM mercancia WHERE codigo = ?";
        
        try (PreparedStatement stmt = conexion.conectar.prepareStatement(query)) {
            stmt.setString(1, codigo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMercancia(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Lista toda la mercancía ordenada por artículo
     */
    public List<Mercancia> listarTodo() throws SQLException {
        List<Mercancia> lista = new ArrayList<>();
        String query = "SELECT * FROM mercancia ORDER BY articulo ASC";
        
        try (PreparedStatement stmt = conexion.conectar.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapResultSetToMercancia(rs));
            }
        }
        return lista;
    }
    
    /**
     * Actualiza la existencia de una mercancía
     */
    public boolean actualizarExistencia(String codigo, int nuevaExistencia) {
        String query = "UPDATE mercancia SET existencia = ? WHERE codigo = ?";
        
        try (PreparedStatement stmt = conexion.conectar.prepareStatement(query)) {
            stmt.setInt(1, nuevaExistencia);
            stmt.setString(2, codigo);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mapea ResultSet a objeto Mercancia
     */
    private Mercancia mapResultSetToMercancia(ResultSet rs) throws SQLException {
        return new Mercancia(
            rs.getInt("IDmercancia"),
            rs.getString("codigo"),
            rs.getString("articulo"),
            rs.getString("descripcion"),
            rs.getString("marca"),
            rs.getString("presentacion"),
            rs.getInt("existencia"),
            rs.getString("almacen"),
            rs.getString("anaquel"),
            rs.getString("repisa")
        );
    }
}
```

---

## 2. Manejo de Errores

### 2.1 Excepciones Personalizadas

**Crear:** `src/exceptions/AlmazenException.java`

```java
package exceptions;

public class AlmazenException extends Exception {
    
    public AlmazenException(String message) {
        super(message);
    }
    
    public AlmazenException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

```java
package exceptions;

public class DatabaseException extends AlmazenException {
    
    public DatabaseException(String message) {
        super("Error de base de datos: " + message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super("Error de base de datos: " + message, cause);
    }
}
```

### 2.2 Manejo Centralizado de Errores

```java
package util;

import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorHandler {
    
    private static final Logger logger = LogManager.getLogger(ErrorHandler.class);
    
    /**
     * Maneja excepciones mostrando mensaje al usuario y registrando en log
     */
    public static void handleError(Exception e, String userMessage) {
        logger.error(userMessage, e);
        JOptionPane.showMessageDialog(null, 
            userMessage + "\nPor favor contacte al administrador si el problema persiste.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Maneja errores de base de datos
     */
    public static void handleDatabaseError(SQLException e) {
        logger.error("Error de base de datos", e);
        JOptionPane.showMessageDialog(null,
            "Error al acceder a la base de datos.\n" +
            "Verifique que WampServer esté ejecutándose.",
            "Error de Base de Datos",
            JOptionPane.ERROR_MESSAGE);
    }
}
```

---

## 3. Validación de Datos

### 3.1 Clase Validadora Centralizada

**Crear:** `src/util/Validator.java`

```java
package util;

import java.util.regex.Pattern;

public class Validator {
    
    // Patrones de validación
    private static final Pattern BOLETA_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern CODIGO_PATTERN = Pattern.compile("^[A-Z0-9-]+$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,35}$");
    
    /**
     * Valida que un string no esté vacío
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Valida formato de boleta (10 dígitos)
     */
    public static boolean isValidBoleta(String boleta) {
        return boleta != null && BOLETA_PATTERN.matcher(boleta).matches();
    }
    
    /**
     * Valida código de mercancía
     */
    public static boolean isValidCodigo(String codigo) {
        return codigo != null && CODIGO_PATTERN.matcher(codigo).matches();
    }
    
    /**
     * Valida nickname de usuario
     */
    public static boolean isValidNickname(String nickname) {
        return nickname != null && NICKNAME_PATTERN.matcher(nickname).matches();
    }
    
    /**
     * Valida que un número sea positivo
     */
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
    
    /**
     * Valida que un número esté en un rango
     */
    public static boolean isInRange(int number, int min, int max) {
        return number >= min && number <= max;
    }
    
    /**
     * Valida longitud de string
     */
    public static boolean isValidLength(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }
}
```

### 3.2 Uso de Validaciones

```java
// En formularios de registro
if (!Validator.isValidNickname(txtNickname.getText())) {
    JOptionPane.showMessageDialog(this,
        "El nickname debe tener entre 3 y 35 caracteres alfanuméricos.");
    return;
}

if (!Validator.isValidBoleta(txtBoleta.getText())) {
    JOptionPane.showMessageDialog(this,
        "La boleta debe tener exactamente 10 dígitos.");
    return;
}
```

---

## 4. Constantes y Configuración

### 4.1 Centralizar Constantes

**Crear:** `src/util/Constants.java`

```java
package util;

public class Constants {
    
    // Mensajes de la aplicación
    public static final class Messages {
        public static final String LOGIN_SUCCESS = "Inicio de sesión exitoso";
        public static final String LOGIN_FAILED = "Usuario o contraseña incorrectos";
        public static final String USER_INACTIVE = "El usuario no está activo";
        public static final String CONNECTION_ERROR = "Error al conectar con la base de datos";
    }
    
    // Configuración de la UI
    public static final class UI {
        public static final int WINDOW_WIDTH = 1024;
        public static final int WINDOW_HEIGHT = 768;
        public static final String APP_TITLE = "Almazen - Sistema de Inventario";
    }
    
    // Configuración de base de datos
    public static final class Database {
        public static final String TABLE_USUARIOS = "usuarios";
        public static final String TABLE_MERCANCIA = "mercancia";
        public static final String TABLE_MOVIMIENTO = "movimiento";
        public static final String TABLE_PRIVILEGIOS = "privilegios";
    }
    
    // Tipos de movimiento
    public static final class MovementType {
        public static final String ENTRADA = "E";
        public static final String SALIDA = "S";
        public static final String UPDATE = "U";
    }
    
    // Validaciones
    public static final class Validation {
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final int MAX_NICKNAME_LENGTH = 35;
        public static final int BOLETA_LENGTH = 10;
    }
}
```

### 4.2 Usar Constantes

```java
// Antes:
if (result.getString(3).equals("S")) {

// Después:
if (Constants.MovementType.SALIDA.equals(result.getString(3))) {
```

---

## 5. Pruebas Unitarias

### 5.1 Estructura de Pruebas

```
test/
├── model/
│   └── dao/
│       ├── MercanciaDAOTest.java
│       └── UsuarioDAOTest.java
├── util/
│   ├── ValidatorTest.java
│   └── PasswordSecurityTest.java
└── settings/
    └── ConfigurationTest.java
```

### 5.2 Ejemplo de Prueba con JUnit

**Crear:** `test/util/ValidatorTest.java`

```java
package util;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorTest {
    
    @Test
    public void testValidNickname() {
        assertTrue(Validator.isValidNickname("usuario123"));
        assertTrue(Validator.isValidNickname("user_name"));
        assertFalse(Validator.isValidNickname("ab")); // Muy corto
        assertFalse(Validator.isValidNickname("usuario con espacios"));
        assertFalse(Validator.isValidNickname("usuario@especial"));
    }
    
    @Test
    public void testValidBoleta() {
        assertTrue(Validator.isValidBoleta("2023123456"));
        assertFalse(Validator.isValidBoleta("123")); // Muy corto
        assertFalse(Validator.isValidBoleta("abcd123456")); // No numérico
    }
    
    @Test
    public void testIsNotEmpty() {
        assertTrue(Validator.isNotEmpty("texto"));
        assertFalse(Validator.isNotEmpty(""));
        assertFalse(Validator.isNotEmpty("   "));
        assertFalse(Validator.isNotEmpty(null));
    }
}
```

---

## 6. Documentación del Código

### 6.1 JavaDoc Completo

```java
/**
 * Data Access Object para la gestión de mercancía en el inventario.
 * Proporciona métodos CRUD y consultas especializadas para la tabla mercancia.
 * 
 * @author Equipo Almazen
 * @version 1.0
 * @since 2023
 */
public class MercanciaDAO {
    
    /**
     * Busca una mercancía específica por su código único.
     * 
     * @param codigo El código único de la mercancía (no puede ser null)
     * @return Objeto Mercancia si se encuentra, null si no existe
     * @throws SQLException Si hay un error al acceder a la base de datos
     * @throws IllegalArgumentException Si el código es null o vacío
     */
    public Mercancia buscarPorCodigo(String codigo) throws SQLException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        // Implementación...
    }
}
```

### 6.2 README Mejorado

Actualizar `README.md` con:
- Instrucciones detalladas de instalación
- Requisitos del sistema
- Guía de configuración
- Capturas de pantalla
- FAQ
- Instrucciones de desarrollo

---

## 7. Control de Versiones

### 7.1 .gitignore Mejorado

```gitignore
# Build artifacts
/build/
/dist/
/nbproject/private/

# IDE
.idea/
*.iml
.vscode/
.DS_Store

# Configuration files with sensitive data
config.properties
*.properties.local

# Logs
*.log
logs/

# Temporary files
*.tmp
*.bak
*.swp
*~

# Compiled class files
*.class

# Package files (should be managed by build system)
# Uncomment if moving to Maven/Gradle
# *.jar
# *.war
# *.ear

# Database
*.db
*.sqlite
```

### 7.2 Commits Semánticos

Usar formato de commits consistente:

```
feat: Agregar módulo de reportes de inventario
fix: Corregir validación de boletas en registro
docs: Actualizar documentación de instalación
refactor: Separar lógica de negocio de Inicio.java
style: Aplicar formato consistente a código Java
test: Agregar pruebas para MercanciaDAO
chore: Actualizar dependencias a versiones recientes
```

---

## 8. Performance

### 8.1 Connection Pool

En lugar de crear conexiones individuales, usar un pool:

```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {
    
    private static HikariDataSource dataSource;
    
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Configuration.getDatabaseUrl());
        config.setUsername(Configuration.getDatabaseUser());
        config.setPassword(Configuration.getDatabasePassword());
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        
        dataSource = new HikariDataSource(config);
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
```

### 8.2 Paginación de Resultados

Para consultas grandes:

```java
public List<Mercancia> listarPaginado(int pagina, int porPagina) {
    int offset = (pagina - 1) * porPagina;
    String query = "SELECT * FROM mercancia LIMIT ? OFFSET ?";
    
    try (PreparedStatement stmt = conexion.conectar.prepareStatement(query)) {
        stmt.setInt(1, porPagina);
        stmt.setInt(2, offset);
        // Ejecutar y retornar resultados
    }
}
```

---

## 9. Internacionalización (i18n)

### 9.1 Preparar para Múltiples Idiomas

**Crear:** `resources/messages_es.properties`

```properties
login.title=Inicio de Sesión
login.username=Usuario
login.password=Contraseña
login.button=Iniciar Sesión
error.invalid_credentials=Usuario o contraseña incorrectos
```

**Crear:** `resources/messages_en.properties`

```properties
login.title=Login
login.username=Username
login.password=Password
login.button=Sign In
error.invalid_credentials=Invalid username or password
```

### 9.2 Clase de Mensajes

```java
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    
    private static ResourceBundle bundle;
    
    static {
        loadMessages(new Locale("es"));
    }
    
    public static void loadMessages(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }
    
    public static String get(String key) {
        return bundle.getString(key);
    }
}
```

---

## 10. Lista de Verificación de Calidad

Antes de cada commit:

- [ ] El código compila sin errores ni warnings
- [ ] Se agregaron pruebas para nueva funcionalidad
- [ ] La documentación JavaDoc está actualizada
- [ ] Se usan constantes en lugar de valores mágicos
- [ ] Los recursos se cierran correctamente
- [ ] Las excepciones se manejan apropiadamente
- [ ] El código sigue las convenciones de nomenclatura
- [ ] No hay código comentado innecesario
- [ ] Los TODOs están implementados o documentados
- [ ] El commit tiene un mensaje descriptivo

---

**Recuerda:** El código se escribe una vez pero se lee muchas veces. Prioriza la claridad sobre la brevedad.
