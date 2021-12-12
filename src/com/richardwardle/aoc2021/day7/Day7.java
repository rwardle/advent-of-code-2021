package com.richardwardle.aoc2021.day7;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;

public class Day7 {

    public static void main(String[] args) {
        new Day7().execute();
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
                    .map(c -> Math.abs(c - targetPosition))
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

    private String readInput() {
        try (Scanner scanner = InputUtils.day7Data()) {
            if (scanner.hasNext()) {
                return scanner.nextLine();
            }
        }

        throw new RuntimeException("Invalid data");
    }
}
