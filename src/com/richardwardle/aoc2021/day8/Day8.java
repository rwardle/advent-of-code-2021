package com.richardwardle.aoc2021.day8;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) {
        new Day8().execute();
    }

    private static List<SignalEntry> readInput() {
        var entries = new ArrayList<SignalEntry>();

        try (Scanner scanner = InputUtils.day8Data()) {
            while (scanner.hasNext()) {
                entries.add(parseLine(scanner.nextLine()));
            }
        }

        return entries;
    }

    private static SignalEntry parseLine(String line) {
        var tokens = line.split("\s\\|\s");
        var signalPatterns = Arrays.stream(tokens[0].split("\s")).toList();
        var outPutValues = Arrays.stream(tokens[1].split("\s")).toList();
        return new SignalEntry(signalPatterns, outPutValues);
    }

    private void execute() {
        Map<Digit, Long> digitCount = readInput()
                .stream()
                .flatMap(signalEntry -> signalEntry.outputValues().stream())
                .map(value -> Digit.lookup(value.length()))
                .flatMap(Optional::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(digitCount.values().stream().mapToLong(count -> count).sum());
    }

    private enum Digit {
        ONE(2), FOUR(4), SEVEN(3), EIGHT(7);

        private static final Map<Integer, Digit> digitsBySegmentCount = new HashMap<>();

        static {
            for (Digit digit : Digit.values()) {
                digitsBySegmentCount.put(digit.segmentCount, digit);
            }
        }

        private final int segmentCount;

        Digit(int segmentCount) {
            this.segmentCount = segmentCount;
        }

        static Optional<Digit> lookup(int segmentCount) {
            return Optional.ofNullable(digitsBySegmentCount.get(segmentCount));
        }
    }

    private record SignalEntry(List<String> signalPatterns, List<String> outputValues) {
    }
}
