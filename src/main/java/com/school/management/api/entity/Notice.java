package com.school.management.api.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 通知
 */
@Entity
@Table(name = "notice")
public class Notice implements Serializable {

    /**
     * 通知ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "notice_id")
    private long noticeId;

    /**
     * 通知时间
     */
    @Column(name = "notice_time")
    private String noticeTime;

    /**
     * 通知等级
     */
    @Column(name = "notice_level")
    private Integer noticeLevel;

    /**
     * 通知内容
     */
    @Column(name = "notice_content")
    private String noticeContent;

    /**
     * 通知来源
     */
    @Column(name = "notice_source")
    private String noticeSource;

    @Column(name = "notice_photo")
    private String noticePhoto;

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticePhoto() {
        return noticePhoto;
    }

    public void setNoticePhoto(String noticePhoto) {
        this.noticePhoto = noticePhoto;
    }

    public Integer getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(Integer noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeSource() {
        return noticeSource;
    }

    public void setNoticeSource(String noticeSource) {
        this.noticeSource = noticeSource;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", noticeTime='" + noticeTime + '\'' +
                ", noticeLevel=" + noticeLevel +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeSource='" + noticeSource + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return noticeId == notice.noticeId &&
                Objects.equals(noticeTime, notice.noticeTime) &&
                Objects.equals(noticeLevel, notice.noticeLevel) &&
                Objects.equals(noticeContent, notice.noticeContent) &&
                Objects.equals(noticeSource, notice.noticeSource);
    }

    @Override
    public int hashCode() {

        return Objects.hash(noticeId, noticeTime, noticeLevel, noticeContent, noticeSource);
    }
}
