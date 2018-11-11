package com.pixelcatalyst.imgp.core;

import com.pixelcatalyst.imgp.filter.*;
import com.pixelcatalyst.imgp.gui.ImageComparison;
import com.pixelcatalyst.imgp.image.Image;

import processing.core.PApplet;

public class ImageProcessing extends PApplet {
    private Image img;
    private Image test;
    private ImageComparison slider;

    public static void main(String[] args) {
        PApplet.main("com.pixelcatalyst.imgp.core.ImageProcessing");
    }

    public void settings() {
        size(500, 500);
    }

    public void mouseDragged() {
        slider.setDefaultBarPos(mouseX / (float) width);
    }

    public void setup() {
        img = new Image("img.png", this);

        surface.setSize(img.getWidth(), img.getHeight());
        surface.setLocation((displayWidth - img.getWidth()) / 2, (displayHeight - img.getHeight()) / 2);

        Filter filter = new TestFilter(this);
        test = filter.applyRepeatedlyTo(img, 5);

        slider = new ImageComparison(img, test, this);
    }

    public void draw() {
        background(0);

        slider.draw();
    }
}
