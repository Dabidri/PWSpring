# springBoot_securityDB — Spring Security con usuarios y roles en MySQL

Tarea de Programación Web (USCO). Basado en `springBoot_securityMemory` y `springBoot_jpa`,
pero ahora los usuarios **se leen desde MySQL** y sus contraseñas van **hasheadas con BCrypt**.

## Qué incluye

| Requisito | Dónde |
|---|---|
| 3 tablas (`users`, `roles`, `users_roles`) — un usuario puede tener muchos roles | `model/User.java`, `model/Role.java` (Lombok + JPA `@ManyToMany`) |
| Leer usuario desde la BD | `service/JpaUserDetailsService.java` |
| Encriptación de Spring Security / hashear contraseñas | `BCryptPasswordEncoder` en `config/SecurityConfig.java`, usado en `config/DataInitializer.java` |
| Configuración de seguridad como la diapositiva | `config/SecurityConfig.java` |
| Páginas personalizadas login / logout / 403 | `src/main/webapp/WEB-INF/views/login.jsp`, `logout.jsp`, `403.jsp` |
| Rutas `/`, `/new`, `/edit/**`, `/delete/**` | `controller/HelloController.java` |

### Permisos

| Ruta | Roles |
|---|---|
| `/` (lista de productos) | USER, CREATOR, EDITOR, ADMIN |
| `/new` | ADMIN, CREATOR |
| `/edit/**` | ADMIN, EDITOR |
| `/delete/**` | ADMIN |

Si el rol no alcanza, Spring Security manda a la página personalizada **`/403`**.

### Usuarios de prueba (se crean solos al arrancar)

| Usuario | Clave | Roles |
|---|---|---|
| admin | admin123 | ADMIN |
| creator | creator123 | CREATOR |
| editor | editor123 | EDITOR |
| user | user123 | USER |

## Cómo ejecutarlo en Eclipse

1. Tener **MySQL** encendido (usuario `root`, clave `1234`; si la tuya es otra, cámbiala en
   `src/main/resources/application.properties`).
2. Tener **Lombok instalado en Eclipse** (ejecutar `lombok.jar` y señalar `eclipse.exe`), si no,
   Eclipse marcará errores en los getters/setters.
3. `File > Import > Maven > Existing Maven Projects` y elegir esta carpeta.
4. Clic derecho en `SpringBootSecurityDbApplication.java` → `Run As > Spring Boot App` (o `Java Application`).
5. Abrir <http://localhost:8080>.

Al arrancar, JPA crea la base `securitydb` y las tablas (`ddl-auto=update`) y `DataInitializer`
inserta los roles y los usuarios con la clave hasheada. No hace falta tocar MySQL Workbench.

Si el profesor pide crear las tablas a mano, está el script opcional
`src/main/resources/db/script.sql` (con los hashes BCrypt ya generados).

## Pruebas

`SecurityRulesTests` verifica el login contra la BD, que las claves estén en BCrypt y los 403 por rol
(usa H2 en memoria, no necesita MySQL): clic derecho en el proyecto → `Run As > Maven test`.
