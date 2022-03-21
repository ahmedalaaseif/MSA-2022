package Structural.Bridge;

public abstract class RemoteControl {
    private TV tv;

    public void on() {
        tv.on();
    }

    public void off() {
        tv.off();
    }

    public void setChannel(int channel) {
        tv.tuneChannel(channel); // This method is implemented in the terms of the `Implenentation` Hierarchy
    }

    public void volumeUp(){}

}
