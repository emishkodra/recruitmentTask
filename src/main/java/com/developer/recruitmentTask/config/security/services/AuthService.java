package com.developer.recruitmentTask.config.security.services;

import com.developer.recruitmentTask.config.security.jwt.JwtService;
import com.developer.recruitmentTask.config.security.jwt.pojo.JwtRequest;
import com.developer.recruitmentTask.config.security.jwt.pojo.RegisterRequest;
import com.developer.recruitmentTask.config.security.jwt.pojo.JwtResponse;
import com.developer.recruitmentTask.config.security.repository.JwtUserRepository;
import com.developer.recruitmentTask.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private JwtUserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createUser(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .isEnabled(true)
                .build();
        var savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return JwtResponse.builder().jwtToken(jwtToken).build();
    }

    public JwtResponse authenticate(JwtRequest jwtRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername(),
                jwtRequest.getPassword())
        );

        var user = userRepository.findByUsername(jwtRequest.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return JwtResponse.builder().jwtToken(jwtToken).build();
    }
}
