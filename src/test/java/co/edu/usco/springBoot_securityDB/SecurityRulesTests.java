package co.edu.usco.springBoot_securityDB;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import co.edu.usco.springBoot_securityDB.model.User;
import co.edu.usco.springBoot_securityDB.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityRulesTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void clavesGuardadasConBCrypt() {
		User admin = userRepository.findByUsername("admin").orElseThrow();
		assertThat(admin.getPassword()).startsWith("$2a$").isNotEqualTo("admin123");
		assertThat(passwordEncoder.matches("admin123", admin.getPassword())).isTrue();
	}

	@Test
	void loginDesdeBaseDeDatos() throws Exception {
		mvc.perform(formLogin().user("admin").password("admin123")).andExpect(authenticated())
				.andExpect(redirectedUrl("/"));
		mvc.perform(formLogin().user("admin").password("mala")).andExpect(unauthenticated())
				.andExpect(redirectedUrl("/login?error"));
	}

	@Test
	void sinSesionVaAlLoginPersonalizado() throws Exception {
		mvc.perform(get("/")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
		mvc.perform(get("/login")).andExpect(forwardedUrl("/WEB-INF/views/login.jsp"));
	}

	@Test
	void permisosPorRol() throws Exception {
		mvc.perform(get("/").with(user("u").authorities(() -> "USER"))).andExpect(status().isOk());
		mvc.perform(get("/new").with(user("u").authorities(() -> "USER"))).andExpect(status().isForbidden())
				.andExpect(forwardedUrl("/403"));
		mvc.perform(get("/new").with(user("c").authorities(() -> "CREATOR"))).andExpect(status().isOk());
		mvc.perform(get("/edit/1").with(user("c").authorities(() -> "CREATOR"))).andExpect(status().isForbidden());
		mvc.perform(get("/delete/1").with(user("e").authorities(() -> "EDITOR"))).andExpect(status().isForbidden());
		mvc.perform(get("/403").with(user("u").authorities(() -> "USER")))
				.andExpect(forwardedUrl("/WEB-INF/views/403.jsp"));
	}

}
