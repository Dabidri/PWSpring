-- =====================================================================
-- Script OPCIONAL. La aplicacion ya crea las tablas (JPA, ddl-auto=update)
-- y los usuarios (DataInitializer) al ejecutarla desde Eclipse.
-- Sirve si el profesor pide crear las tablas a mano en MySQL Workbench.
-- Las claves estan hasheadas con BCrypt (clave = usuario + "123").
-- =====================================================================

CREATE DATABASE IF NOT EXISTS securitydb;
USE securitydb;

CREATE TABLE IF NOT EXISTS users (
	user_id INT(11) NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	enabled TINYINT(4) NOT NULL,
	PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS roles (
	role_id INT(11) NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL UNIQUE,
	PRIMARY KEY (role_id)
);

-- Tabla intermedia: un usuario puede tener muchos roles
CREATE TABLE IF NOT EXISTS users_roles (
	user_id INT(11) NOT NULL,
	role_id INT(11) NOT NULL,
	PRIMARY KEY (user_id, role_id),
	CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

INSERT INTO roles (name) VALUES ('USER'), ('CREATOR'), ('EDITOR'), ('ADMIN');

INSERT INTO users (username, password, enabled) VALUES
	('admin',   '$2a$10$4xalFtDrhhnDJMlYeQN9AuOirmeV75UFK6.2.B5eHLdAOS.mE1SZG', 1),
	('creator', '$2a$10$JPF0DQoePtqQEyjoPXXWy.vY6DUz/sNz.67P7TkMGoab2uEmBVQEi', 1),
	('editor',  '$2a$10$E72qy57dFU/T0dmuwGX.husUBTbBi2xd.VnPR910tTvkEHdmiyzv.', 1),
	('user',    '$2a$10$grHHGoAj2OHhTVtL9BDnx.oCQofCyxfrUwv.YAK7Ia3H/FcB.cTS.', 1);

INSERT INTO users_roles (user_id, role_id)
SELECT u.user_id, r.role_id FROM users u JOIN roles r
WHERE (u.username = 'admin'   AND r.name = 'ADMIN')
   OR (u.username = 'creator' AND r.name = 'CREATOR')
   OR (u.username = 'editor'  AND r.name = 'EDITOR')
   OR (u.username = 'user'    AND r.name = 'USER')
