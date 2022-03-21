package Creational.AbstractFactory;

public class PizzaYFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        Dough d = new Pan();
        return d;
    }

    public Sauce createSauce() {
        Sauce s = new PlainTomato();
        return s;
    }

    public Cheese createCheese() {
        Cheese c = new Mozzarella();
        return c;
    }

    public Mushrooms createMushrooms() {
        Mushrooms m = new FreshMushrooms();
        return m;
    }
}
