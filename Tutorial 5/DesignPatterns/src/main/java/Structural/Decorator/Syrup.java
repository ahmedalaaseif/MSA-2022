package Structural.Decorator;

public class Syrup extends CondimentDecorator{
    @Override
    public double getCost() {
        return 2 + this.cost;
    }

    public Syrup(Beverage b) {
        super(b);
        this.cost = b.getCost();
    }
}
