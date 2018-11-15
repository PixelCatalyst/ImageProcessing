package com.pixelcatalyst.imgp.core;

import com.pixelcatalyst.imgp.filter.*;
import com.pixelcatalyst.imgp.gui.ImageComparison;
import com.pixelcatalyst.imgp.gui.SliderBar;
import com.pixelcatalyst.imgp.image.Image;

import processing.core.PApplet;

public class ImageProcessing extends PApplet {
    private Image img;
    private Image test;
    private ImageComparison comp;
    private SliderBar slider;
    private int lastX;

    public static void main(String[] args) {
        PApplet.main("com.pixelcatalyst.imgp.core.ImageProcessing");
    }

    public void settings() {
        size(500, 500);
    }

    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (slider.isInsideHandle(mouseX, mouseY))
                slider.enableDragging();
            else
                slider.disableDragging();
            lastX = mouseX;
        }
    }

    public void mouseDragged() {
        if (mouseButton == LEFT) {
            int deltaX = mouseX - lastX;
            lastX = mouseX;
            slider.drag(deltaX);
        }
    }

    public void mouseReleased() {
        if (mouseButton == LEFT)
            slider.disableDragging();
    }

    public void setup() {
        img = new Image("img.png", this);

        surface.setSize(img.getWidth(), img.getHeight());
        surface.setLocation((displayWidth - img.getWidth()) / 2, (displayHeight - img.getHeight()) / 2);

        Filter filter = new TestFilter(this);
        test = filter.applyRepeatedlyTo(img, 5);

        comp = new ImageComparison(img, test, this);
        slider = new SliderBar(0, width, height, this);
        slider.setX(width / 2);
        comp.attachSliderBar(slider);
    }

    public void draw() {
        checkFocus();
        background(0);

        comp.draw();
        slider.draw();
    }

    private void checkFocus() {
        if (focused == false)
            lostFocus();
    }

    private void lostFocus() {
        slider.disableDragging();
    }
}
