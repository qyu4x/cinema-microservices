package com.neko.userservice.model.entity;

import com.neko.userservice.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private UserRole role;

}
