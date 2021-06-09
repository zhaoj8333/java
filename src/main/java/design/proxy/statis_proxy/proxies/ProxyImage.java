package design.proxy.statis_proxy.proxies;

import design.proxy.statis_proxy.image.Image;
import design.proxy.statis_proxy.image.RealImage;

public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
