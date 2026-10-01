package com.practice.recipes.user;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                           BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("confirmPassword")
              && form.getPassword() != null
              && !form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "The passwords don't match.");
        }
        if (!bindingResult.hasFieldErrors("username") && userService.usernameTaken(form.getUsername())) {
            bindingResult.rejectValue("username", "taken", "The username is already taken.");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.register(form);

        try {
            request.login(form.getUsername(), form.getPassword());
        } catch (ServletException e) {
            return "redirect:/login?registered";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Welcome, " + form.getUsername() + "! You have successfully registered.");
        return "redirect:/recipes";
    }
}
