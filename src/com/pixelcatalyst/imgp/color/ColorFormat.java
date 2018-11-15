package com.pixelcatalyst.imgp.color;

public class ColorFormat {
    public enum Channel {
        Red, Green, Blue, White, Black;
    }

    private static final int CHANNEL_COUNT = 3;
    private static final int CHANNEL_SIZE = 255;
    private static final int CHANNEL_BITS = 8;

    private Channel[] channels;

    public ColorFormat(ColorFormat other) {
        channels = new Channel[CHANNEL_COUNT];
        channels[0] = other.channels[0];
        channels[1] = other.channels[1];
        channels[2] = other.channels[2];
    }

    public ColorFormat(Channel first, Channel second, Channel third) {
        channels = new Channel[CHANNEL_COUNT];
        channels[0] = first;
        channels[1] = second;
        channels[2] = third;
    }

    public NormalizedColor normalizeInt(int value) {
        float[] chunks = { 0, 0, 0 }; // {R, G, B}
        int offset = CHANNEL_BITS * 2;
        for (int i = 0; i < CHANNEL_COUNT; ++i) {
            chunks[i] = (value >> offset) & CHANNEL_SIZE;
            chunks[i] /= (float) (CHANNEL_SIZE);
            offset -= CHANNEL_BITS;
        }
        return new NormalizedColor(chunks[0], chunks[1], chunks[2]);
    }

    public int expandToInt(NormalizedColor normal) {
        int[] chunks = { 0, 0, 0 }; // {R, G, B}
        for (int i = 0; i < CHANNEL_COUNT; ++i) {
            Channel ch = channels[i];
            if (ch == Channel.Red)
                chunks[i] = (int) (normal.red() * CHANNEL_SIZE);
            else if (ch == Channel.Green)
                chunks[i] = (int) (normal.green() * CHANNEL_SIZE);
            else if (ch == Channel.Blue)
                chunks[i] = (int) (normal.blue() * CHANNEL_SIZE);
            else if (ch == Channel.White)
                chunks[i] = CHANNEL_SIZE;
            else if (ch == Channel.Black)
                chunks[i] = 0;
        }
        return assembleChunks(chunks);
    }

    private int assembleChunks(int[] chunks) {
        final int alpha = CHANNEL_SIZE << (CHANNEL_BITS * 3);
        return alpha + (chunks[0] << (CHANNEL_BITS * 2)) + (chunks[1] << CHANNEL_BITS) + chunks[2];
    }
}
