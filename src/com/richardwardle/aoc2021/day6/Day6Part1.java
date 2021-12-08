package com.richardwardle.aoc2021.day6;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day6Part1 {

    private static class Fish {

        private final AtomicInteger timer;

        Fish(int value) {
            timer = new AtomicInteger(value);
        }

        boolean cycleTimer() {
            if (timer.compareAndExchange(0, 6) == 0) {
                return true;
            } else {
                timer.decrementAndGet();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        new Day6Part1().execute();
    }

    private void execute() {
        var fishData = new ArrayList<>(getFishData());

        for (int i = 0; i < 80; i++) {
            List<Fish> newFish = new ArrayList<>();
            for (Fish fish : fishData) {
                if (fish.cycleTimer()) {
                    newFish.add(new Fish(8));
                }
            }
            fishData.addAll(newFish);
        }

        System.out.println("Number of fish: " + fishData.size());
    }

    private List<Fish> getFishData() {
        return Arrays.stream(readInput().split(","))
                .map(t -> new Fish(Integer.parseInt(t)))
                .toList();
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
