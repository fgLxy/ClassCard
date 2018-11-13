package com.school.management.api.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "schedule_week")
public class ScheduleWeek implements Serializable {

    /**
     * 本周课程表ID
     */
    @Id
    @Column(name = "schedule_week_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private int weekId;

    /**
     * 本周星期几
     */
    @Column(name = "schedule_week_day")
    private String weekDay;

    /**
     * 本周日期
     */
    @Column(name = "schedule_week_date")
    private String weekDate;

    /**
     * 本周课程
     */
    @Column(name = "schedule_week_course")
    private String weekCourse;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "schedule_week_course",
//            foreignKey = @ForeignKey(name = "schedule_week_day"),
//            joinColumns = {
//                    @JoinColumn(table = "schedule_week", name = "schedule_week_day"),
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(table = "course", name = "course_name", foreignKey = @ForeignKey(name = "course_name"))
//            }
//    )
//    private List<Course> weekCourse;


    @Column(name = "class_code")
    private int classCode;

    @Override
    public String toString() {
        return "ScheduleWeek{" +
                "weekId='" + weekId + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", weekDate='" + weekDate + '\'' +
                ", weekCourse='" + weekCourse + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleWeek that = (ScheduleWeek) o;
        return Objects.equals(weekId, that.weekId) &&
                Objects.equals(weekDay, that.weekDay) &&
                Objects.equals(weekDate, that.weekDate) &&
                Objects.equals(weekCourse, that.weekCourse);
    }

    @Override
    public int hashCode() {

        return Objects.hash(weekId, weekDay, weekDate, weekCourse);
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public String getWeekCourse() {
        return weekCourse;
    }

    public void setWeekCourse(String weekCourse) {
        this.weekCourse = weekCourse;
    }

//    public List<Course> getWeekCourse() {
//        return weekCourse;
//    }
//
//    public void setWeekCourse(List<Course> weekCourse) {
//        this.weekCourse = weekCourse;
//    }
}
