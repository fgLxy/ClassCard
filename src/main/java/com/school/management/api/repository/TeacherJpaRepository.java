package com.school.management.api.repository;

import com.school.management.api.entity.Course;
import com.school.management.api.entity.Teacher;
import com.school.management.api.entity.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TeacherJpaRepository extends JpaRepository<Teacher, Long> {

    /**
     * @param teacherID 教师ID
     * @return 根据教师ID来查找教师详情
     */
    Teacher findByTeacherId(long teacherID);

    /**
     * @param userID 用户ID
     * @return 登录后根据用户ID来查找对应教师，再返回相关权限
     */
    Teacher findByUserID(long userID);

    /**
     * @param name 教师名称
     * @return 根据教师名称查询得到的教师信息
     */
    Teacher findByTeacherName(String name);

    List<Teacher> findByCourse(String course);
}
