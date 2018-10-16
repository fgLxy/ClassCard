package com.school.management.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "micro_video")
public class MicroVideo {

    @Id
    @Column(name = "micro_video_id")
    private long microVideoId;

    @Column(name = "micro_video_title")
    private String microVideoTitle;

    @Column(name = "micro_video_url")
    private String microVideoURL;

    @Column(name = "micro_video_date")
    private Timestamp microVideoDate;

    @Column(name = "micro_video_cover")
    private String microVideoCover;

    @Override
    public String toString() {
        return "MicroVideo:{" +
                "microVideoId:" + microVideoId +
                ", microVideoTitle:\"" + microVideoTitle + '\"' +
                ", microVideoURL:\"" + microVideoURL + '\"' +
                ", microVideoDate:" + microVideoDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MicroVideo that = (MicroVideo) o;
        return microVideoId == that.microVideoId &&
                Objects.equals(microVideoTitle, that.microVideoTitle) &&
                Objects.equals(microVideoURL, that.microVideoURL) &&
                Objects.equals(microVideoDate, that.microVideoDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(microVideoId, microVideoTitle, microVideoURL, microVideoDate);
    }

    public long getMicroVideoId() {
        return microVideoId;
    }

    public void setMicroVideoId(long microVideoId) {
        this.microVideoId = microVideoId;
    }

    public String getMicroVideoTitle() {
        return microVideoTitle;
    }

    public void setMicroVideoTitle(String microVideoTitle) {
        this.microVideoTitle = microVideoTitle;
    }

    public String getMicroVideoURL() {
        return microVideoURL;
    }

    public void setMicroVideoURL(String microVideoURL) {
        this.microVideoURL = microVideoURL;
    }

    public Timestamp getMicroVideoDate() {
        return microVideoDate;
    }

    public void setMicroVideoDate(Timestamp microVideoDate) {
        this.microVideoDate = microVideoDate;
    }

    public String getMicroVideoCover() {
        return microVideoCover;
    }

    public void setMicroVideoCover(String microVideoCover) {
        this.microVideoCover = microVideoCover;
    }
}
