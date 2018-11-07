package com.pixelcatalyst.imgp.image;

import com.pixelcatalyst.imgp.color.NormalizedColor;

public class Neighbor {
    private NormalizedColor color;
    private float weight;

    public NormalizedColor getColor() {
        return color;
    }

    public void setColor(NormalizedColor color) {
        this.color = color;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Neighbor() {
        color = new NormalizedColor();
        weight = 1.0f;
    }
}
