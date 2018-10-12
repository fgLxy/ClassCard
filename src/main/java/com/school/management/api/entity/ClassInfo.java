package com.school.management.api.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_info")
public class ClassInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "class_info_id")
    private long classInfoId;
    
    @Column(name = "class_id")
    @JoinColumn(name = "class_id", table = "class")
    private Long classId;
    
    @Column(name = "student_id")
    private Long studentId;
    
    @Column(name = "attendance_id")
    private Long attendanceId;
    
    @Column(name = "duty_id")
    private Long dutyId;
    
    @Column(name = "album_id")
    private Long albumId;
    
    @Column(name = "opus_id")
    private Long opusId;
    
    @Column(name = "repair_id")
    private Long repairId;
    
    @Column(name = "quantify_id")
    private Long quantifyId;
    
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "schedule_week_id")
    private Long scheduleWeekId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return classInfoId == classInfo.classInfoId &&
                Objects.equals(classId, classInfo.classId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(classInfoId, classId);
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "classInfoId=" + classInfoId +
                ", classId=" + classId +
                ", studentId=" + studentId +
                ", attendanceId=" + attendanceId +
                ", dutyId=" + dutyId +
                ", albumId=" + albumId +
                ", opusId=" + opusId +
                ", repairId=" + repairId +
                ", quantifyId=" + quantifyId +
                ", scheduleId=" + scheduleId +
                '}';
    }

    public long getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(long classInfoId) {
        this.classInfoId = classInfoId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getOpusId() {
        return opusId;
    }

    public void setOpusId(Long opusId) {
        this.opusId = opusId;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public Long getQuantifyId() {
        return quantifyId;
    }

    public void setQuantifyId(Long quantifyId) {
        this.quantifyId = quantifyId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleWeekId() {
        return scheduleWeekId;
    }

    public void setScheduleWeekId(Long scheduleWeekId) {
        this.scheduleWeekId = scheduleWeekId;
    }
}
