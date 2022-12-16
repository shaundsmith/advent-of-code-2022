package com.shaundsmith.aoc2022;

public record Coordinate(int x, int y) {

    public static Coordinate fromString(String value) {
        String[] parts = value.split(",");

        return new Coordinate(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]));
    }

    public Coordinate translateX(int x) {
        return new Coordinate(this.x + x, y);
    }

    public Coordinate translateY(int y) {
        return new Coordinate(x, this.y + y);
    }

    public Coordinate translateXY(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public int manhattanDistanceTo(Coordinate coordinate) {
        return Math.abs(coordinate.x - this.x) + Math.abs(coordinate.y - this.y);
    }

}
