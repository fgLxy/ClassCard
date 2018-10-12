package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ip")
public class Ip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "ip_id")
    private long ipId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "device_uuid")
    private String deviceUUID;

    @Column(name = "class_id")
    private long class_id;

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", insertable = false, updatable = false)
    private Class classId;

    @Override
    public String toString() {
        return "Ip{" +
                "ipId=" + ipId +
                ", ip='" + ip + '\'' +
                ", deviceUUID=" + deviceUUID +
                ", classId=" + classId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ip ip1 = (Ip) o;
        return ipId == ip1.ipId &&
                deviceUUID == ip1.deviceUUID &&
                Objects.equals(ip, ip1.ip) &&
                Objects.equals(classId, ip1.classId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ipId, ip, deviceUUID, classId);
    }

    public long getIpId() {
        return ipId;
    }

    public void setIpId(long ipId) {
        this.ipId = ipId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
    }

    public Class getClassId() {
        return classId;
    }

    public void setClassId(Class classId) {
        this.classId = classId;
    }
}
