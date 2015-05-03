package com.someazndude.scripts.api.methods.util;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class Image {
    public static java.awt.Image getImage(final String s) {
        try {
            return ImageIO.read(new URL(s));
        } catch (IOException e) {
        }
        return null;
    }
}
