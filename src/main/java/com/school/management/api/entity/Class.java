package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "class")
public class Class implements Serializable {

    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long classId;

    @Column(name = "class_name")
    private String className;

    @OneToOne
    @JoinColumn(referencedColumnName = "teacher_ID", name = "class_headmaster")
    private Teacher classHeadmaster;

    @Column(name = "class_student_total")
    private String classStudentTotal;

    @Column(name = "class_status")
    private String classStatus;

    @Column(name = "class_max_student")
    private Integer classMaxStudent;

    @Column(name = "class_room_code")
    private Integer classroomCode;

    @Column(name = "class_sign_in_count")
    private int classSignInCount;

    @Column(name = "class_late_count")
    private int classLateCount;

    @Column(name = "class_holiday_count")
    private int classHolidayCount;

    @Column(name = "class_moral_education")
    private String classMoralEducation;

    @Column(name = "class_date")
    private String classDate;

    @Column(name = "class_score")
    private int classScore;

    @Column(name = "class_badge")
    private String classBadge;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_room_code", referencedColumnName = "class_room_code", insertable = false, updatable = false)
    private Set<Album> albums;

    @OneToOne
    @JoinColumn(name = "class_monitor", referencedColumnName = "student_card_num")
    private Student classMonitor;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_classroom", referencedColumnName = "class_name")
    private Set<Student> students;

    @ManyToMany(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Set<Teacher> teachers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_room_code", referencedColumnName = "class_room_code")
    private Set<Quantify> quantifies;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_class_name", referencedColumnName = "class_name")
    @OrderBy("schedule_course_starttime")
    private Set<Schedule> schedules;

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public Set<Album> getAlbum() {
        return albums;
    }

    public void setAlbum(Set<Album> album) {
        this.albums = album;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getClassHeadmaster() {
        return classHeadmaster;
    }

    public void setClassHeadmaster(Teacher classHeadmaster) {
        this.classHeadmaster = classHeadmaster;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getClassBadge() {
        return classBadge;
    }

    public void setClassBadge(String classBadge) {
        this.classBadge = classBadge;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getClassStudentTotal() {
        return classStudentTotal;
    }

    public void setClassStudentTotal(String classStudentTotal) {
        this.classStudentTotal = classStudentTotal;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public Integer getClassMaxStudent() {
        return classMaxStudent;
    }

    public void setClassMaxStudent(Integer classMaxStudent) {
        this.classMaxStudent = classMaxStudent;
    }

    public Integer getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(Integer classroomCode) {
        this.classroomCode = classroomCode;
    }

    public int getClassSignInCount() {
        return classSignInCount;
    }

    public void setClassSignInCount(int classSignInCount) {
        this.classSignInCount = classSignInCount;
    }

    public int getClassLateCount() {
        return classLateCount;
    }

    public void setClassLateCount(int classLateCount) {
        this.classLateCount = classLateCount;
    }

    public int getClassHolidayCount() {
        return classHolidayCount;
    }

    public void setClassHolidayCount(int classHolidayCount) {
        this.classHolidayCount = classHolidayCount;
    }

    public String getClassMoralEducation() {
        return classMoralEducation;
    }

    public void setClassMoralEducation(String classMoralEducation) {
        this.classMoralEducation = classMoralEducation;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public int getClassScore() {
        return classScore;
    }

    public void setClassScore(int classScore) {
        this.classScore = classScore;
    }

    public Student getClassMonitor() {
        return classMonitor;
    }

    public void setClassMonitor(Student classMonitor) {
        this.classMonitor = classMonitor;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Quantify> getQuantifies() {
        return quantifies;
    }

    public void setQuantifies(Set<Quantify> quantifies) {
        this.quantifies = quantifies;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", classHeadmaster=" + classHeadmaster +
                ", classStudentTotal='" + classStudentTotal + '\'' +
                ", classStatus='" + classStatus + '\'' +
                ", classMaxStudent=" + classMaxStudent +
                ", classroomCode=" + classroomCode +
                ", classSignInCount=" + classSignInCount +
                ", classLateCount=" + classLateCount +
                ", classHolidayCount=" + classHolidayCount +
                ", classMoralEducation='" + classMoralEducation + '\'' +
                ", classDate='" + classDate + '\'' +
                ", classScore=" + classScore +
//                ", albums=" + albums +
                ", classMonitor=" + classMonitor +
                ", students=" + students +
                ", teachers=" + teachers +
                ", quantifies=" + quantifies +
                ", schedules=" + schedules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return classId == aClass.classId &&
                Objects.equals(className, aClass.className) &&
                Objects.equals(classHeadmaster, aClass.classHeadmaster) &&
                Objects.equals(classStudentTotal, aClass.classStudentTotal) &&
                Objects.equals(classStatus, aClass.classStatus) &&
                Objects.equals(classMaxStudent, aClass.classMaxStudent) &&
                Objects.equals(classroomCode, aClass.classroomCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(classId, className, classHeadmaster, classStudentTotal, classStatus, classMaxStudent, classroomCode);
    }
}
