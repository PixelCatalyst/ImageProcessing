package com.pixelcatalyst.imgp.filters;

import com.pixelcatalyst.imgp.Neighbor;
import com.pixelcatalyst.imgp.NeighborStencil;

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
            modified = color.clone().withRed(dominant).get();
        else if (localDominant == color.green())
            modified = color.clone().withGreen(dominant).get();
        else
            modified = color.clone().withBlue(dominant).get();

        return fmt.expandToInt(modified);
    }
}
