package Structural.Adapter;

public class GermanToUKAdapter implements UKPlugConnector {
    private GermanPlugConnector plug;

    public GermanToUKAdapter(GermanPlugConnector plug) {
        this.plug = plug;
    }

    public void provideElectricity() {
        plug.giveElectricity();
        System.out.println("UK Adapter Connected");
    }
}
