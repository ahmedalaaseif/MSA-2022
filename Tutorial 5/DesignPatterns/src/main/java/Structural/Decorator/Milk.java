package Structural.Decorator;

public class Milk extends CondimentDecorator {
    @Override
    public double getCost() {
        return this.cost + 2;

    }


    public Milk(Beverage b){
        super(b);
        this.cost = b.getCost();
    }
}
