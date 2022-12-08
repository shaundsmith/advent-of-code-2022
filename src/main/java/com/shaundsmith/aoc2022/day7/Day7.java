package com.shaundsmith.aoc2022.day7;

import com.shaundsmith.aoc2022.FileReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day7 {

    private static final Pattern COMMAND_REGEX = Pattern.compile("\\$ cd (.+)");
    private static final Pattern DIR_REGEX = Pattern.compile("dir (.+)");
    private static final Pattern FILE_REGEX = Pattern.compile("(\\d+) (.+)");

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day7/input.txt");
        Directory rootDirectory = new Directory(null, "/", new ArrayList<>());
        Directory currentDirectory = rootDirectory;
        for (String line : lines) {

            // Navigation
            Matcher cdCommandMatcher = COMMAND_REGEX.matcher(line);
            if (cdCommandMatcher.find()) {
                String target = cdCommandMatcher.group(1);
                if ("/".equals(target)) {
                    currentDirectory = rootDirectory;
                } else if ("..".equals(target)) {
                    currentDirectory = currentDirectory.parent();
                } else {
                    currentDirectory = currentDirectory.getDirectory(target);
                }
                continue;
            }

            // Directories
            Matcher dirMatcher = DIR_REGEX.matcher(line);
            if (dirMatcher.find()) {
                String dir = dirMatcher.group(1);
                if (!currentDirectory.hasItem(dir)) {
                    currentDirectory.addItem(new Directory(currentDirectory, dir, new ArrayList<>()));
                } else {
                    System.out.printf("%s already contains %s%n", currentDirectory.name(), dir);
                }
                continue;
            }

            // Files
            Matcher fileMatcher = FILE_REGEX.matcher(line);
            if (fileMatcher.find()) {
                String size = fileMatcher.group(1);
                String file = fileMatcher.group(2);
                if (!currentDirectory.hasItem(file)) {
                    currentDirectory.addItem(new File(file, Long.parseLong(size)));
                } else {
                    System.out.printf("%s already contains %s%n", currentDirectory.name(), file);
                }
            }

        }

        System.out.println(rootDirectory.format(0));

        // Part 1
        System.out.println("Sum of directory sizes < 100000: " + rootDirectory.flattenDirectories()
                .stream()
                .mapToLong(Directory::size)
                .filter(size -> size <= 100000)
                .sum());

        // Part 2
        long totalSize = 70000000L;
        long requiredSpace = 30000000L;
        long usedSpace = totalSize - rootDirectory.size();
        long spaceToFree = requiredSpace - usedSpace;

        Directory dirToDelete = rootDirectory.flattenDirectories()
                .stream()
                .filter(dir -> dir.size() >= spaceToFree)
                .sorted(Comparator.comparing(Directory::size))
                .findFirst()
                .orElseThrow();

        System.out.printf("Delete directory %s with size %d to free up %d space", dirToDelete.name(), dirToDelete.size(),spaceToFree);
    }


}
