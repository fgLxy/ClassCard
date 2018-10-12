package com.school.management.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "homework")
public class HomeWork {

    @Id
    @Column(name = "home_work_id")
    private long homeworkID;

    @Column(name = "class_code")
    private int classCode;

    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "content")
    private String content;

    @Column(name = "work_date")
    private Date workDate;

    @Column(name = "work_photo")
    private String workPhotos;

    @Override
    public String toString() {
        return "HomeWork{" +
                "homeworkID=" + homeworkID +
                ", classCode=" + classCode +
                ", teacherId=" + teacherId +
                ", content='" + content + '\'' +
                ", workDate=" + workDate.toLocalDate() +
                ", workPhotos='" + workPhotos + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeWork homeWork = (HomeWork) o;
        return homeworkID == homeWork.homeworkID &&
                classCode == homeWork.classCode &&
                teacherId == homeWork.teacherId &&
                Objects.equals(content, homeWork.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homeworkID, classCode, teacherId, content);
    }

    public long getHomeworkID() {
        return homeworkID;
    }

    public void setHomeworkID(long homeworkID) {
        this.homeworkID = homeworkID;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getWorkPhotos() {
        return workPhotos;
    }

    public void setWorkPhotos(String workPhotos) {
        this.workPhotos = workPhotos;
    }
}
