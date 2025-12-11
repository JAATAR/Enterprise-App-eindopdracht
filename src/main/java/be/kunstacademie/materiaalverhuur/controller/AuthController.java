package be.kunstacademie.materiaalverhuur.controller;

import be.kunstacademie.materiaalverhuur.model.User;
import be.kunstacademie.materiaalverhuur.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Ongeldige gebruikersnaam of wachtwoord");
        }
        if (logout != null) {
            model.addAttribute("message", "U bent succesvol uitgelogd");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               @RequestParam String confirmPassword,
                               Model model,
                               RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Wachtwoorden komen niet overeen");
            return "register";
        }

        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Gebruikersnaam bestaat al");
            return "register";
        }

        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email is al geregistreerd");
            return "register";
        }

        try {
            userService.registerUser(user);
            redirectAttrs.addFlashAttribute("success", "Registratie succesvol! U kunt nu inloggen.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registratie mislukt: " + e.getMessage());
            return "register";
        }
    }
}
