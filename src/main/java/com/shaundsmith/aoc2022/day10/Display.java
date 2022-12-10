package com.shaundsmith.aoc2022.day10;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
class Display {

    private final ClockCircuit clockCircuit;
    private final List<Pixel> pixels;
    private final int width;

    Display(ClockCircuit clockCircuit, int width, int height) {
        this.clockCircuit = clockCircuit;
        this.width = width;
        pixels = IntStream.range(0, width * height)
                .mapToObj(i -> new Pixel())
                .toList();
    }

    void render() {
        while(clockCircuit.canTick()) {
            renderPixel();
            clockCircuit.tick();
        }
    }

    private void renderPixel() {
        int position = clockCircuit.getCycle() - 1;
        if (isSpriteVisible(position)) {
            pixels.get(position).light();
        }
    }

    private boolean isSpriteVisible(int position) {
        int spriteCentre = clockCircuit.getRegister();
        int horizontalPosition = position % width;
        return spriteCentre - 1 <= horizontalPosition && spriteCentre + 1 >=  horizontalPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.size(); i++) {
            if (i % width == 0) {
                sb.append("\n");
            }
            sb.append(pixels.get(i).getDisplayValue());
        }

        return sb.toString();
    }

}
