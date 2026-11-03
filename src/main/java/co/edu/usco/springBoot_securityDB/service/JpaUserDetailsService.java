package co.edu.usco.springBoot_securityDB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.usco.springBoot_securityDB.model.User;
import co.edu.usco.springBoot_securityDB.repository.UserRepository;

/**
 * Lee el usuario y sus roles desde la base de datos (tablas users, roles y
 * users_roles) en lugar de tenerlos en memoria.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		// Cada rol se vuelve una authority: "USER", "CREATOR", "EDITOR", "ADMIN"
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
				.toList();

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword())
				.disabled(!user.isEnabled())
				.authorities(authorities)
				.build();
	}

}
