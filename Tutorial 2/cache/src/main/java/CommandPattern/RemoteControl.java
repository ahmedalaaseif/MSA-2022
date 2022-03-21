package CommandPattern;

public class RemoteControl{ // Invoker

    private Command command;

    public void setCommand(Command command){

        this.command = command;

    }

    public void pressButton(){

        command.execute();

    }

}
