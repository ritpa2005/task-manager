package com.ritesh.task_manager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ritesh.task_manager.dto.LoginDto;
import com.ritesh.task_manager.dto.RegisterDto;
import com.ritesh.task_manager.enums.UserRole;
import com.ritesh.task_manager.model.User;
import com.ritesh.task_manager.repository.UserRepository;
import com.ritesh.task_manager.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
