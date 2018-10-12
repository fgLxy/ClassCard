package com.school.management.api.vo;

import com.school.management.api.entity.TeacherInfo;

public class TeacherVo {

    private String teacherName;

    private TeacherInfo info;

    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public TeacherInfo getInfo() {
        return info;
    }

    public void setInfo(TeacherInfo info) {
        this.info = info;
    }

}
