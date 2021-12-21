package com.richardwardle.aoc2021.day6;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day6Part2 {

    public static void main(String[] args) {
        new Day6Part2().execute();
    }

    private void execute() {
        var fishData = new HashMap<>(getFishData());
        Function<Integer, Long> getFishCount = index -> Objects.requireNonNullElse(fishData.get(index), 0L);

        for (int i = 0; i < 256; i++) {
            var newFishCount = getFishCount.apply(0);
            for (int j = 0; j < 8; j++) {
                fishData.put(j, getFishCount.apply((j + 1)));
            }
            fishData.put(6, getFishCount.apply(6) + newFishCount);
            fishData.put(8, newFishCount);
        }

        System.out.println("Number of fish: " + fishData.values().stream().mapToLong(count -> count).sum());
    }

    private Map<Integer, Long> getFishData() {
        return Arrays.stream(readInput().split(","))
                .collect(Collectors.groupingBy(Integer::valueOf, Collectors.counting()));
    }

    private String readInput() {
        try (Scanner scanner = InputUtils.day6Data()) {
            if (scanner.hasNext()) {
                return scanner.nextLine();
            }
        }

        throw new RuntimeException("Invalid data");
    }
}
