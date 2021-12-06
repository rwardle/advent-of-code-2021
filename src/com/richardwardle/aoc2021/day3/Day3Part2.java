package com.richardwardle.aoc2021.day3;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class Day3Part2 {

    private enum MostCommonBit {
        ONE, ZERO, EQUAL
    }

    private static class BitSum {
        private final AtomicInteger oneSum = new AtomicInteger(0);
        private final AtomicInteger zeroSum = new AtomicInteger(0);

        private void recordOneBit() {
            oneSum.incrementAndGet();
        }

        private void recordZeroBit() {
            zeroSum.incrementAndGet();
        }

        MostCommonBit mostCommonBit() {
            if (oneSum.intValue() > zeroSum.intValue()) {
                return MostCommonBit.ONE;
            } else if (zeroSum.intValue() > oneSum.intValue()) {
                return MostCommonBit.ZERO;
            } else {
                return MostCommonBit.EQUAL;
            }
        }
    }

    private static class Report {

        private final List<Line> lines = new ArrayList<>();
        private Integer lineLength;

        void addLine(Line line) {
            if (lineLength == null) {
                lineLength = line.length();
            }
            lines.add(line);
        }

        int calculateLifeSupportRating() {
            String oxygenRating = findRating((lines, index) ->
                    switch (mostCommonBitAt(lines, index)) {
                        case ONE, EQUAL -> 1;
                        case ZERO -> 0;
                    });
            String co2Rating = findRating((lines, index) ->
                    switch (mostCommonBitAt(lines, index)) {
                        case ONE, EQUAL -> 0;
                        case ZERO -> 1;
                    });
            return Integer.parseUnsignedInt(oxygenRating, 2) * Integer.parseUnsignedInt(co2Rating, 2);
        }

        private String findRating(BiFunction<List<Line>, Integer, Integer> bitCriteria) {
            List<Line> filtered = new ArrayList<>(this.lines);
            for (int i = 0; i < lineLength && filtered.size() != 1; i++) {
                final int index = i;
                int bitToMatch = bitCriteria.apply(filtered, index);
                filtered = filtered.stream().filter(l -> l.bitAtPosition(index) == bitToMatch).toList();
            }

            return filtered.get(0).text;
        }

        private MostCommonBit mostCommonBitAt(List<Line> filtered, int index) {
            BitSum bitSum = new BitSum();
            for (Line line : filtered) {
                int bit = line.bitAtPosition(index);
                if (bit == 1) {
                    bitSum.recordOneBit();
                } else {
                    bitSum.recordZeroBit();
                }
            }
            return bitSum.mostCommonBit();
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

            System.out.println("Life support rating: " + report.calculateLifeSupportRating());
        }
    }

    public static void main(String[] args) {
        new Day3Part2().execute();
    }
}
