package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "teacher")
public class TeacherInfo implements Serializable {

    /**
     * 教师ID
     */
    @Id
    @Column(name = "teacher_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long teacherId;

    /**
     * 教师理念
     */
    @Column(name = "teacher_concept")
    private String teacherConcept;

    /**
     * 教师简介
     */
    @Column(name = "teacher_blurb")
    private String teacherblurb;

    /**
     * 教师感言
     */
    @Column(name = "teacher_speech")
    private String teacherSpeech;

    /**
     * 教师本人图片
     */
    @Column(name = "teacher_photo")
    private String teacherPhoto;

    /**
     * 获奖图片1
     */
    @Column(name = "teacher_award_photo_url_1")
    private String awardPhotoUrl1;

    /**
     * 获奖图片2
     */
    @Column(name = "teacher_award_photo_url_2")
    private String awardPhotoUrl2;

    /**
     * 获奖图片3
     */
    @Column(name = "teacher_award_photo_url_3")
    private String awardPhotoUrl3;

    /**
     * 获奖图片4
     */
    @Column(name = "teacher_award_photo_url_4")
    private String awardPhotoUrl4;

    /**
     * 获奖图片5
     */
    @Column(name = "teacher_award_photo_url_5")
    private String awardPhotoUrl5;

    /**
     * 获奖图片
     */
    @Column(name = "teacher_award_photo_url_6")
    private String awardPhotoUrl6;

    /**
     * 获奖图片
     */
    @Column(name = "teacher_award_photo_url_7")
    private String awardPhotoUrl7;

    /**
     * 获奖图片
     */
    @Column(name = "teacher_award_photo_url_8")
    private String awardPhotoUrl8;

    /**
     * 获奖图片
     */
    @Column(name = "teacher_award_photo_url_9")
    private String awardPhotoUrl9;

    public TeacherInfo (long teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherInfo() {}

    public String getTeacherConcept() {
        return teacherConcept;
    }

    public void setTeacherConcept(String teacherConcept) {
        this.teacherConcept = teacherConcept;
    }

    public String getTeacherblurb() {
        return teacherblurb;
    }

    public void setTeacherblurb(String teacherblurb) {
        this.teacherblurb = teacherblurb;
    }

    public String getTeacherSpeech() {
        return teacherSpeech;
    }

    public void setTeacherSpeech(String teacherSpeech) {
        this.teacherSpeech = teacherSpeech;
    }

    public String getTeacherPhoto() {
        return teacherPhoto;
    }

    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    public String getAwardPhotoUrl1() {
        return awardPhotoUrl1;
    }

    public void setAwardPhotoUrl1(String awardPhotoUrl1) {
        this.awardPhotoUrl1 = awardPhotoUrl1;
    }

    public String getAwardPhotoUrl2() {
        return awardPhotoUrl2;
    }

    public void setAwardPhotoUrl2(String awardPhotoUrl2) {
        this.awardPhotoUrl2 = awardPhotoUrl2;
    }

    public String getAwardPhotoUrl3() {
        return awardPhotoUrl3;
    }

    public void setAwardPhotoUrl3(String awardPhotoUrl3) {
        this.awardPhotoUrl3 = awardPhotoUrl3;
    }

    public String getAwardPhotoUrl4() {
        return awardPhotoUrl4;
    }

    public void setAwardPhotoUrl4(String awardPhotoUrl4) {
        this.awardPhotoUrl4 = awardPhotoUrl4;
    }

    public String getAwardPhotoUrl5() {
        return awardPhotoUrl5;
    }

    public void setAwardPhotoUrl5(String awardPhotoUrl5) {
        this.awardPhotoUrl5 = awardPhotoUrl5;
    }

    public String getAwardPhotoUrl6() {
        return awardPhotoUrl6;
    }

    public void setAwardPhotoUrl6(String awardPhotoUrl6) {
        this.awardPhotoUrl6 = awardPhotoUrl6;
    }

    public String getAwardPhotoUrl7() {
        return awardPhotoUrl7;
    }

    public void setAwardPhotoUrl7(String awardPhotoUrl7) {
        this.awardPhotoUrl7 = awardPhotoUrl7;
    }

    public String getAwardPhotoUrl8() {
        return awardPhotoUrl8;
    }

    public void setAwardPhotoUrl8(String awardPhotoUrl8) {
        this.awardPhotoUrl8 = awardPhotoUrl8;
    }

    public String getAwardPhotoUrl9() {
        return awardPhotoUrl9;
    }

    public void setAwardPhotoUrl9(String awardPhotoUrl9) {
        this.awardPhotoUrl9 = awardPhotoUrl9;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherInfo that = (TeacherInfo) o;
        return teacherId == that.teacherId &&
                Objects.equals(teacherConcept, that.teacherConcept) &&
                Objects.equals(teacherblurb, that.teacherblurb) &&
                Objects.equals(teacherSpeech, that.teacherSpeech) &&
                Objects.equals(teacherPhoto, that.teacherPhoto) &&
                Objects.equals(awardPhotoUrl1, that.awardPhotoUrl1) &&
                Objects.equals(awardPhotoUrl2, that.awardPhotoUrl2) &&
                Objects.equals(awardPhotoUrl3, that.awardPhotoUrl3) &&
                Objects.equals(awardPhotoUrl4, that.awardPhotoUrl4) &&
                Objects.equals(awardPhotoUrl5, that.awardPhotoUrl5) &&
                Objects.equals(awardPhotoUrl6, that.awardPhotoUrl6) &&
                Objects.equals(awardPhotoUrl7, that.awardPhotoUrl7) &&
                Objects.equals(awardPhotoUrl8, that.awardPhotoUrl8) &&
                Objects.equals(awardPhotoUrl9, that.awardPhotoUrl9);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teacherId, teacherConcept, teacherblurb, teacherSpeech, teacherPhoto, awardPhotoUrl1, awardPhotoUrl2, awardPhotoUrl3, awardPhotoUrl4, awardPhotoUrl5, awardPhotoUrl6, awardPhotoUrl7, awardPhotoUrl8, awardPhotoUrl9);
    }

    @Override

    public String toString() {
        return "TeacherInfo{" +
                "teacherId=" + teacherId +
                ", teacherConcept='" + teacherConcept + '\'' +
                ", teacherblurb='" + teacherblurb + '\'' +
                ", teacherSpeech='" + teacherSpeech + '\'' +
                ", teacherPhoto='" + teacherPhoto + '\'' +
                ", awardPhotoUrl1='" + awardPhotoUrl1 + '\'' +
                ", awardPhotoUrl2='" + awardPhotoUrl2 + '\'' +
                ", awardPhotoUrl3='" + awardPhotoUrl3 + '\'' +
                ", awardPhotoUrl4='" + awardPhotoUrl4 + '\'' +
                ", awardPhotoUrl5='" + awardPhotoUrl5 + '\'' +
                ", awardPhotoUrl6='" + awardPhotoUrl6 + '\'' +
                ", awardPhotoUrl7='" + awardPhotoUrl7 + '\'' +
                ", awardPhotoUrl8='" + awardPhotoUrl8 + '\'' +
                ", awardPhotoUrl9='" + awardPhotoUrl9 + '\'' +
                '}';
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}

