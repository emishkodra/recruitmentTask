package com.developer.recruitmentTask.config.security.contoller;

import com.developer.recruitmentTask.config.security.jwt.pojo.JwtRequest;
import com.developer.recruitmentTask.config.security.jwt.pojo.RegisterRequest;
import com.developer.recruitmentTask.config.security.jwt.pojo.JwtResponse;
import com.developer.recruitmentTask.config.security.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "The Authentication API With Description Tag Annotation")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Sign Up")
    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> saveUser(@RequestBody RegisterRequest request){
        JwtResponse authResponse = authService.createUser(request);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Log In")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(authService.authenticate(jwtRequest));
    }
}
