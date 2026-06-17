package com.danceschool.booking.repository;

import com.danceschool.booking.entity.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long> {
}
