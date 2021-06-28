package com.arman.traplus.FoodData;

import java.util.ArrayList;

public class Izakaya {
    private ArrayList<Food> MeatDishes = new ArrayList<Food>();

    private ArrayList<Food> FriedFoods = new ArrayList<Food>();

    private ArrayList<Food> OtherFoods = new ArrayList<Food>();

    public Izakaya(){
        Food food;
        String[][] foodList = {{"雞肉", "とり"},{"烤雞肉串", "焼き鳥"},{"雞腿肉", "鶏もも"},{"雞翅膀", "手羽先"},{"雞胸肉", "ささみ"},
                {"雞軟骨", "なんこつ"},{"雞屁股", "ぼんじり"},{"雞胗", "すなぎも"},{"雞皮", "かわ"},{"雞肉丸", "つくね"},
                {"牛筋", "牛すじ"},{"豬五花", "豚バラ"},{"豬肝", "レバー"},{"豬腸", "シロ"},{"德式香腸", "ウインナー"}};
        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            MeatDishes.add(food);
        }

        foodList = new String[][]{{"炸雞塊", "唐揚げ"}, {"炸薯條", "ポテトフライ"}, {"南蠻炸雞", "チキン南蛮"}, {"可樂餅", "コロッケ"}};
        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            FriedFoods.add(food);
        }


        foodList = new String[][]{{"關東煮", "おでん"}, {"奶油馬鈴薯", "じゃがバター"}, {"內臟燉物", "もつ煮"}, {"茶泡飯", "お茶漬け"},
                {"飯糰", "おにぎり"}, {"炒麵", "焼きそば"}, {"炒飯", "チャーハン"}, {"韓式煎餅", "チヂミ"},
                {"毛豆", "枝豆"}, {"小黃瓜", "きゅうり"}, {"番茄冷盤", "冷やしトマト"}, {"起司", "チーズ"}, {"凱薩沙拉", "シーザーサラダ"},
                {"白蘿蔔沙拉", "大根サラダ"}, {"醃黃瓜", "キューリ漬"}, {"生高麗菜", "キャベツ"}, {"蘆筍", "アスパラ"}, {"杏鮑菇", "しいたけ"}, {"玉子燒蛋捲", "出し巻き"}};
        for (int i = 0; i < foodList.length; i++){
            food = new Food(foodList[i][0],foodList[i][1]);
            OtherFoods.add(food);
        }

    }

    public String[] getArrayName(){
        String[] arrayName = new String[]{"MeatDishes", "FriedFoods", "OtherFoods"};
        return arrayName;
    }

    public ArrayList<Food> getMeatDishes() {
        return MeatDishes;
    }

    public ArrayList<Food> getFriedFoods() {
        return FriedFoods;
    }

    public ArrayList<Food> getOtherFoods() {
        return OtherFoods;
    }
}
