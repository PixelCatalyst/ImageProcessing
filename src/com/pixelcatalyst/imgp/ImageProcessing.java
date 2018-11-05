package com.pixelcatalyst.imgp;

import com.pixelcatalyst.imgp.filters.*;

import processing.core.PApplet;

public class ImageProcessing extends PApplet {
    private Image img;
    private Image test;

    public static void main(String[] args) {
        PApplet.main("com.pixelcatalyst.imgp.ImageProcessing");
    }

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        img = new Image("img.png", this);

        surface.setSize(img.getWidth(), img.getHeight());
        surface.setLocation((displayWidth - img.getWidth()) / 2, (displayHeight - img.getHeight()) / 2);

        Filter filter = new TestFilter(this);
        test = filter.applyRepeatedlyTo(img, 5);
    }

    public void draw() {
        background(0);

        if (frameCount % 70 < 22)
            img.draw();
        else
            test.draw();
    }
}
