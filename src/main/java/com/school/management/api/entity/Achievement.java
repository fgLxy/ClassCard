package com.school.management.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "achievement_id", nullable = false)
    private long achievementId;

    @Column(name = "student_code")
    private int studentCode;

    @Column(name = "average_achievement")
    private String averageAchievement;

    @Column(name = "grade_type")
    private String gradeType;

    @Column(name = "mathematics_score")
    private int mathematicsScore;

    @Column(name = "chinese_score")
    private int chineseScore;

    @Column(name = "physic_score")
    private int physicScore;

    @Column(name = "chemistry_score")
    private int chemistryScore;

    @Column(name = "english_score")
    private int englishScore;

    @Column(name = "student_name")
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(long achievementId) {
        this.achievementId = achievementId;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public String getAverageAchievement() {
        return averageAchievement;
    }

    public void setAverageAchievement(String averageAchievement) {
        this.averageAchievement = averageAchievement;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public int getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(int mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public int getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(int chineseScore) {
        this.chineseScore = chineseScore;
    }

    public int getPhysicScore() {
        return physicScore;
    }

    public void setPhysicScore(int physicScore) {
        this.physicScore = physicScore;
    }

    public int getChemistryScore() {
        return chemistryScore;
    }

    public void setChemistryScore(int chemistryScore) {
        this.chemistryScore = chemistryScore;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }
}
