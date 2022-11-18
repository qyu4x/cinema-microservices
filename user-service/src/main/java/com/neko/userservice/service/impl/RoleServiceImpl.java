package com.neko.userservice.service.impl;

import com.neko.userservice.config.PasswordEncoderConfiguration;
import com.neko.userservice.model.entity.Role;
import com.neko.userservice.model.entity.User;
import com.neko.userservice.model.enums.UserRole;
import com.neko.userservice.model.request.RoleRequest;
import com.neko.userservice.model.response.RoleResponse;
import com.neko.userservice.repository.RoleRespository;
import com.neko.userservice.repository.UserRepository;
import com.neko.userservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRespository roleRespository;
    private final UserRepository userRepository;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    public RoleServiceImpl(RoleRespository roleRespository, UserRepository userRepository, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.roleRespository = roleRespository;
        this.userRepository = userRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Override
    public RoleResponse save(RoleRequest roleRequest) {
        Role role = new Role(
                UUID.randomUUID().toString(),
                roleRequest.getRole()
        );

        roleRespository.save(role);

        return new RoleResponse(
                role.getId(),
                role.getRole()
        );
    }

    @Override
    public void initRoleUserAndAdmin() {
        Role isUserRoleExist = roleRespository.findByRole(UserRole.USER)
                .orElse(null);

        Role isAdminRoleExist = roleRespository.findByRole(UserRole.ADMIN)
                .orElse(null);

        Role isCustomerRoleExist = roleRespository.findByRole(UserRole.CUSTOMER)
                .orElse(null);

        if (isAdminRoleExist == null && isUserRoleExist == null && isCustomerRoleExist == null) {
            Role adminRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.ADMIN
            );

            Role userRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.USER
            );

            Role customerRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.CUSTOMER
            );

            roleRespository.save(adminRole);
            roleRespository.save(userRole);
            roleRespository.save(customerRole);

            Set<Role> managerRoles = new HashSet<>();
            managerRoles.add(adminRole);
            managerRoles.add(userRole);
            managerRoles.add(customerRole);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User manager = new User(
                    UUID.randomUUID().toString(),
                    "ai",
                    "aichan@gmail.com",
                    "0822671281272",
                    passwordEncoderConfiguration.passwordEncoder().encode("aichan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false,
                    managerRoles
            );
            userRepository.save(manager);

            User admin = new User(
                    UUID.randomUUID().toString(),
                    "chinochan",
                    "chinochan@gmail.com",
                    "0822671281271",
                    passwordEncoderConfiguration.passwordEncoder().encode("chinochan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false,
                    adminRoles
            );

            userRepository.save(admin);

            // save init admin
            User user = new User(
                    UUID.randomUUID().toString(),
                    "kaguyachan",
                    "kaguya@gmail.com",
                    "0822671281274",
                    passwordEncoderConfiguration.passwordEncoder().encode("kaguyachan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false,
                    userRoles
            );

            userRepository.save(user);
        } else {
            log.warn("init role user and admin is available");
        }
    }
}
