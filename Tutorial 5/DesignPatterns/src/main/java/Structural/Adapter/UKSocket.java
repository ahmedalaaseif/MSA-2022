package Structural.Adapter;

public class UKSocket {
    public void plugIn(UKPlugConnector plug) {
        plug.provideElectricity();
        System.out.println("UK socket connected");
    }
}
