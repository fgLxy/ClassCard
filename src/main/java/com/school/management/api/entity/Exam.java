package com.school.management.api.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import com.school.management.api.entity.Teacher;

/**
 * 考试
 */
@Entity
@Table(name = "exam")
public class Exam implements Serializable {

    /**
     * 考试ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "exam_id")
    private long examId;

    /**
     * 考场编号
     */
    @Column(name = "exam_room")
    private String examRoom;

    /**
     * 考试时间
     */
    @Column(name = "exam_time")
    private String examTime;

    /**
     * 考试编号
     */
    @Column(name = "exam_code")
    private String examCode;

    /**
     * 考试地点
     */
    @Column(name = "exam_place")
    private String examPlace;

    /**
     * 考试时间
     */
    @Column(name = "exam_date")
    private String examDate;

    /**
     * 考试开始时间
     */
    @Column(name = "exam_start_time")
    private String examStartTime;

    /**
     * 考试结束时间
     */
    @Column(name = "exam_end_time")
    private String examEndTime;

    /**
     * 考试学科
     */
    @Column(name = "exam_subject")
    private String examSubject;

    @Column(name = "exam_teacher")
    private String teacherId;

//    studentNum
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exam_students",
            joinColumns = {
                    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "student_num", referencedColumnName = "student_num"),
            }
    )
    private Set<Student> students;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", examRoom='" + examRoom + '\'' +
                ", examTime='" + examTime + '\'' +
                ", examCode='" + examCode + '\'' +
                ", examSubject='" + examSubject + '\'' +
                ", examPlace='" + examPlace + '\'' +
                ", examDate='" + examDate + '\'' +
                ", examStartTime='" + examStartTime + '\'' +
                ", examEndTime='" + examEndTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return examId == exam.examId &&
                Objects.equals(examRoom, exam.examRoom) &&
                Objects.equals(examTime, exam.examTime) &&
                Objects.equals(examCode, exam.examCode) &&
                Objects.equals(examSubject, exam.examSubject) &&
                Objects.equals(examPlace, exam.examPlace) &&
                Objects.equals(examDate, exam.examDate) &&
                Objects.equals(examStartTime, exam.examStartTime) &&
                Objects.equals(examEndTime, exam.examEndTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(examId, examRoom, examTime, examCode, examSubject, examPlace, examDate, examStartTime, examEndTime);
    }
}
