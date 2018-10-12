package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "teacher")
@Cacheable
public class Teacher implements Serializable {

    /**
     * 教师ID
     */
    @Id
    @Column(name = "teacher_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long teacherId;

    /**
     * 教师名称
     */
    @Column(name = "teacher_name")
    private String teacherName;

    /**
     * 教师性别
     */
    @Column(name = "teacher_sex")
    private String teacherSex;

    /**
     * 教师年龄
     */
    @Column(name = "teacher_age")
    private Integer teacherAge;

    /**
     * 教师头衔
     */
    @Column(name = "teacher_title")
    private String teacherTitle;

    /**
     * 电话号码
     */
    @Column(name = "teacher_phone_num")
    private Long teacherPhoneNum;

    /**
     * 状态
     */
    @Column(name = "teacher_status")
    private Integer teacherStatus;

    /**
     * 登录名
     */
    @Column(name = "teacher_login_name")
    private String teacherLoginName;

    /**
     * 登录密码
     */
    @Column(name = "teacher_password")
    private String teacherPassword;

    /**
     * 入职时间
     */
    @Column(name = "teacher_entry_time")
    private String entryTime;

    /**
     * 离职时间
     */
    @Column(name = "teacher_separation_time")
    private String separationTime;

    /**
     * 教师头像
     */
    @Column(name = "teacher_head_photo")
    private String headPhoto;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private long userID;

//    @Column(name = "teacher_course_name")
//    private String courseName;

    /**
     * 教师详情
     */
    @OneToOne
    @JoinColumn(name = "teacher_ID", referencedColumnName = "teacher_ID")
    private TeacherInfo teacherInfo;

    /**
     * 教师授课
     */
//    @OneToOne
    @Column(name = "teacher_course_name", insertable = false, updatable = false)
    @JoinColumn(
            name = "teacher_course_name",
            referencedColumnName = "course_name",
            foreignKey = @ForeignKey(name = "teacher_course_name"),
            insertable = false,
            updatable = false
    )
    private String course;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    public Integer getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(Integer teacherAge) {
        this.teacherAge = teacherAge;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacherTitle() {
        return teacherTitle;
    }

    public void setTeacherTitle(String teacherTitle) {
        this.teacherTitle = teacherTitle;
    }

    public Long getTeacherPhoneNum() {
        return teacherPhoneNum;
    }

    public void setTeacherPhoneNum(Long teacherPhoneNum) {
        this.teacherPhoneNum = teacherPhoneNum;
    }

    public Integer getTeacherStatus() {
        return teacherStatus;
    }

    public void setTeacherStatus(Integer teacherStatus) {
        this.teacherStatus = teacherStatus;
    }

    public String getTeacherLoginName() {
        return teacherLoginName;
    }

    public void setTeacherLoginName(String teacherLoginName) {
        this.teacherLoginName = teacherLoginName;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getSeparationTime() {
        return separationTime;
    }

    public void setSeparationTime(String separationTime) {
        this.separationTime = separationTime;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

//    public String getCourseName() {
//        return courseName;
//    }
//
//    public void setCourseName(String courseName) {
//        this.courseName = courseName;
//    }

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return teacherId == teacher.teacherId &&
                Objects.equals(teacherName, teacher.teacherName) &&
                Objects.equals(teacherSex, teacher.teacherSex) &&
                Objects.equals(teacherAge, teacher.teacherAge) &&
                Objects.equals(teacherTitle, teacher.teacherTitle) &&
                Objects.equals(teacherPhoneNum, teacher.teacherPhoneNum) &&
                Objects.equals(teacherStatus, teacher.teacherStatus) &&
                Objects.equals(teacherLoginName, teacher.teacherLoginName) &&
                Objects.equals(teacherPassword, teacher.teacherPassword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teacherId, teacherName, teacherSex, teacherAge, teacherTitle, teacherPhoneNum, teacherStatus, teacherLoginName, teacherPassword);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherSex='" + teacherSex + '\'' +
                ", teacherAge=" + teacherAge +
                ", teacherTitle='" + teacherTitle + '\'' +
                ", teacherPhoneNum=" + teacherPhoneNum +
                ", teacherStatus=" + teacherStatus +
                ", teacherLoginName='" + teacherLoginName + '\'' +
                ", teacherPassword='" + teacherPassword + '\'' +
                ", headPhoto='" + headPhoto + '\'' +
//                ", userID=" + userID +
                ", teacherInfo=" + teacherInfo +
                ", course=" + course +
                '}';
    }
}
