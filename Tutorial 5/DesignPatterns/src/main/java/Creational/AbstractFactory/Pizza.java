package Creational.AbstractFactory;

public class Pizza {
    private PizzaIngredientFactory pizzaFactory;
    private Cheese cheese;
    private Mushrooms mushrooms;
    private Sauce sauce;
    private Dough dough;


    public PizzaIngredientFactory createPizza(PizzaIngredientFactory pizzaFactory){
        this.pizzaFactory = pizzaFactory;
        this.cheese = pizzaFactory.createCheese();
        this.mushrooms = pizzaFactory.createMushrooms();
        this.dough= pizzaFactory.createDough();
        this.sauce = pizzaFactory.createSauce();

        return this.pizzaFactory;

    }

    public PizzaIngredientFactory getPizzaFactory() {
        return pizzaFactory;
    }

    public Cheese getCheese() {
        return cheese;
    }

    public Mushrooms getMushrooms() {
        return mushrooms;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public Dough getDough() {
        return dough;
    }
}

