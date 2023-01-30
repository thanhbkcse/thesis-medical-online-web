package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);
    Boolean existsByPhone(String phone);
    Boolean existsByUsername(String username);
    List<User> findAllByRoles_Name(String name);
}
