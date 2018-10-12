package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {

    /**
     * 成绩ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "grade_id")
    private long gradeId;

    /**
     * 分数
     */
    @Column(name = "grade_score")
    private int gradeSorce;

    /**
     * 排名
     */
    @Column(name = "grade_rank")
    private int gradeRank;

    /**
     * 科目
     */
    @JoinColumn(name = "grade_course", referencedColumnName = "course_name", insertable = false, updatable = false)
    private String gradeCourse;

    /**
     * 任课老师
     */
    @Column(name = "grade_course_teacher")
    private String courseTeacher;

    /**
     * 学生编号
     */
    @Column(name = "grade_student_id")
    private int studentId;

    @Column(name = "grade_type")
    private String gradeType;

    @Column(name = "grade_date")
    private String gradeDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return gradeId == grade.gradeId &&
                gradeSorce == grade.gradeSorce &&
                gradeRank == grade.gradeRank &&
                studentId == grade.studentId &&
                Objects.equals(gradeCourse, grade.gradeCourse) &&
                Objects.equals(courseTeacher, grade.courseTeacher);
    }

    @Override
    public int hashCode() {

        return Objects.hash(gradeId, gradeSorce, gradeRank, gradeCourse, courseTeacher, studentId);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", gradeSorce=" + gradeSorce +
                ", gradeRank=" + gradeRank +
                ", gradeCourse='" + gradeCourse + '\'' +
                ", courseTeacher='" + courseTeacher + '\'' +
                ", studentId=" + studentId +
                '}';
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public int getGradeSorce() {
        return gradeSorce;
    }

    public void setGradeSorce(int gradeSorce) {
        this.gradeSorce = gradeSorce;
    }

    public int getGradeRank() {
        return gradeRank;
    }

    public void setGradeRank(int gradeRank) {
        this.gradeRank = gradeRank;
    }

    public String getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(String gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getGradeCourse() {
        return gradeCourse;
    }

    public void setGradeCourse(String gradeCourse) {
        this.gradeCourse = gradeCourse;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }
}
