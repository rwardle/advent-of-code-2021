package com.richardwardle.aoc2021.day2;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.Scanner;

public class Day2Part2 {

    private enum Direction {
        UP, DOWN, FORWARD
    }

    private int horizontal = 0;
    private int depth = 0;
    private int aim = 0;

    private void execute() {
        try (Scanner scanner = InputUtils.day2Data()) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");

                Direction direction = Direction.valueOf(tokens[0].toUpperCase());
                int units = Integer.parseInt(tokens[1]);

                switch (direction) {
                    case FORWARD -> {
                        horizontal = horizontal + units;
                        depth = depth + aim * units;
                    }
                    case UP -> aim = aim - units;
                    case DOWN -> aim = aim + units;
                }
            }
        }

        System.out.println("Position: " + horizontal * depth);
    }

    public static void main(String[] args) {
        new Day2Part2().execute();
    }
}
