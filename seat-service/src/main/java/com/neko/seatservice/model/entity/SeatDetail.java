package com.neko.seatservice.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat_details")
public class SeatDetail {

    @Id
    private String id;

    private String studioId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_code", referencedColumnName = "seatCode")
    private Seat seatId;


    private String scheduleId;

    private String orderId;

    private Boolean status;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
