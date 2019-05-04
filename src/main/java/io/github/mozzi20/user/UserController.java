package io.github.mozzi20.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	@Secured("ROLE_USER")
	String userPage(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("username", user.getUsername());
		return "user";
	}
	
	@PostMapping
	@Secured("ROLE_USER")
	String updateUser(
			@RequestParam String username,
			@RequestParam(defaultValue="false") boolean confirm,
			RedirectAttributes redirect
			) {
		if(confirm) {
			userService.updateUsername(username);
			redirect.addFlashAttribute("message", "Ditt användarnamn har sparats!");
		} else {
			redirect.addFlashAttribute("message", "Du måste acceptera våra användarvillkor.");
		}
		return "redirect:/user";
	}

}
