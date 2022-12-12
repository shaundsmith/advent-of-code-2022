package com.shaundsmith.aoc2022.day12;

import com.shaundsmith.aoc2022.Coordinate;
import com.shaundsmith.aoc2022.Grid;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class HillMap {

    private final Grid<Character> grid;
    private final Set<Path> openPaths;
    private final Set<Path> closedPaths;
    private final Coordinate start;
    private final Coordinate destination;

    HillMap(Grid<Character> grid, Coordinate start, Coordinate destination) {

        this.grid = grid;
        this.openPaths = new HashSet<>();
        this.destination = destination;
        this.start = start;
        this.openPaths.add(new Path(start, computeEstimateScore(start)));
        this.closedPaths = new HashSet<>();
    }

    double getEstimateForRoute() {
        return computeEstimateScore(start);
    }

    Optional<Path> routeIfCloser(int score) {
        return score > getEstimateForRoute() ? route() : Optional.empty();
    }

    Optional<Path> route() {

        Optional<Path> winningPath;
        do {
            Path lowestScoringPath = getLowestScoringPath();
            openPaths.remove(lowestScoringPath);
            closedPaths.add(lowestScoringPath);
            for (var path : getNeighbours(lowestScoringPath)) {
                int currentScore = lowestScoringPath.getScore();
                int score = path.getScore();
                if (score < currentScore && hasPathWithEndCoordinates(closedPaths, path.getCurrentPosition())) {
                    closedPaths.removeIf(otherPath -> otherPath.getCurrentPosition().equals(path.getCurrentPosition()));
                    closedPaths.add(path);
                } else if (score > currentScore && openPaths.contains(path)) {
                    openPaths.removeIf(otherPath -> otherPath.getCurrentPosition().equals(path.getCurrentPosition()));
                    openPaths.add(path);
                } else if (!hasPathWithEndCoordinates(openPaths, path.getCurrentPosition()) && !hasPathWithEndCoordinates(closedPaths, path.getCurrentPosition())) {
                    openPaths.add(path);
                }
            }
            winningPath = getWinningPath();
        } while (winningPath.isEmpty() && !openPaths.isEmpty());

        return winningPath;
    }

    private Set<Path> getNeighbours(Path current) {

        Coordinate currentCoordinate = current.getCurrentPosition();
        return Set.of(
                        currentCoordinate.translateX(1),
                        currentCoordinate.translateX(-1),
                        currentCoordinate.translateY(1),
                        currentCoordinate.translateY(-1))
                .stream()
                .filter(to -> isValidMove(current, currentCoordinate, to))
                .map(coordinate -> current.addStep(coordinate, this.computeEstimateScore(coordinate)))
                .collect(Collectors.toSet());
    }

    private Path getLowestScoringPath() {

        return openPaths.stream()
                .sorted(Comparator.comparing(path -> path.getScore() + path.getEstimatedScore()))
                .findFirst()
                .orElseThrow();
    }

    private boolean isValidMove(Path path, Coordinate from, Coordinate to) {

        return grid.hasPosition(to)
                && grid.get(from) + 1 >= grid.get(to)
                && !path.contains(to);
    }

    private double computeEstimateScore(Coordinate currentPosition) {

        var xDiff = currentPosition.x() - destination.x();
        var yDiff = currentPosition.y() - destination.y();

        return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
    }

    private Optional<Path> getWinningPath() {

        return openPaths.stream()
                .filter(path -> path.getCurrentPosition().equals(destination))
                .min(Comparator.comparing(path -> path.getScore()));
    }

    private boolean hasPathWithEndCoordinates(Set<Path> paths, Coordinate coordinate) {
        return paths.stream()
                .anyMatch(path -> path.getCurrentPosition().equals(coordinate));
    }

}
