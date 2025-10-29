package com.tekmez.crud.controller;

import com.tekmez.crud.interfaces.IUserService;
import com.tekmez.crud.model.dto.LoginRequest;
import com.tekmez.crud.model.dto.RegisterRequest;
import com.tekmez.crud.model.dto.UserResponse;
import com.tekmez.crud.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final IUserService userService;

    public AuthController(IUserService userService, AuthenticationManager authManager, JwtService jwtService ){
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginRequest request){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getEmail(), new HashMap<>());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        UserResponse created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
