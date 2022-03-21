package Creational.Factory;

public class PizzaFactory {
    public Pizza createPizza(String type){
        if(type.toLowerCase() == "x"){
            return new PizzaX().createPizza();
        } else {
            return new PizzaY().createPizza();
        }
    }
}
