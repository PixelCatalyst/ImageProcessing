package com.pixelcatalyst.imgp.image;

import java.util.ArrayList;

import com.pixelcatalyst.imgp.color.NormalizedColor;

public class NeighborStencil {
    private class Offset {
        private int x, y;

        public Offset(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int calcX(int xSource) {
            return xSource + x;
        }

        public int calcY(int ySource) {
            return ySource + y;
        }
    }

    private ArrayList<Offset> offsets;
    private ArrayList<Float> weights;
    private ArrayList<Neighbor> neighbors;

    public NeighborStencil() {
        offsets = new ArrayList<Offset>();
        weights = new ArrayList<Float>();
        neighbors = new ArrayList<Neighbor>();
    }

    public void addPattern(int xOffset, int yOffset) {
        addPattern(xOffset, yOffset, 1.0f);
    }

    public void addPattern(int xOffset, int yOffset, float weight) {
        offsets.add(new Offset(xOffset, yOffset));
        weights.add(weight);
        neighbors.add(new Neighbor());
    }

    public void sampleFrom(Image image, int xSource, int ySource) {
        for (int i = 0; i < offsets.size(); ++i) {
            Offset off = offsets.get(i);
            int xNeighbor = off.calcX(xSource);
            int yNeighbor = off.calcY(ySource);
            Neighbor neigh = neighbors.get(i);
            NormalizedColor color = image.getPixel(xNeighbor, yNeighbor);
            neigh.setColor(color);
            neigh.setWeight(weights.get(i));
        }
    }

    public int getSize() {
        return neighbors.size();
    }

    public Neighbor getNeighbor(int index) {
        if ((index >= 0) && (index < neighbors.size()))
            return neighbors.get(index);
        return null;
    }
}
