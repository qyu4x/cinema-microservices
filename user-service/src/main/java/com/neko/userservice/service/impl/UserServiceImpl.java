package com.neko.userservice.service.impl;

import com.neko.userservice.config.PasswordEncoderConfiguration;
import com.neko.userservice.exception.DataAlreadyExistsException;
import com.neko.userservice.exception.DataNotFoundException;
import com.neko.userservice.exception.NotMatchException;
import com.neko.userservice.model.entity.Role;
import com.neko.userservice.model.entity.User;
import com.neko.userservice.model.enums.UserRole;
import com.neko.userservice.model.request.UserRequest;
import com.neko.userservice.model.request.UserUpdateRequest;
import com.neko.userservice.model.response.UserResponse;
import com.neko.userservice.repository.RoleRespository;
import com.neko.userservice.repository.UserRepository;
import com.neko.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRespository roleRepository;

    private final PasswordEncoderConfiguration passwordEncoderConfiguration;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRespository roleRepository, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        log.info("do register new user account");
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            log.warn("user with email {} is already exist", userRequest.getEmail());
            throw new DataAlreadyExistsException(
                    "There is an account with that email adress:" + userRequest.getEmail()
            );
        } else {
            Set<Role> userRoles = new HashSet<>();
            Role roleUser = roleRepository.findByRole(UserRole.USER).get();
            Role roleCustomer = roleRepository.findByRole(UserRole.CUSTOMER).get();
            userRoles.add(roleUser);
            userRoles.add(roleCustomer);

            User user = userRequest.toUser();
            user.setId(UUID.randomUUID().toString());
            user.setRoles(userRoles);
            user.setPassword(
                    passwordEncoderConfiguration.passwordEncoder().encode(user.getPassword())
            );
            User registeredUser = userRepository.save(user);
            log.info("successfuly register user with email {} ", userRequest.getEmail());
            return UserResponse.builder()
                    .id(registeredUser.getId())
                    .name(registeredUser.getName())
                    .email(registeredUser.getEmail())
                    .phone(registeredUser.getPhone())
                    .build();
        }
    }

    @Override
    public UserResponse update(UserUpdateRequest userRequest, String id) {
        log.info("do upadate user with user id {} ", id);
        User toUser = userRequest.toUser();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User updateUser = user.get();
            boolean match = passwordEncoderConfiguration.passwordEncoder().matches(userRequest.getOldPassword(), updateUser.getPassword());
            if (match) {
                updateUser.setName(toUser.getName());
                updateUser.setEmail(toUser.getEmail());
                updateUser.setUpdatedAt(LocalDateTime.now());
                updateUser.setPassword(
                        passwordEncoderConfiguration.passwordEncoder().encode(userRequest.getNewPassword())
                );

                User updatedUser = userRepository.save(updateUser);
                log.info("successfuly update user with id {} ", id);
                return UserResponse.builder()
                        .id(updatedUser.getId())
                        .name(updatedUser.getName())
                        .email(updateUser.getEmail())
                        .phone(updateUser.getPhone())
                        .build();
            } else {
                log.error("passwords don't match for user id {}", id);
                throw new NotMatchException("Opps, passwords don't match");
            }
        } else {
            log.info("user id {} not found", id);
            throw new DataNotFoundException("user id " + id + " not found");
        }
    }

    @Override
    public void delete(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("successfully delet user with id {}", id);
        } else {
            log.info("user with id {} not found", id);
            throw new DataNotFoundException("user with id " + id + "not found");
        }
    }

    @Override
    public UserResponse findById(String id) {
        log.info("do find user by id {} ", id);
        Optional<User> userResposne = userRepository.findById(id);
        if (userResposne.isEmpty()) {
            log.warn("user with id {} not found", id);
            throw new DataNotFoundException("user with id " + id + " not found");
        } else {
            log.info("successfully get user with id {}", id);
            User user = userResposne.get();
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone()).build();
        }
    }
}
