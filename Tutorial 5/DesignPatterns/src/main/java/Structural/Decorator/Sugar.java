package Structural.Decorator;

public class Sugar extends CondimentDecorator {
    @Override
    public double getCost() {
        return 1 + this.cost;
    }

    public Sugar(Beverage b) {
        super(b);
        this.cost = b.getCost();
    }
}
