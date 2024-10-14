package com.example.backend.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// This class is used for hashing passwords using the BCrypt algorithm, which is a strong and adaptive hashing algorithm
// designed to be computationally intensive to resist brute-force attacks.
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    // Method to handle the home page request
    @GetMapping("")
    public String viewHomePage() {
        // Returns the name of the view to be rendered for the home page
        return "index";
    }
    
    // Method to handle the registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Adds a new Users object to the model to be used in the registration form
        model.addAttribute("user", new User());
        // Returns the name of the view to be rendered for the registration form
        return "signup_form";
    }

    // Method to handle the registration form submission
    @PostMapping("/process_register")
    public String processRegister(User user) {
        // Create a BCryptPasswordEncoder instance to encode the user's password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user.getPassword() == null) {

            throw new IllegalArgumentException("Password cannot be null");
    
        }
        // Encode the user's password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // Set the encoded password back to the user object
        user.setPassword(encodedPassword);
        // Save the user object to the repository
        userRepo.save(user);
        // Returns the name of the view to be rendered after successful registration
        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
