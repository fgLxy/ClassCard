package com.school.management.api.vo;

public class FoodVo {

    private String foodName;

    private int dinner;

    public FoodVo() {
    }

    public FoodVo(int dinner) {
        this.dinner = dinner;
    }

    public FoodVo(String foodName) {
        this.foodName = foodName;
    }

    public FoodVo(String foodName, int dinner) {
        this.foodName = foodName;
        this.dinner = dinner;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }
}
