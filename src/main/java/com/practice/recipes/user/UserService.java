package com.practice.recipes.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public AppUser register(RegistrationForm form) {
        String hash = passwordEncoder.encode(form.getPassword());
        return userRepository.save(new AppUser(form.getUsername(), hash));
    }
}
