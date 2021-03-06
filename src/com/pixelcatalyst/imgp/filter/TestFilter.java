package com.pixelcatalyst.imgp.filter;

import com.pixelcatalyst.imgp.color.ColorFormat;
import com.pixelcatalyst.imgp.color.NormalizedColor;
import com.pixelcatalyst.imgp.image.Neighbor;
import com.pixelcatalyst.imgp.image.NeighborStencil;

import processing.core.PApplet;

public class TestFilter extends Filter {

    public TestFilter(PApplet parent) {
        super(parent);
    }

    @Override
    protected NeighborStencil createNeighborStencil() {
        NeighborStencil neighbors = new NeighborStencil();
        neighbors.addPattern(-1, -1);
        neighbors.addPattern(0, -1);
        neighbors.addPattern(1, -1);
        neighbors.addPattern(-1, 0);
        neighbors.addPattern(1, 0);
        neighbors.addPattern(-1, 1);
        neighbors.addPattern(0, 1);
        neighbors.addPattern(1, 1);
        return neighbors;
    }

    @Override
    protected int calculatePixel(NormalizedColor color, NeighborStencil neighbors) {
        ColorFormat fmt = getDefFormat();

        float dominant = 0.0f;
        for (int i = 0; i < neighbors.getSize(); ++i) {
            Neighbor n = neighbors.getNeighbor(i);
            NormalizedColor nColor = n.getColor();
            dominant += nColor.getMax();
        }
        dominant /= (float) (neighbors.getSize());
        float localDominant = color.getMax();
        NormalizedColor modified;
        if (localDominant == color.red())
            modified = color.copy().withRed(dominant).get();
        else if (localDominant == color.green())
            modified = color.copy().withGreen(dominant).get();
        else
            modified = color.copy().withBlue(dominant).get();

        return fmt.expandToInt(modified);
    }
}
