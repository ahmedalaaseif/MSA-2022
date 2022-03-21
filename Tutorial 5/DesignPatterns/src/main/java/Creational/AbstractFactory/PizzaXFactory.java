package Creational.AbstractFactory;

public class PizzaXFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        Dough d = new ThinCrust();
        return d;
    }

    public Sauce createSauce() {
        Sauce s = new ChilliTomato();
        return s;
    }

    public Cheese createCheese() {
        Cheese c = new MixCheese();
        return c;
    }

    public Mushrooms createMushrooms() {
        Mushrooms m = new FrozenMushrooms();
        return m;
    }
}
