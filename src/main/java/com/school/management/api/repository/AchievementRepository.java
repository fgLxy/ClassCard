package com.school.management.api.repository;

import com.school.management.api.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAll();

    @Query(value = "SELECT * FROM achievement WHERE grade_type=?1",nativeQuery = true)
    List<Achievement> allStudentAchievement(String gradeType);

    Achievement getAchievementByStudentCodeAndGradeType(int studentCode, String gradeType);
}
