package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "video")
@Cacheable
public class Video implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private Long id;

    /**
     * 视频流地址
     * */
    @Column(name = "stream_url")
    private String streamUrl;

    /**
     * 班级编号
     * */
    @Column(name = "class_code")
    private String classCode;

    /**
     * 班级名称
     * */
    @Column(name = "class_name")
    private String className;

    /**
     * 相机编号
     * */
    @Column(name = "camera_code")
    private String cameraCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCameraCode() {
        return cameraCode;
    }

    public void setCameraCode(String cameraCode) {
        this.cameraCode = cameraCode;
    }
}
