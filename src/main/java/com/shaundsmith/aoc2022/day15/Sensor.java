package com.shaundsmith.aoc2022.day15;

import com.shaundsmith.aoc2022.Coordinate;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
class Sensor {

    private final Coordinate position;
    private final Coordinate closestBeacon;
    private final int beaconDistance;

    Sensor(Coordinate position, Coordinate closestBeacon) {
        this.position = position;
        this.closestBeacon = closestBeacon;
        this.beaconDistance = position.manhattanDistanceTo(closestBeacon);
    }

    Set<Coordinate> getEdge() {

        int edgeDistance = beaconDistance + 1;
        Coordinate topVertex = position.translateY(edgeDistance);
        Coordinate bottomVertex = position.translateY(-edgeDistance);
        Coordinate leftVertex = position.translateX(-edgeDistance);
        Coordinate rightVertex = position.translateX(edgeDistance);

        Set<Coordinate> edgeCoordinates = new HashSet<>();
        edgeCoordinates.addAll(interpolate(topVertex, leftVertex));
        edgeCoordinates.addAll(interpolate(leftVertex, bottomVertex));
        edgeCoordinates.addAll(interpolate(bottomVertex, rightVertex));
        edgeCoordinates.addAll(interpolate(rightVertex, topVertex));

        return edgeCoordinates;
    }

    private Set<Coordinate> interpolate(Coordinate start, Coordinate finish) {

        Set<Coordinate> line  = new HashSet<>();
        int distanceX = finish.x() - start.x();
        int distanceY = finish.y() - start.y();
        int stepX = (int) Math.signum(distanceX);
        int stepY = (int) Math.signum(distanceY);

        int interpolationDistance = stepX == 0 ? distanceY : distanceX;

        line.add(start);
        for (int i = 0; i < Math.abs(interpolationDistance); i++) {
            line.add(start.translateXY(stepX * i, stepY * i));
        }
        line.add(finish);

        return line;
    }

}
