package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable, Comparable<Schedule> {

    /**
     * 课程表ID
     */
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long scheduleId;

    /**
     * 课时
     */
    @Column(name = "schedule_period")
    private String period;

    /**
     * 课程开始时间
     */
    @Column(name = "schedule_course_starttime")
    private String courseStarttime;

    /**
     * 任课老师
     */
    @OneToOne
    @JoinColumn(referencedColumnName = "teacher_ID", name = "schedule_instructor")
    private Teacher instructor;

    /**
     * 班级名称
     */
    @Column(name = "schedule_class_name")
    private String className;

    /**
     * 课程结束时间
     */
    @Column(name = "schedule_lesson_endtime")
    private String lessonEndtime;

    /**
     * 课代表
     */
    @Column(name = "schedule_lesson_representative")
    private String lessonRepresentative;

    /**
     * 日期
     */
    @Column(name = "schedule_date")
    private String scheduleDate;

    /**
     * 课程名称
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "course_name", name = "schedule_course_name")
    private Course courseName;

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", period='" + period + '\'' +
                ", courseStarttime='" + courseStarttime + '\'' +
                ", instructor='" + instructor + '\'' +
                ", className='" + className + '\'' +
                ", lessonEndtime='" + lessonEndtime + '\'' +
                ", lessonRepresentative='" + lessonRepresentative + '\'' +
                ", scheduleDate='" + scheduleDate + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule that = (Schedule) o;
        return scheduleId == that.scheduleId &&
                Objects.equals(period, that.period) &&
                Objects.equals(courseStarttime, that.courseStarttime) &&
                Objects.equals(instructor, that.instructor) &&
                Objects.equals(className, that.className) &&
                Objects.equals(lessonEndtime, that.lessonEndtime) &&
                Objects.equals(lessonRepresentative, that.lessonRepresentative) &&
                Objects.equals(scheduleDate, that.scheduleDate) &&
                Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scheduleId, period, courseStarttime, instructor, className, lessonEndtime, lessonRepresentative, scheduleDate, courseName);
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCourseStarttime() {
        return courseStarttime;
    }

    public void setCourseStarttime(String courseStarttime) {
        this.courseStarttime = courseStarttime;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLessonEndtime() {
        return lessonEndtime;
    }

    public void setLessonEndtime(String lessonEndtime) {
        this.lessonEndtime = lessonEndtime;
    }

    public String getLessonRepresentative() {
        return lessonRepresentative;
    }

    public void setLessonRepresentative(String lessonRepresentative) {
        this.lessonRepresentative = lessonRepresentative;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Course getCourseName() {
        return courseName;
    }

    public void setCourseName(Course courseName) {
        this.courseName = courseName;
    }

    @Override
    public int compareTo(Schedule schedule) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            if (format.parse(schedule.getCourseStarttime()).getTime() > format.parse(this.courseStarttime).getTime()) {
                return -1;
            } else {
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
