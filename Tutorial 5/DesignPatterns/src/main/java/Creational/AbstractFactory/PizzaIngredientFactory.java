package Creational.AbstractFactory;

public interface PizzaIngredientFactory {
    public Dough createDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public Mushrooms createMushrooms();
}
