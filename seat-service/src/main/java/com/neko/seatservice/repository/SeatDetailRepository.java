package com.neko.seatservice.repository;

import com.neko.seatservice.model.entity.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatDetailRepository extends JpaRepository<SeatDetail, String> {
    List<SeatDetail> findSeatDetailByStatus(Boolean status);

}
