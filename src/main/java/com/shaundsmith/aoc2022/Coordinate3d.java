package com.shaundsmith.aoc2022;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public record Coordinate3d(int x, int y, int z) {

    public static Coordinate3d fromString(String value) {
        String[] parts = value.split(",");

        return new Coordinate3d(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]));
    }

    public Coordinate3d translateX(int x) {
        return new Coordinate3d(this.x + x, y, z);
    }

    public Coordinate3d translateY(int y) {
        return new Coordinate3d(x, this.y + y, z);
    }

    public Coordinate3d translateXYZ(int x, int y, int z) {
        return new Coordinate3d(this.x + x, this.y + y, this.z + z);
    }

    public Collection<Coordinate3d> getAdjacentPositions() {

        return List.of(
                translateXYZ(1, 0, 0),
                translateXYZ(-1, 0, 0),
                translateXYZ(0, 1, 0),
                translateXYZ(0, -1, 0),
                translateXYZ(0, 0, 1),
                translateXYZ(0, 0, -1));
    }

}
