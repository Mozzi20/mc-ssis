package io.github.mozzi20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mozzi20.user.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	String admin(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin";
	}
	
	@PostMapping("/admin/ban")
	String toggleBan(
			@RequestParam("userId") String userId
			) {
		userService.toggleBan(userId);
		return "redirect:/admin";
	}
	
}
