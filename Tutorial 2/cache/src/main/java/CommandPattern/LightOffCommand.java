package CommandPattern;

public class LightOffCommand implements Command{ // Concrete Command

    //reference to the light

    Light light;

    public LightOffCommand(Light light){

        this.light = light;

    }

    public void execute(){

        light.switchOff();
        System.out.println("Light Off");
    }

}
