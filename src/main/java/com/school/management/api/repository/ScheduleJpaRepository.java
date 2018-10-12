package com.school.management.api.repository;

import com.school.management.api.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

    /**
     * @param className 班级名称
     * @return 根据班级名称来查询对应的课表
     */
    List<Schedule> findByClassName(String className);
}
