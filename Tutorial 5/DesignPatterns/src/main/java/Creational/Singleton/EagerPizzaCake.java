package Creational.Singleton;

public class EagerPizzaCake {

    private static final EagerPizzaCake pizzaInstance = new EagerPizzaCake();


    private EagerPizzaCake() {

    }

    public static EagerPizzaCake getInstance(){
       return pizzaInstance;
    }

}
