package com.richardwardle.aoc2021.day3;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3Part1 {

    private static class Report {

        private final List<Line> lines = new ArrayList<>();
        private Integer lineLength;

        void addLine(Line line) {
            if (lineLength == null) {
                lineLength = line.length();
            }
            lines.add(line);
        }

        int calculatePowerConsumption() {
            int[] bitSums = new int[lineLength];
            for (Line line : lines) {
                for (int i = 0; i < lineLength; i++) {
                    int bit = line.bitAtPosition(i);
                    bitSums[i] += bit;
                }
            }

            int threshold = lines.size() / 2;
            StringBuilder gammaBuilder = new StringBuilder(lineLength);
            StringBuilder epsilonBuilder = new StringBuilder(lineLength);
            for (int sum : bitSums) {
                if (sum > threshold) {
                    gammaBuilder.append("1");
                    epsilonBuilder.append("0");
                } else {
                    gammaBuilder.append("0");
                    epsilonBuilder.append("1");
                }
            }

            int gamma = Integer.parseUnsignedInt(gammaBuilder.toString(), 2);
            int epsilon = Integer.parseUnsignedInt(epsilonBuilder.toString(), 2);
            return gamma * epsilon;
        }
    }

    private record Line(String text) {

        int bitAtPosition(int position) {
            return Integer.parseInt(String.valueOf(text.charAt(position)));
        }

        int length() {
            return text.length();
        }
    }

    private void execute() {
        try (Scanner scanner = InputUtils.day3Data()) {
            Report report = new Report();
            while (scanner.hasNext()) {
                String text = scanner.nextLine();
                Line line = new Line(text);
                report.addLine(line);
            }

            System.out.println("Power consumption: " + report.calculatePowerConsumption());
        }
    }

    public static void main(String[] args) {
        new Day3Part1().execute();
    }
}
