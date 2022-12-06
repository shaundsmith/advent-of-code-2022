package com.shaundsmith.aoc2022;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {

    @SneakyThrows
    public static List<String> readFile(String location) {

        return Files.readAllLines(Path.of("src/main/resources/" + location));
    }

}
