package com.richardwardle.aoc2021.day9;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) {
        new Day9().execute();
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
        var heightMap = buildHeightMap(lines);
        var lowPoints = heightMap.getLowPoints();
        var riskLevel = lowPoints
                .stream()
                .map(HeightMap.Height::value)
                .mapToLong(v -> v + 1)
                .sum();
        System.out.println(riskLevel);
    }

    private HeightMap buildHeightMap(List<String> lines) {
        var heightMap = new HeightMap();
        for (int y = 0; y < lines.size(); y++) {
            var heights = lines.get(y).split("");
            for (int x = 0; x < heights.length; x++) {
                var height = new HeightMap.Height(Integer.parseInt(heights[x]), new HeightMap.Position(x, y));
                heightMap.addHeight(height);
            }
        }
        return heightMap;
    }
}
