package com.school.management.api.repository;

import com.school.management.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Integer> {

    @Query(nativeQuery = true, value = "SELECT comment_id, comment_teacher, comment_parent, comment_time, comment_status, comment_account, comment_type, comment_photo_1, comment_photo_2, comment_photo_3, a.* FROM COMMENT c, (SELECT s.student_num, s.student_name, s.student_classroom, t.teacher_ID, t.teacher_name FROM student s, teacher t) a WHERE c.comment_parent = a.student_num AND c.comment_teacher=a.teacher_ID AND teacher_ID=?1 AND student_num=?2 ORDER BY c.`comment_id` DESC LIMIT 1")
    Map<String, Object> findAllBy(int teacherId, int studentId);

    @Query(nativeQuery = true, value = "select comment_id, comment_teacher, comment_parent, comment_time, comment_status, comment_account, comment_type, comment_photo_1, comment_photo_2, comment_photo_3, a.* from comment c, (select s.student_num, s.student_name, s.student_classroom, t.teacher_ID, t.teacher_name from student s, teacher t) a where c.comment_parent = a.student_num and c.comment_teacher=a.teacher_ID and student_num=?1 ORDER BY c.`comment_id` DESC")
    List<Map<String, Object>> findAllByStudentId(int studentId);

    List<Comment> findByCommentParentAndCommentStatus(int studentNum, int type);
}
