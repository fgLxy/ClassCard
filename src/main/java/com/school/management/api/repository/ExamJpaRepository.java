package com.school.management.api.repository;

import com.school.management.api.entity.Exam;
import com.school.management.api.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ExamJpaRepository extends JpaRepository<Exam, Long> {

    /**
     * @param examRoom 考场编号
     * @return 根据考场编号查询的到的考试信息
     */
    List<Exam> findByExamRoom(String examRoom);

    Exam findByExamId(long examId);

    List<Exam> findByExamDateLike(String date);

    Page<Exam> findByExamDateLike(String date, Pageable pageable);
}
