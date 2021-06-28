package com.arman.traplus.menu;

public class Item {
        private String id;
        private String zhName;
        private String jaName;
        private int count;

        public Item(String id, String zhName, String jaName, int count){
            this.id = id;
            this.zhName = zhName;
            this.jaName = jaName;
            this.count = count;
        }

        public void setCount(int count) {
            this.count += count;
            if (this.count <= 0){
                this.count =0;
            }
        }

        public void resetCount() {
            count = 0;
        }

        public String getId() {
            return id;
        }

        public String getZhName() {
            return zhName;
        }

        public String getJaName() {
            return jaName;
        }

        public int getCount() {
            return count;
        }
}