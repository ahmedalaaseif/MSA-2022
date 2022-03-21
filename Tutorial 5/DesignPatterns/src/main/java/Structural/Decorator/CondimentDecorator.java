package Structural.Decorator;

public abstract class CondimentDecorator extends Beverage{
    protected Beverage b;
    protected double cost;
    public CondimentDecorator(Beverage b){
        this.b = b;
    }
}
