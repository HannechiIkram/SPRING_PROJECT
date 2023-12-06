package com.example.project.repository;

import com.example.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    User findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    boolean updatePassword(@Param("email") String email, @Param("password") String password);

    User findByResetToken(String token);

}
