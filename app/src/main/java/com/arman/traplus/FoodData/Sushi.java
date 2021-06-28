package com.arman.traplus.FoodData;

import java.util.ArrayList;

public class Sushi {
    private ArrayList<Food> Sushi = new ArrayList<Food>();

    private ArrayList<Food> Ingredients = new ArrayList<Food>();

    private ArrayList<Food> Special = new ArrayList<Food>();

    public Sushi(){
        Food food;
        String[][] foodList = {{"壽司", "すし"}, {"生魚片", "さしみ"}, {"山葵", "わさび"}, {"握壽司", "にぎり"}, {"豆皮壽司", "いなりずし"}, {"散壽司", "ちらしずし"},
                {"粗卷壽司", "ふとまき"}, {"箱型握壽司", "押し寿司"}, {"綜合拼盤", "盛り合わせ"}, {"炙燒", "炙り"}, {"湯品", "汁物"} };

        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            Sushi.add(food);
        }

        //Ingredients
        foodList = new String[][]{{"鮪魚", "まぐろ"}, {"鮪魚中腹肉", "中トロ"}, {"鮪魚大腹肉", "大トロ"}, {"鮭魚", "サーモン"}, {"竹筴魚", "あじ"},
                {"香魚", "あゆ"}, {"鰹魚", "かつお"}, {"鮃魚", "ひらめ"}, {"鰈魚", "かれい"}, {"紅魽魚", "かんぱち"}, {"鮟鱇魚", "あんこう"}, {"秋刀魚", "さんま"},
                {"沙丁魚", "いわし"}, {"鱸魚", "すずき"}, {"柳葉魚", "ししゃも"}, {"鰤魚", "はまち"}, {"鯛魚", "たい"}, {"真鯛", "まだい"}, {"鯖魚", "さば"}, {"比目魚鰭邊肉", "えんがわ"},
                {"鰻魚", "うなぎ"}, {"海膽", "うに"}, {"鮑魚", "あわび"}, {"星鰻", "穴子"}, {"扇貝", "ほたて"}, {"螺肉", "つぶ貝"}, {"北寄貝", "ほっき貝"},
                {"蝦子", "えび"}, {"蝦蛄", "しゃこ"}, {"螃蟹", "かに"}, {"松葉蟹", "ずわいかに"}, {"蟹黃", "かにみそ"}, {"花枝", "いか"}, {"花枝觸鬚", "げそ"},
                {"章魚", "たこ"}, {"吻仔魚", "しらす"}, {"鮭魚卵", "いくら"}, {"鯡魚卵", "かずのこ"}, {"明太子", "たらこ"}, {"魚白", "しらこ"}};

        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            Ingredients.add(food);
        }

        foodList = new String[][]{{"鐵火卷", "鉄火巻き"}, {"小黃瓜細捲壽司", "かっぱまき"}, {"醃蘿蔔細捲壽司", "新香巻き"}, {"炸蝦天婦羅壽司", "えび天"}, {"花枝天婦羅壽司", "いか天"},
                {"納豆細捲壽司", "なっとう巻"}, {"瓢瓜細捲壽司", "干ぴょう巻"}, {"紫蘇捲壽司", "しそまき"}, {"牛五花壽司", "牛カルビ"}, {"漢堡排壽司", "ハンバーグ"},
                {"烤牛肉壽司", "ローストビーフ"}};

        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            Special.add(food);
        }
    }

    public String[] getArrayName(){
        String[] arrayName = new String[]{"Sushi", "Ingredients", "Special"};
        return arrayName;
    }

    public ArrayList<Food> getSushi() {
        return Sushi;
    }

    public ArrayList<Food> getIngredients() {
        return Ingredients;
    }

    public ArrayList<Food> getSpecial() {
        return Special;
    }
}
