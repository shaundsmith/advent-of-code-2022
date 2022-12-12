package com.shaundsmith.aoc2022;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Grid<T> {

    private T[][] contents;

    public T get(Coordinate position) {

        return contents[position.x()][position.y()];
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

}
