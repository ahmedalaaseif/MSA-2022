package Creational.Factory;

public class PizzaX extends PizzaFactory {

    public Pizza createPizza() {
        return new Pizza("X");
    }
}
