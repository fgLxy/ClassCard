package com.school.management.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "comment_account")
    private String commentAccount;

    @Column(name = "comment_time")
    private String commentTime;

    @Column(name = "comment_parent")
    private int commentParent;

    @Column(name = "comment_teacher", insertable = false, updatable = false)
    private int commentTeacher;

    @Column(name = "comment_status")
    private int commentStatus;

    @Column(name = "comment_type")
    private int commentType;

    @Column(name = "comment_photo_1")
    private String commentPhoto1;

    @Column(name = "comment_photo_2")
    private String commentPhoto2;

    @Column(name = "comment_photo_3")
    private String commentPhoto3;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_teacher", referencedColumnName = "teacher_Id")
    private Teacher teacher;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentAccount() {
        return commentAccount;
    }

    public void setCommentAccount(String commentAccount) {
        this.commentAccount = commentAccount;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentTeacher() {
        return commentTeacher;
    }

    public void setCommentTeacher(int commentTeacher) {
        this.commentTeacher = commentTeacher;
    }

    public int getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(int commentParent) {
        this.commentParent = commentParent;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCommentPhoto1() {
        return commentPhoto1;
    }

    public void setCommentPhoto1(String commentPhoto1) {
        this.commentPhoto1 = commentPhoto1;
    }

    public String getCommentPhoto2() {
        return commentPhoto2;
    }

    public void setCommentPhoto2(String commentPhoto2) {
        this.commentPhoto2 = commentPhoto2;
    }

    public String getCommentPhoto3() {
        return commentPhoto3;
    }

    public void setCommentPhoto3(String commentPhoto3) {
        this.commentPhoto3 = commentPhoto3;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId &&
                commentParent == comment.commentParent &&
                commentTeacher == comment.commentTeacher &&
                Objects.equals(commentAccount, comment.commentAccount) &&
                Objects.equals(commentTime, comment.commentTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(commentId, commentAccount, commentTime, commentParent, commentTeacher);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentAccount='" + commentAccount + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", commentParent=" + commentParent +
                ", commentTeacher=" + commentTeacher +
                '}';
    }
}
