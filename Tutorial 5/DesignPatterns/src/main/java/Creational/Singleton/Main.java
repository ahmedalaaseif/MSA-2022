package Creational.Singleton;


public class Main {
    public static void main(String[] args) {
        LazyPizzaCake lpc = LazyPizzaCake.getInstance();
        EagerPizzaCake epc = EagerPizzaCake.getInstance();
    }
}
