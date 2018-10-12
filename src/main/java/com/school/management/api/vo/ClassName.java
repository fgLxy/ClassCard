package com.school.management.api.vo;

public class ClassName {

    private Long classID;

    private String className;

    private int classCode;

    @Override
    public String toString() {
        return "ClassName{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
