package com.pixelcatalyst.imgp.gui;

import com.pixelcatalyst.imgp.color.ColorFormat;
import com.pixelcatalyst.imgp.color.NormalizedColor;
import com.pixelcatalyst.imgp.color.ColorFormat.Channel;
import com.pixelcatalyst.imgp.drawable.Drawable;

import processing.core.PApplet;

public class SliderBar extends Drawable {
    private int minPos;
    private int maxPos;
    private int height;
    private int handleRadius;
    private boolean draggingEnabled;
    private NormalizedColor borderColor;
    private NormalizedColor idleColor;
    private NormalizedColor dragColor;
    private NormalizedColor currentColor;
    private ColorFormat colorFormat;

    public SliderBar(int minPos, int maxPos, int height, PApplet parent) {
        super(parent);
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.height = height;
        handleRadius = height / 16;
        draggingEnabled = false;
        borderColor = new NormalizedColor(0.51f);
        idleColor = new NormalizedColor(0.75f);
        dragColor = new NormalizedColor(0.9f);
        currentColor = idleColor;
        colorFormat = new ColorFormat(Channel.Red, Channel.Green, Channel.Blue);
    }

    public void setHandleRadius(int handleRadius) {
        this.handleRadius = handleRadius;
    }

    public void setIdleColor(NormalizedColor color) {
        this.idleColor = color;
    }

    public void setDragColor(NormalizedColor color) {
        this.dragColor = color;
    }

    public boolean isInsideHandle(int x, int y) {
        float radius = handleRadius / 2;
        radius *= radius;
        float xOrigin = x - getX();
        xOrigin *= xOrigin;
        float yOrigin = y - (getY() + height) / 2;
        yOrigin *= yOrigin;
        return (xOrigin + yOrigin) <= radius;
    }

    public void enableDragging() {
        draggingEnabled = true;
        currentColor = dragColor;
    }

    public void disableDragging() {
        draggingEnabled = false;
        currentColor = idleColor;
    }

    public void drag(int deltaX) {
        if (draggingEnabled)
            setX(getX() + deltaX);
    }

    @Override
    public void setX(int x) {
        x = Math.max(minPos, Math.min(maxPos, x));
        super.setX(x);
    }

    public float getPosRatio() {
        return (getX() - minPos) / (float) (maxPos - minPos);
    }

    @Override
    protected void draw(PApplet parent) {
        parent.stroke(colorFormat.expandToInt(borderColor));
        parent.line(getX(), getY(), getX(), getY() + height);
        drawHandle(parent);
    }

    private void drawHandle(PApplet parent) {
        parent.fill(colorFormat.expandToInt(currentColor));
        parent.ellipse(getX(), (getY() + height) / 2, handleRadius, handleRadius);
        final int lineOffset = handleRadius / 12;
        final int halfBase = handleRadius / 7;
        final int width = handleRadius / 6;
        drawArrow(lineOffset, halfBase, width, parent);
        drawArrow(-lineOffset, -halfBase, -width, parent);
    }

    private void drawArrow(int lineOffset, int halfBase, int width, PApplet parent) {
        int x1 = getX() + lineOffset;
        int y1 = getY() + (height / 2) - halfBase;
        int x2 = x1 + width;
        int y2 = y1 + halfBase;
        int y3 = y2 + halfBase;
        parent.fill(colorFormat.expandToInt(borderColor));
        parent.triangle(x1, y1, x2, y2, x1, y3);
    }
}
