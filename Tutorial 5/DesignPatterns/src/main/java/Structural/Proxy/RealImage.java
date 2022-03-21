package Structural.Proxy;

import java.net.URL;

public class RealImage implements Image {
    public RealImage(URL url) {
        loadImage(url);
    }

    public void displayImage() {
        //display the image a method that only the real image has
        renderImage();
    }

    private void loadImage(URL url) {
        //do resource intensive operation to load image
    }

    private void renderImage(){

    }
}
