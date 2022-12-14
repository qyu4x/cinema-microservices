package com.neko.moviescheduleservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "schedules")
public class Schedule implements Serializable {

    @Id
    private String id;

    @JsonIgnore
    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.EAGER)
    private Set<MovieSchedule> movieSchedules;

    private LocalDate showDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
