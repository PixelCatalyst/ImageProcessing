package com.pixelcatalyst.imgp.filter;

import com.pixelcatalyst.imgp.color.ColorFormat;
import com.pixelcatalyst.imgp.color.NormalizedColor;
import com.pixelcatalyst.imgp.color.ColorFormat.Channel;
import com.pixelcatalyst.imgp.image.Image;
import com.pixelcatalyst.imgp.image.NeighborStencil;

import processing.core.PApplet;

public abstract class Filter {
    public final PApplet parent;
    private final ColorFormat defFormat;

    public Filter(PApplet parent) {
        this.parent = parent;
        defFormat = new ColorFormat(Channel.Red, Channel.Green, Channel.Blue);
    }

    public Filter(PApplet parent, ColorFormat defFormat) {
        this.parent = parent;
        this.defFormat = defFormat;
    }

    protected final ColorFormat getDefFormat() {
        return defFormat;
    }

    public final Image applyRepeatedlyTo(Image input, int times) {
        Image output = null;
        for (int i = 0; i < times; ++i) {
            output = applyTo(input);
            input = output;
        }
        return output;
    }

    public final Image applyTo(Image input) {
        Image output = new Image(input.getWidth(), input.getHeight(), parent);
        NeighborStencil neighbors = createNeighborStencil();
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                NormalizedColor color = input.getPixel(x, y);
                if (neighbors != null)
                    neighbors.sampleFrom(input, x, y);
                int pixel = calculatePixel(color, neighbors);
                output.setPixel(x, y, pixel);
            }
        }
        output.update();
        return output;
    }

    protected NeighborStencil createNeighborStencil() {
        return null;
    }

    protected int calculatePixel(NormalizedColor color, NeighborStencil neighbors) {
        return defFormat.expandToInt(color);
    }
}
