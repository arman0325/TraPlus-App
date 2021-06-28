package com.arman.traplus.FoodData;

public class Food {
    private String zhName;
    private String jaName;

    public Food(String zh, String ja){
        this.zhName = zh;
        this.jaName = ja;
    }

    public String getZhName() {
        return zhName;
    }

    public String getJaName() {
        return jaName;
    }
}
