package CommandPattern;

public class LightOnCommand implements Command{ // Concrete Command

    //reference to the light

    Light light;

    public LightOnCommand(Light light){

        this.light = light;

    }

    public void execute(){

        light.switchOn();
        System.out.println("Light On");

    }

}
