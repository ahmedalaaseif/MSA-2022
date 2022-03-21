package Creational.Singleton;

public class LazyPizzaCake {

    private static LazyPizzaCake pizzaInstance = null;


    private LazyPizzaCake() {

    }

    public static LazyPizzaCake getInstance(){
        if(pizzaInstance == null ){
            pizzaInstance = new LazyPizzaCake();
            return pizzaInstance;
        } else {
            return pizzaInstance;
        }
    }

}
