package com.school.management.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 学生相关信息
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "student")
public class Student implements Serializable {

    /**
     * 学生ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    /**
     * 学生姓名
     */
    @Column(name = "student_name", nullable = false)
    private String studentName;

    /**
     * 学生年龄
     */
    @Column(name = "student_age")
    private int studentAge;

    /**
     * 学生性别, 0为男性，1为女性
     */
    @Column(name = "student_sex")
    private int studentSex;

    /**
     * 学生所属班级
     */
    @Column(name = "student_classroom", nullable = false)
    private String studentClassroom;

    /**
     * 学分总分
     */
    @Column(name = "student_total_credit")
    private int studentTotalCredit;

    /**
     * 是否值日,1代表值日，0代表不值日
     */
    @Column(name = "student_duty_day_state", nullable = false)
    private int studentDutyDayState;

    /**
     * 学生头像
     */
    @Column(name = "student_header_url")
    private String studentHeaderUrl;

    /**
     * 学生班牌卡号
     */
    @Column(name = "student_card_num", nullable = false)
    private String studentCardNum;

    /**
     * 学生号
     */
    @Column(name = "student_num", nullable = false)
    private int studentNum;

    /**
     * 用户ID
     */
    @JoinColumn(name = "userID", referencedColumnName = "user_id")
    private long userID;

    /**
     * 学生生日
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 入学时间
     */
    @Column(name = "matriculation_time")
    private String matriculationTime;

    /**
     * 毕业时间
     */
    @Column(name = "graduation_time")
    private String graduationTime;

    /**
     * 户籍
     */
    @Column(name = "household_registration")
    private String householdRegistration;

    /**
     * 户口（1——城镇；2——农村）
     */
    @Column(name = "account")
    private String account;

    /**
     * 学生成绩
     */
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "grade_student_id", referencedColumnName = "student_num", insertable = false, updatable = false)
    private List<Grade> grades;

    /**
     * 学生考勤
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "attendance_student_num", referencedColumnName = "student_num")
    private List<Attendance> attendances;

    public String getStudentCardNum() {
        return studentCardNum;
    }

    public void setStudentCardNum(String studentCardNum) {
        this.studentCardNum = studentCardNum;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public int getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(int studentSex) {
        this.studentSex = studentSex;
    }

    public String getStudentClassroom() {
        return studentClassroom;
    }

    public void setStudentClassroom(String studentClassroom) {
        this.studentClassroom = studentClassroom;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMatriculationTime() {
        return matriculationTime;
    }

    public void setMatriculationTime(String matriculationTime) {
        this.matriculationTime = matriculationTime;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getHouseholdRegistration() {
        return householdRegistration;
    }

    public void setHouseholdRegistration(String householdRegistration) {
        this.householdRegistration = householdRegistration;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public int getStudentTotalCredit() {
        return studentTotalCredit;
    }

    public void setStudentTotalCredit(int studentTotalCredit) {
        this.studentTotalCredit = studentTotalCredit;
    }

    public int getStudentDutyDayState() {
        return studentDutyDayState;
    }

    public void setStudentDutyDayState(int studentDutyDayState) {
        this.studentDutyDayState = studentDutyDayState;
    }

    public String getStudentHeaderUrl() {
        return studentHeaderUrl;
    }

    public void setStudentHeaderUrl(String studentHeaderUrl) {
        this.studentHeaderUrl = studentHeaderUrl;
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentAge=" + studentAge +
                ", studentSex=" + studentSex +
                ", studentClassroom='" + studentClassroom + '\'' +
                ", studentTotalCredit=" + studentTotalCredit +
                ", studentDutyDayState=" + studentDutyDayState +
                ", studentHeaderUrl='" + studentHeaderUrl + '\'' +
                ", studentCardNum='" + studentCardNum + '\'' +
                ", studentNum=" + studentNum +
                ", userID=" + userID +
                ", birthday='" + birthday + '\'' +
                ", matriculationTime='" + matriculationTime + '\'' +
                ", graduationTime='" + graduationTime + '\'' +
                ", householdRegistration='" + householdRegistration + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentAge == student.studentAge &&
                studentSex == student.studentSex &&
                studentTotalCredit == student.studentTotalCredit &&
                studentDutyDayState == student.studentDutyDayState &&
                studentCardNum == student.studentCardNum &&
                studentNum == student.studentNum &&
                Objects.equals(studentId, student.studentId) &&
                Objects.equals(studentName, student.studentName) &&
                Objects.equals(studentClassroom, student.studentClassroom) &&
                Objects.equals(studentHeaderUrl, student.studentHeaderUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(studentId, studentName, studentAge, studentSex, studentClassroom, studentTotalCredit, studentDutyDayState, studentHeaderUrl, studentCardNum, studentNum);
    }
}
