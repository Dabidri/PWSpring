package co.edu.usco.springBoot_securityDB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

	// Encriptacion de Spring Security: las claves se guardan con hash BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// Deja pasar el forward interno hacia las vistas JSP
				.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
				.requestMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
				.requestMatchers("/new", "/save").hasAnyAuthority("ADMIN", "CREATOR")
				.requestMatchers("/edit/**", "/update/**").hasAnyAuthority("ADMIN", "EDITOR")
				.requestMatchers("/delete/**").hasAuthority("ADMIN")
				.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.failureUrl("/login?error")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.exceptionHandling(ex -> ex.accessDeniedPage("/403"));
		return http.build();
	}
}
