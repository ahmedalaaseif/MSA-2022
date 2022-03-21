package Structural.Proxy;

import java.net.URL;

public class ProxyImage implements Image {
    private URL url;

    public ProxyImage(URL url) {
        //this method delegates to the real image
        this.url = url;
    }

    public void displayImage() {
        RealImage real = new RealImage(url);
        real.displayImage();
    }
}
