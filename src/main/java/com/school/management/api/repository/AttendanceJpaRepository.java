package com.school.management.api.repository;

import com.school.management.api.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
    /**
     * 获取出勤列表
     */
    List<Attendance> findAllBy();

    /**
     * 学生离校打卡，更新出勤信息
     */
    @Modifying
    @Query(value = "UPDATE attendance SET attendance_leaving_time=?2 WHERE attendance_student_num=?1", nativeQuery = true)
    int updateAttendance(@RequestParam("attendance_student_num") int studentNum,
                         @RequestParam("attendance_leaving_time") String leavingTime);

    /**
     * @param studentNum  学生号
     * @param arrivalTime 到达时间
     * @return 已经到达的指定学生的出勤信息
     */
    Attendance findByStudentNumAndAttendanceArrivalTimeLike(String studentNum, String arrivalTime);

    Page<Attendance> findByClassCode(int classCode, Pageable pageable);

    List<Attendance> findByClassCode(int classCode);

    Attendance findByAttendanceStudentId(long attendanceId);

    Attendance findByStudentNum(String studentNum);
}
