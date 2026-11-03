package co.edu.usco.springBoot_securityDB.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.edu.usco.springBoot_securityDB.model.Role;
import co.edu.usco.springBoot_securityDB.model.User;
import co.edu.usco.springBoot_securityDB.repository.RoleRepository;
import co.edu.usco.springBoot_securityDB.repository.UserRepository;

/**
 * Al arrancar la aplicacion crea los roles y los usuarios de prueba (si aun no
 * existen) guardando las claves hasheadas con BCrypt.
 */
@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		Role user = crearRol("USER");
		Role creator = crearRol("CREATOR");
		Role editor = crearRol("EDITOR");
		Role admin = crearRol("ADMIN");

		crearUsuario("admin", "admin123", Set.of(admin));
		crearUsuario("creator", "creator123", Set.of(creator));
		crearUsuario("editor", "editor123", Set.of(editor));
		crearUsuario("user", "user123", Set.of(user));
	}

	private Role crearRol(String name) {
		return roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(null, name)));
	}

	private void crearUsuario(String username, String clave, Set<Role> roles) {
		if (userRepository.findByUsername(username).isEmpty()) {
			userRepository.save(new User(null, username, passwordEncoder.encode(clave), true, roles));
		}
	}

}
