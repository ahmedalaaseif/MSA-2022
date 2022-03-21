package Creational.Prototype;

public class Main {
    public static void main(String[] args) {
        ItemRegistry registry = new ItemRegistry();

        Item myBook = registry.createBasicItem("Book");

        myBook.setTitle("Custom Title");
    }
}
