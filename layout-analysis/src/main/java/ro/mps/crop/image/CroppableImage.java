package ro.mps.crop.image;

import ro.mps.properties.Properties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CroppableImage {

    protected BufferedImage img;
    protected String imageName;

    public CroppableImage(String path) throws IOException {
        img = ImageIO.read(new File(path));
        imageName = new File(path).getName();
    }

    public Image asImage() {
        return img;
    }

    public BufferedImage crop(int x, int y, int height, int width) {
        //Outside of image
        if (x > img.getWidth() || y > img.getHeight())
            return null;

        if (height + y > img.getHeight())
            height = img.getHeight() - y;

        if (width + x > img.getWidth())
            width = img.getWidth() - x;

        return img.getSubimage(x, y, width, height);
    }

    public boolean writeCroppedImage(String path, int x, int y, int height, int width) {
        try {
            ImageIO.write(crop(x, y, height, width), Properties.OUTPUT_FORMAT.getValue(), new File(path));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
