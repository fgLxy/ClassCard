package com.school.management.api.repository;

import com.school.management.api.entity.Class;
import com.school.management.api.entity.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(rollbackFor = Exception.class)
public interface ClassJpaRepository extends JpaRepository<Class, Long> {

    /**
     * @param classCode 班级编号
     * @return 根据班级编号查询得到的班级数据
     */
    @QueryHints
    Class findByClassroomCode(Integer classCode);

    /**
     * @param className 班级名称
     * @return 根据班级名称来找到对应班级
     */
    Class findByClassName(String className);

    Class findByClassId(long classId);

    Class findByClassHeadmaster(Teacher teacher);

    List<Class> findByClassNameLike(String className);

    List<Class> findByClassNameLike(String className, Pageable pageable);
}
