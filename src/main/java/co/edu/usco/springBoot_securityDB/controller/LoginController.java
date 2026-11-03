package co.edu.usco.springBoot_securityDB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Paginas personalizadas de login, logout y error 403.
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// Pagina de confirmacion; el cierre real lo hace Spring Security con POST /logout
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

	@GetMapping("/403")
	public String accesoDenegado() {
		return "403";
	}

}
