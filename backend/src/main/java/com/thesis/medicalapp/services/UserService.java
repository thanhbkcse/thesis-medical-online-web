package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Role;
import com.thesis.medicalapp.models.User;
import com.thesis.medicalapp.pojo.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<UserDTO>getUsers();
    Boolean existsByPhone(String phone);
    Boolean existsByUsername(String username);
    List<UserDTO> findAllByRoles_Name(String role);
    UserDTO partialUpdateUser(String id, UserDTO userDTO);
    User findByUsername(String username);
}
