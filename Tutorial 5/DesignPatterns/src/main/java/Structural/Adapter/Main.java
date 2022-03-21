package Structural.Adapter;

public class Main {
    public static void main(String[] args) {
        GermanPlugConnector plugConnector = new GermanPlugConnector() {
            @Override
            public void giveElectricity() {
                System.out.println("Running");
            }
        };

        UKSocket electricalSocket = new UKSocket();
        UKPlugConnector ukAdapter = new GermanToUKAdapter(plugConnector);
        electricalSocket.plugIn(ukAdapter);
    }
}
