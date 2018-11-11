package com.pixelcatalyst.imgp.gui;

import com.pixelcatalyst.imgp.drawable.Drawable;
import com.pixelcatalyst.imgp.image.Image;
import com.pixelcatalyst.imgp.image.Sector;

import processing.core.PApplet;

public class ImageComparison extends Drawable {
    private Image left;
    private Image right;
    private int width;
    private float defBarPos;

    public ImageComparison(Image left, Image right, PApplet parent) {
        super(parent);
        this.left = left;
        this.right = right;
        if ((left.getWidth() != right.getWidth()) || (left.getHeight() != right.getHeight()))
            PApplet.println("Images in " + this.getClass().getSimpleName() + " should be the same size.");
        width = Math.max(left.getWidth(), right.getWidth());
        defBarPos = 0.5f;
    }

    public void setDefaultBarPos(float value) {
        defBarPos = Math.max(0.0f, Math.min(1.0f, value));
    }

    @Override
    protected void draw(PApplet parent) {
        int division = (int) (defBarPos * width);
        Sector sectorLeft = new Sector(0, 0, division, left.getHeight());
        Sector sectorRight = new Sector(division, 0, right.getWidth() - division, right.getHeight());
        left.setDrawSector(sectorLeft);
        right.setDrawSector(sectorRight);
        int xSaved = right.getX();
        right.setX(division);

        left.draw();
        right.draw();

        left.setDrawSector(null);
        right.setDrawSector(null);
        right.setX(xSaved);
    }
}
