package com.practice.recipes.security;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("currentUsername")
    public String currentUsername(Principal principal) {
        return principal != null ? principal.getName() : null;
    }
}
