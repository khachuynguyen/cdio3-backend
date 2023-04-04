package com.banxedap.cdio3.Controllers;


import com.banxedap.cdio3.Config.JwtUtils;
import com.banxedap.cdio3.DTO.UserDTO;
import com.banxedap.cdio3.Entities.User;
import com.banxedap.cdio3.Request.CreateUserRequest;
import com.banxedap.cdio3.Request.LoginRequest;
import com.banxedap.cdio3.Services.UserDetailServiceImp;
import com.banxedap.cdio3.Services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    @PostMapping("api/auth/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest userRequest) throws Exception {
            UserDTO userDTO = userService.createUser(userRequest);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PostMapping("api/auth/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.getUserByUserName(loginRequest.getUserName());
        UserDTO userDTO = new UserDTO(loginRequest.getUserName(), jwtUtils.generateJwtToken(user),user.getRole());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("api/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
}
