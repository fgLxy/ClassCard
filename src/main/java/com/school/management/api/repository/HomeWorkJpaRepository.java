package com.school.management.api.repository;


import com.school.management.api.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeWorkJpaRepository extends JpaRepository<HomeWork, Long> {

    List<HomeWork> findByClassCode(int classCode);

}
