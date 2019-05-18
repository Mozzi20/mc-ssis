package io.github.mozzi20.user;

import org.springframework.beans.factory.annotation.Autowired;
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
	String userPage(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("user", userService.getCurrentUser());
		return "user";
	}

	@PostMapping
	String updateUser(
			@RequestParam String username, 
			@RequestParam(defaultValue = "false") boolean confirm,
			RedirectAttributes redirect
			) {
		if (!userService.getCurrentUser().isBanned()) {
			if (confirm) {
				if (userService.usernameExists(username)) {
					redirect.addFlashAttribute("warning", "Det användarnamnet har redan registrerats.");
				} else {
					userService.updateUsername(username);
					redirect.addFlashAttribute("success", "Ditt användarnamn har sparats!");
				}
			} else {
				redirect.addFlashAttribute("warning", "Du måste acceptera våra användarvillkor.");
			}
		}
		return "redirect:/user";
	}

}
