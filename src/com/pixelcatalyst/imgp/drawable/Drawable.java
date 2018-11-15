package com.pixelcatalyst.imgp.drawable;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Drawable {
    private PApplet parent;
    private PVector position;

    public Drawable(PApplet parent) {
        this.parent = parent;
        position = new PVector(0.0f, 0.0f);
    }

    public int getX() {
        return (int) position.x;
    }

    public void setX(int x) {
        position.x = x;
    }

    public int getY() {
        return (int) position.y;
    }

    public void setY(int y) {
        position.y = y;
    }

    protected final PApplet getParent() {
        return parent;
    }

    public final void draw() {
        draw(parent);
    }

    protected abstract void draw(PApplet parent);
}
