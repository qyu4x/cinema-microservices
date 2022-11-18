package com.neko.userservice.repository;

import com.neko.userservice.model.entity.Role;
import com.neko.userservice.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRespository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
