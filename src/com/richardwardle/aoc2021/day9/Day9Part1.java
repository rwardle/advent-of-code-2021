package com.richardwardle.aoc2021.day9;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.richardwardle.aoc2021.day9.HeightMap.heightMapFrom;

public class Day9Part1 {
    public static void main(String[] args) {
        new Day9Part1().execute();
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
        var lowPoints = heightMap.getLowPoints();
        var riskLevel = lowPoints
                .stream()
                .map(HeightMap.Height::value)
                .mapToLong(v -> v + 1)
                .sum();
        System.out.println(riskLevel);
    }
}
