package com.school.management.api.repository;

import com.school.management.api.entity.Quantify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantifyJpaRepository extends JpaRepository<Quantify, Long> {

    Quantify findByQuantifyId(long id);

    Page<Quantify> findByClassId(long classId, Pageable pageable);

    List<Quantify> findByClassCode(long classId, Sort sort);
    Page<Quantify> findByClassCode(long classId,  Pageable pageable);
}
