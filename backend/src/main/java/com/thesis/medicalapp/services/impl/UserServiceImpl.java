package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.Role;
import com.thesis.medicalapp.models.User;
import com.thesis.medicalapp.pojo.UserDTO;
import com.thesis.medicalapp.repository.HospitalRepository;
import com.thesis.medicalapp.repository.RoleRepository;
import com.thesis.medicalapp.repository.UserRepository;
import com.thesis.medicalapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final HospitalRepository hospitalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUsername(username);
        if(!userOp.isPresent() || !userOp.get().getEnabled()) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        User user = userOp.get();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            if (role.getName().equals("ROLE_HOSPITAL_ADMIN")) {
                Hospital hospital = hospitalRepository.findByName("Bệnh Viện Đại Học Y Dược HCM");
                authorities.add(new SimpleGrantedAuthority(hospital.getId()));
            }
        });
        authorities.add(new SimpleGrantedAuthority(user.getId()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Optional<User> userOp = userRepository.findByUsername(username);
        User user = userOp.get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll()
                .stream()
                .collect(Collectors.toList());
        List<UserDTO> userDTOS = users.stream().map(u -> {
            UserDTO userDTO = UserDTO.from(u);
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<UserDTO> findAllByRoles_Name(String role) {
        List<User> users = userRepository.findAllByRoles_Name(role)
                .stream()
                .collect(Collectors.toList());
        List<UserDTO> userDTOS = users.stream().map(u -> {
            UserDTO userDTO = UserDTO.from(u);
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public UserDTO partialUpdateUser(String id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
           throw new UsernameNotFoundException("User id not found!");
        }
        User user = optionalUser.get();
        if (userDTO.getImageUrl() != null)
            user.setImageUrl(userDTO.getImageUrl());
        userRepository.save(user);
        return UserDTO.from(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) throw new UsernameNotFoundException("Username not found!");
        return user.get();
    }
}
