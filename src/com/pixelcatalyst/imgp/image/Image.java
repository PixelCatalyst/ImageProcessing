package com.pixelcatalyst.imgp.image;

import com.pixelcatalyst.imgp.color.ColorFormat;
import com.pixelcatalyst.imgp.color.NormalizedColor;
import com.pixelcatalyst.imgp.drawable.Drawable;
import com.pixelcatalyst.imgp.color.ColorFormat.Channel;
import com.pixelcatalyst.imgp.image.EdgeHealer.HealMethod;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;

public class Image extends Drawable {
    private PImage img;
    private Sector drawSector;
    private ColorFormat format;
    private EdgeHealer edge;
    private boolean notLoaded = true;
    private boolean stale = false;

    public Image(String file, PApplet parent) {
        super(parent);
        img = parent.loadImage(file);
        init();
    }

    public Image(int width, int height, PApplet parent) {
        super(parent);
        img = parent.createImage(width, height, PConstants.ARGB);
        init();
    }

    private void init() {
        format = new ColorFormat(Channel.Red, Channel.Green, Channel.Blue);
        edge = new EdgeHealer(0, 0, getWidth() - 1, getHeight() - 1);
        edge.setHealMethod(HealMethod.Extend);
    }

    public int getWidth() {
        return img.width;
    }

    public int getHeight() {
        return img.height;
    }

    public void setDrawSector(Sector drawSector) {
        this.drawSector = drawSector;
    }

    public NormalizedColor getPixel(int x, int y) {
        loadPixels();
        NormalizedColor color;
        if (edge.outsideRect(x, y)) {
            x = edge.healX(x);
            y = edge.healY(y);
        }
        if (edge.insideRect(x, y)) {
            int value = img.pixels[y * img.width + x];
            color = format.normalizeInt(value);
        } else
            color = new NormalizedColor();
        return color;
    }

    public void setPixel(int x, int y, int color) {
        loadPixels();
        if (x >= 0 && y >= 0 && x < img.width && y < img.height) {
            img.pixels[y * img.width + x] = color;
            stale = true;
        }
    }

    private void loadPixels() {
        if (notLoaded) {
            img.loadPixels();
            notLoaded = false;
        }
    }

    public void update() {
        if (stale) {
            img.updatePixels();
            stale = false;
            notLoaded = true;
        }
    }

    @Override
    protected void draw(PApplet parent) {
        if (drawSector == null)
            parent.image(img, getX(), getY());
        else {
            parent.image(img, getX(), getY(), drawSector.getWidth(), drawSector.getHeight(), drawSector.getX(),
                    drawSector.getY(), drawSector.getWidth() + drawSector.getX(),
                    drawSector.getHeight() + drawSector.getY());
        }
    }
}
