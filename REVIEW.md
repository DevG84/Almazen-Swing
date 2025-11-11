# Revisión del Repositorio Almazen-Swing

**Fecha de Revisión:** 11 de noviembre de 2025  
**Revisor:** GitHub Copilot Agent  
**Versión del Proyecto:** Almazen (CECyT 14)

---

## 📋 Resumen Ejecutivo

Este documento presenta los resultados de una revisión integral del repositorio **Almazen-Swing**, un sistema de gestión de inventario desarrollado en Java Swing para el CECyT 14 "Luis Enrique Erro" del Instituto Politécnico Nacional.

### Estado General del Proyecto
- ✅ **Compilación:** El proyecto compila correctamente con Java 17 y Ant
- ✅ **Estructura:** Código bien organizado con separación clara entre capas
- ⚠️ **Seguridad:** Se identificaron algunas áreas de mejora
- ⚠️ **Calidad de Código:** Algunas prácticas podrían optimizarse

---

## 🔍 Hallazgos Detallados

### 1. Seguridad

#### 1.1 Credenciales Hardcodeadas (Severidad: MEDIA)
**Ubicación:** `src/settings/conexionBD.java`

**Problema:**
Las credenciales de la base de datos están codificadas directamente en el código fuente en múltiples ubicaciones:

```java
String user = "root";
String password = "";
String url = "jdbc:mysql://localhost:3306/almazen";
```

**Impacto:**
- Las credenciales están expuestas en el código fuente
- No es posible cambiar las credenciales sin recompilar
- Dificulta el despliegue en diferentes entornos

**Recomendación:**
- Mover las credenciales a un archivo de configuración externo (`.properties` o `.env`)
- Usar variables de entorno para configuraciones sensibles
- Agregar el archivo de configuración al `.gitignore`

**Ejemplo de implementación recomendada:**
```java
// Leer desde archivo de configuración
Properties config = new Properties();
config.load(new FileInputStream("config.properties"));
String user = config.getProperty("db.user");
String password = config.getProperty("db.password");
String url = config.getProperty("db.url");
```

#### 1.2 Uso de PreparedStatement (Severidad: BAJA - Buena Práctica)
**Estado:** ✅ **CORRECTO**

El código utiliza correctamente `PreparedStatement` para todas las consultas SQL, lo que previene inyecciones SQL:

```java
String consulta = "SELECT password,nombre,status FROM usuarios WHERE nickname LIKE ?";
cmd = (PreparedStatement)conexion.conectar.prepareStatement(consulta);
cmd.setString(1, txtNick.getText());
```

**Observación:** Esta es una excelente práctica de seguridad que está bien implementada en todo el proyecto.

#### 1.3 Algoritmo de Cifrado Personalizado (Severidad: ALTA)
**Ubicación:** `src/settings/Key.java`

**Problema:**
El sistema implementa un cifrado de contraseñas personalizado usando una matriz de sustitución simple:

```java
char matriz[][]={
    {'A','a','B','g','C','m','D','q','E'},
    {'F','b','G','h','H','n','I','r','J'},
    // ...
};
```

**Impacto:**
- Cifrado débil y reversible
- No cumple con estándares de seguridad modernos
- Vulnerable a ataques de criptoanálisis
- No usa salt, haciendo contraseñas iguales fácilmente identificables

**Recomendación:**
Reemplazar con algoritmos de hash estándar y seguros:
```java
// Usar BCrypt, PBKDF2, o Argon2
import org.mindrot.jbcrypt.BCrypt;

public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
}

public boolean checkPassword(String password, String hashed) {
    return BCrypt.checkpw(password, hashed);
}
```

#### 1.4 Gestión de Excepciones (Severidad: BAJA)
**Ubicación:** Múltiples archivos

**Problema:**
Se usa `printStackTrace()` en producción, lo que puede exponer información sensible:

```java
} catch (SQLException e) {
    e.printStackTrace();  // src/settings/conexionBD.java:95, 143
}
```

**Recomendación:**
- Implementar un sistema de logging apropiado (Log4j, SLF4J)
- No mostrar stack traces completos en producción
- Registrar errores de manera estructurada

---

### 2. Gestión de Recursos

#### 2.1 Conexiones de Base de Datos (Severidad: MEDIA)
**Problema:**
La clase `conexionBD` mantiene una conexión abierta como variable de instancia pública:

```java
public Connection conectar = null;
```

**Impacto:**
- Posibles fugas de recursos
- Conexiones pueden quedar abiertas indefinidamente
- No se gestionan correctamente los errores de conexión

**Recomendación:**
Implementar patrón try-with-resources para gestión automática:

```java
public void executeQuery(String query) {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        // Usar la conexión
    } catch (SQLException e) {
        logger.error("Error en consulta", e);
    }
}
```

#### 2.2 Cierre de Recursos (Severidad: MEDIA)
**Problema:**
Los `PreparedStatement` y `ResultSet` no siempre se cierran correctamente, solo se encontró un cierre en `Login.java:429`.

**Recomendación:**
Usar try-with-resources consistentemente:
```java
try (PreparedStatement cmd = conexion.conectar.prepareStatement(consulta);
     ResultSet result = cmd.executeQuery()) {
    // Procesar resultados
}
```

---

### 3. Calidad de Código

#### 3.1 Comentarios TODO Generados Automáticamente (Severidad: BAJA)
**Problema:**
36 comentarios `// TODO add your handling code here:` generados por NetBeans sin implementar.

**Ubicaciones:**
- `Login.java`: 4 ocurrencias
- `Inicio.java`: 22 ocurrencias
- `ajustesUsuario.java`: 3 ocurrencias
- `ajustesAdmin.java`: múltiples ocurrencias
- `registrarMaterial.java`: múltiples ocurrencias
- `registrarUsuario.java`: múltiples ocurrencias

