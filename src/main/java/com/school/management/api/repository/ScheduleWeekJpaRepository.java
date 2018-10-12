package com.school.management.api.repository;

import com.school.management.api.entity.ScheduleWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleWeekJpaRepository extends JpaRepository<ScheduleWeek, Long> {

    /**
     * @param week 星期几
     * @return 根据星期几来查询当天的课表
     */
    List<ScheduleWeek> findByWeekDay(String week);

    /**
     * @param classCode 班级编号
     * @return 该班级的每周的课程
     */
    List<ScheduleWeek> findByClassCode(int classCode);

}
