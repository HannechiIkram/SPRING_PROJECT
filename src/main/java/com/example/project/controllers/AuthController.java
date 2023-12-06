package com.example.project.controllers;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.project.models.ERole;
import com.example.project.models.ProfileImage;
import com.example.project.models.Role;
import com.example.project.models.User;
import com.example.project.payload.response.MessageResponse;
import com.example.project.payload.response.UserInfoResponse;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.payload.request.LoginRequest;
import com.example.project.payload.request.SignupRequest;
import com.example.project.security.jwt.JwtUtils;
import com.example.project.security.services.UserDetailsImpl;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    MailSender mailSender ;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService ;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

  @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/{userId}/addImage")
    public ResponseEntity<String> addImageToUser(@PathVariable Long userId,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            // Validate if the user exists
            User user = userService.getUserById(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            byte[] imageData = file.getBytes();

            // Create a new ProfileImage entity and associate it with the user
            ProfileImage profileImage = new ProfileImage();
            profileImage.setImage(imageData);
            profileImage.setUser(user);
            user.setProfileImage(profileImage);

            // Save the user (this will cascade to save the associated profile image)
            userService.updateUsser(user);

            return new ResponseEntity<>("Image added successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return new ResponseEntity<>("Error uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
 @PostMapping("/signup")
 public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
                                       @RequestParam("image") MultipartFile image) {
     try {
         if (userRepository.existsByUsername(signUpRequest.getUsername())) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
         }

         if (userRepository.existsByEmail(signUpRequest.getEmail())) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
         }

         // Handle image upload
         String imageName = storeImage(image);

         // Create new user's account
         User user = new User(signUpRequest.getUsername(),
                 signUpRequest.getEmail(),
                 encoder.encode(signUpRequest.getPassword()),
                 imageName);

         Set<String> strRoles = signUpRequest.getRole();
         Set<Role> roles = new HashSet<>();

         if (strRoles == null) {
             Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                     .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
             roles.add(userRole);
         } else {
             strRoles.forEach(role -> {
                 switch (role) {
                     case "admin":
                         Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                 .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                         roles.add(adminRole);

                         break;
                     case "mod":
                         Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                 .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                         roles.add(modRole);

                         break;
                     default:
                         Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                 .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                         roles.add(userRole);
                 }
             });
         }

         user.setRoles(roles);
         userRepository.save(user);

         return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
     }
 }
*/
    private String storeImage(MultipartFile image) throws IOException {
        // Customize this method based on how you want to store and manage images
        // For example, you might save the image to a specific folder or a database
        // Here, we are just using a simple example to save the image with a unique name

        String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get("path-to-your-image-upload-folder" + imageName);

        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return imageName;
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") Long userId,@RequestBody User updatedUser){
        return userService.updateUser(userId,updatedUser);
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




}
