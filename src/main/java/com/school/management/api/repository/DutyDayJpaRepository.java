package com.school.management.api.repository;

import com.school.management.api.entity.DutyDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyDayJpaRepository extends JpaRepository<DutyDay, Long> {


    /**
     * @param classCode 班级编号
     * @return 根据班级编号查询得到的值日信息集合
     */
    List<DutyDay> findByClassRoomCode(int classCode);

    Page<DutyDay> findByClassRoomCode(int classCode, Pageable pageable);

    DutyDay findByDutyId(long dutyId);

    List<DutyDay> findByDutyStudentName(String studentName);
    List<DutyDay> findByDutyDayAndClassRoomCode(String dutyDate, int classCode);
    Page<DutyDay> findByDutyDayAndClassRoomCode(String dutyDate, int classCode, Pageable pageable);
}
