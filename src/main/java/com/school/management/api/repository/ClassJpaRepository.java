package com.school.management.api.repository;

import com.school.management.api.entity.Class;
import com.school.management.api.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


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

    Page<Class> findByClassNameLike(String className, Pageable pageable);

    @Query(nativeQuery = true, value = "select school.class.class_room_code, school.class.class_badge, school.class.class_name from school.class where school.class.class_name=?1")
    List<Map<String, Object>> getAllBadge(String className);

    @Query(nativeQuery = true, value = "select c.class_name from school.class c where c.class_room_code=?1")
    String getNameByClassCode(int classCode);

    @Query(nativeQuery = true, value = "select count(*) from school.class c where c.class_name=?1")
    int hasClass(String className);

}
