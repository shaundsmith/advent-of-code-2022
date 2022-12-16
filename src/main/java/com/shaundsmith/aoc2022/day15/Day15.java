package com.shaundsmith.aoc2022.day15;

import com.shaundsmith.aoc2022.Coordinate;
import com.shaundsmith.aoc2022.FileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day15 {

    private static final Pattern SENSOR_DEFINITION_PATTERN = Pattern.compile(
            "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    public static void main(String[] args) {

        List<Sensor> sensors = FileReader.readFile("day15/input.txt")
                .stream()
                .map(Day15::parseSensor)
                .toList();
        final int yToCheck = 2000000;

        int leftMostPoint = sensors.stream()
                .mapToInt(sensor -> sensor.getPosition().x() - sensor.getBeaconDistance())
                .min()
                .orElseThrow();

        int rightMostPoint = sensors.stream()
                .mapToInt(sensor -> sensor.getPosition().x() + sensor.getBeaconDistance())
                .max()
                .orElseThrow();

        List<Coordinate> beaconLocations = sensors.stream()
                .map(Sensor::getClosestBeacon)
                .toList();

        int placesBeaconsCantBe = 0;
        for (int i = leftMostPoint; i <= rightMostPoint; i++) {
            for (Sensor sensor : sensors) {
                var coordinateToCheck = new Coordinate(i, yToCheck);
                if (beaconLocations.contains(coordinateToCheck)) {
                    break;
                } else if (sensor.getPosition().manhattanDistanceTo(coordinateToCheck) <= sensor.getBeaconDistance()) {
                    placesBeaconsCantBe++;
                    break;
                }
            }
        }
        System.out.printf("Beacons can't be in %d places%n", placesBeaconsCantBe);

        findBeacon(sensors);
    }

    static Sensor parseSensor(String line) {

        var matcher = SENSOR_DEFINITION_PATTERN.matcher(line);

        if (matcher.find()) {
            Coordinate sensorPosition = Coordinate.fromString(matcher.group(1) + "," + matcher.group(2));
            Coordinate closestBeacon = Coordinate.fromString(matcher.group(3) + "," + matcher.group(4));

            return new Sensor(sensorPosition, closestBeacon);
        } else {
            throw new RuntimeException();
        }
    }

    static void findBeacon(List<Sensor> sensors) {

        final int boundarySize = 4000000;
        Set<Coordinate> coordinates = new HashSet<>();
        Set<Coordinate> edges = sensors.stream()
                .flatMap(sensor -> sensor.getEdge().stream())
                .filter(coordinate -> coordinate.x() >= 0 && coordinate.x() <= boundarySize && coordinate.y() >= 0 && coordinate.y() <= boundarySize)
                .collect(Collectors.toSet());
        System.out.println("Found %d edge coordinates".formatted(edges.size()));

        for (Coordinate coordinate : edges) {
            if (sensors.stream().noneMatch(sensor ->sensor.getPosition().manhattanDistanceTo(coordinate) <= sensor.getBeaconDistance())) {
                coordinates.add(coordinate);
            }
        }

        System.out.println(coordinates);
        var beacon = coordinates.stream().findFirst().orElseThrow();
        System.out.printf("Value is %d",
                (beacon.x() * 4000000L) + beacon.y());
    }

}
