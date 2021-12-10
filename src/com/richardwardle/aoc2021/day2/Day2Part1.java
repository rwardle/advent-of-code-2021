package com.richardwardle.aoc2021.day2;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.Scanner;

public class Day2Part1 {

    private int horizontal = 0;
    private int depth = 0;

    public static void main(String[] args) {
        new Day2Part1().execute();
    }

    private void execute() {
        try (Scanner scanner = InputUtils.day2Data()) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");

                Direction direction = Direction.valueOf(tokens[0].toUpperCase());
                int distance = Integer.parseInt(tokens[1]);

                switch (direction) {
                    case FORWARD -> horizontal = horizontal + distance;
                    case UP -> depth = depth - distance;
                    case DOWN -> depth = depth + distance;
                }
            }
        }

        System.out.println("Position: " + horizontal * depth);
    }

    private enum Direction {
        UP, DOWN, FORWARD
    }
}
