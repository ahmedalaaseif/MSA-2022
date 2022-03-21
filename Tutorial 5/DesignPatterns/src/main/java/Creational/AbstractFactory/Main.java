package Creational.AbstractFactory;

public class Main {
    public static void main(String[] args) {

        Pizza pizza = new Pizza();
        pizza.createPizza(new PizzaXFactory());

        System.out.println(pizza.getCheese());

    }
}
