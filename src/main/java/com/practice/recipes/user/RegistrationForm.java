package com.practice.recipes.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message = "Please choose a username")
    @Size(min = 3, max = 30, message = "The username must be 3 to 30 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Use only letters, digits and underscores.")
    private String username;

    @NotBlank(message = "Please choose a password.")
    @Size(min = 8, max = 72, message = "The password must be 8 to 72 characters.")
    private String password;

    @NotBlank(message = "Please repeat the password")
    private String confirmPassword;

    public RegistrationForm() {

    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
