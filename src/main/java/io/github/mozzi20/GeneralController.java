package io.github.mozzi20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.mozzi20.user.UserService;

@Controller
public class GeneralController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	String home(Model model) {
		model.addAttribute("userCount", userService.userCount());
		return "index";
	}
	
	@GetMapping("/admin")
	String admin(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin";
	}
	
}
