package com.pixelcatalyst.imgp.color;

public class NormalizedColor {
    private float R;
    private float G;
    private float B;

    public static class Cloner {
        private NormalizedColor copy;

        private Cloner(NormalizedColor base) {
            this.copy = new NormalizedColor(base.R, base.G, base.B);
        }

        public Cloner withRed(float red) {
            copy.R = red;
            return this;
        }

        public Cloner withGreen(float green) {
            copy.G = green;
            return this;
        }

        public Cloner withBlue(float blue) {
            copy.B = blue;
            return this;
        }

        public NormalizedColor get() {
            return copy;
        }
    }

    public Cloner clone() {
        return new Cloner(this);
    }

    public NormalizedColor() {
        R = G = B = -1.0f;
    }

    public NormalizedColor(float grey) {
        R = G = B = grey;
    }

    public NormalizedColor(float R, float G, float B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public float red() {
        return R;
    }

    public float green() {
        return G;
    }

    public float blue() {
        return B;
    }

    public void clamp() {
        R = clampChannel(R);
        G = clampChannel(G);
        B = clampChannel(B);
    }

    private float clampChannel(float value) {
        return Math.max(0.0f, Math.min(1.0f, value));
    }

    public void renormalize() {
        float maxChannel = getMax();
        if (Float.compare(maxChannel, 1.0f) > 0)
            div(maxChannel);
    }

    public float getMax() {
        return Math.max(R, Math.max(G, B));
    }

    public float getMin() {
        return Math.min(R, Math.min(G, B));
    }

    public void abs() {
        R = Math.abs(R);
        G = Math.abs(G);
        B = Math.abs(B);
    }

    public NormalizedColor add(NormalizedColor other) {
        this.R += other.R;
        this.G += other.G;
        this.B += other.B;
        return this;
    }

    public NormalizedColor mult(NormalizedColor other) {
        this.R *= other.R;
        this.G *= other.G;
        this.B *= other.B;
        return this;
    }

    public NormalizedColor mult(float number) {
        R *= number;
        G *= number;
        B *= number;
        return this;
    }

    public NormalizedColor div(float number) {
        R /= number;
        G /= number;
        B /= number;
        return this;
    }
}
