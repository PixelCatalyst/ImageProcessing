package com.pixelcatalyst.imgp.image;

public class EdgeHealer {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Healer healer;

    public EdgeHealer(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        healer = null;
    }

    public enum HealMethod {
        Extend, Mirror, Wrap
    }

    public void setHealMethod(HealMethod method) {
        if (method == HealMethod.Extend)
            healer = new ExtendHealer();
        else if (method == HealMethod.Mirror)
            healer = new MirrorHealer();
        else if (method == HealMethod.Wrap)
            healer = new WrapHealer();
    }

    public boolean outsideRect(int x, int y) {
        return !insideRect(x, y);
    }

    public boolean insideRect(int x, int y) {
        return (x >= left) && (y >= top) && (x <= right) && (y <= bottom);
    }

    public int healX(int x) {
        return healer.heal(x, left, right);
    }

    public int healY(int y) {
        return healer.heal(y, top, bottom);
    }

    private interface Healer {

        public int heal(int value, int min, int max);
    }

    private class ExtendHealer implements Healer {

        @Override
        public int heal(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }
    }

    private class MirrorHealer implements Healer {

        @Override
        public int heal(int value, int min, int max) {
            if (value > max)
                return max - (value - max);
            else if (value < min)
                return min + (min - value);
            else
                return value;
        }
    }

    private class WrapHealer implements Healer {

        @Override
        public int heal(int value, int min, int max) {
            int offset = 0;
            if (value < 0)
                offset = -value;
            value += offset;
            min += offset;
            max += offset;

            if (value < min)
                value = max - (min - value);
            else if (value > max)
                value = min + (value - max);
            return value - offset;
        }
    }
}
