package Structural.Adapter;

public class GermanSocket {
    public void plugIn(GermanPlugConnector plug) {
        plug.giveElectricity();
        System.out.println("German Socket Connected");
    }
}
