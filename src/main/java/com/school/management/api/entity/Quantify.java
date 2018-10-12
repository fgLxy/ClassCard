package com.school.management.api.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 量化
 */
@Entity
@Table(name = "quantify")
public class Quantify implements Serializable {

    /**
     * 量化ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "quantify_id")
    private long quantifyId;

    /**
     * 量化类型（1、卫生；2、纪律；3、早操）
     */
    @Column(name = "quantify_type")
    private Integer quantifyType;

    /**
     * 量化备注
     */
    @Column(name = "quantify_remark")
    private String quantifyRemark;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_1")
    private String quantifyPhotoUrl;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_2")
    private String quantifyPhotoUrl2;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_3")
    private String quantifyPhotoUrl3;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_4")
    private String quantifyPhotoUrl4;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_5")
    private String quantifyPhotoUrl5;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_6")
    private String quantifyPhotoUrl6;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_7")
    private String quantifyPhotoUrl7;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_8")
    private String quantifyPhotoUrl8;

    /**
     * 被扣分数的相关图片
     */
    @Column(name = "quantify_photo_url_9")
    private String quantifyPhotoUrl9;

    /**
     * 对应的班级ID
     */
    @Column(name = "class_room_code")
    private Long classCode;

    @Column(name = "quantify_user")
    private String quantifyUser;

    @Column(name = "quantify_date")
    private String quantifyDate;

    public long getQuantifyId() {
        return quantifyId;
    }

    public void setQuantifyId(long quantifyId) {
        this.quantifyId = quantifyId;
    }

    public Integer getQuantifyType() {
        return quantifyType;
    }

    public void setQuantifyType(Integer quantifyType) {
        this.quantifyType = quantifyType;
    }

    public String getQuantifyRemark() {
        return quantifyRemark;
    }

    public void setQuantifyRemark(String quantifyRemark) {
        this.quantifyRemark = quantifyRemark;
    }

    public String getQuantifyPhotoUrl() {
        return quantifyPhotoUrl;
    }

    public void setQuantifyPhotoUrl(String quantifyPhotoUrl) {
        this.quantifyPhotoUrl = quantifyPhotoUrl;
    }

    public String getQuantifyPhotoUrl2() {
        return quantifyPhotoUrl2;
    }

    public void setQuantifyPhotoUrl2(String quantifyPhotoUrl2) {
        this.quantifyPhotoUrl2 = quantifyPhotoUrl2;
    }

    public String getQuantifyPhotoUrl3() {
        return quantifyPhotoUrl3;
    }

    public void setQuantifyPhotoUrl3(String quantifyPhotoUrl3) {
        this.quantifyPhotoUrl3 = quantifyPhotoUrl3;
    }

    public Long getClassId() {
        return classCode;
    }

    public void setClassId(Long classCode) {
        this.classCode = classCode;
    }

    public String getQuantifyUser() {
        return quantifyUser;
    }

    public void setQuantifyUser(String quantifyUser) {
        this.quantifyUser = quantifyUser;
    }

    public String getQuantifyDate() {
        return quantifyDate;
    }

    public void setQuantifyDate(String quantifyDate) {
        this.quantifyDate = quantifyDate;
    }

    public String getQuantifyPhotoUrl4() {
        return quantifyPhotoUrl4;
    }

    public void setQuantifyPhotoUrl4(String quantifyPhotoUrl4) {
        this.quantifyPhotoUrl4 = quantifyPhotoUrl4;
    }

    public String getQuantifyPhotoUrl5() {
        return quantifyPhotoUrl5;
    }

    public void setQuantifyPhotoUrl5(String quantifyPhotoUrl5) {
        this.quantifyPhotoUrl5 = quantifyPhotoUrl5;
    }

    public String getQuantifyPhotoUrl6() {
        return quantifyPhotoUrl6;
    }

    public void setQuantifyPhotoUrl6(String quantifyPhotoUrl6) {
        this.quantifyPhotoUrl6 = quantifyPhotoUrl6;
    }

    public String getQuantifyPhotoUrl7() {
        return quantifyPhotoUrl7;
    }

    public void setQuantifyPhotoUrl7(String quantifyPhotoUrl7) {
        this.quantifyPhotoUrl7 = quantifyPhotoUrl7;
    }

    public String getQuantifyPhotoUrl8() {
        return quantifyPhotoUrl8;
    }

    public void setQuantifyPhotoUrl8(String quantifyPhotoUrl8) {
        this.quantifyPhotoUrl8 = quantifyPhotoUrl8;
    }

    public String getQuantifyPhotoUrl9() {
        return quantifyPhotoUrl9;
    }

    public void setQuantifyPhotoUrl9(String quantifyPhotoUrl9) {
        this.quantifyPhotoUrl9 = quantifyPhotoUrl9;
    }

    @Override
    public String toString() {
        return "Quantify{" +
                "quantifyId=" + quantifyId +
                ", quantifyType=" + quantifyType +
                ", quantifyRemark='" + quantifyRemark + '\'' +
                ", quantifyPhotoUrl='" + quantifyPhotoUrl + '\'' +
                ", quantifyPhotoUrl2='" + quantifyPhotoUrl2 + '\'' +
                ", quantifyPhotoUrl3='" + quantifyPhotoUrl3 + '\'' +
                ", classCode=" + classCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantify quantify = (Quantify) o;
        return quantifyId == quantify.quantifyId &&
                Objects.equals(quantifyType, quantify.quantifyType) &&
                Objects.equals(quantifyRemark, quantify.quantifyRemark) &&
                Objects.equals(quantifyPhotoUrl, quantify.quantifyPhotoUrl) &&
                Objects.equals(classCode, quantify.classCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(quantifyId, quantifyType, quantifyRemark, quantifyPhotoUrl, classCode);
    }
}
