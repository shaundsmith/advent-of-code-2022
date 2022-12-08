package com.shaundsmith.aoc2022.day7;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Directory(Directory parent, String name, List<FileSystemItem> contents) implements FileSystemItem {

    void addItem(FileSystemItem fileSystemItem) {
        contents.add(fileSystemItem);
    }

    boolean hasItem(String name) {
        return contents.stream()
                .anyMatch(item -> name.equals(item.name()));
    }

    Directory getDirectory(String name) {

        return (Directory) contents.stream()
                .filter(item -> name.equals(item.name()) && item instanceof Directory)
                .findFirst()
                .orElseThrow();
    }

    List<Directory> flattenDirectories() {
        return contents.stream()
                .filter(item -> item instanceof Directory)
                .flatMap(item -> Stream.concat(Stream.of((Directory) item), ((Directory) item).flattenDirectories().stream()))
                .toList();
    }

    @Override
    public long size() {
        return contents.stream()
                .mapToLong(FileSystemItem::size)
                .sum();
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        String thisString = toString();
        sb.append(StringUtils.leftPad(thisString, thisString.length() + indent, " "));
        contents.forEach(item -> {
            sb.append(item.format(indent + 1));
        });
        return sb.toString();
    }

    @Override
    public String toString() {
        return "- %s (dir)%n".formatted(name());
    }

}
