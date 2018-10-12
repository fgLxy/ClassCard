package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 食谱
 */
@Entity
@Table(name = "food_menu")
public class FoodMenu implements Serializable {

    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "menu_id")
    private long menuId;
    /**
     * 主食名称
     */
    @Column(name = "menu_staple_food_name")
    private String menuStapleFoodName;
    /**
     * 主食图片地址
     */
    @Column(name = "menu_staple_food_image_url")
    private String menuStapleFoodImageUrl;
    /**
     * 星期几
     */
    @Column(name = "menu_week_of_day")
    private int menuWeekOfDay;
    /**
     * 1-早、 2-午、 3-晚
     */
    @Column(name = "menu_dinner")
    private int menuDinner;
    /**
     * 日期
     */
    @Column(name = "menu_date")
    private Date menuDate;
    /**
     * 状态
     */
    @Column(name = "menu_state")
    private String menuState;

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuStapleFoodName() {
        return menuStapleFoodName;
    }

    public void setMenuStapleFoodName(String menuStapleFoodName) {
        this.menuStapleFoodName = menuStapleFoodName;
    }

    public String getMenuStapleFoodImageUrl() {
        return menuStapleFoodImageUrl;
    }

    public void setMenuStapleFoodImageUrl(String menuStapleFoodImageUrl) {
        this.menuStapleFoodImageUrl = menuStapleFoodImageUrl;
    }

//    public String getMenuNonStapleFoodName() {
//        return menuNonStapleFoodName;
//    }
//
//    public void setMenuNonStapleFoodName(String menuNonStapleFoodName) {
//        this.menuNonStapleFoodName = menuNonStapleFoodName;
//    }
//
//    public String getMenuNonStapleFoodImageUrl() {
//        return menuNonStapleFoodImageUrl;
//    }
//
//    public void setMenuNonStapleFoodImageUrl(String menuNonStapleFoodImageUrl) {
//        this.menuNonStapleFoodImageUrl = menuNonStapleFoodImageUrl;
//    }

    public int getMenuWeekOfDay() {
        return menuWeekOfDay;
    }

    public void setMenuWeekOfDay(int menuWeekOfDay) {
        this.menuWeekOfDay = menuWeekOfDay;
    }

    public int getMenuDinner() {
        return menuDinner;
    }

    public void setMenuDinner(int menuDinner) {
        this.menuDinner = menuDinner;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    @Override
    public String toString() {
        return "FoodMenu{" +
                "menuId=" + menuId +
                ", menuStapleFoodName='" + menuStapleFoodName + '\'' +
                ", menuStapleFoodImageUrl='" + menuStapleFoodImageUrl + '\'' +
                ", menuWeekOfDay='" + menuWeekOfDay + '\'' +
                ", menuDinner='" + menuDinner + '\'' +
                ", menuDate='" + menuDate + '\'' +
                ", menuState='" + menuState + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodMenu foodMenu = (FoodMenu) o;
        return menuId == foodMenu.menuId &&
                Objects.equals(menuStapleFoodName, foodMenu.menuStapleFoodName) &&
                Objects.equals(menuStapleFoodImageUrl, foodMenu.menuStapleFoodImageUrl) &&
                Objects.equals(menuWeekOfDay, foodMenu.menuWeekOfDay) &&
                Objects.equals(menuDinner, foodMenu.menuDinner) &&
                Objects.equals(menuDate, foodMenu.menuDate) &&
                Objects.equals(menuState, foodMenu.menuState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, menuStapleFoodName, menuStapleFoodImageUrl, menuWeekOfDay, menuDinner, menuDate, menuState);
    }
}
