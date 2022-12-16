package com.shaundsmith.aoc2022.day14;

import com.shaundsmith.aoc2022.Coordinate;
import com.shaundsmith.aoc2022.Grid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class Cave {

    private final Grid<GridKey> grid;
    private final Coordinate sandDropper;

    void fillCave() {

        int sandCounterBeforeFloor = 0;
        int sandCounterTotal = 0;
        while (canDropSand()) {
            if (!sandOnFloor()) {
                sandCounterBeforeFloor++;
            }
            sandCounterTotal++;
            dropSand();
        }

        System.out.printf("Placed %d sand before hitting the floor%n", sandCounterBeforeFloor - 1);
        System.out.printf("Placed %d sand in total%n", sandCounterTotal);
    }

    private boolean sandOnFloor() {

        return grid.streamRow(grid.getHeight() - 2)
                .anyMatch(key -> key == GridKey.SAND);
    }

    private boolean canDropSand() {

        return grid.get(sandDropper) != GridKey.SAND;
    }

    private void dropSand() {

        Coordinate currentPosition;
        Coordinate nextPosition = sandDropper;
        do {
            currentPosition = nextPosition;
            nextPosition = getSandNextPosition(currentPosition);
        } while (nextPosition != currentPosition && grid.hasPosition(nextPosition));

        if (grid.hasPosition(nextPosition)) {
            grid.set(nextPosition, GridKey.SAND);
        }
    }

    private Coordinate getSandNextPosition(Coordinate currentPosition) {
        Coordinate nextPosition = currentPosition;

        Coordinate down = currentPosition.translateY(1);
        Coordinate downLeft = currentPosition.translateXY(-1, 1);
        Coordinate downRight = currentPosition.translateXY(1, 1);

        if (grid.hasPosition(down) && grid.get(down).isEmpty()) {
            nextPosition = down;
        } else if (grid.hasPosition(downLeft) && grid.get(downLeft).isEmpty()) {
            nextPosition = downLeft;
        } else if (grid.hasPosition(downRight) && grid.get(downRight).isEmpty()) {
            nextPosition = downRight;
        }

        return nextPosition;
    }

}
