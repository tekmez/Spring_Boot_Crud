package com.tekmez.crud.service;

import com.tekmez.crud.enums.Role;
import com.tekmez.crud.interfaces.IUserService;
import com.tekmez.crud.model.dto.RegisterRequest;
import com.tekmez.crud.model.dto.UserResponse;
import com.tekmez.crud.model.entity.User;
import com.tekmez.crud.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
        public UserResponse register(RegisterRequest request){
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EntityExistsException("Email already registered");
            }
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setRole(Role.USER);
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

            User saved = userRepository.save(user);
            return UserResponse.from(saved.getId(), saved.getEmail(), saved.getName(), saved.getRole());
        }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponse.from(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }
}
