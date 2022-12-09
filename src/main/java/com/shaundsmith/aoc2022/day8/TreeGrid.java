package com.shaundsmith.aoc2022.day8;

import com.shaundsmith.aoc2022.Coordinate;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class TreeGrid {

    private final int[][] treeHeights;

    // Part 1

    Set<Coordinate> getVisibleTrees() {
        Set<Coordinate> visibleTrees = new HashSet<>();
        for (int i = 0; i < treeHeights.length; i++) {
            for (int j = 0; j < treeHeights[i].length; j++) {
                if (isTreeVisible(i, j)) {
                    visibleTrees.add(new Coordinate(i, j));
                }
            }
        }
        return visibleTrees;
    }


    private boolean isTreeVisible(int x, int y) {

        return isTreeOnEdge(x, y)
                || isTreeVisibleFromBottom(x, y)
                || isTreeVisibleFromTop(x, y)
                || isTreeVisibleFromLeft(x, y)
                || isTreeVisibleFromRight(x, y);
    }

    private boolean isTreeOnEdge(int x, int y) {
        return x == 0 || y == 0 ||
                x == treeHeights.length || y == treeHeights[x].length;
    }

    private boolean isTreeVisibleFromTop(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        for (int i = y - 1; i >= 0; i--) {
            if (treeHeights[x][i] >= targetTreeHeight) {
                return false;
            }
        }
        return true;
    }

    private boolean isTreeVisibleFromBottom(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        for (int i = y + 1; i < treeHeights[x].length; i++) {
            if (treeHeights[x][i] >= targetTreeHeight) {
                return false;
            }
        }
        return true;
    }

    private boolean isTreeVisibleFromLeft(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        for (int i = x - 1; i >= 0; i--) {
            if (treeHeights[i][y] >= targetTreeHeight) {
                return false;
            }
        }
        return true;
    }

    private boolean isTreeVisibleFromRight(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        for (int i = x + 1; i < treeHeights.length; i++) {
            if (treeHeights[i][y] >= targetTreeHeight) {
                return false;
            }
        }
        return true;
    }

    // Part 2
    Set<ScenicTreeScore> getTreeScores() {
        Set<ScenicTreeScore> scenicTreeScores = new HashSet<>();

        for (int i = 0; i < treeHeights.length; i++) {
            for (int j = 0; j < treeHeights[i].length; j++) {
                if (isTreeVisible(i, j)) {
                    scenicTreeScores.add(getTreeScore(i, j));
                }
            }
        }
        return scenicTreeScores;
    }

    private ScenicTreeScore getTreeScore(int x, int y) {
        int score = getTopTreeScore(x, y) * getBottomTreeScore(x, y) * getLeftTreeScore(x, y) * getRightTreeScore(x, y);
        return new ScenicTreeScore(new Coordinate(x, y), score);
    }

    private int getTopTreeScore(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        int distance = 0;
        for (int i = y; i >= 0; i--) {
            if (i != y && treeHeights[x][i] >= targetTreeHeight) {
                return distance;
            }
            distance++;
        }
        return distance - 1;
    }

    private int getBottomTreeScore(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        int distance = 0;
        for (int i = y; i < treeHeights[x].length; i++) {
            if (i != y && treeHeights[x][i] >= targetTreeHeight) {
                return distance;
            }
            distance++;
        }
        return distance - 1;
    }

    private int getLeftTreeScore(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        int distance = 0;
        for (int i = x; i >= 0; i--) {
            if (i != x && treeHeights[i][y] >= targetTreeHeight) {
                return distance;
            }
            distance++;
        }
        return distance - 1;
    }

    private int getRightTreeScore(int x, int y) {
        int targetTreeHeight = treeHeights[x][y];
        int distance = 0;
        for (int i = x; i < treeHeights.length; i++) {
            if (i != x && treeHeights[i][y] >= targetTreeHeight) {
                return distance;
            }
            distance++;
        }
        return distance - 1;
    }



}
