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

    @SuppressWarnings("unused")
    static Scanner day1Part1Example() {
        return fileScanner("day1part1-example.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day1Part2Example() {
        return fileScanner("day1part2-example.txt");
    }

    static Scanner day2Data() {
        return fileScanner("day2-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day2Example() {
        return fileScanner("day2-example.txt");
    }

    static Scanner day3Data() {
        return fileScanner("day3-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day3Example() {
        return fileScanner("day3-example.txt");
    }

    static Scanner day4Data() {
        return fileScanner("day4-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day4Example() {
        return fileScanner("day4-example.txt");
    }

    static Scanner day5Data() {
        return fileScanner("day5-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day5Example() {
        return fileScanner("day5-example.txt");
    }

    static Scanner day6Data() {
        return fileScanner("day6-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day6Example() {
        return fileScanner("day6-example.txt");
    }

    static Scanner day7Data() {
        return fileScanner("day7-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day7Example() {
        return fileScanner("day7-example.txt");
    }

    static Scanner day8Data() {
        return fileScanner("day8-data.txt");
    }

    @SuppressWarnings("unused")
    static Scanner day8Example() {
        return fileScanner("day8-example.txt");
    }
}
