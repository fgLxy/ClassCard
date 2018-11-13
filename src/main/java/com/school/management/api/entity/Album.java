package com.school.management.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "album")
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "album_id", nullable = false)
    private Long id;

    @Column(name = "album_photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "album_describe", nullable = false)
    private String describe;

    @Column(name = "class_room_code", nullable = false)
    private int classRoomCode;

    @Column(name = "album_type")
    private int albumType;

    @Column(name = "album_added_date")
    private String addedDate;

    public Album() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAlbumType() {
        return albumType;
    }

    public void setAlbumType(int albumType) {
        this.albumType = albumType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getClassRoomCode() {
        return classRoomCode;
    }

    public void setClassRoomCode(int classRoomCode) {
        this.classRoomCode = classRoomCode;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", photoUrl='" + photoUrl + '\'' +
                ", describe='" + describe + '\'' +
                ", classRoomCode=" + classRoomCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return classRoomCode == album.classRoomCode &&
                Objects.equals(id, album.id) &&
                Objects.equals(photoUrl, album.photoUrl) &&
                Objects.equals(describe, album.describe);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, photoUrl, describe, classRoomCode);
    }
}
