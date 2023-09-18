package com.GerenciaTcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GerenciaTcc.model.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

	Optional<Area> findByArea(String area);
}
