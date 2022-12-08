package com.shaundsmith.aoc2022.day8;

import com.shaundsmith.aoc2022.FileReader;

import java.util.Comparator;
import java.util.List;

class Day8 {

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day8/input.txt");

        int[][] grid = new int[lines.get(0).length()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                grid[j][i] = Integer.valueOf(lines.get(j).charAt(i) + "");
            }
        }

        TreeGrid trees = new TreeGrid(grid);

        // Part 1
        var visibleTrees = trees.getVisibleTrees();
        System.out.println("Found %d trees visible".formatted(visibleTrees.size()));

        // Part 2
        var bestScoringTree = trees.getTreeScores()
                .stream()
                .max(Comparator.comparing(ScenicTreeScore::score))
                .orElseThrow();
        System.out.println("Found best scoring tree with score %d at %s"
                .formatted(bestScoringTree.score(), bestScoringTree.location()));
    }

}
