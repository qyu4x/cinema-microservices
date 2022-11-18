package com.neko.seatservice.repository;

import com.neko.seatservice.model.entity.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatDetailRepository extends JpaRepository<SeatDetail, String> {
}
