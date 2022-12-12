package com.shaundsmith.aoc2022.day12;

import com.shaundsmith.aoc2022.Coordinate;
import com.shaundsmith.aoc2022.FileReader;
import com.shaundsmith.aoc2022.Grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class Day12 {

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day12/input.txt");

        Character[][] chars = new Character[lines.get(0).length()][lines.size()];
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(0, 0);
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char lineChar = line.charAt(x);
                if (lineChar == 'S') {
                    start = new Coordinate(x, y);
                    chars[x][y] = 'a';
                } else if (lineChar == 'E') {
                    end = new Coordinate(x, y);
                    chars[x][y] = 'z';
                } else {
                    chars[x][y] = line.charAt(x);
                }
            }
        }

        var grid = new Grid<>(chars);
        HillMap hillMap = new HillMap(grid, start, end);
        Path path = hillMap.route().get();
        System.out.println("Escape route: " + path.getScore());

        List<HillMap> hikingRoutes = new ArrayList<>();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Coordinate hikeStart = new Coordinate(x, y);
                if (grid.get(hikeStart) == 'a') {
                    hikingRoutes.add(new HillMap(grid, hikeStart, end));
                }
            }
        }
        hikingRoutes.sort(Comparator.comparing(HillMap::getEstimateForRoute));

        int bestScore = Integer.MAX_VALUE;
        System.out.println("Found %d hiking routes".formatted(hikingRoutes.size()));
        for (int i = 0; i < hikingRoutes.size(); i++) {
            Optional<Path> hikingRoute = hikingRoutes.get(i).routeIfCloser(bestScore);
            if (hikingRoute.isPresent() && hikingRoute.get().getScore() < bestScore) {
                bestScore = hikingRoute.get().getScore();
                System.out.println("Best score now %d".formatted(bestScore));
            }
            if (i % 3 == 0) {
                System.out.println("Tried %d routes".formatted(i));
            }
        }
        System.out.println("Best score for hike is " + bestScore);
    }

}
