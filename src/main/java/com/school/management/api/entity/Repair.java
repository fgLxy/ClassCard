package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
/**
 * 修理
 */
@Entity
@Table(name = "repair")
public class Repair implements Serializable {

    /**
     * 保修ID
     */
    @Id
    @Column(name = "repair_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    private long repairId;

    /**
     * 保修日期
     */
    @Column(name = "repair_date")
    private String repairDate;

    /**
     * 保修物品
     */
    @Column(name = "repair_things")
    private String repairThings;

    /**
     * 保修描述
     */
    @Column(name = "repair_descript")
    private String repairDescript;

    /**
     * 班级编号
     */
    @Column(name = "class_room_code")
    private int classCode;

    @Column(name = "repair_status")
    private int repairStatus;

    public long getRepairId() {
        return repairId;
    }

    public void setRepairId(long repairId) {
        this.repairId = repairId;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairThings() {
        return repairThings;
    }

    public void setRepairThings(String repairThings) {
        this.repairThings = repairThings;
    }

    public String getRepairDescript() {
        return repairDescript;
    }

    public void setRepairDescript(String repairDescript) {
        this.repairDescript = repairDescript;
    }

    public int getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(int repairStatus) {
        this.repairStatus = repairStatus;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairId=" + repairId +
                ", repairDate='" + repairDate + '\'' +
                ", repairThings='" + repairThings + '\'' +
                ", repairDescript='" + repairDescript + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repair repair = (Repair) o;
        return repairId == repair.repairId &&
                Objects.equals(repairDate, repair.repairDate) &&
                Objects.equals(repairThings, repair.repairThings) &&
                Objects.equals(repairDescript, repair.repairDescript);
    }

    @Override
    public int hashCode() {

        return Objects.hash(repairId, repairDate, repairThings, repairDescript);
    }
}
