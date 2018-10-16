package com.school.management.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "short_video")
public class ShortVideo {

    @Id
    @Column(name = "short_video_id")
    private long shortVideoId;

    @Column(name = "short_video_title")
    private String shortVideoTitle;

    @Column(name = "short_video_url")
    private String shortVideoURL;

    @Column(name = "short_video_date")
    private Timestamp shortVideoDate;

    @Column(name = "short_video_cover")
    private String shortVideoCover;

    @Override
    public String toString() {
        return "ShortVideo:{" +
                "ShortVideoId:" + shortVideoId +
                ", ShortVideoTitle:\"" + shortVideoTitle + '\"' +
                ", ShortVideoURL:\"" + shortVideoURL + '\"' +
                ", ShortVideoDate:" + shortVideoDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortVideo that = (ShortVideo) o;
        return shortVideoId == that.shortVideoId &&
                Objects.equals(shortVideoTitle, that.shortVideoTitle) &&
                Objects.equals(shortVideoURL, that.shortVideoURL) &&
                Objects.equals(shortVideoDate, that.shortVideoDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(shortVideoId, shortVideoTitle, shortVideoURL, shortVideoDate);
    }

    public long getShortVideoId() {
        return shortVideoId;
    }

    public void setShortVideoId(long shortVideoId) {
        this.shortVideoId = shortVideoId;
    }

    public String getShortVideoTitle() {
        return shortVideoTitle;
    }

    public void setShortVideoTitle(String shortVideoTitle) {
        this.shortVideoTitle = shortVideoTitle;
    }

    public String getShortVideoURL() {
        return shortVideoURL;
    }

    public void setShortVideoURL(String shortVideoURL) {
        this.shortVideoURL = shortVideoURL;
    }

    public Timestamp getShortVideoDate() {
        return shortVideoDate;
    }

    public void setShortVideoDate(Timestamp shortVideoDate) {
        this.shortVideoDate = shortVideoDate;
    }

    public String getShortVideoCover() {
        return shortVideoCover;
    }

    public void setShortVideoCover(String shortVideoCover) {
        this.shortVideoCover = shortVideoCover;
    }
}
