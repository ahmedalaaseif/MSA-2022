package Structural.Decorator;

public class Main {
    public static void main(String[] args) {
        Beverage syrupLatte = new Syrup(new Latte());
        Beverage milkTea = new Milk(new Tea());
        System.out.println("Syrup Latte: "+ syrupLatte.getCost());
        System.out.println("Milk Tea: "+ milkTea.getCost());

    }
}
