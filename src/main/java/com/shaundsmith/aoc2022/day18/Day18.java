package com.shaundsmith.aoc2022.day18;

import com.shaundsmith.aoc2022.Coordinate3d;
import com.shaundsmith.aoc2022.FileReader;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

class Day18 {

    public static void main(String[] args) {

        List<Coordinate3d> droplets = FileReader.readFile("day18/input.txt")
                .stream()
                .map(Coordinate3d::fromString)
                .toList();

        int nonConnectedEdges = 6 * droplets.size();
        for (Coordinate3d currentDroplet : droplets) {

            long connectedEdges = currentDroplet.getAdjacentPositions()
                    .stream()
                    .filter(droplets::contains)
                    .count();
            nonConnectedEdges -= connectedEdges;
        }

        System.out.println("Non-connected edges: " + nonConnectedEdges);


        var airPockets = findAirPockets(droplets);
        int nonConnectedEdgesAndPockets = 6 * droplets.size();
        for (Coordinate3d currentDroplet : droplets) {

            long connectedEdges = currentDroplet.getAdjacentPositions()
                    .stream()
                    .filter(edge -> droplets.contains(edge) || airPockets.contains(edge))
                    .count();
            nonConnectedEdgesAndPockets -= connectedEdges;
        }
        System.out.println("Non-connected edges (and pockets): " + nonConnectedEdgesAndPockets);
    }

    static Set<Coordinate3d> findAirPockets(List<Coordinate3d> droplets) {

        Set<Coordinate3d> airPockets = droplets
                .stream()
                .flatMap(d -> d.getAdjacentPositions().stream())
                .collect(Collectors.toSet());

        Coordinate3d smallCorner = new Coordinate3d(
                droplets.stream().mapToInt(Coordinate3d::x).min().orElse(0) - 1,
                droplets.stream().mapToInt(Coordinate3d::y).min().orElse(0) - 1,
                droplets.stream().mapToInt(Coordinate3d::z).min().orElse(0) - 1);
        Coordinate3d largeCorner = new Coordinate3d(
                droplets.stream().mapToInt(Coordinate3d::x).max().orElse(0) + 1,
                droplets.stream().mapToInt(Coordinate3d::y).max().orElse(0) + 1,
                droplets.stream().mapToInt(Coordinate3d::z).max().orElse(0) + 1);

        Queue<Coordinate3d> openPaths = new LinkedList<>();
        openPaths.offer(smallCorner);

        Set<Coordinate3d> foundAirPoints = new HashSet<>();

        do {
            Coordinate3d point = openPaths.poll();
            Set<Coordinate3d> newAirPoints = point.getAdjacentPositions()
                    .stream()
                    .filter(p -> isAir(p, droplets, smallCorner, largeCorner))
                    .filter(p -> !foundAirPoints.contains(p))
                    .collect(Collectors.toSet());
            openPaths.addAll(newAirPoints);
            foundAirPoints.addAll(newAirPoints);
        } while (!openPaths.isEmpty());

        airPockets.removeAll(foundAirPoints);
        return airPockets;
    }

    private static boolean isAir(Coordinate3d point, List<Coordinate3d> droplets, Coordinate3d smallCorner, Coordinate3d largeCorner) {

        boolean isOutsideSmallBoundary = point.x() < smallCorner.x() || point.y() < smallCorner.y() || point.z() < smallCorner.z();
        boolean isOutsideLargeBoundary = point.x() > largeCorner.x() || point.y() > largeCorner.y() || point.z() > largeCorner.z();

        return !isOutsideLargeBoundary && !isOutsideSmallBoundary && !droplets.contains(point);
    }

}
