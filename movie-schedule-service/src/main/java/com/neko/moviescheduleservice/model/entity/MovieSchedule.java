package com.neko.moviescheduleservice.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie_schedule")
@ToString
public class MovieSchedule {

    @Id
    private String id;

    private String movieId;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "schedule_id",
            referencedColumnName = "id"
    )
    private Schedule scheduleId;

}
