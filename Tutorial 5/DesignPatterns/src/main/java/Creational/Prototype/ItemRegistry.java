package Creational.Prototype;


import java.util.HashMap;


public class ItemRegistry {
    private HashMap<String, Item> map = new HashMap();

    public ItemRegistry(){
        loadCache();
    }

    public Item createBasicItem(String type) {
        return map.get(type).clone();
    }

    private void loadCache() {
        Book book = new Book();
        book.setTitle("Design Patterns");
        book.setPrice(20.00);
        map.put("Book", book);
        CD cd = new CD();
        cd.setTitle("Various");
        cd.setPrice(10.00);
        map.put("CD", cd);
    }
}

