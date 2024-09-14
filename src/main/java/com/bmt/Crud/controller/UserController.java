package com.bmt.Crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.Crud.models.User;
import com.bmt.Crud.models.UserDto;
import com.bmt.Crud.repositories.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository reponn;

    // List all users (Optional: used for admin purposes)
    @GetMapping
    public String getUsers(Model model) {
        List<User> users = reponn.getUsers(); // Correct variable name
        model.addAttribute("users", users);
        return "users/list"; // Update view if necessary
    }

    // Show login or sign-up page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        UserDto userDto = new UserDto(); // Initialize UserDto for the form
        model.addAttribute("userDto", userDto);
        return "users/login";
    }

    // Handle sign-up POST request
    @PostMapping("/login")
    public String createUser(
        @Valid @ModelAttribute("userDto") UserDto userDto, // Validates the DTO
        BindingResult result) {

        // Check if the email is already in use
        if (reponn.getUser(userDto.getEmail()) != null) {
            result.addError(new FieldError("userDto", "email", userDto.getEmail(),
                    false, null, null, "Email address is already in use"));
        }

        // Return to login form if validation errors exist
        if (result.hasErrors()) {
            return "users/login";
        }

        // Create new User entity and save to the database
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        reponn.createUser(user); // Persist the new user

        // Redirect to login page or another appropriate page after success
        return "redirect:/users/login";  // Consider redirecting to /users/dashboard or homepage
    }
}
