package com.richardwardle.aoc2021.input;

import java.util.Objects;
import java.util.Scanner;

public interface InputUtils {

    private static Scanner fileScanner(String fileName) {
        return new Scanner(Objects.requireNonNull(InputUtils.class.getResourceAsStream(fileName)));
    }

    static Scanner day1Data() {
        return fileScanner("day1-data.txt");
    }

    static Scanner day1Part1Example() {
        return fileScanner("day1part1-example.txt");
    }

    static Scanner day1Part2Example() {
        return fileScanner("day1part2-example.txt");
    }
}
