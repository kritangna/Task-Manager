package com.todomanagement.todo_managemnet.service.impl;

import com.todomanagement.todo_managemnet.dto.LoginDto;
import com.todomanagement.todo_managemnet.dto.RegisterDto;
import com.todomanagement.todo_managemnet.entity.Role;
import com.todomanagement.todo_managemnet.entity.User;
import com.todomanagement.todo_managemnet.exception.TodoAPIException;
import com.todomanagement.todo_managemnet.repositoty.RoleRepository;
import com.todomanagement.todo_managemnet.repositoty.UserRepository;
import com.todomanagement.todo_managemnet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterDto registerDto) {
        // check username is available in th db
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists: " + registerDto.getUsername());
        }

        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists: " + registerDto.getEmail());
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User Registered Successfully!";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged in successfully!";
    }
}
