package com.neko.studioservice.repository;

import com.neko.studioservice.model.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, String> {

}
