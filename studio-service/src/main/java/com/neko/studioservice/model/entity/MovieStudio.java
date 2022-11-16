package com.neko.studioservice.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "movie_studio")
public class MovieStudio {

    @Id
    private String id;

    private String movieId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id", referencedColumnName = "id")
    private Studio studioId;
}