**Recomendación:**
- Revisar cada TODO y decidir si necesita implementación
- Eliminar TODOs innecesarios
- Implementar lógica faltante donde sea necesario

#### 3.2 Uso de System.exit() (Severidad: BAJA)
**Ubicaciones:**
- `conexionBD.java:70, 137`
- `Login.java:317`

**Observación:**
El uso de `System.exit()` es aceptable en aplicaciones de escritorio cuando la aplicación debe cerrarse, pero podría manejarse de manera más elegante con eventos de cierre de ventana.

---

### 4. Dependencias

#### 4.1 Bibliotecas Incluidas en el Repositorio (Severidad: BAJA)
**Problema:**
Los archivos JAR están incluidos directamente en el control de versiones:
- `flatlaf-3.0.jar` (765 KB)
- `flatlaf-intellij-themes-3.0.jar` (404 KB)
- `jcalendar-1.4.jar` (162 KB)
- `mysql-connector-j-8.0.32.jar` (2.4 MB)

**Impacto:**
- Aumenta el tamaño del repositorio
- Dificulta las actualizaciones
- No es una práctica recomendada moderna

**Recomendación:**
- Migrar a Maven o Gradle para gestión de dependencias
- Excluir JARs del repositorio
- Documentar dependencias en `pom.xml` o `build.gradle`

#### 4.2 Análisis de Vulnerabilidades
✅ **MySQL Connector J 8.0.32:** No se encontraron vulnerabilidades conocidas

**Nota:** Las versiones de FlatLaf y JCalendar deben verificarse manualmente ya que no están en el formato Maven estándar.

---

### 5. Estructura del Proyecto

#### 5.1 Organización de Código ✅
**Estado:** BUENO

El proyecto está bien organizado:
- `src/code/`: Clases de interfaz de usuario
- `src/settings/`: Configuración y utilidades
- `src/sources/`: Recursos gráficos
- `src/z_libraries/`: Bibliotecas externas

#### 5.2 Separación de Responsabilidades ⚠️
**Observación:**
La clase `Inicio.java` tiene 4,556 líneas, lo que sugiere que podría beneficiarse de refactorización para separar responsabilidades.

**Recomendación:**
- Dividir `Inicio.java` en clases más pequeñas y cohesivas
- Aplicar principios SOLID
- Extraer lógica de negocio de la interfaz de usuario

---

### 6. Base de Datos

#### 6.1 Scripts SQL ✅
**Estado:** BUENO

Los scripts SQL están bien estructurados:
- `almazenScriptBuildDB.sql`: Creación de tablas
- `almazenScriptDBDemo.sql`: Datos de demostración

**Observación:**
El sistema crea automáticamente la base de datos si no existe, lo cual es una buena práctica para facilitar la instalación.

---

## 📊 Estadísticas del Código

| Métrica | Valor |
|---------|-------|
| Líneas totales de código Java | 9,233 |
| Archivos Java | 11 |
| Clases principales | 7 |
| Clases de utilidad | 4 |
| Imports totales | 191 |
| Archivo más grande | Inicio.java (4,556 líneas) |

---

## 🎯 Recomendaciones Priorizadas

### Prioridad Alta
1. **Reemplazar algoritmo de cifrado personalizado** con BCrypt o PBKDF2
2. **Implementar gestión adecuada de recursos** con try-with-resources

### Prioridad Media
3. **Externalizar credenciales de base de datos** a archivo de configuración
4. **Refactorizar Inicio.java** para reducir complejidad
5. **Implementar sistema de logging** profesional

### Prioridad Baja
6. **Limpiar comentarios TODO** no utilizados
7. **Migrar a sistema de gestión de dependencias** (Maven/Gradle)
8. **Mejorar manejo de excepciones** sin printStackTrace

---

## ✅ Aspectos Positivos

1. ✅ **Uso correcto de PreparedStatement** previene inyección SQL
2. ✅ **Estructura de proyecto clara** y organizada
3. ✅ **Compilación exitosa** con Java 17
4. ✅ **Scripts SQL bien documentados**
5. ✅ **Creación automática de base de datos** facilita instalación
6. ✅ **Interfaz gráfica moderna** usando FlatLaf
7. ✅ **Sin vulnerabilidades conocidas** en MySQL Connector

---

## 🔐 Resumen de Seguridad

| Categoría | Estado | Observaciones |
|-----------|--------|---------------|
| Inyección SQL | ✅ SEGURO | PreparedStatement usado correctamente |
| Credenciales | ⚠️ MEJORABLE | Hardcodeadas en código fuente |
| Cifrado | ❌ CRÍTICO | Algoritmo personalizado débil |
| Gestión de recursos | ⚠️ MEJORABLE | Falta try-with-resources |
| Manejo de errores | ⚠️ MEJORABLE | printStackTrace en producción |

---

## 📝 Conclusión

El proyecto **Almazen-Swing** es un sistema funcional y bien estructurado que cumple con su propósito de gestionar el inventario del CECyT 14. El código está organizado y compila correctamente.

Sin embargo, existen algunas áreas críticas de seguridad que deben atenderse, especialmente:
- El algoritmo de cifrado de contraseñas debe reemplazarse urgentemente
- Las credenciales deben externalizarse
- La gestión de recursos debe mejorarse

Implementar las recomendaciones de prioridad alta mejorará significativamente la seguridad y robustez del sistema.

---

## 📚 Referencias

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Java Security Best Practices](https://www.oracle.com/java/technologies/javase/seccodeguide.html)
- [Effective Resource Management in Java](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)

---

**Nota:** Esta revisión se realizó de manera automatizada. Se recomienda una revisión manual adicional por un desarrollador senior antes de implementar cambios en producción.
