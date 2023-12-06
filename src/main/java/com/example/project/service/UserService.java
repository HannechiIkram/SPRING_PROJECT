package com.example.project.service;

import com.example.project.models.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();


    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User updateUsser(User user){
       /* String saleh="saleh";
        user.setPassword(encoder.encode(saleh));*/
        return userRepository.save(user) ;
    }
    public User updateUser(Long userId, User updatedUser) {
        // Check if the user with the given ID exists
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update the fields with the new values
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        String encodedpassword=encoder.encode(updatedUser.getPassword());
        existingUser.setPassword(encodedpassword);
        existingUser.setRoles(updatedUser.getRoles());

        // Save the updated user
        return userRepository.save(existingUser);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void resetPassword(User user, String newPassword) {
        // Load the existing user from the database

        // Log the old and new passwords
        System.out.println("Old Password: " + user.getPassword());
        System.out.println("New Password (Plain): " + newPassword);

        // Update user details (if needed)
        // Encode and update the password


        System.out.println(user.getId());
        String encodedPassword = encoder.encode(newPassword);
        user.setPassword(encodedPassword);

        // Save the updated user
        userRepository.save(user);

        // Log the new password after encoding
        System.out.println("New Password (Encoded): " + user.getPassword());
    }

    public User getUserById(long userId){
        return this.userRepository.findById(userId).get();
    }

    public void resetPassword1(User user, String newPassword) {
        String oldPassword = user.getPassword();

        // Log the old and new passwords
        System.out.println("Old Password: " + oldPassword);
        System.out.println("New Password (Plain): " + newPassword);

        user.setPassword(encoder.encode(newPassword));
        User us=user ;
     /*   us.
        userRepository.save(user);

      /*  updateUser(user.getId(), user);*/

        // Log the new password after encodings
        System.out.println("New Password (Encoded): " + user.getPassword());
    }


    public User findByResetToken(String token){
        return this.userRepository.findByResetToken(token);
    }


}

