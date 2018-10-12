package com.school.management.api.repository;

import com.school.management.api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    /**
     * @param cardNum 学生卡号
     * @return 根据学生卡号来查询对应的学生
     */
    Student getStudentByStudentCardNum(String cardNum);

    /**
     * @return 查询所有的学生
     */
    List<Student> getStudentsBy();

    /**
     * @param userID 用户ID
     * @return 登录后根据用户ID来查找对应学生，再返回相关权限
     */
    Student findByUserID(long userID);

    Student findByStudentNameAndStudentClassroom(String studentName, String classRoom);

    Student findByStudentId(Long studentId);

    List<Student> findByStudentClassroom(String classRoom);

    Page<Student> findByStudentClassroom(String classRoom, Pageable pageable);

    Student findByStudentName(String attendanceStudentName);

    Student findByStudentNum(int studentNum);

}
