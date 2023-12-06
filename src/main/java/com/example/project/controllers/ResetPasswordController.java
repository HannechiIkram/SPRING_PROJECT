package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reset")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,allowCredentials="true")

public class ResetPasswordController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String showResetPasswordForm(@RequestParam("token") String token) {
        User user = userService.findByResetToken(token);

        if (user == null) {
            // Handle invalid or expired token
            return "Invalid or expired token";
        }

        // You might return a JSON response or handle the reset process here
        return "Reset password for user: " + user.getUsername();
    }

   /* @PostMapping("/reset-password")
    public String processResetPasswordForm(@PathVariable("token") String token,
                                           @PathVariable("password") String password) {
        User user = userService.findByResetToken(token);

        if (user == null) {
            // Handle invalid or expired token
            return "redirect:/login?error";
        }

        // Reset the password and clear the reset token
        userService.resetPassword(user, password);

        return "redirect:/login?passwordResetSuccess";
    }*/
}
