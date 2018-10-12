package com.school.management.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "news")
public class News implements Serializable {

    /**
     * 新闻ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "news_id", nullable = false)
    private Long newsId;

    /**
     * 新闻标题
     */
    @Column(name = "news_title", nullable = false)
    private String title;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_1", nullable = false)
    private String content1;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_2", nullable = false)
    private String content2;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_3", nullable = false)
    private String content3;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_4", nullable = false)
    private String content4;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_5", nullable = false)
    private String content5;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_6", nullable = false)
    private String content6;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_7", nullable = false)
    private String content7;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_8", nullable = false)
    private String content8;

    /**
     * 新闻内容
     */
    @Column(name = "news_content_9", nullable = false)
    private String content9;

    /**
     * 新闻发布时间
     */
    @Column(name = "news_publish_date", nullable = false)
    private String publishDate;

    /**
     * 新闻图片地址1
     */
    @Column(name = "news_image_url_1", nullable = false)
    private String imageUrl_1;

    /**
     * 新闻图片地址2
     */
    @Column(name = "news_image_url_2", nullable = false)
    private String imageUrl_2;

    /**
     * 新闻图片地址3
     */
    @Column(name = "news_image_url_3", nullable = false)
    private String imageUrl_3;

    /**
     * 新闻图片地址4
     */
    @Column(name = "news_image_url_4", nullable = false)
    private String imageUrl_4;

    /**
     * 新闻图片地址5
     */
    @Column(name = "news_image_url_5", nullable = false)
    private String imageUrl_5;

    /**
     * 新闻图片地址6
     */
    @Column(name = "news_image_url_6", nullable = false)
    private String imageUrl_6;

    /**
     * 新闻图片地址7
     */
    @Column(name = "news_image_url_7", nullable = false)
    private String imageUrl_7;

    /**
     * 新闻图片地址8
     */
    @Column(name = "news_image_url_8", nullable = false)
    private String imageUrl_8;

    /**
     * 新闻图片地址9
     */
    @Column(name = "news_image_url_9", nullable = false)
    private String imageUrl_9;

    @Column(name = "news_little_1", nullable = false)
    private String little1;

    @Column(name = "news_little_2", nullable = false)
    private String little2;

    @Column(name = "news_little_3", nullable = false)
    private String little3;

    @Column(name = "news_little_4", nullable = false)
    private String little4;

    @Column(name = "news_little_5", nullable = false)
    private String little5;

    @Column(name = "news_little_6", nullable = false)
    private String little6;

    @Column(name = "news_little_7", nullable = false)
    private String little7;

    @Column(name = "news_little_8", nullable = false)
    private String little8;

    @Column(name = "news_little_9", nullable = false)
    private String little9;

    @Column(name = "news_publish_user", nullable = false)
    private String publishUser;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getImageUrl_1() {
        return imageUrl_1;
    }

    public void setImageUrl_1(String imageUrl_1) {
        this.imageUrl_1 = imageUrl_1;
    }

    public String getImageUrl_2() {
        return imageUrl_2;
    }

    public void setImageUrl_2(String imageUrl_2) {
        this.imageUrl_2 = imageUrl_2;
    }

    public String getImageUrl_3() {
        return imageUrl_3;
    }

    public void setImageUrl_3(String imageUrl_3) {
        this.imageUrl_3 = imageUrl_3;
    }

    public String getImageUrl_4() {
        return imageUrl_4;
    }

    public void setImageUrl_4(String imageUrl_4) {
        this.imageUrl_4 = imageUrl_4;
    }

    public String getImageUrl_5() {
        return imageUrl_5;
    }

    public void setImageUrl_5(String imageUrl_5) {
        this.imageUrl_5 = imageUrl_5;
    }

    public String getImageUrl_6() {
        return imageUrl_6;
    }

    public void setImageUrl_6(String imageUrl_6) {
        this.imageUrl_6 = imageUrl_6;
    }

    public String getImageUrl_7() {
        return imageUrl_7;
    }

    public void setImageUrl_7(String imageUrl_7) {
        this.imageUrl_7 = imageUrl_7;
    }

    public String getImageUrl_8() {
        return imageUrl_8;
    }

    public void setImageUrl_8(String imageUrl_8) {
        this.imageUrl_8 = imageUrl_8;
    }

    public String getImageUrl_9() {
        return imageUrl_9;
    }

    public void setImageUrl_9(String imageUrl_9) {
        this.imageUrl_9 = imageUrl_9;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getContent5() {
        return content5;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public String getContent6() {
        return content6;
    }

    public void setContent6(String content6) {
        this.content6 = content6;
    }

    public String getContent7() {
        return content7;
    }

    public void setContent7(String content7) {
        this.content7 = content7;
    }

    public String getContent8() {
        return content8;
    }

    public void setContent8(String content8) {
        this.content8 = content8;
    }

    public String getContent9() {
        return content9;
    }

    public void setContent9(String content9) {
        this.content9 = content9;
    }

    public String getLittle1() {
        return little1;
    }

    public void setLittle1(String little1) {
        this.little1 = little1;
    }

    public String getLittle2() {
        return little2;
    }

    public void setLittle2(String little2) {
        this.little2 = little2;
    }

    public String getLittle3() {
        return little3;
    }

    public void setLittle3(String little3) {
        this.little3 = little3;
    }

    public String getLittle4() {
        return little4;
    }

    public void setLittle4(String little4) {
        this.little4 = little4;
    }

    public String getLittle5() {
        return little5;
    }

    public void setLittle5(String little5) {
        this.little5 = little5;
    }

    public String getLittle6() {
        return little6;
    }

    public void setLittle6(String little6) {
        this.little6 = little6;
    }

    public String getLittle7() {
        return little7;
    }

    public void setLittle7(String little7) {
        this.little7 = little7;
    }

    public String getLittle8() {
        return little8;
    }

    public void setLittle8(String little8) {
        this.little8 = little8;
    }

    public String getLittle9() {
        return little9;
    }

    public void setLittle9(String little9) {
        this.little9 = little9;
    }

    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", content1='" + content1 + '\'' +
                ", content2='" + content2 + '\'' +
                ", content3='" + content3 + '\'' +
                ", content4='" + content4 + '\'' +
                ", content5='" + content5 + '\'' +
                ", content6='" + content6 + '\'' +
                ", content7='" + content7 + '\'' +
                ", content8='" + content8 + '\'' +
                ", content9='" + content9 + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", imageUrl_1='" + imageUrl_1 + '\'' +
                ", imageUrl_2='" + imageUrl_2 + '\'' +
                ", imageUrl_3='" + imageUrl_3 + '\'' +
                ", imageUrl_4='" + imageUrl_4 + '\'' +
                ", imageUrl_5='" + imageUrl_5 + '\'' +
                ", imageUrl_6='" + imageUrl_6 + '\'' +
                ", imageUrl_7='" + imageUrl_7 + '\'' +
                ", imageUrl_8='" + imageUrl_8 + '\'' +
                ", imageUrl_9='" + imageUrl_9 + '\'' +
                '}';
    }
}
