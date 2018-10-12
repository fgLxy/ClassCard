package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 值日
 */
@Entity
@Table(name = "duty_day")
public class DutyDay implements Serializable {

    /**
     * 值日ID
     */
    @Id
    @Column(name = "duty_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long dutyId;

    /**
     * 值日日期
     */
    @Column(name = "duty_date")
    private String dutyDate;

    /**
     * 学生ID
     */
    @Column(name = "duty_student_id")
    private Long dutyStudentId;

    /**
     * 学生姓名
     */
    @Column(name = "duty_student_name")
    private String dutyStudentName;

    /**
     * 星期几
     */
    @Column(name = "duty_week_day")
    private String dutyDay;

    /**
     * 班级编号
     */
    @JoinColumn(referencedColumnName = "class_room_code", name = "class_room_code")
    @Column(name = "class_room_code")
    private int classRoomCode;

    public long getDutyId() {
        return dutyId;
    }

    public void setDutyId(long dutyId) {
        this.dutyId = dutyId;
    }

    public String getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    public Long getDutyStudentId() {
        return dutyStudentId;
    }

    public void setDutyStudentId(Long dutyStudentId) {
        this.dutyStudentId = dutyStudentId;
    }

    public String getDutyStudentName() {
        return dutyStudentName;
    }

    public void setDutyStudentName(String dutyStudentName) {
        this.dutyStudentName = dutyStudentName;
    }

    public String getDutyDay() {
        return dutyDay;
    }

    public void setDutyDay(String dutyDay) {
        this.dutyDay = dutyDay;
    }

    @Override
    public String toString() {
        return "DutyDay{" +
                "dutyId=" + dutyId +
                ", dutyDate='" + dutyDate + '\'' +
                ", dutyStudentId=" + dutyStudentId +
                ", dutyStudentName='" + dutyStudentName + '\'' +
                ", dutyDay='" + dutyDay + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DutyDay dutyDay = (DutyDay) o;
        return dutyId == dutyDay.dutyId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dutyId);
    }
}
