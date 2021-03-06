package com.richardwardle.aoc2021.day11;

import com.richardwardle.aoc2021.Point;
import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) {
        new Day11().execute();
    }

    private static List<String> readInput() {
        var lines = new ArrayList<String>();
        try (Scanner scanner = InputUtils.day11Data()) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    private static Cave parseInput(List<String> lines) {
        var cave = new Cave();
        for (int y = 0; y < lines.size(); y++) {
            String[] energyLevels = lines.get(y).split("");
            for (int x = 0; x < energyLevels.length; x++) {
                cave.addOctopus(new Octopus(new Point(x, y), Integer.parseInt(energyLevels[x])));
            }
        }
        return cave;
    }

    private void execute() {
        var lines = readInput();
        var cave = parseInput(lines);

        var totalFlashes = 0;
        var allFlashed = false;
        for (int i = 1; !allFlashed; i++) {
            int flashCount = cave.step();
            allFlashed = flashCount == 100;
            totalFlashes += flashCount;
            System.out.printf("Total flashes after step %d: %d\n", i, totalFlashes);
        }
    }
}
