package com.thesis.medicalapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.payload.SignupRequest;
import com.thesis.medicalapp.payload.VerificationRequest;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.UserDTO;
import com.thesis.medicalapp.repository.UserRepository;
import com.thesis.medicalapp.services.OTPService;
import com.thesis.medicalapp.services.SmsService;
import com.thesis.medicalapp.services.UserService;
import com.twilio.exception.ApiException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final SmsService smsService;
    private final OTPService otpService;
    private final UserRepository userRepository;

    @GetMapping("/users/all")
    public ResponseEntity<Object>getAllUser() {
        List<UserDTO> userDTOS = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(userDTOS)
        );
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        List<UserDTO> userDTOS = userService.findAllByRoles_Name("ROLE_USER");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(userDTOS)
        );
    }
    @PostMapping("/auth/register")
    public ResponseEntity<Object>addUser(@RequestBody @Valid SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        if (userService.existsByUsername(username)) {
                User user_request = userService.getUser(username);
                if (user_request.getEnabled())
                    throw new ApiRequestException("Tên đăng nhập đã tồn tại!");
        }
        User userDB;
        String phone_request = signupRequest.getPhone();
        if (userService.existsByPhone(phone_request)) {
            userDB = userRepository.findByPhone(phone_request).get();
            if (userDB.getEnabled())
                throw new ApiRequestException("Số điện thoại đã tồn tại!");
        } else {
            User user = new User(
                    null,
                    signupRequest.getUsername(),
                    signupRequest.getPhone(),
                    signupRequest.getPassword(),
                    false,
                    null,
                    new ArrayList<>()
            );
            userDB = userService.saveUser(user);
            String role = "";
            if (signupRequest.getRole() == null) role = "ROLE_USER";
            else role = signupRequest.getRole();
            userService.addRoleToUser(username, role);
        }
        OTP otp = new OTP();
        otp.setToken(OTP.generateOTP());
        OTP otpDB = otpService.generateOTP(userDB);
        if (otpDB == null) throw new ApiRequestException("Không tìm thấy OTP!", HttpStatus.NOT_FOUND);
        Sms sms = new Sms();
        sms.setTo(signupRequest.getPhone());
        sms.setMessage(otpDB.getToken());
        smsService.sendSMS(sms);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(HttpStatus.OK.value(), "Đăng ký thành công!")
        );
    }
    @PostMapping("/auth/register/verify")
    public ResponseEntity<Object> verifyUser(@RequestBody @Valid VerificationRequest verificationRequest) throws Exception {
        String token = verificationRequest.getOtp();
        otpService.verifyUser(verificationRequest.getUsername(), token);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(HttpStatus.OK.value(), "Xác nhận thành công!")
        );
    }

    @PatchMapping(value ="/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> partialUpdateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO userRes = userService.partialUpdateUser(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(userRes)
        );
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getPhone())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("fresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            }

        } else {
            throw new RuntimeException("Refresh toke is missing");
        }
    }
}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
