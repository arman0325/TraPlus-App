package com.arman.traplus.menu;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Menu {
    private Hashtable<String, Item> items = new Hashtable<String, Item>();
    private boolean viewAllItem = true;

    public boolean getViewAllProduct() {
        return viewAllItem;
    }

    // Toggle view all/view added item
    public void toggleView() {
        viewAllItem = !viewAllItem;
    }

    // Return all item in array format
    public Item[] getItems() {
        // View selected item
        if (!viewAllItem) {
            List<Item> resultItem = new ArrayList<Item>();
            Set<String> keys = items.keySet();
            for(String key: keys){
                Item product = items.get(key);
                if (product.getCount()>0)
                    resultItem.add(product);
            }
            return resultItem.toArray(new Item[resultItem.size()]);
        }

        // View all items
        return items.values().toArray(new Item[items.size()]);
    }

    // Add a item to cart for user selection
    public void addItem(String id, String zhName, String jaName, int count) {
        Item item = new Item(id, zhName, jaName, count);
        items.put(id, item);
    }

    // Add a specific item to cart
    public void addToCart(String pid, int count) {
        Item item = items.get(pid);
        if (item != null) {
            item.setCount(count);
        }
    }

    // Delete all selected item from cart
    public void removeAllItemFromCart() {
        Set<String> keys = items.keySet();
        for(String key: keys){
            Item item = items.get(key);
            item.resetCount();
        }
    }

}