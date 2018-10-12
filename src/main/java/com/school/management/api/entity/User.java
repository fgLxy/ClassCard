package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_teltphone_num")
    private String userTeltphoneNum;

    @Column(name = "user_mobilephone_num")
    private String userMobilephoneNum;

    @Column(name = "isTeacher")
    private int isTeacher;

    @Column(name = "user_add_date")
    private String addDate;

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Permission permission;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", realName='" + realName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userTeltphoneNum=" + userTeltphoneNum +
                ", userMobilephoneNum=" + userMobilephoneNum +
                ", permission=" + permission +
                '}';
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserTeltphoneNum() {
        return userTeltphoneNum;
    }

    public void setUserTeltphoneNum(String userTeltphoneNum) {
        this.userTeltphoneNum = userTeltphoneNum;
    }

    public String getUserMobilephoneNum() {
        return userMobilephoneNum;
    }

    public void setUserMobilephoneNum(String userMobilephoneNum) {
        this.userMobilephoneNum = userMobilephoneNum;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }
}
