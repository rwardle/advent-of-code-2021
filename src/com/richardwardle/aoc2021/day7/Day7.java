package com.richardwardle.aoc2021.day7;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Day7 {

    public static void main(String[] args) {
        new Day7().execute();
    }

    private static String readInput() {
        try (var scanner = InputUtils.day7Data()) {
            if (scanner.hasNext()) {
                return scanner.nextLine();
            }
        }

        throw new RuntimeException("Invalid data");
    }

    private static int calculateFuel(int crabPosition, int targetPosition) {
        int distance = Math.abs(crabPosition - targetPosition);
        return IntStream.rangeClosed(1, distance).sum();
    }

    private void execute() {
        var crabPositions = Arrays.stream(readInput().split(","))
                .map(Integer::valueOf)
                .toList();
        int minPosition = crabPositions.stream().min(Comparator.naturalOrder()).orElseThrow();
        int maxPosition = crabPositions.stream().max(Comparator.naturalOrder()).orElseThrow();

        var fuelByPosition = new HashMap<Integer, Long>();
        for (var i = minPosition; i < maxPosition + 1; i++) {
            var targetPosition = i;
            var fuel = crabPositions
                    .stream()
                    .map(c -> calculateFuel(c, targetPosition))
                    .mapToLong(s -> s)
                    .sum();
            fuelByPosition.put(targetPosition, fuel);
        }

        var entry = fuelByPosition.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.printf("Position: %d, fuel: %d", entry.getKey(), entry.getValue());
    }
}
