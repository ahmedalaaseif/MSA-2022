package Creational.Factory;

public class PizzaY extends PizzaFactory {
    public Pizza createPizza() {
        return new Pizza("Y");
    }
}
