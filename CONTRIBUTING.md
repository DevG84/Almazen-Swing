# Guía de Contribución - Almazen-Swing

¡Gracias por tu interés en contribuir al proyecto Almazen! Esta guía te ayudará a colaborar de manera efectiva.

## 📋 Tabla de Contenidos

1. [Código de Conducta](#código-de-conducta)
2. [Cómo Contribuir](#cómo-contribuir)
3. [Configuración del Entorno](#configuración-del-entorno)
4. [Estándares de Código](#estándares-de-código)
5. [Process de Pull Request](#proceso-de-pull-request)
6. [Reportar Bugs](#reportar-bugs)
7. [Sugerir Mejoras](#sugerir-mejoras)

---

## Código de Conducta

Este proyecto y todos los participantes están sujetos a un código de conducta de respeto mutuo:

- Usar lenguaje inclusivo y respetuoso
- Aceptar críticas constructivas
- Enfocarse en lo que es mejor para la comunidad
- Mostrar empatía hacia otros miembros

---

## Cómo Contribuir

### Tipos de Contribuciones

Valoramos las siguientes contribuciones:

- 🐛 **Reportar bugs**: Ayúdanos a encontrar y corregir errores
- ✨ **Nuevas características**: Propón y desarrolla nuevas funcionalidades
- 📝 **Documentación**: Mejora o traduce la documentación
- 🎨 **Diseño UI/UX**: Mejora la interfaz de usuario
- 🔒 **Seguridad**: Identifica y corrige vulnerabilidades
- ⚡ **Performance**: Optimiza el rendimiento del código

### Flujo de Trabajo

1. **Fork** el repositorio
2. **Crea una rama** para tu característica (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** tus cambios (`git commit -m 'feat: Agregar nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. **Abre un Pull Request**

---

## Configuración del Entorno

### Requisitos Previos

- **Java JDK 17** o superior
- **Apache NetBeans 12.5+** (recomendado) o cualquier IDE Java
- **WampServer 3.3.0+** con MySQL 5.7.40+
- **Apache Ant** (incluido con NetBeans)

### Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/DevG84/Almazen-Swing.git
   cd Almazen-Swing
   ```

2. **Configurar la base de datos:**
   ```bash
   # Iniciar WampServer
   # La base de datos se creará automáticamente al ejecutar la aplicación
   ```

3. **Configurar credenciales:**
   ```bash
   cp config.properties.example config.properties
   # Editar config.properties con tus credenciales de MySQL
   ```

4. **Compilar el proyecto:**
   ```bash
   ant clean compile
   ```

5. **Ejecutar la aplicación:**
   ```bash
   ant run
   # O desde NetBeans: F6
   ```

### Ejecutar Pruebas

```bash
# Cuando se implementen pruebas unitarias
ant test
```

---

## Estándares de Código

### Convenciones de Nomenclatura

- **Clases**: `PascalCase` (Ej: `UsuarioController`)
- **Métodos**: `camelCase` (Ej: `buscarPorCodigo()`)
- **Variables**: `camelCase` (Ej: `nombreUsuario`)
- **Constantes**: `UPPER_SNAKE_CASE` (Ej: `MAX_INTENTOS`)
- **Paquetes**: `lowercase` (Ej: `model.dao`)

### Formato de Código

```java
// Bueno
public class Usuario {
    private String nombre;
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

// Evitar
public class usuario{
private String nombre;
public String getNombre(){return nombre;}
}
```

### Documentación

Todas las clases y métodos públicos deben tener JavaDoc:

```java
/**
 * Busca un usuario por su nickname único.
 * 
 * @param nickname El identificador único del usuario
 * @return El objeto Usuario si se encuentra, null en caso contrario
 * @throws SQLException Si hay un error de base de datos
 */
public Usuario buscarPorNickname(String nickname) throws SQLException {
    // Implementación
}
```

### Manejo de Recursos

Siempre usar try-with-resources:

```java
// Correcto
try (PreparedStatement stmt = conn.prepareStatement(query);
     ResultSet rs = stmt.executeQuery()) {
    // Procesar resultados
} catch (SQLException e) {
    logger.error("Error en consulta", e);
}

// Evitar
PreparedStatement stmt = null;
try {
    stmt = conn.prepareStatement(query);
    // ...
} finally {
    if (stmt != null) stmt.close();
}
```

### Seguridad

**SIEMPRE** usar PreparedStatement para consultas SQL:

```java
// Correcto
String query = "SELECT * FROM usuarios WHERE nickname = ?";
PreparedStatement stmt = conn.prepareStatement(query);
stmt.setString(1, nickname);

// NUNCA hacer esto (vulnerable a SQL injection)
String query = "SELECT * FROM usuarios WHERE nickname = '" + nickname + "'";
```

---

## Proceso de Pull Request

### Antes de Enviar

- [ ] El código compila sin errores
- [ ] Todas las pruebas pasan
- [ ] El código sigue los estándares del proyecto
- [ ] Se agregó documentación JavaDoc
- [ ] Se actualizó README.md si es necesario
- [ ] Los commits tienen mensajes descriptivos

### Formato de Commits

Usar [Conventional Commits](https://www.conventionalcommits.org/):

```
tipo(alcance): descripción breve

[cuerpo opcional]

[footer opcional]
```

**Tipos:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Formato de código (no afecta funcionalidad)
- `refactor`: Refactorización de código
- `test`: Agregar o modificar pruebas
- `chore`: Tareas de mantenimiento

**Ejemplos:**
```
feat(inventario): Agregar búsqueda por código de barras
fix(login): Corregir validación de contraseñas vacías
docs(readme): Actualizar instrucciones de instalación
refactor(dao): Separar lógica de conexión a BD
```

### Proceso de Revisión

1. Un mantenedor revisará tu PR
2. Puede solicitar cambios o mejoras
3. Una vez aprobado, se fusionará con la rama principal
4. Tu contribución será reconocida en los créditos

---

## Reportar Bugs

### Antes de Reportar

- Verifica que no exista un issue similar
- Asegúrate de usar la última versión
- Intenta reproducir el error consistentemente

### Plantilla de Reporte

```markdown
**Descripción del Bug**
Descripción clara y concisa del problema.

**Pasos para Reproducir**
1. Ve a '...'
2. Haz clic en '...'
3. Observa el error

**Comportamiento Esperado**
Qué debería haber sucedido.

**Capturas de Pantalla**
Si aplica, agrega capturas de pantalla.

**Entorno:**
- OS: [ej: Windows 10]
- Java Version: [ej: 17.0.2]
- WampServer: [ej: 3.3.0]
- MySQL: [ej: 5.7.40]

**Información Adicional**
Cualquier otro contexto relevante.
```

---

## Sugerir Mejoras

### Formato de Sugerencia

```markdown
**¿Tu sugerencia está relacionada con un problema?**
Descripción del problema. Ej: Siempre es frustrante cuando [...]

**Describe la solución que te gustaría**
Descripción clara de lo que quieres que suceda.

**Describe alternativas que hayas considerado**
Otras soluciones o características que hayas considerado.

**Contexto adicional**
Cualquier otro contexto o captura de pantalla sobre la sugerencia.
```

---

## Áreas que Necesitan Ayuda

Actualmente buscamos contribuciones en:

1. **Migración a Maven/Gradle** para mejor gestión de dependencias
2. **Implementación de pruebas unitarias** con JUnit
3. **Mejoras de seguridad** (ver SECURITY_RECOMMENDATIONS.md)
4. **Refactorización** de clases grandes (ver BEST_PRACTICES.md)
5. **Internacionalización** (soporte para múltiples idiomas)
6. **Documentación** de API y guías de usuario

---

## Recursos Útiles

- [Revisión del Código](REVIEW.md)
- [Recomendaciones de Seguridad](SECURITY_RECOMMENDATIONS.md)
- [Mejores Prácticas](BEST_PRACTICES.md)
- [Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Git Branching Model](https://nvie.com/posts/a-successful-git-branching-model/)

---

## Licencia

Al contribuir a Almazen, aceptas que tus contribuciones serán licenciadas bajo los mismos términos del proyecto.

---

## Contacto

Para preguntas o discusiones:

- Abrir un [Issue](https://github.com/DevG84/Almazen-Swing/issues)
- Contactar a los mantenedores del proyecto

---

**¡Gracias por contribuir a Almazen!** 🎉

Cada contribución, sin importar qué tan pequeña sea, hace que este proyecto sea mejor.
