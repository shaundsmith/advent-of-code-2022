package com.shaundsmith.aoc2022.day14;

import com.shaundsmith.aoc2022.Coordinate;
import com.shaundsmith.aoc2022.FileReader;
import com.shaundsmith.aoc2022.Grid;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day14 {

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day14/input.txt");

        Set<Coordinate> rocks = new HashSet<>();
        for (String line : lines) {
            List<Coordinate> coordinates = Arrays.stream(line.split(" -> "))
                    .map(Coordinate::fromString)
                    .toList();
            rocks.addAll(interpolate(coordinates));
        }

        Coordinate bottomMostCoordinate = rocks.stream()
                .max(Comparator.comparing(Coordinate::y))
                .get();
        rocks.addAll(interpolatePair(new Coordinate(0, bottomMostCoordinate.y() + 2),
                new Coordinate(1000, bottomMostCoordinate.y() + 2)));

        Coordinate rightMostCoordinate = rocks.stream()
                .max(Comparator.comparing(Coordinate::x))
                .get();

        var grid = buildGrid(rocks, rightMostCoordinate.x() + 1, bottomMostCoordinate.y() + 3);

        Cave cave = new Cave(grid, new Coordinate(500, 0));
        cave.fillCave();
        System.out.println(grid);

    }

    private static Set<Coordinate> interpolate(List<Coordinate> coordinates) {

        Set<Coordinate> formation  = new HashSet<>();

        for (int i = 1; i < coordinates.size(); i++) {
            formation.addAll(interpolatePair(coordinates.get(i - 1), coordinates.get(i)));
        }

        return formation;
    }

    private static Set<Coordinate> interpolatePair(Coordinate start, Coordinate finish) {
        Set<Coordinate> formation  = new HashSet<>();
        int distanceX = finish.x() - start.x();
        int distanceY = finish.y() - start.y();
        int stepX = (int) Math.signum(distanceX);
        int stepY = (int) Math.signum(distanceY);

        int interpolationDistance = stepX == 0 ? distanceY : distanceX;

        formation.add(start);
        for (int i = 0; i < Math.abs(interpolationDistance); i++) {
            formation.add(start.translateXY(stepX * i, stepY * i));
        }
        formation.add(finish);

        return formation;
    }

    private static Grid<GridKey> buildGrid(Set<Coordinate> rocks, int width, int height) {

        Coordinate sandStart = new Coordinate(500, 0);
        GridKey[][] grid = new GridKey[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Coordinate currentPosition = new Coordinate(x, y);
                if (rocks.contains(currentPosition)) {
                    grid[x][y] = GridKey.ROCK;
                } else if (currentPosition.equals(sandStart)) {
                    grid[x][y] = GridKey.SAND_START;
                } else {
                    grid[x][y] = GridKey.AIR;
                }
            }
        }

        return new Grid<>(grid);
    }


}
