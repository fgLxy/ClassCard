package com.school.management.api.vo;

public class AddedClass {

    private String className;

    private String classHeadmaster;

    private String classStudentTotal;

    private String classMonitor;

    private String classMoralEducation;

    private String classDate;

    private String classScore;

    private Integer classroomCode;

    public String getClassName() {
        return className;
    }

    public String getClassHeadmaster() {
        return classHeadmaster;
    }

    public String getClassStudentTotal() {
        return classStudentTotal;
    }

    public String getClassMonitor() {
        return classMonitor;
    }

    public String getClassMoralEducation() {
        return classMoralEducation;
    }

    public String getClassDate() {
        return classDate;
    }

    public String getClassScore() {
        return classScore;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassHeadmaster(String classHeadmaster) {
        this.classHeadmaster = classHeadmaster;
    }

    public void setClassStudentTotal(String classStudentTotal) {
        this.classStudentTotal = classStudentTotal;
    }

    public void setClassMonitor(String classMonitor) {
        this.classMonitor = classMonitor;
    }

    public void setClassMoralEducation(String classMoralEducation) {
        this.classMoralEducation = classMoralEducation;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public void setClassScore(String classScore) {
        this.classScore = classScore;
    }

    public Integer getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(Integer classroomCode) {
        this.classroomCode = classroomCode;
    }
}
