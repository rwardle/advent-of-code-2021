package com.richardwardle.aoc2021.day9;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.richardwardle.aoc2021.day9.HeightMap.heightMapFrom;

public class Day9Part2 {
    public static void main(String[] args) {
        new Day9Part2().execute();
    }

    private static List<String> readInput() {
        var lines = new ArrayList<String>();
        try (Scanner scanner = InputUtils.day9Data()) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    private void execute() {
        var lines = readInput();
        var heightMap = heightMapFrom(lines);
        var result = heightMap.getBasins()
                .stream()
                .map(Set::size)
                .sorted((o1, o2) -> o2 - o1)
                .limit(3)
                .reduce((x, y) -> x * y)
                .orElseThrow();
        System.out.println(result);
    }
}
