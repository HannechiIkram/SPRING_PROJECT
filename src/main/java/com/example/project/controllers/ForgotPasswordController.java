package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.jwt.JwtUtils;
import com.example.project.security.services.UserDetailsImpl;
import com.example.project.security.services.UserDetailsServiceImpl;
import com.example.project.service.EmailService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/reset")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,allowCredentials="true")

public class ForgotPasswordController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils ;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password/{email}")
    public String processForgotPasswordForm(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            // Handle case where email is not found
            return "redirect:/forgot-password?error";
        }
        String resetToken = jwtUtils.generateTokenForPasswordReset(user.getUsername());
        user.setResetToken(resetToken);
        userRepository.save(user);

        String resetLink = "http://localhost:4200/reset-password/" + resetToken;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

        return "redirect:/forgot-password?success"+resetToken;
    }
    @PostMapping("/reset-password")
    public String processResetPasswordForm(@RequestParam String token,
                                           @RequestBody String password) {
        User user = userService.findByResetToken(token);
        if (user == null) {
            return "redirect:/login?error";
        }
        userService.resetPassword(user, password);
        return "redirect:/login?passwordResetSuccess";
    }
    @PutMapping("/upuser")
    public User updateUsser(User user){
        return this.userService.updateUsser(user);
    }


    @PostMapping("/reset-passwordd")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody String password, HttpServletResponse response) {
        // Validate the token
        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            ResponseCookie cleanJwtCookie = jwtUtils.getCleanJwtCookie();
            response.addHeader(HttpHeaders.SET_COOKIE, cleanJwtCookie.toString());
            User user = userService.findByResetToken(token);

            userService.resetPassword(user, password);
            System.out.println("New Password (Encoded): " + user.getPassword());

            // Re-authenticate the user and generate a new token

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie((UserDetailsImpl) userDetails);

            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
