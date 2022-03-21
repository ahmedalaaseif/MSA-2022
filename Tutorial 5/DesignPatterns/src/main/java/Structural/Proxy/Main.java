package Structural.Proxy;

import java.net.URL;

public class Main {
    public static void main(String[] args) {

        try {
            ProxyImage p = new ProxyImage(new URL("ds"));
            p.displayImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
