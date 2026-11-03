package co.edu.usco.springBoot_securityDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import co.edu.usco.springBoot_securityDB.model.Producto;
import co.edu.usco.springBoot_securityDB.service.ProductoService;

/**
 * Rutas protegidas por rol (ver SecurityConfig):
 * "/"          -> USER, CREATOR, EDITOR, ADMIN
 * "/new"       -> ADMIN, CREATOR
 * "/edit/**"   -> ADMIN, EDITOR
 * "/delete/**" -> ADMIN
 * Si el rol no alcanza, Spring Security redirige a "/403".
 */
@Controller
public class HelloController {

	@Autowired
	private ProductoService productoService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("productos", productoService.getAllProductos());
		return "index";
	}

	@GetMapping("/new")
	public String nuevo(Model model) {
		model.addAttribute("producto", new Producto());
		return "nuevo-producto";
	}

	@PostMapping("/save")
	public String guardar(@ModelAttribute Producto producto) {
		productoService.createProducto(producto);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("producto", productoService.getProductoById(id).orElseThrow());
		return "editar-producto";
	}

	@PostMapping("/update/{id}")
	public String actualizar(@PathVariable Long id, @ModelAttribute Producto producto) {
		productoService.updateProducto(id, producto);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable Long id) {
		productoService.deleteProducto(id);
		return "redirect:/";
	}

}
