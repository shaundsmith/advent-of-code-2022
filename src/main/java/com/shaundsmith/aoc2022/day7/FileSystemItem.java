package com.shaundsmith.aoc2022.day7;

interface FileSystemItem {
    String name();
    long size();

    String format(int indent);
}
