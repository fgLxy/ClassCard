package com.school.management.api.repository;

import com.school.management.api.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public interface GradeJpaRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findByStudentId(int studentId);

    Grade findByGradeId(long gradeId);

    List<Grade> findByStudentIdAndGradeDate(int studentNum, String date);

    List<Grade> findByGradeDateLike(String date);

    @Query(nativeQuery = true, value = "select g.grade_date, g.grade_type from grade g group by g.grade_date, g.grade_type DESC")
    List<Map<String, Object>> getAllDate();

    List<Grade> getByStudentIdAndGradeDateLike(int studentNum, String date);
}
