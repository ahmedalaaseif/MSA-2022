package Creational.Factory;

public class Main {
    public static void main(String[] args) {
        PizzaFactory pf = new PizzaFactory();
        Pizza p = pf.createPizza("x");
        System.out.println(p.name);
    }
}
