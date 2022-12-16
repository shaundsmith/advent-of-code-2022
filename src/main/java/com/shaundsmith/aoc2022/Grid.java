package com.shaundsmith.aoc2022;

import lombok.AllArgsConstructor;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@AllArgsConstructor
public class Grid<T> {

    private T[][] contents;

    public T get(Coordinate position) {

        return contents[position.x()][position.y()];
    }

    public void set(Coordinate position, T elem) {

        contents[position.x()][position.y()] = elem;
    }

    public boolean hasPosition(Coordinate position) {

        return position.x() >= 0 && position.y() >= 0
                && position.x() < contents.length && position.y() < contents[0].length;
    }


    public int getWidth() {

        return contents.length;
    }

    public int getHeight() {

        return contents[0].length;
    }

    public Stream<T> streamRow(int y) {

        return IntStream.range(0, getWidth())
                .mapToObj(x -> contents[x][y]);
    }

    @Override
    public String toString() {

        var sb = new StringBuilder();
        for (int y = 0; y < contents[0].length; y++) {
            for (int x = 0; x < contents.length; x++) {
                sb.append(contents[x][y]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
