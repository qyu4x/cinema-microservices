package com.neko.studioservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "studios")
public class Studio implements Serializable {

    @Id
    private String id;

    @JsonIgnore
    @OneToMany(mappedBy = "studioId", fetch = FetchType.LAZY)
    private Set<MovieStudio> movieStudios;

    private String name;

    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean isFull;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
