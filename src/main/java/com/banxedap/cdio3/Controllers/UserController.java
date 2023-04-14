package com.banxedap.cdio3.Controllers;


import com.banxedap.cdio3.Config.JwtUtils;
import com.banxedap.cdio3.DTO.UserDTO;
import com.banxedap.cdio3.Entities.User;
import com.banxedap.cdio3.Request.CreateUserRequest;
import com.banxedap.cdio3.Request.LoginRequest;
import com.banxedap.cdio3.Request.UpdateUserRequest;
import com.banxedap.cdio3.Services.UserDetailServiceImp;
import com.banxedap.cdio3.Services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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
    @PostMapping("api/user/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UpdateUserRequest userRequest){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User auth = userService.getUserByUserName(user.getUsername());
        User found = userService.getUserById(userRequest.getId());
        if(auth.getId() == userRequest.getId() || auth.getRole().toUpperCase().equalsIgnoreCase("ADMIN")  ){
            User userUpdate = userService.updateUser(found, userRequest);
            return new ResponseEntity<>(userUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("api/user/{userName}")
    public ResponseEntity<Object> getInfor(@PathVariable("userName") String userName){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User auth = userService.getUserByUserName(user.getUsername());
        User found = userService.getUserByUserName(userName);
        if(auth.getRole().toUpperCase().equalsIgnoreCase("ADMIN") || auth.getUserName().equalsIgnoreCase(userName))
            return new ResponseEntity<>(found, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("api/auth/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.getUserByUserName(loginRequest.getUserName());
        UserDTO userDTO = new UserDTO(user.getUserName(), jwtUtils.generateJwtToken(user),user.getRole());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("api/admin/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
}
