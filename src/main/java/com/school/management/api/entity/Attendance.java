package com.school.management.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "attendance")
public class Attendance implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "attendance_student_id", nullable = false)
    private Long attendanceStudentId;

    /**
     * 到校时间
     */
    @Column(name = "attendance_arrival_time")
    private String attendanceArrivalTime;

    /**
     * 离校时间
     */
    @Column(name = "attendance_leaving_time")
    private String attendanceLeavingTime;

    /**
     * 学生姓名
     */
    @Column(name = "attendance_student_name", nullable = false)
    private String attendanceStudentName;

    /**
     * 学生号
     */
    @Column(name = "attendance_student_num", nullable = false)
    private String studentNum;

    /**
     * 签到状态,0代表没签到，1代表已签到
     */
    @Column(name = "attendance_sign_state", nullable = false)
    private int signState = 0;

    /**
     * 学生头像
     */
    @Column(name = "attendance_student_photo_url", nullable = false)
    private String photoUrl;

    /**
     * 签到日期
     */
    @Column(name = "attendance_sign_date", nullable = false)
    private String signDate;

    @Column(name = "attendance_arrival_status")
    private int arrivalStatus;

    @Column(name = "attendance_arrival_type")
    private int arrivalType;

    @Column(name = "attendance_level_type")
    private int levelType;

    @Column(name = "attendance_level_status")
    private int levelStatus;

    @Column(name = "attendance_class_code")
    private int classCode;

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Long getAttendanceStudentId() {
        return attendanceStudentId;
    }

    public void setAttendanceStudentId(Long attendanceStudentId) {
        this.attendanceStudentId = attendanceStudentId;
    }

    public String getAttendanceArrivalTime() {
        return attendanceArrivalTime;
    }

    public void setAttendanceArrivalTime(String attendanceArrivalTime) {
        this.attendanceArrivalTime = attendanceArrivalTime;
    }

    public String getAttendanceLeavingTime() {
        return attendanceLeavingTime;
    }

    public void setAttendanceLeavingTime(String attendanceLeavingTime) {
        this.attendanceLeavingTime = attendanceLeavingTime;
    }

    public String getAttendanceStudentName() {
        return attendanceStudentName;
    }

    public void setAttendanceStudentName(String attendanceStudentName) {
        this.attendanceStudentName = attendanceStudentName;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public int getSignState() {
        return signState;
    }

    public void setSignState(int signState) {
        this.signState = signState;
    }

    public int getArrivalStatus() {
        return arrivalStatus;
    }

    public void setArrivalStatus(int arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    public int getArrivalType() {
        return arrivalType;
    }

    public void setArrivalType(int arrivalType) {
        this.arrivalType = arrivalType;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(int levelStatus) {
        this.levelStatus = levelStatus;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceStudentId=" + attendanceStudentId +
                ", attendanceArrivalTime='" + attendanceArrivalTime + '\'' +
                ", attendanceLeavingTime='" + attendanceLeavingTime + '\'' +
                ", attendanceStudentName='" + attendanceStudentName + '\'' +
                ", studentNum=" + studentNum +
                ", signState=" + signState +
                ", photoUrl='" + photoUrl + '\'' +
                ", signDate='" + signDate + '\'' +
                ", classCode=" + classCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return studentNum == that.studentNum &&
                signState == that.signState &&
                classCode == that.classCode &&
                Objects.equals(attendanceStudentId, that.attendanceStudentId) &&
                Objects.equals(attendanceArrivalTime, that.attendanceArrivalTime) &&
                Objects.equals(attendanceLeavingTime, that.attendanceLeavingTime) &&
                Objects.equals(attendanceStudentName, that.attendanceStudentName) &&
                Objects.equals(photoUrl, that.photoUrl) &&
                Objects.equals(signDate, that.signDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attendanceStudentId, attendanceArrivalTime, attendanceLeavingTime, attendanceStudentName, studentNum, signState, photoUrl, signDate, classCode);
    }
}
