package Structural.Facade;

public class TheaterControl {
    private boolean running;

    public void on(){
        this.running = true;
        System.out.println(this.getClass().getSimpleName() + " is now on");
    }

    public void off(){
        this.running = false;
        System.out.println(this.getClass().getSimpleName() + " is now off");
    }

}
