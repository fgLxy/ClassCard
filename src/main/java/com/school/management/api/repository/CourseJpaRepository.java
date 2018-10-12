package com.school.management.api.repository;

import com.school.management.api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    Course findByCourseName(String courseName);
}
